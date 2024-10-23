package cc.becurious.framework.config;

import cc.becurious.framework.security.filter.JwtAuthenticationTokenFilter;
import cc.becurious.framework.security.handle.AuthenticationEntryPointImpl;
import cc.becurious.framework.security.handle.LogoutSuccessHandlerImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
                // CSRF禁用，因为不使用session
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用HTTP响应标头
                .headers((headers)->{
                    headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable);
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                // 认证失败处理类
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint);
                })
                // 基于token，所以不需要session
                .sessionManagement((session) ->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 过滤请求
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/login").permitAll()
                        // 静态资源，可匿名访问
//                        .requestMatchers(HttpMethod.GET,  "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/*/api-docs/**", "/druid/**").permitAll()
                        .anyRequest().authenticated()
                );
//                .httpBasic(withDefaults());
        // 添加Logout handler
        httpSecurity.logout(logoutConfigurer -> {
            logoutConfigurer.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        });
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter,JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter,LogoutFilter.class);

        // @formatter:on
        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 设置用户相信信息，可以从数据库中读取、或者缓存、或者配置文件
        authProvider.setUserDetailsService(userDetailsService);
        // 设置加密机制，若想要尝试对用户进行身份验证，我们需要知道使用的是什么编码
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }






}
