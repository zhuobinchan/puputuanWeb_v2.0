package com.puputuan.binding;

import com.puputuan.annontation.*;
import com.puputuan.utils.ConvertMapUtils;
import com.puputuan.utils.RestClientUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/14.
 */
public class RestfulInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        //URL 配置
        StringBuffer url = new StringBuffer();
        IPAddress ipAddress = method.getDeclaringClass().getAnnotation(IPAddress.class);
        Path classPath = method.getDeclaringClass().getAnnotation(Path.class);
        Path methodPath = method.getAnnotation(Path.class);
        Boolean isGet = method.getAnnotation(GET.class) != null;
        Boolean isPost = method.getAnnotation(POST.class) != null;
        if (ipAddress == null) {
            throw new Exception("class:" + method.getDeclaringClass().getName() + " class must hava Annotion @IPAddress");
        }
        if (!(isGet || isPost)) {
            throw new Exception("method:" + method.getDeclaringClass().getName() + "." + method.getName() + "  method must hava Annotion @GET or @POST");
        }
        if (methodPath == null) {
            throw new Exception("method:" + method.getDeclaringClass().getName() + "." + method.getName() + " method must hava Annotion @Path");
        }

        url.append(ipAddress.ip()).append(":").append(ipAddress.port());
        if (classPath != null) {
            url.append(classPath.value());
        }
        url.append(methodPath.value());


        //参数请求配置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap httpVars = new LinkedMultiValueMap();

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Header header = parameters[i].getAnnotation(Header.class);
            Param param = parameters[i].getAnnotation(Param.class);
            if (header != null) {
                httpHeaders.setAll((Map) args[i]);

            } else if (param != null) {
                if (isGet){
                    httpVars = ConvertMapUtils.mapToMultiValueMap((Map) args[i],true);
                }
                if (isPost){
                    httpVars = ConvertMapUtils.mapToMultiValueMap((Map) args[i],false);
                }
            }
        }

        if (isGet) {
            //由于get请求参
            //配置到URLBuilder中
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(httpVars);

            //请求头的配置
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(httpHeaders);
            return RestClientUtils.getResourcesByUri(builder.build().encode().toUri(), request);
        } else if (isPost) {
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(httpVars, httpHeaders);
            return RestClientUtils.postResources(url.toString(), request);
        }
        return null;
    }
}
