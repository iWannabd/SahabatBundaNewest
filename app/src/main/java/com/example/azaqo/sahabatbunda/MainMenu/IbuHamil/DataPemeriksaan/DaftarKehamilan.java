package com.example.azaqo.sahabatbunda.MainMenu.IbuHamil.DataPemeriksaan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.azaqo.sahabatbunda.MainMenu.IbuHamil.DataPemeriksaan.DataPemeriksaan.DataPemeriksaan;
import com.example.azaqo.sahabatbunda.MainMenu.MainActivity;
import com.example.azaqo.sahabatbunda.PostRequest;
import com.example.azaqo.sahabatbunda.R;
import com.example.azaqo.sahabatbunda.RequestHttp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaftarKehamilan extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView list;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_listview);
        ButterKnife.bind(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Tunggu...");

        HashMap<String,String> post = new HashMap<>();
        post.put("unameibu",getSharedPreferences("MAIN",MODE_PRIVATE).getString("USERNAME","kosong"));
        new Req(post,"http://sahabatbundaku.org/request_android/get_kehamilan.php").execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.just_homebutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.home:
                Intent ten = new Intent(this,MainActivity.class);
                ten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ten);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListKehamilan(final String json) throws JSONException {
        final JSONArray jsondata = new JSONArray(json);
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < jsondata.length(); i++) {
            data.add("Kehamilan ke-"+jsondata.getJSONObject(i).getString("ke"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String idkehamilan = jsondata.getJSONObject(i).getString("idHamil");
                    //buat dialog loading
                    ProgressDialog pd = new ProgressDialog(DaftarKehamilan.this);
                    pd.setMessage("Tunggu...");
                    //uubah list
                    HashMap<String,String> post = new HashMap<String, String>();
                    post.put("idkehamilan",idkehamilan);
                    new RequestHttp(post, "http://sahabatbundaku.org/request_android/get_record_pemeriksaan.php", new RequestHttp.Setelah() {
                        @Override
                        public void Lakukan(String s) {
                            try {
                                setListKunjungan(s);
                            } catch (JSONException e) {
//                                e.printStackTrace();
                                Toast.makeText(DaftarKehamilan.this,R.string.no_examination,Toast.LENGTH_SHORT).show();
                            }
                        }
                    },pd).execute();
                    Log.d("PHP", "onItemClick: "+idkehamilan);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setListKunjungan(String json) throws JSONException {
        final JSONArray jsondata = new JSONArray(json);
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < jsondata.length(); i++) {
            data.add(jsondata.getJSONObject(i).getString("tanggalperiksa"));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Waktu Kunjungan")
                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Log.d("PHP", "id pemeriksaan: "+jsondata.getString(i));
                            Intent ten = new Intent(DaftarKehamilan.this,DataPemeriksaan.class);
                            ten.putExtra("idpemeriksaan",jsondata.getJSONObject(i).getString("idpemeriksaan"));
                            startActivity(ten);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
        .show();
    }

    private class Req extends PostRequest{

        public Req(HashMap<String, String> data, String url) {
            super(data, url);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("PHP", "onPostExecute: "+s);
            super.onPostExecute(s);
            try {
                setListKehamilan(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pd.dismiss();
        }
    }
}
