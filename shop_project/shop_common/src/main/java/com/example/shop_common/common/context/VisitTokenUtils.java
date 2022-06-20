package com.example.shop_common.common.context;

import com.example.shop_common.common.constant.NormalConstant;
import com.example.shop_common.common.dto.CoreException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class VisitTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(VisitTokenUtils.class);

    private static final int tokenPoolSize = 50;
    private static final int VALID_TIME = 60000;// 每个访问给予60秒有效时间
    private static Hashtable<String, Long> tokens = new Hashtable<>(tokenPoolSize);

    public static String gen(String token, String uri) {
        return token + NormalConstant.SPLIT + uri;
    }

    public static boolean check(String token) throws Exception {
        boolean flag = tokens.containsKey(token) && new Date().getTime() - tokens.get(token) < VALID_TIME;
        if (!flag) {
            throw new Exception("无操作权限或者token已失效！");
        }
        return flag;
    }

    public static void add(String token) {
        if (tokens.containsKey(token)) {
            gc();
            if (tokens.containsKey(token)){
                throw new CoreException("正在访问！");
            }
        }
        tokens.put(token, new Date().getTime());
        int visitCount = tokens.size();
        logger.info("visiting-token:{}, 并发量:{}", token, visitCount);
        logger.info("并发中:{}", tokens);
        if (visitCount >= tokenPoolSize) {
            logger.info("token pool release", visitCount);
            gc();
        }
    }

    static void del(String token) {
        if(StringUtils.isNotEmpty(token)) {
            tokens.remove(token);
        }
    }

    private static void gc() {
        Iterator<Map.Entry<String, Long>> it = tokens.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            boolean del = new Date().getTime() - entry.getValue() > VALID_TIME;
            if (del) {
                it.remove();
            }
        }
    }
}
