package com.devsaidur.saidur.playnearn.ui.home.datamodel;

public class UserModel {
    String id;
    String name;
    String earnamount;
    String joindate;
    String image;

    public UserModel(String id, String name, String earnamount, String joindate, String image) {
        this.id = id;
        this.name = name;
        this.earnamount = earnamount;
        this.joindate = joindate;
        this.image = image;
    }

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEarnamount() {
        return earnamount;
    }

    public void setEarnamount(String earnamount) {
        this.earnamount = earnamount;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
