package com.example.shop_product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.utils.JSONUtils;
import com.example.shop_product.entity.Prize;
import com.example.shop_product.entity.dto.PrizeDto;
import com.example.shop_product.mapper.PrizeMapper;
import com.example.shop_product.service.PrizeService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <p>
 * 奖品表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-29
 */
@Service
public class PrizeServiceImpl extends CrudServiceImpl<PrizeMapper, Prize> implements PrizeService {



    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedissonClient redissonClient;

    // 奖品池初始化Key + groupId
    private static final String PRIZE_POOL_KEY = "lottery:prize_pool:";
    // 奖品库存Key前缀
    private static final String STOCK_KEY_PREFIX = "lottery:stock:";
    // 分布式锁Key
    private static final String LOCK_KEY = "lottery:global_lock:";


    @Override
    public Prize savePrize(Prize prize) {
        save(prize);
        return prize;
    }

    @Override
    public void initPrizes(PrizeDto prizeDto) {
        List<Prize> prizes = dao.selectList(new QueryWrapper<Prize>().eq("group_id", prizeDto.getGroupId()));
        // 使用Hash结构存储奖品信息
        Map<String, String> prizeMap = prizes.stream().collect(Collectors.toMap(p -> p.getId().toString(), JSONUtils::toJSONString));
        redisTemplate.opsForHash().putAll(PRIZE_POOL_KEY + prizeDto.getGroupId(), prizeMap);
        // 初始化库存
        prizes.forEach(prize -> {
            String stockKey = STOCK_KEY_PREFIX + prizeDto.getGroupId() + ":" + prize.getId();
            redisTemplate.opsForValue().set(stockKey, prize.getStock());
        });
    }

    @Override
    public Prize drawPrizes(PrizeDto prizeDto) {
        // 1. 获取全局锁防止并发问题
        RLock lock = redissonClient.getLock(LOCK_KEY + prizeDto.getGroupId());
        try {
            // 等待时间0秒，锁过期时间3秒
            if (lock.tryLock(0, 3, SECONDS)) {
                // 2. 执行Lua脚本保证原子性
                String luaScript = getDrawLuaScript();
                RedisScript<Long> script = new DefaultRedisScript<>(luaScript, Long.class);
                // 3. 执行脚本
                Long prizeId = redisTemplate.execute(script, Collections.singletonList(PRIZE_POOL_KEY + prizeDto.getGroupId()), prizeDto.getUserId().toString(), prizeDto.getGroupId());
                if (prizeId > 0) {
                    // 4. 返回奖品信息
                    Prize data = getPrizeById(prizeDto.getGroupId(), prizeId);
                    save(data);
                    return data;
                }
                return null; // 未中奖或库存不足
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }
    
     /**
      * 获取抽奖Lua脚本
     */
    private String getDrawLuaScript() {
        return """
                   local prizePoolKey = KEYS[1]
                   local userId = ARGV[1]
                   local groupId = ARGV[2]
                -- 1. 获取所有奖品
                  local prizes = redis.call('HGETALL', prizePoolKey)
                  local availablePrizes = {}
                  local totalProbability = 0
                -- 2. 筛选有库存的奖品并计算总概率
                  for i = 1, #prizes, 2 do
                      local prizeId = prizes[i]
                      local prizeJson = prizes[i+1]
                      local stockKey = 'lottery:stock:' .. groupId .. prizeId
                      local stock = tonumber(redis.call('GET', stockKey) or 0)
                      if stock > 0 then
                          local prize = cjson.decode(prizeJson)
                          table.insert(availablePrizes, {
                              id = prizeId,
                              probability = tonumber(prize.probability)
                          })
                          totalProbability = totalProbability + tonumber(prize.probability)
                      end
                  end
                -- 3. 如果没有可用奖品
                  if #availablePrizes == 0 then
                      return -1
                  end
                -- 4. 随机选择奖品
                  math.randomseed(tonumber(redis.call('TIME')[1]) + tonumber(userId))
                  local randomValue = math.random() * totalProbability
                  local accumulatedProbability = 0
                  local selectedPrizeId = nil
                  for _, prize in ipairs(availablePrizes) do
                      accumulatedProbability = accumulatedProbability + prize.probability
                      if randomValue <= accumulatedProbability then
                          selectedPrizeId = prize.id
                          break
                      end
                  end
                -- 5. 扣减库存
                  if selectedPrizeId then
                      local stockKey = 'lottery:stock:' ..groupId .. selectedPrizeId
                      redis.call('DECR', stockKey)
                      return tonumber(selectedPrizeId)
                  end
                  return -6""";
    }


     /**
      * 根据ID获取奖品
     */
    private Prize getPrizeById(String groupId ,Long prizeId) {
        Object prizeJson = redisTemplate.opsForHash().get(PRIZE_POOL_KEY + groupId, prizeId.toString());
        return JSONUtils.fromJSONString((String) prizeJson, Prize.class);
    }
}
