package com.devsaidur.saidur.playnearn.ui.home.datamodel;

public class TopernrModel {
    String id;
  UserModel userModel;

    public TopernrModel(String id, UserModel userModel) {
        this.id = id;
        this.userModel = userModel;
    }

    public TopernrModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
