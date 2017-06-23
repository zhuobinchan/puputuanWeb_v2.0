package com.puputuan.config.shiro;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by chenzhuobin on 2017/5/26.
 */
@Configuration
//@Import(value = {ShiroAuthService.class})
@ImportResource(locations = {"classpath:spring/spring-cache-config.xml","classpath:spring/spring-config-shiro.xml","classpath:spring/spring-mvc-shiro.xml"})
public class ShiroConfig {

//    private static final Logger logger = Logger.getLogger(ShiroConfig.class);
//
//    @Resource(name = "authService")
//    private ShiroAuthService authService;
//
//    @Resource(name = "shiroCacheManager")
//    EhCacheManager shiroCacheManager;
//    @Bean("shiroCacheManager")
//    @Primary
//    public EhCacheManager shiroCacheManager(){
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return ehCacheManager;
//    }
//
//
//
//    @Resource(name = "credentialsMatcher")
//    RetryLimitHashedCredentialsMatcher credentialsMatcher;
//    @Bean("credentialsMatcher")
//    @Primary
////    @DependsOn(value = {"shiroCacheManager"})
////    public RetryLimitHashedCredentialsMatcher credentialsMatcher(EhCacheManager shiroCacheManager){
//    public RetryLimitHashedCredentialsMatcher credentialsMatcher(){
//        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(shiroCacheManager);
//        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
//        retryLimitHashedCredentialsMatcher.setHashIterations(2);
//        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return retryLimitHashedCredentialsMatcher;
//    }
//
//    @Resource(name = "userRealm")
//    UserVerifyRealm userRealm;
//    @Bean("userRealm")
//    @Primary
////    @DependsOn(value = {"credentialsMatcher"})
////    public UserVerifyRealm userRealm(RetryLimitHashedCredentialsMatcher credentialsMatcher){
//    public UserVerifyRealm userRealm(){
//        UserVerifyRealm userVerifyRealm = new UserVerifyRealm();
//        userVerifyRealm.setCredentialsMatcher(credentialsMatcher);
//        userVerifyRealm.setCachingEnabled(true);
//        userVerifyRealm.setAuthenticationCachingEnabled(true);
//        userVerifyRealm.setAuthorizationCacheName("shiro.authorizationCache");
//        return userVerifyRealm;
//    }
//
//    @Resource(name = "sessionIdGenerator")
//    JavaUuidSessionIdGenerator sessionIdGenerator;
//    @Bean("sessionIdGenerator")
//    @Primary
//    public JavaUuidSessionIdGenerator sessionIdGenerator(){
//        return new JavaUuidSessionIdGenerator();
//    }
//
//
//    @Resource(name = "sessionIdCookie")
//    SimpleCookie sessionIdCookie;
//    @Bean("sessionIdCookie")
//    public SimpleCookie sessionIdCookie(){
//        SimpleCookie simpleCookie = new SimpleCookie("sid");
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setMaxAge(-1);
//        return simpleCookie;
//    }
//
//
//    @Resource(name = "rememberMeCookie")
//    SimpleCookie rememberMeCookie;
//    @Bean("rememberMeCookie")
//    public SimpleCookie rememberMeCookie(){
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setMaxAge(2592000);
//        return simpleCookie;
//    }
//
//    @Resource(name = "rememberMeManager")
//    CookieRememberMeManager rememberMeManager;
//    @Bean("rememberMeManager")
////    @DependsOn(value = {"rememberMeCookie"})
////    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie){
//    public CookieRememberMeManager rememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        return cookieRememberMeManager;
//    }
//
//    @Resource(name = "sessionDAO")
//    EnterpriseCacheSessionDAO sessionDAO;
//    @Bean("sessionDAO")
////    @DependsOn(value = {"sessionIdGenerator"})
////    public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator){
//    public EnterpriseCacheSessionDAO sessionDAO(){
//        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
//        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
//        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator);
//        return enterpriseCacheSessionDAO;
//    }
//
//    @Resource(name = "sessionValidationScheduler")
//    QuartzSessionValidationScheduler sessionValidationScheduler;
//    @Bean("sessionValidationScheduler")
////    @DependsOn(value = {"sessionManager"})
////    public QuartzSessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager){
//    public QuartzSessionValidationScheduler sessionValidationScheduler(){
//        QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
//        quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
//        quartzSessionValidationScheduler.setSessionManager(sessionManager);
//        return quartzSessionValidationScheduler;
//    }
//
//    @Resource(name = "sessionManager")
//    DefaultWebSessionManager sessionManager;
//    @Bean("sessionManager")
////    @DependsOn(value = {"sessionValidationScheduler","sessionDAO","sessionIdCookie"})
////    public DefaultWebSessionManager sessionManager(QuartzSessionValidationScheduler sessionValidationScheduler,EnterpriseCacheSessionDAO sessionDAO,SimpleCookie sessionIdCookie){
//    public DefaultWebSessionManager sessionManager(){
//        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
//        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
//        defaultWebSessionManager.setDeleteInvalidSessions(true);
//        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
//        defaultWebSessionManager.setSessionValidationScheduler(sessionValidationScheduler);
//        defaultWebSessionManager.setSessionDAO(sessionDAO);
//        defaultWebSessionManager.setSessionIdCookieEnabled(true);
//        defaultWebSessionManager.setSessionIdCookie(sessionIdCookie);
//        return defaultWebSessionManager;
//    }
//
//    @Resource(name = "securityManager")
//    DefaultWebSecurityManager securityManager;
//    @Bean("securityManager")
////    @DependsOn(value = {"userRealm","shiroCacheManager"})
////    public DefaultWebSecurityManager securityManager(UserVerifyRealm userRealm, EhCacheManager shiroCacheManager){
//    public DefaultWebSecurityManager securityManager(){
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        defaultWebSecurityManager.setRealm(userRealm);
//        defaultWebSecurityManager.setCacheManager(shiroCacheManager);
//        return defaultWebSecurityManager;
//    }
//
////    @Resource(name = "methodInvokingFactory")
////    MethodInvokingFactoryBean methodInvokingFactory;
//    @Bean("methodInvokingFactory")
////    @DependsOn(value = {"securityManager"})
////    public MethodInvokingFactoryBean methodInvokingFactory(DefaultWebSecurityManager securityManager){
//    public MethodInvokingFactoryBean methodInvokingFactory(){
//        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
//        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
//        methodInvokingFactoryBean.setArguments(new Object[]{securityManager});
//        return methodInvokingFactoryBean;
//    }
//
//    @Resource(name = "formAuthenticationFilter")
//    FormAuthenticationFilter formAuthenticationFilter;
//    @Bean("formAuthenticationFilter")
//    public FormAuthenticationFilter formAuthenticationFilter(){
//        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
//        formAuthenticationFilter.setUsernameParam("username");
//        formAuthenticationFilter.setPasswordParam("password");
//        formAuthenticationFilter.setRememberMeParam("remember_me");
//        formAuthenticationFilter.setLoginUrl("/login");
//        formAuthenticationFilter.setSuccessUrl("/");
//        return formAuthenticationFilter;
//    }
//
//    @Resource(name = "sysUserFilter")
//    SysUserFilter sysUserFilter;
//    @Bean("sysUserFilter")
//    public SysUserFilter sysUserFilter(){
//        return new SysUserFilter();
//    }
//
//
//    @Resource(name = "shiroFilter")
//    ShiroFilterFactoryBean shiroFilter;
//    @Bean("shiroFilter")
////    @DependsOn(value = {"securityManager","formAuthenticationFilter","sysUserFilter"})
////    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,FormAuthenticationFilter formAuthenticationFilter,SysUserFilter sysUserFilter){
//    public ShiroFilterFactoryBean shiroFilter(){
////        ShiroAuthService authService = new ShiroAuthService();
//
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
//
//        Map<String,javax.servlet.Filter> filters = new HashMap<>();
//        filters.put("authc",formAuthenticationFilter);
//        filters.put("sysUser",sysUserFilter);
//        shiroFilterFactoryBean.setFilters(filters);
//
////        shiroFilterFactoryBean.setFilterChainDefinitions(authService.loadFilterChainDefinitions());
//        shiroFilterFactoryBean.setFilterChainDefinitions(authService.loadFilterChainDefinitions());
//
//        return shiroFilterFactoryBean;
//    }
//
////    @Resource(name = "exceptionHandlerExceptionResolver")
////    ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;
//    @Bean("exceptionHandlerExceptionResolver")
//    ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver(){
//        return new ExceptionHandlerExceptionResolver();
//    }
//
////    @Resource(name = "lifecycleBeanPostProcessor")
////    LifecycleBeanPostProcessor lifecycleBeanPostProcessor;
//    @Bean("lifecycleBeanPostProcessor")
//    LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//
////    @Resource(name = "defaultExceptionHandler")
////    DefaultExceptionHandler defaultExceptionHandler;
//    @Bean("defaultExceptionHandler")
//    DefaultExceptionHandler defaultExceptionHandler(){
//        return new DefaultExceptionHandler();
//    }
//
////    @Resource(name = "defaultAdvisorAutoProxyCreator")
////    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator;
//    @Bean("defaultAdvisorAutoProxyCreator")
//    @DependsOn(value = "lifecycleBeanPostProcessor")
//    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        return new DefaultAdvisorAutoProxyCreator();
//    }
//
////    @Resource(name = "authorizationAttributeSourceAdvisor")
////    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor;
//    @Bean("authorizationAttributeSourceAdvisor")
////    @DependsOn(value = {"securityManager"})
////    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
//    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
}
