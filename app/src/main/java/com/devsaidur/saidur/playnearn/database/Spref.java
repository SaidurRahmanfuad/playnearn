package com.devsaidur.saidur.playnearn.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.devsaidur.saidur.playnearn.ui.logsing.Login;

import java.util.HashMap;

public class Spref {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor btneditor;
    Context context;
    int Private_Mode=0;
    public static String IS_Login="isLogin";
    public static final String Pref_Name="Pref_Name"; //file name
    public static final String UID="uid";
    // public static final String Autoid="autoid";
    public static final String UserName="userName";
    public static final String UserMobile="userPhone";
    public static final String UserEmail="userEmail";
    public static final String UserRole="userRole";

    public Spref(Context context) {
        sharedPreferences = context.getSharedPreferences(Pref_Name,Private_Mode);
        this.context = context;
        editor=sharedPreferences.edit();

    }
    public void saveSessaionUid(String uid){
        editor.putBoolean(IS_Login,true);
        editor.putString(UID,uid);
        editor.commit();
    }
    public void saveSessaion(String uid,String userName,String userMobile,String userEmail,String userRole){
        editor.putBoolean(IS_Login,true);
        editor.putString(UID,uid);
        editor.putString(UserName,userName);
        editor.putString(UserMobile,userMobile);
        editor.putString(UserEmail,userEmail);
        editor.putString(UserRole,userRole);
        editor.commit();
    }
    public HashMap<String,String> getUserInfo()
    {
        HashMap<String,String> UserData=new HashMap<>();
        UserData.put(UID,sharedPreferences.getString(UID,null));
        UserData.put(UserName,sharedPreferences.getString(UserName,null));
        UserData.put(UserMobile,sharedPreferences.getString(UserMobile,null));
        UserData.put(UserEmail,sharedPreferences.getString(UserEmail,null));
        UserData.put(UserRole,sharedPreferences.getString(UserRole,null));

        return UserData;
    }
    public void logout(){
        editor.clear();
        editor.commit();
     /*   Intent intent=new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }
}
