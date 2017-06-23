package com.puputuan.restful;

import com.puputuan.annontation.GET;
import com.puputuan.annontation.IPAddress;
import com.puputuan.annontation.Param;
import com.puputuan.annontation.Path;

import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/16.
 */
@IPAddress(ip = "http://127.0.0.1", port = 9083)
@Path("/ctrl/common")
public interface CommonRestfulService {


    @Path(value = "/checkLogin")
    @GET
    String checkLogin(@Param Map param);
}
