package com.alan.aplikasipenduduk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.aplikasipenduduk.R;
import com.alan.aplikasipenduduk.model.ModelData;
import com.alan.aplikasipenduduk.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterWarga extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelData> item;

    public AdapterWarga(Activity activity, List<ModelData> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_data, null);


        TextView namaWarga = (TextView) convertView.findViewById(R.id.txtnamaWarga);
        TextView jenisKelamin     = (TextView) convertView.findViewById(R.id.txtjenisKelamin);
        TextView alamat          = (TextView) convertView.findViewById(R.id.txtAlamat);
        TextView nik         = (TextView) convertView.findViewById(R.id.txtNik);
        TextView agama         = (TextView) convertView.findViewById(R.id.txtAgama);
        ImageView gambar         = (ImageView) convertView.findViewById(R.id.gambar);

        namaWarga.setText(item.get(position).getNamaWarga());
        jenisKelamin.setText(item.get(position).getJenisKelamin());
        alamat.setText(item.get(position).getAlamat());
        nik.setText("Rp." + item.get(position).getNik());
        agama.setText(item.get(position).getAgama());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }

}
