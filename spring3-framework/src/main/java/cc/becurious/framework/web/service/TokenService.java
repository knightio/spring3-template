package cc.becurious.framework.web.service;

import cc.becurious.common.constant.CacheConstants;
import cc.becurious.common.constant.Constants;
import cc.becurious.common.core.domain.LoginUser;
import cc.becurious.common.core.redis.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // token 开头
    @Value("${token.prefix}")
    private String prefix;


    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private int expireTime;


    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;



    @Resource
    private RedisCache redisCache;

    public String createToken(LoginUser loginUser){
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String,Object> map = new HashMap<>(1);
        map.put(Constants.LOGIN_USER_KEY,token);
        return createToken(map);
    }

    public void refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey,loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= 20 * MILLIS_MINUTE)
        {
            refreshToken(loginUser);
        }
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

    public String createToken(Map<String, Object> claims){
//        SecretKey key = Jwts.SIG.HS512.key().build();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder().claims(claims).signWith(key,Jwts.SIG.HS512).compact();
    }

    private Jwe<Claims> parseToken(String token)
    {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parser()
                .verifyWith(key)
                .build().parseEncryptedClaims(token);

    }

    private Claims getClaimsFromToken(String token)
    {
        return parseToken(token).getPayload();
    }

    private Header getHeaderFromToken(String token){
        return parseToken(token).getHeader();
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.hasText(token)){
            Claims claims = getClaimsFromToken(token);
            String uuid = claims.get(Constants.LOGIN_USER_KEY).toString();
            String tokenKey = getTokenKey(uuid);
            return (LoginUser) redisCache.getCacheObject(tokenKey);

        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if(ObjectUtils.isEmpty(token) && token.startsWith(prefix)){
            token = token.replace(prefix,"");
        }
        return token;
    }
}
