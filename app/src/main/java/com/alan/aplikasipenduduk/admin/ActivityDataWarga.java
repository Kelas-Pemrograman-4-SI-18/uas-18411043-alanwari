package com.alan.aplikasipenduduk.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alan.aplikasipenduduk.R;
import com.alan.aplikasipenduduk.adapter.AdapterWarga;
import com.alan.aplikasipenduduk.model.ModelData;
import com.alan.aplikasipenduduk.server.BaseURL;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataWarga extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterWarga adapter;
    ListView list;

    ArrayList<ModelData> newsList = new ArrayList<ModelData>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_warga);

        getSupportActionBar().setTitle("Data Buku");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterWarga(ActivityDataWarga.this, newsList);
        list.setAdapter(adapter);
        getAllBuku();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataWarga.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllBuku() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataWarga, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data buku = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelData buku = new ModelData();
                                    final String _id = jsonObject.getString("_id");
                                    final String kodeData = jsonObject.getString("kodeData");
                                    final String namaWarga = jsonObject.getString("namaWarga");
                                    final String jenisKelamin = jsonObject.getString("jenisKelamin");
                                    final String alamat = jsonObject.getString("alamat");
                                    final String nik = jsonObject.getString("nik");
                                    final String agama = jsonObject.getString("agama");
                                    final String gambar = jsonObject.getString("gambar");
                                    buku.setKodeData(kodeData);
                                    buku.setNamaWarga(namaWarga);
                                    buku.setJenisKelamin(jenisKelamin);
                                    buku.setAlamat(alamat);
                                    buku.setNik(nik);
                                    buku.setAgama(agama);
                                    buku.setGambar(gambar);
                                    buku.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataWarga.this, EditDataDanHapusActivity.class);
                                            a.putExtra("kodeData", newsList.get(position).getKodeData());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaWarga", newsList.get(position).getNamaWarga());
                                            a.putExtra("jenisKelamin", newsList.get(position).getJenisKelamin());
                                            a.putExtra("alamat", newsList.get(position).getAlamat());
                                            a.putExtra("nik", newsList.get(position).getNik());
                                            a.putExtra("agama", newsList.get(position).getAgama());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(buku);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}