package com.puputuan.utils;

import com.alibaba.fastjson.JSON;
import com.puputuan.model.Fancy;
import com.puputuan.model.Interest;
import com.puputuan.model.Job;
import com.puputuan.model.User;
import com.puputuan.model.jsonMedol.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/1.
 */
public class ConvertUtils {
    public static JSONUser convertUser(User user) {
        JSONUser jsonUser = new JSONUser();
        jsonUser.setUserId(user.getUserId());
        jsonUser.setIdentify(user.getIdentify());
        jsonUser.setNickName(EmojiCharacterUtil.emojiRecovery2(user.getNickName()));
        jsonUser.setNickNameChar(user.getNickNameChr());
        jsonUser.setSex(user.getSex());
        jsonUser.setHaveBindedWeChat(user.getWechatId() == null ? false : true);

//        IMUserBody iMUser = null;
//        try {
//            iMUser = HuanXinUserService.getIMUsersByUserName(user.getUserBase().getAccount());
//        } catch (HuanXinUserException e) {
//            e.printStackTrace();
//            logger.error("convertDBModelToJSONModel get HuanXin account fail with " + user.getUserBase().getAccount() + ".");
//
//            iMUser = new IMUserBody(user.getUserBase().getAccount(), MD5Utils.getMd5(user.getUserBase().getAccount()), null);
//        }
        ImUser imUser = new ImUser();
        imUser.setUserName(user.getUserBase().getAccount());
        imUser.setPassword(MD5Utils.getMd5(user.getUserBase().getAccount()));
        jsonUser.setImUser(imUser);

        jsonUser.setSign(user.getSign());
        jsonUser.setBirthday(user.getBirthday());
        jsonUser.setConstellation(user.getConstellation());

        if (user.getCity() == null) {
            jsonUser.setComeFrom(null);
        } else {
            ComeFrom comeFrom = new ComeFrom();
            comeFrom.setProvinceId(user.getProvinceId() != null ? user.getProvinceId() : 0);
            comeFrom.setProvince(user.getProvince() != null ? user.getProvince() : "");
            comeFrom.setAreaId(user.getAreaId() != null ? user.getAreaId() : 0);
            comeFrom.setArea(user.getArea() != null ? user.getArea() : "");
            comeFrom.setCityId(user.getCityId() != null ? user.getCityId() : 0);
            comeFrom.setCity(user.getCity() != null ? user.getCity() : "");
            jsonUser.setComeFrom(comeFrom);
        }

        if (user.getInterestList() != null && user.getInterestList().size() != 0) {
            List<JSONInterest> interestLists = new ArrayList<>();
            List<Interest> interests = user.getInterestList();
            for (Interest interest : interests) {
                if (null != interest.getId()) {
                    JSONInterest temp1 = new JSONInterest();
                    temp1.setId(interest.getId());
                    temp1.setName(interest.getName());
                    temp1.setIsSelected(interest.getHaveSelect());
                    interestLists.add(temp1);
                }
            }
            jsonUser.setInterestList(interestLists);
        } else {
            jsonUser.setInterestList(null);
        }

        if (user.getJobList() != null && user.getJobList().size() != 0) {
            List<JSONJob> jobList = new ArrayList<>();
            List<Job> jobs = user.getJobList();
            for (Job job : jobs) {
                if (null != job.getId()) {
                    JSONJob temp1 = new JSONJob();
                    temp1.setId(job.getId());
                    temp1.setName(job.getName());
                    temp1.setIsSelected(job.getHaveSelect());
                    jobList.add(temp1);
                }
            }
            jsonUser.setJobList(jobList);
        } else {
            jsonUser.setJobList(null);
        }

        if (user.getFancyList() != null && user.getFancyList().size() != 0) {
            List<JSONFancy> fancyList = new ArrayList<>();
            List<Fancy> fancies = user.getFancyList();
            for (Fancy fancy : fancies) {
                if (null != fancy.getId()) {
                    JSONFancy temp1 = new JSONFancy();
                    temp1.setId(fancy.getId());
                    temp1.setName(fancy.getName());
                    temp1.setIsSelected(fancy.getHaveSelect());
                    fancyList.add(temp1);
                }
            }
            jsonUser.setFancyList(fancyList);
        } else {
            jsonUser.setFancyList(null);
        }

        //由于头像从相册中设置,所以这里最少会有一张照片
        // TODO 以后这里要通过微服务调取所以不需要图片，以后再设进去
//        List<AlbumPhoto> album = new ArrayList<>();
//        List<Photo> photos = user.getAlbum();
//
//        for (Photo photo : photos) {
//            AlbumPhoto albumPhoto = new AlbumPhoto();
//            albumPhoto.setAlbumPhotoId(photo.getId());
//            albumPhoto.setImageId(photo.getImageId());
//            albumPhoto.setImageUrl(photo.getImage().getUrl());
//            albumPhoto.setIsAvatar(photo.getIsAvatar());
//            album.add(albumPhoto);
//
//            //如果图片被选为头像,则设置头像URL及ID
//            if (photo.getIsAvatar()) {
//                jsonUser.setAvatarId(photo.getId());
//                jsonUser.setAvatarUrl(photo.getImage().getUrl());
//            }
//        }
//        jsonUser.setAlbum(album);

        jsonUser.setFriendUpdateTime(user.getFriendUpdateTime());

        return jsonUser;
    }



    /**
     * 通过传进来的转成Map，成功返回true，失败为false
     * @param json
     * @return
     */
    public static Map jsonToMap(String json){
        try {
            return JSON.parseObject(json,Map.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Object jsonToObject(String json,String beanKey){
        try {
            return JSON.parseObject(json,Class.forName(beanKey));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

