package com.puputuan.model.jsonMedol;

import com.puputuan.model.base.BaseModel;

/**
 * Created by admin on 2016/7/29.
 */
public class JSONJob extends BaseModel {

    private long id;

    private String name;

    private boolean isSelected = true;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
