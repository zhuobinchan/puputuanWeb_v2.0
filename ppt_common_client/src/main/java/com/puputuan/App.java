package com.puputuan;

import com.puputuan.binding.RestfulProxyFactory;
import com.puputuan.restful.UserAppRestfulService;
import com.puputuan.restful.UserRestfulService;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        UserRestfulService proxy = (UserRestfulService) RestfulProxyFactory.newInstance(UserRestfulService.class);
        Map<String, String> head = new HashMap<>();
        head.put("Authorization", "84baff70109147a3832f39b3c2639c46");
//
        Map<String, Object> param = new HashMap();
//        param.put("methodType",3);
//        param.put("daoKey","cpdUserSuperLikeConfig");
//        param.put("page",1);
//        param.put("size",2);
//        param.put("paramsJson","{'userId':'2840'}");
//
//        System.out.println(proxy.search(head,param));
//
        UserAppRestfulService proxy2 = (UserAppRestfulService) RestfulProxyFactory.newInstance(UserAppRestfulService.class);
//        System.out.println(proxy2.searchUserSuperLikeTimes(head));
//        param = new HashMap();
        param.put("level", "+1");
        System.out.println(proxy2.getInfo(head, param));

    }
}
