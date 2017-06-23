package com.puputuan.binding;


import java.lang.reflect.Proxy;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/13.
 */

/**
 * 接口代理工厂类
 * 这里直接改成单例模式
 * 并且对newInstance进行函数进行加锁，以防重复实例实例
 * 并且用instanceMap 作为注册表 装载新建的实例，达到缓存的效果
 */
public class RestfulProxyFactory {
    private static Map<String,Object> instanceMap;
    private RestfulProxyFactory(){}

    public static synchronized Object newInstance(Class interfaceClass) {
        if (instanceMap==null){
            instanceMap = new Hashtable<>();
        }
        Object instance = instanceMap.get(interfaceClass.toString());
        if (instance==null){
            Object newInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new RestfulInvocationHandler());
            instanceMap.put(interfaceClass.toString(),newInstance);
            return newInstance;
        }
        return instance;
    }
}
