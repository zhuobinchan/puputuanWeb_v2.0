package com.puputuan.utils;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenzhuobin on 2017/6/8.
 */
@Component
public class MemcacheUtils {

    /**
     * 请求超时时间5秒
     */
    private static final int timeout = 5000;

    @Resource(name = "memcachedClient")
    MemcachedClient memcachedClient;


    /**
     *
     * @param key
     * @param value
     * @param exptime  key-value有效时长
     * @return
     * true 成功设置
     * false 失败设置
     */
    public Boolean set(String key,Object value,int exptime){
        if (CheckNullUtils.checkParaNULL(key,value,exptime)){
            try {
                return memcachedClient.set(key,exptime,value,timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 默认设置是永久有效
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key,Object value){
        return set(key,value,0);
    }



    /**
     * 添加
     * 默认是永久有效
     * @param key
     * @param value
     * @return
     */
    public Boolean add(String key,Object value){
        return add(key,value,0);
    }

    public Boolean add(String key, Object value, int exptime) {
        if (CheckNullUtils.checkParaNULL(key,value,exptime)) {
            try {
                return memcachedClient.add(key, exptime, value, timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Boolean replace(String key,Object value){
        return replace(key,value,0);
    }

    public Boolean replace(String key, Object value, int exptime) {
        if (CheckNullUtils.checkParaNULL(key,value,exptime)) {
            try {
                return memcachedClient.replace(key, exptime, value, timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 对象追加
     * @param key
     * @param value
     * @return
     */
    public Boolean append(String key, Object value) {
        if (CheckNullUtils.checkParaNULL(key,value)) {
            try {
                return memcachedClient.append(key, value, timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Boolean prepend(String key, Object value) {
        if (CheckNullUtils.checkParaNULL(key,value)) {
            try {
                return memcachedClient.prepend(key, value, timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Object get(String key) {
        if (CheckNullUtils.checkParaNULL(key)) {
            try {
                return memcachedClient.get(key, timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Boolean remove(String key){
        if (CheckNullUtils.checkParaNULL(key)) {
            try {
                return memcachedClient.delete(key, timeout);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * check-and-set 操作
     * 保证该改value为最新才进行操作
     * 当高并发时，可以锁住数据，保证数据一致性
     * @param key
     * @param value
     * @return
     */
    public Boolean cas(String key,Object value,int exptime){
        if (CheckNullUtils.checkParaNULL(key,value,exptime)) {
            try {
                return memcachedClient.cas(key, exptime, new CASOperation<Object>() {
                    @Override
                    public int getMaxTries() {
                        return Integer.MAX_VALUE;
                    }

                    @Override
                    public Object getNewValue(long l, Object o) {
                        return value;
                    }
                });
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public Object cas(String key,Object value){
        return cas(key,value,0);
    }
}
