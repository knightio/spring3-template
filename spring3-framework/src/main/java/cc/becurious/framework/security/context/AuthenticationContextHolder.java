package cc.becurious.framework.security.context;

import org.springframework.security.core.Authentication;

public class AuthenticationContextHolder {

    public static final ThreadLocal<Authentication> CONTEXT_HOLDER = new ThreadLocal<>();

    public static Authentication getContext(){
        return CONTEXT_HOLDER.get();
    }

    public static void setContextHolder(Authentication authentication){
        CONTEXT_HOLDER.set(authentication);
    }

    public static void clearContext(){
        CONTEXT_HOLDER.remove();
    }

}
