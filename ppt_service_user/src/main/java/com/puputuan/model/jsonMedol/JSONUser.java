package com.puputuan.model.jsonMedol;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dazai on 2016/7/27.
 */

public class JSONUser extends JSONUserBase {

    @Column
    private String sign;

    @Column
    private Date birthday;

    @Column
    private String constellation;

    @Column
    private ComeFrom comeFrom;

    @Transient
    private List<JSONJob> jobList;

    @Transient
    private List<JSONInterest> interestList;

    //
    @Transient
    private List<JSONFancy> fancyList;

    @Transient
    private List<AlbumPhoto> album;

    private boolean haveBindedWeChat;
//    @Column
//    private LatestUpdateFriends latestUpdateFriends;

    @Column
    private Date friendUpdateTime;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getBirthday() {
        if (birthday == null) {
            return null;
        }
        return (Date) birthday.clone();
    }

    public void setBirthday(Date birthday) {
        if (birthday == null) {
            this.birthday = null;
        } else {
            this.birthday = (Date) birthday.clone();
        }
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public ComeFrom getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(ComeFrom comeFrom) {
        this.comeFrom = comeFrom;
    }

    public List<JSONJob> getJobList() {
        return jobList;
    }

    public void setJobList(List<JSONJob> jobList) {
        this.jobList = jobList;
    }

    public List<JSONInterest> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<JSONInterest> interestList) {
        this.interestList = interestList;
    }

    public List<AlbumPhoto> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumPhoto> album) {
        this.album = album;
    }

//    public LatestUpdateFriends getLatestUpdateFriends() {
//        return latestUpdateFriends;
//    }
//
//    public void setLatestUpdateFriends(LatestUpdateFriends latestUpdateFriends) {
//        this.latestUpdateFriends = latestUpdateFriends;
//    }

    public Date getFriendUpdateTime() {
        if (friendUpdateTime == null) {
            return null;
        }
        return (Date) friendUpdateTime.clone();
    }

    public void setFriendUpdateTime(Date friendUpdateTime) {
        if (friendUpdateTime == null) {
            this.friendUpdateTime = null;
        } else {
            this.friendUpdateTime = (Date) friendUpdateTime.clone();
        }
    }

    public boolean isHaveBindedWeChat() {
        return haveBindedWeChat;
    }

    public void setHaveBindedWeChat(boolean haveBindedWeChat) {
        this.haveBindedWeChat = haveBindedWeChat;
    }

    public List<JSONFancy> getFancyList() {
        return fancyList;
    }

    public void setFancyList(List<JSONFancy> fancyList) {
        this.fancyList = fancyList;
    }

    @Override
    public String toString() {
        return "JSONUser{" +
                "userId= " + this.getUserId() +
                ", identify= " + this.getIdentify() +
                ", avatarId= " + this.getAvatarId() +
                ", avatarUrl= " + this.getAvatarUrl() +
                ", nickName= " + this.getNickName() +
                ", nickNameChar= " + this.getNickNameChar() +
                ", sex= " + this.getSex() +
                ", imUser= " + this.getImUser() +
                ", sign= " + sign +
                ", birthday= " + birthday +
                ", constellation= " + constellation +
                ", comeFrom= " + comeFrom +
                ", interestList= " + interestList +
                ", jobList= " + jobList +
                ", album= " + album +
                ", friendUpdateTime= " + friendUpdateTime +
                "}";
    }
}
