package com.puputuan;

import com.puputuan.binding.RestfulProxyFactory;
import com.puputuan.restful.CommonRestfulService;
import com.puputuan.restful.UserAppRestfulService;
import com.puputuan.restful.UserRestfulService;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guodikai on 2017/6/2.
 */
public class ClientTest {
    @Test
    public void testClient(){
        CommonRestfulService proxy = (CommonRestfulService) RestfulProxyFactory.newInstance(CommonRestfulService.class);
        Map<String,String> head = new HashMap<>();
        head.put("token","340c6c169bc84e2381e84558609b06f7");

        System.out.println(proxy.checkLogin(head));
    }
}
