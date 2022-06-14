package com.example.shop_common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-06-13 23:32
 */
public class HttpUtils {
    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String headerIP = request.getHeader("x-real-ip");
        if (headerIP == null || "".equals(headerIP) || "null".equals(headerIP)) {
            headerIP = request.getHeader("x-forwarded-for");
        }
        System.out.println("headerIP:" + headerIP);
        if (headerIP != null && !"".equals(headerIP) && !"null".equals(headerIP)) {
            ip = headerIP;
        }
        return ip;
    }

    /**
     * 获取真实的ip
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip;
        // 有的user可能使用代理，为处理用户使用代理的情况，使用x-forwarded-for
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        if ("127.0.0.1".equals(ip)) {
            try {
                // 获取本机真正的ip地址
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ip;
    }
}
