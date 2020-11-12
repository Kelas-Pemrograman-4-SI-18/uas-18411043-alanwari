package com.alan.aplikasipenduduk.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alan.aplikasipenduduk.R;
import com.alan.aplikasipenduduk.admin.HomeAdminActivity;
import com.alan.aplikasipenduduk.pengguna.HomePengguna;
import com.alan.aplikasipenduduk.server.BaseURL;
import com.alan.aplikasipenduduk.session.PrefSetting;
import com.alan.aplikasipenduduk.session.SessionManager;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button btnRegistrasi;
    NoboButton btnLogin;
    EditText edtEmail, edtPassword;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnRegistrasi= (Button) findViewById(R.id.btnRegistrasi);
        btnLogin = (NoboButton) findViewById(R.id.btnLogin);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(this);

        prefSetting.checkLogin(session, prefs);

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(i);
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtEmail.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Masukkan email terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if (strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Masukkan password terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {
                    login(strEmail, strPassword);
                }
            }
        });
    }

    public void login(String email, String password){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        pDialog.setMessage("Mohon tunggu");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status = response.getBoolean("error");
                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String role = jsonObject.getString("role");
                                String _id = jsonObject.getString("_id");
                                String nik = jsonObject.getString("nik");
                                String email = jsonObject.getString("email");
                                String password = jsonObject.getString("password");
                                String confirmPassword = jsonObject.getString("confirmPassword");
                                session.setLogin(true);
                                prefSetting.storeRegIdSharedPreferences(LoginActivity.this, _id, nik, email, password, confirmPassword, role, prefs);
                                if (role.equals("1")) {
                                    Intent i = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                }else {
                                    Intent i = new Intent(LoginActivity.this, HomePengguna.class);
                                    startActivity(i);
                                    finish();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}