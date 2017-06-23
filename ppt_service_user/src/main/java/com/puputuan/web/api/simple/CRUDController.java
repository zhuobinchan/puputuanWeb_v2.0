package com.puputuan.web.api.simple;

import com.puputuan.common.mybatis.PageEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenzhuobin on 2017/6/6.
 */
@RequestMapping("/ctrl/crud")
public interface CRUDController {
    /**
     * * 通过daokey找出对应的dao并进行分页操作
     * @param daoKey
     * @param paramsJson 查询所带的参数，是一条json的字符串
     *                   将json字符串转成map
     *
     * @param methodType
     * methodType ：1   不分页查询
     * methodType ：2   通过id查询
     * methodType ：3   分页查询
     *
     * @param id
     * @param daoKey
     * @param paramsJson
     * 通过Json字符串 得到筛选信息
     *
     * @param pageEntity
     * @return
     */

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    Object search(Integer methodType, Long id, String daoKey, String paramsJson, @ModelAttribute PageEntity pageEntity);

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    Object insert(String daoKey,String paramsJson,String beanKey);

    @RequestMapping(value = "/updateByPK", method = RequestMethod.POST)
    @ResponseBody
    Object updateByPK(String daoKey,String paramsJson,String beanKey);

    @RequestMapping(value = "/removeByPK", method = RequestMethod.POST)
    @ResponseBody
    Object removeByPK(String daoKey,Long id);
}
