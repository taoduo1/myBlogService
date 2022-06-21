package com.example.shop_common.common.constant;

/**
 * @author duo.tao
 * @Description: 常用文本存放
 * @date 2022-06-13 23:14
 */
public interface NormalConstant {
    //  用户密码 盐
    String SALT = "2wef65h#$%JVG<:}{>";
    String REDIS_TOKEN_KEY = "token";


    //
    String ADD = "+";
    String REDUCE = "-";
    String BOTTOM_LINE = "_";
    String SPLIT = "|";
    String AND = "&";
    String COVERT_SPLIT = "\\|";
    String WAVE_LINE = "~";
    String COMMA = "、";
    String BRACKET = "[]";
    String ENG_COLON = ",";
    String CHI_COLON = "，";
    String CHI_MAO = "：";
    String ENG_MAO = ":";
    String CHI_FEN = ";";
    String EQUAL_ARROW = "=>";
    String L_INCLUDE = "【";
    String R_INCLUDE = "】";
    String DOWNLOAD_FLAG = "?attname=";
    String DEFAULT_PASSWORD = "123456";
    String SPACE = "";
    String WRAP = "\n";
    String WRAP_R = "\r\n";

    String appid = "";
    String secret = "";
    String grant_type = "";

    int limit = 10;
    int START_FLOW = 0;
    int END_FLOW = 1000;
    int NO_FLOW = -1;

    int INT_TRUE = 1;
    int INT_FALSE = 0;

    String TOTAL = "总计";

    String FLOW_LINK = "FLOW_LINK-";
    String FLOW_GATEWAY = "FLOW_GATEWAY-";
    String FLOW_ID = "FLOW_ID-";
    String FLOW_NAME = "FLOW_NAME-";

    String N_REGEX = "[0-9]*$";

    static String include(String s) {
        return L_INCLUDE + s + R_INCLUDE;
    }



}
