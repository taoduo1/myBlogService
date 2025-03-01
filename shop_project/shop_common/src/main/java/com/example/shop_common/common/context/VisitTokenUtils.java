package com.example.shop_common.common.context;

import com.example.shop_common.common.constant.NormalConstant;
import com.example.shop_common.common.dto.CoreException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author duo.tao
 */
public class VisitTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(VisitTokenUtils.class);

    private static final int TOKEN_POOL_SIZE = 50;

    /**
     * 每个访问给予60秒有效时间
     */
    private static final int VALID_TIME = 60000;

    private static final Hashtable<String, Long> TOKENS = new Hashtable<>(TOKEN_POOL_SIZE);

    public static String gen(String token, String uri) {
        return token + NormalConstant.SPLIT + uri;
    }

    public static boolean check(String token) throws Exception {
        boolean flag = TOKENS.containsKey(token) && System.currentTimeMillis()- TOKENS.get(token) < VALID_TIME;
        if (!flag) {
            throw new Exception("无操作权限或者token已失效！");
        }
        return true;
    }

    public static void add(String token) {
        if (TOKENS.containsKey(token)) {
            gc();
            if (TOKENS.containsKey(token)){
                throw new CoreException("正在访问！");
            }
        }
        TOKENS.put(token, System.currentTimeMillis());
        int visitCount = TOKENS.size();
        logger.info("visiting-token:{}, 并发量:{}", token, visitCount);
        logger.info("并发中:{}", TOKENS);
        if (visitCount >= TOKEN_POOL_SIZE) {
            logger.info("token pool release", visitCount);
            gc();
        }
    }

    static void del(String token) {
        if(StringUtils.isNotEmpty(token)) {
            TOKENS.remove(token);
        }
    }

    private static void gc() {
        Iterator<Map.Entry<String, Long>> it = TOKENS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            boolean del = System.currentTimeMillis() - entry.getValue() > VALID_TIME;
            if (del) {
                it.remove();
            }
        }
    }
}
