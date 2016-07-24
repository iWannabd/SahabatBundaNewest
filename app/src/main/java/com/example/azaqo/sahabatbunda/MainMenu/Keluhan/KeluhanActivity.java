package com.example.azaqo.sahabatbunda.MainMenu.Keluhan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.azaqo.sahabatbunda.R;
import com.example.azaqo.sahabatbunda.RequestHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeluhanActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_listview);

        ButterKnife.bind(this);

        HashMap<String,String> data = new HashMap<>();
        data.put("idpemeriksaan", "");

        ProgressDialog pd = new ProgressDialog(KeluhanActivity.this);
        pd.setMessage("Tunggu...");

        new RequestHttp(data,
                "http://sahabatbundaku.org/request_android/get_keluhan.php",
                setdata,
                pd)
                .execute();
    }

    private List<String> toList(String data){
        String a  = data.substring(1,data.length()-1);
        return Arrays.asList(a.split("\\s*,\\s*"));
    }

    RequestHttp.Setelah setdata  = new RequestHttp.Setelah() {
        @Override
        public void Lakukan(String s) {
            try {
                JSONObject jobj = new JSONObject(s);
                List<HashMap<String,String>> lines = new ArrayList<>();
                HashMap<String,String> eachline = new HashMap<>();

                String tris = "";
                String kodekeluhan[] = {
                        "Mual dan Muntah",
                        "Susah BAB",
                        "Keputhihan",
                        "Pusing",
                        "Mudah lelah",
                        "Rasa terbakar/Panas pada dada"
                };
                if (!jobj.getString("tris1").equals("[]")){
                    for (String x:toList(jobj.getString("tris1"))) {
                        tris += kodekeluhan[Integer.parseInt(x)]+", ";
                    }
                }

                eachline= new HashMap<>();
                eachline.put("line1","Keluhan Trisemester 1");
                eachline.put("line2",tris);
                lines.add(eachline);

                tris = "";
                kodekeluhan = new String[] {
                        "Pusing",
                        "Nyeri perut bagian bawah",
                        "Nyeri punggung",
                        "Keputhihan",
                        "susah BAB",
                        "Sering berkemih",
                        "Gerak janin berkurang",
                };
                if (!jobj.getString("tris2").equals("[]")){
                    for (String x:toList(jobj.getString("tris2"))) {
                        tris += kodekeluhan[Integer.parseInt(x)]+", ";
                    }
                }

                eachline= new HashMap<>();
                eachline.put("line1","Keluhan Trisemester 2");
                eachline.put("line2",tris);
                lines.add(eachline);

                tris = "";
                kodekeluhan = new String[] {
                        "Susah BAB",
                        "Kram pada kaki",
                        "Nyeri perut bagian bawah",
                        "Gerak janin berkurang",
                        "Sering berkemih",
                        "Kaki bengkak",
                        "Keputhihan",
                        "Sulit tidur",
                        "Sesak napas",
                        "Nyeri pinggang",
                        "Keluar darah dari kemaluan"
                };
                if (!jobj.getString("tris3").equals("[]")){
                    for (String x:toList(jobj.getString("tris3"))) {
                        tris += kodekeluhan[Integer.parseInt(x)]+", ";
                    }
                }

                eachline= new HashMap<>();
                eachline.put("line1","Keluhan Trisemester 2");
                eachline.put("line2",tris);
                lines.add(eachline);

                SimpleAdapter adapter = new SimpleAdapter(
                        KeluhanActivity.this,
                        lines,
                        android.R.layout.simple_list_item_2,
                        new String[]{"line1","line2"},
                        new int[]{android.R.id.text1,android.R.id.text2}
                );
                list.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}
