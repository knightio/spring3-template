package cc.becurious.framework.security.context;

import org.springframework.security.core.Authentication;

public class AuthenticationContextHolder {

    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    private static Authentication getContext(){
        return contextHolder.get();
    }

    private static void setContextHolder(Authentication authentication){
        contextHolder.set(authentication);
    }

    private static void clearContext(){
        contextHolder.remove();
    }

}
