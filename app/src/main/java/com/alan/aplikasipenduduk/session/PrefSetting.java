package com.alan.aplikasipenduduk.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alan.aplikasipenduduk.admin.HomeAdminActivity;
import com.alan.aplikasipenduduk.pengguna.HomePengguna;

public class PrefSetting {
    public static String _id;
    public static String nik;
    public static String email;
    public static String password;
    public static String confirmPassword;
    public static String role;
    Activity activity;

    public PrefSetting(Activity activity){
        this.activity = activity;
    }

    public SharedPreferences getSharePreferences(){
        SharedPreferences preferences = activity.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return preferences;
    }

    public void isLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        if(session.isLoggedIn()){
            pref = getSharePreferences();
            _id = pref.getString("_id", "");
            nik = pref.getString("nik", "");
            email = pref.getString("email", "");
            password = pref.getString("password", "");
            confirmPassword = pref.getString("confirmPassword", "");
            role = pref.getString("role", "");
        }else {
            session.setLogin(false);
            session.setSessid(0);
            Intent i = new Intent(activity, activity.getClass());
            activity.startActivity(i);
            activity.finish();
        }
    }

    public void checkLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        _id = pref.getString("_id", "");
        nik = pref.getString("nik", "");
        email = pref.getString("email", "");
        password = pref.getString("password", "");
        confirmPassword = pref.getString("confirmPassword", "");
        role = pref.getString("role", "");
        if(session.isLoggedIn()){
            if(role.equals("1")){
                Intent i = new Intent(activity, HomeAdminActivity.class);
                activity.startActivity(i);
                activity.finish();
            }else {
                Intent i = new Intent(activity, HomePengguna.class);
                activity.startActivity(i);
                activity.finish();
            }
        }
    }

    public void storeRegIdSharedPreferences(Context context, String _id, String nik,
                                            String email, String password, String confirmPassword, String role, SharedPreferences prefs){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("_id", _id);
        editor.putString("nik", nik);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("confirmPassword", confirmPassword);
        editor.putString("role", role);
        editor.commit();

    }

}
