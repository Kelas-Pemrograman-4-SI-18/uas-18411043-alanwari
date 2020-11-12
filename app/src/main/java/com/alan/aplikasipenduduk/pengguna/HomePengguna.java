package com.alan.aplikasipenduduk.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.alan.aplikasipenduduk.R;
import com.alan.aplikasipenduduk.session.PrefSetting;

public class HomePengguna extends AppCompatActivity {

TextView txtNama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pengguna);

        txtNama = (TextView) findViewById(R.id.txtNama);
        txtNama.setText(PrefSetting.nik);

    }
}