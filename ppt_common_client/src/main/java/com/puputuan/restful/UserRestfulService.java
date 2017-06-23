package com.puputuan.restful;

import com.puputuan.annontation.*;

import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/12.
 */
@IPAddress(ip = "http://127.0.0.1", port = 9083)
@Path("/ctrl/crud")
public interface UserRestfulService {


    @Path(value = "/search")
    @POST
    String search(@Header Map headMap, @Param Map paramMap);


    @Path("/insert")
    @POST
    String insert();


}
