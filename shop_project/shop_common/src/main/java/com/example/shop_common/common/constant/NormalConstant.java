package com.example.shop_common.common.constant;

/**
 * @author duo.tao
 * @Description: 常用文本存放
 * @date 2022-06-13 23:14
 */
public class NormalConstant {
    //  用户密码 盐
    public static final String SALT = "2wef65h#$%JVG<:}{>";

    //
    public static final String ADD = "+";
    public static final String REDUCE = "-";
    public static final String BOTTOM_LINE = "_";
    public static final String SPLIT = "|";
    public static final String AND = "&";
    public static final String COVERT_SPLIT = "\\|";
    public static final String WAVE_LINE = "~";
    public static final String COMMA = "、";
    public static final String BRACKET = "[]";
    public static final String ENG_COLON = ",";
    public static final String CHI_COLON = "，";
    public static final String CHI_MAO = "：";
    public static final String ENG_MAO = ":";
    public static final String CHI_FEN = ";";
    public static final String EQUAL_ARROW = "=>";
    public static final String L_INCLUDE = "【";
    public static final String R_INCLUDE = "】";
    public static final String DOWNLOAD_FLAG = "?attname=";
    public static final String DEFAULT_PASSWORD = "123456";
    public static final String SPACE = "";
    public static final String WRAP = "\n";
    public static final String WRAP_R = "\r\n";

    public static final String appid = "wx96b3a57cf519921f";
    public static final String secret = "f15c86704e538f9a21a5c5add84e0754";
    public static final String grant_type = "authorization_code";

    public static final int limit = 10;
    public static final int START_FLOW = 0;
    public static final int END_FLOW = 1000;
    public static final int NO_FLOW = -1;

    public static final int INT_TRUE = 1;
    public static final int INT_FALSE = 0;

    public static final String TOTAL = "总计";

    public static final String FLOW_LINK = "FLOW_LINK-";
    public static final String FLOW_GATEWAY = "FLOW_GATEWAY-";
    public static final String FLOW_ID = "FLOW_ID-";
    public static final String FLOW_NAME = "FLOW_NAME-";

    public static final String N_REGEX = "[0-9]*$";

    public static String include(String s) {
        return L_INCLUDE + s + R_INCLUDE;
    }
}
