package com.puputuan.restful;

import com.puputuan.annontation.*;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/14.
 */
@IPAddress(ip = "http://127.0.0.1", port = 9083)
@Path("/app/user")
public interface UserAppRestfulService {

    @Path("/searchUserSuperLikeTimes")
    @POST
    public String searchUserSuperLikeTimes(@Header Map headMap);

    @Path("/getInfo")
    @GET
    public String getInfo(@Header Map headMap, @Param Map paramMap);

}
