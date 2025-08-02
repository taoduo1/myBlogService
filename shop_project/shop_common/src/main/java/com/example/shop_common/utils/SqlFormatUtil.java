package com.example.shop_common.utils;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class SqlFormatUtil implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return now + "|" + elapsed + "|" + category + "|connection " + connectionId + "|" + prepared + "|\r\n" + sql;
    }
}
