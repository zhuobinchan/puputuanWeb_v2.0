package com.puputuan.config.mine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by chenzhuobin on 2017/6/2.
 */
@Component
@PropertySource(value = "classpath:/ppt_envi.properties", ignoreResourceNotFound = true)
public class ConfigInfo {
    @Value("${user.account.prex}")
    private String accountPrex;

    public String getAccountPrex() {
        return accountPrex;
    }

    public void setAccountPrex(String accountPrex) {
        this.accountPrex = accountPrex;
    }
}
