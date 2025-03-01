package com.example.shop_common.common.context;

public class SystemContext {

    /**
     * web容器的线程是重复使用的，web容器使用了线程池，当一个请求使用完某个线程，该线程会放回线程池被其它请求使用，这就导致一个问题，不同的请求还是有可能会使用到同一个线程（只要请求数量大于线程数量）
     * springboot如未配置线程池数量，使用tomcat默认
     * https://www.jianshu.com/p/ac7ea422119e
     */
    private static final InheritableThreadLocal<UserContext> USER_CONTEXT = new InheritableThreadLocal<>();
//    private static ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static UserContext getUserContext() {
        if (USER_CONTEXT.get() == null) {
            USER_CONTEXT.set(new UserContext());
        }
        return USER_CONTEXT.get();
    }

    public static void setContext(UserContext context) {
        USER_CONTEXT.set(context);
    }

    public static void clear() {
        if (USER_CONTEXT.get() != null) {
            VisitTokenUtils.del(USER_CONTEXT.get().getToken());
        }
        USER_CONTEXT.remove();
    }

}
