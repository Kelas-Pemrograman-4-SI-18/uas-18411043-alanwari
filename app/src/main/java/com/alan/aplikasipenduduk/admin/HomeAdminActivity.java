package com.alan.aplikasipenduduk.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.alan.aplikasipenduduk.R;
import com.alan.aplikasipenduduk.session.PrefSetting;
import com.alan.aplikasipenduduk.session.SessionManager;
import com.alan.aplikasipenduduk.users.LoginActivity;

import java.util.Calendar;

public class HomeAdminActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataWarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(HomeAdminActivity.this);

        prefSetting.isLogin(session, prefs);

        cardExit = (CardView)findViewById(R.id.cardExit);
        cardDataWarga = (CardView) findViewById(R.id.cardDataWarga);
        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataWarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataWarga.class);
                startActivity(i);
                finish();
            }
        });

    }
}