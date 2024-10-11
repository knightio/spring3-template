package cc.becurious.framework.security.context;

import org.springframework.security.core.Authentication;

public class AuthenticationContextHolder {

    public static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext(){
        return contextHolder.get();
    }

    public static void setContextHolder(Authentication authentication){
        contextHolder.set(authentication);
    }

    public static void clearContext(){
        contextHolder.remove();
    }

}
