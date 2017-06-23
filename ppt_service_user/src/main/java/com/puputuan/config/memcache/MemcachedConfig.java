package com.puputuan.config.memcache;

import net.rubyeye.xmemcached.buffer.SimpleBufferAllocator;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenzhuobin on 2017/6/8.
 */
@Configuration
@PropertySource(value = "classpath:/memcached.properties", ignoreResourceNotFound = true)
public class MemcachedConfig {
    private static final Logger logger = Logger.getLogger(MemcachedConfig.class);

    @Value("${memcached.server1.host}")
    private String server1Host;

    @Value("${memcached.server1.port}")
    private String server1Port;

    @Value("${memcached.server1.weight}")
    private Integer server1Weight;

    @Value("${memcached.config.connectionPoolSize}")
    private Integer connectionPoolSize;

    @Value("${memcached.config.failureMode}")
    private Boolean failureMode;

    @Bean("memcachedClient")
    XMemcachedClientFactoryBean xMemcachedClientFactoryBean(){
        XMemcachedClientFactoryBean xMemcachedClientFactoryBean = new XMemcachedClientFactoryBean();
        xMemcachedClientFactoryBean.setServers(server1Host+":"+server1Port);

        List weights = new ArrayList();
        weights.add(server1Weight);
        xMemcachedClientFactoryBean.setWeights(weights);

        xMemcachedClientFactoryBean.setConnectionPoolSize(connectionPoolSize);
        xMemcachedClientFactoryBean.setCommandFactory(new BinaryCommandFactory());
        xMemcachedClientFactoryBean.setSessionLocator(new KetamaMemcachedSessionLocator());
        xMemcachedClientFactoryBean.setTranscoder(new SerializingTranscoder());
        xMemcachedClientFactoryBean.setBufferAllocator(new SimpleBufferAllocator());

        xMemcachedClientFactoryBean.setFailureMode(failureMode);
        return xMemcachedClientFactoryBean;
    }
}
