package com.alan.aplikasipenduduk.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alan.aplikasipenduduk.R;
import com.alan.aplikasipenduduk.server.BaseURL;
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

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class RegisActivity extends AppCompatActivity {
    Button btnBackLogin;
    NoboButton btnRegistrasi;
    EditText edtnik, edtemail, edtpassword, edtconfirmPassword;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        edtnik = (EditText) findViewById(R.id.edtNik);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        edtpassword = (EditText) findViewById(R.id.edtPassword);
        edtconfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);

        btnBackLogin = (Button) findViewById(R.id.btnBackLogin);
        btnRegistrasi = (NoboButton) findViewById(R.id.btnRegistrasi);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNik               = edtnik.getText().toString();
                String strEmail             = edtemail.getText().toString();
                String strPassword          = edtpassword.getText().toString();
                String strConfirmPassword   = edtconfirmPassword.getText().toString();

                if(strNik.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Masukkan NIK Anda", Toast.LENGTH_LONG).show();
                }else if(strEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Masukkan Email Anda", Toast.LENGTH_LONG).show();
                }else if(strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Masukkan Password Anda", Toast.LENGTH_LONG).show();
                }else if(strConfirmPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ulangi Password", Toast.LENGTH_LONG).show();
                }else {
                    registrasi(strNik,strEmail, strPassword, strConfirmPassword);
                }
            }
        });
    }

    public void registrasi(String nik, String email, String password, String confirmPassword){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("nik", nik);
        params.put("email", email);
        params.put("role", "2");
        params.put("password", password);
        params.put("confirmPassword", confirmPassword);

        pDialog.setMessage("Mohon tunggu");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.register, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status = response.getBoolean("error");
                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
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

    @Override
    public void onBackPressed(){
        Intent i = new Intent(RegisActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
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