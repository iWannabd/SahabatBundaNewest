package com.example.azaqo.sahabatbunda.MainMenu.IbuHamil.DataPemeriksaan.DataPemeriksaan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentDataPemeriksaan extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_IDHAMIL = "idpemeriksaan";

    public PlaceholderFragmentDataPemeriksaan() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragmentDataPemeriksaan newInstance(int sectionNumber) {
        PlaceholderFragmentDataPemeriksaan fragment = new PlaceholderFragmentDataPemeriksaan();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlaceholderFragmentDataPemeriksaan newInstance(int position,String idpemeriksaan) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,position);
        args.putString(ARG_IDHAMIL,idpemeriksaan);
        PlaceholderFragmentDataPemeriksaan fragment = new PlaceholderFragmentDataPemeriksaan();
        fragment.setArguments(args);
        return fragment;
    }

    private ListView list;
    private int position;
    private String idpemeriksaan;
    private ProgressDialog sakedap;

    private List<String> toList(String data){
        String a  = data.substring(1,data.length()-1);
        return Arrays.asList(a.split("\\s*,\\s*"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.just_listview, container, false);
        sakedap = new ProgressDialog(getActivity());
        sakedap.setMessage("Tunggu...");

        list = (ListView) rootView.findViewById(R.id.listView);
        position =  getArguments().getInt(ARG_SECTION_NUMBER);
        idpemeriksaan = getArguments().getString(ARG_IDHAMIL);

        HashMap<String,String> data = new HashMap<>();
        data.put("idpemeriksaan", idpemeriksaan);

        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Tunggu...");

        RequestHttp.Setelah setriawayatkehamilan = new RequestHttp.Setelah() {
            @Override
            public void Lakukan(String s) {
                try {
                    JSONObject jobj = new JSONObject(s);
                    List<HashMap<String,String>> lines = new ArrayList<>();
                    HashMap<String,String> eachline = new HashMap<>();

                    eachline.put("line1","Hari Pertama Haid Terahir");
                    eachline.put("line2",jobj.getString("hpmt"));
                    lines.add(eachline);

                    eachline= new HashMap<>();
                    eachline.put("line1","Hamil ke");
                    eachline.put("line2",jobj.getString("hamilke"));
                    lines.add(eachline);

                    eachline= new HashMap<>();
                    eachline.put("line1","Jarak kehamilan sebelumnya");
                    eachline.put("line2",jobj.getString("jarakhamil")+" tahun");
                    lines.add(eachline);


                    String perskey[] = {"normal","vaccum","sesar","prematur"};
                    for (String k:perskey) {
                        eachline= new HashMap<>();
                        String ke = jobj.getString(k);
                        if (!(ke.equals("-1")||ke.equals(""))) {
                            eachline.put("line1", "Pernah melahirkan " + k + " pada anak ke");
                            eachline.put("line2",ke);
                            lines.add(eachline);
                        }
                    }

                    String kodepenolong[] = {"Bidan", "Dukun", "Dokter", "Lainnya"};
                    String penolong = "";
                    String penolongstring = jobj.getString("penolong");
                    penolongstring = penolongstring.substring(1,penolongstring.length()-1);
                    List<String> penolongs = Arrays.asList(penolongstring.split("\\s*,\\s*"));
                    if (!penolongstring.equals("")) {
                        for (String p: penolongs) {
                            penolong += kodepenolong[Integer.parseInt(p)]+", ";
                        }
                    }

                    eachline= new HashMap<>();
                    eachline.put("line1","Penolong persalinan");
                    eachline.put("line2",penolong);
                    lines.add(eachline);

                    String kodeberatbayis[] = {"bb1","bb2","bb3"};
                    String berats[] = {
                            "Berat bayi kurang dari 2,5 kg pada anak ke",
                            "Berat bayi diantara 2,5 sampai 4 kg pada anak ke",
                            "Berat bayi lebih dari 4 kgpada anak ke"};
                    int i = 0;
                    for (String b:kodeberatbayis) {
                        String value = jobj.getString(b);
                        if (!(value.equals("0") || value.equals("-1"))){
                            eachline= new HashMap<>();
                            eachline.put("line1",berats[i]);
                            eachline.put("line2",value);
                            lines.add(eachline);
                        }
                        i += 1;
                    }

                    Log.d("PHP", "Lakukan: "+lines);

                    SimpleAdapter adapter = new SimpleAdapter(
                            getActivity(),
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



        RequestHttp.Setelah setdatapenyakit = new RequestHttp.Setelah() {
            @Override
            public void Lakukan(String s) {
                try {
                    JSONObject jobj = new JSONObject(s);
                    List<HashMap<String,String>> lines = new ArrayList<>();
                    HashMap<String,String> eachline = new HashMap<>();

                    String penyakitstring = jobj.getString("riwayatpenyakit");
                    penyakitstring = penyakitstring.substring(1,penyakitstring.length()-1);
                    List<String> penyakits = Arrays.asList(penyakitstring.split("\\s*,\\s*"));
                    String kodepenyakit[] = {"Darah tinggi","Gula","Asma","Jantung","Penyakit seks menular","Malaria","Kurang Darah","TPC Paru-paru"};
                    String penyakit = "";
                    if (!penyakitstring.equals("")) {
                        for (String p : penyakits) {
                            penyakit += kodepenyakit[Integer.parseInt(p)]+", ";
                        }
                    }

                    eachline= new HashMap<>();
                    eachline.put("line1","Riwayat Penyakit");
                    eachline.put("line2",penyakit);
                    lines.add(eachline);

                    String penyakitturun = "";
                    String kodepenyakitturn[] = {"Darah tinggi","Gula","Asma","Jantung"};
                    String rawpenyakit = jobj.getString("penyakitturun");
                    if (!rawpenyakit.equals("[]")) {
                        for (String x : toList(rawpenyakit)) {
                            penyakitturun += kodepenyakitturn[Integer.parseInt(x)] + ", ";
                        }
                    }

                    eachline= new HashMap<>();
                    eachline.put("line1","Riwayat penyakit turunan");
                    eachline.put("line2",penyakitturun);
                    lines.add(eachline);

                    String kodekontrasepsi[] = {"AKDR/IUD","Suntik","Implan/Susuk","Tidak ada"};
                    eachline= new HashMap<>();
                    eachline.put("line1","Riwayat kontrasepsi");
                    eachline.put("line2",kodekontrasepsi[Integer.parseInt(jobj.getString("riwayatkont"))]);
                    lines.add(eachline);

                    String kodeimun[] = {
                            "Pemberian imunisais TT1 pada tahun ",
                            "Pemberian imunisais TT2 pada tahun ",
                            "Pemberian imunisais TT3 pada tahun ",
                            "Pemberian imunisais TT4 pada tahun ",
                            "Pemberian imunisais TT5 pada tahun "};

                    String kenilaiimun[] = {
                            "imunisasiTT1",
                            "imunisasiTT2",
                            "imunisasiTT3",
                            "imunisasiTT4",
                            "imunisasiTT5"
                    };

                    for (int i = 0; i < kodeimun.length; i++) {
                        String val = jobj.getString(kenilaiimun[i]);
                        if (!(val.equals("") || val.equals("-1"))) {
                            eachline = new HashMap<>();
                            eachline.put("line1", kodeimun[i]);
                            eachline.put("line2", val);
                            lines.add(eachline);
                        }
                    }

                    SimpleAdapter adapter = new SimpleAdapter(
                            getActivity(),
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

        RequestHttp.Setelah setdatakeluhan = new RequestHttp.Setelah() {
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
                            getActivity(),
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

        RequestHttp.Setelah setdatapemeriksaan = new RequestHttp.Setelah() {
            @Override
            public void Lakukan(String s) {
                try {
                    JSONObject jobj = new JSONObject(s);
                    List<HashMap<String,String>> lines = new ArrayList<>();
                    HashMap<String,String> eachline = new HashMap<>();

                    String key[] = {
                            "kembar",
                            "suhutubuh",
                            "tekdarsistol",
                            "tekdardiastol",
                            "beratbadanlama",
                            "beratbadan",
                            "tinggibadan",
                            "lila",
                            "tfu"
                    };

                    String title[] = {
                            "Jumlah janin",
                            "Suhu tubuh (derajat celcius)",
                            "Tekanan darah sistol (mm)",
                            "Tekanan darah diastol (Hg)",
                            "Berat badan sebelum hamil (Kg)",
                            "Berat badan (Kg)",
                            "LILA (Cm)",
                            "TFU (Cm)",
                            "Tinggi badan (Cm)",
                    };

                    String kodekeadaan[] = {
                            "Baik",
                            "Sedang",
                            "Tanpa Anemis"
                    };

                    eachline = new HashMap<>();
                    eachline.put("line1", "Keadaan umum");
                    eachline.put("line2", kodekeadaan[Integer.parseInt(jobj.getString("keadaanumum"))]);
                    lines.add(eachline);

                    String kodekeadaan2[] = {
                            "Tidak ada",
                            "Bengkak muka/ Tungkai",
                            "Kembar air",
                            "Kejang-kejang",
                    } ;

                    eachline = new HashMap<>();
                    eachline.put("line1", "Keadaan khusus");
                    eachline.put("line2", kodekeadaan2[Integer.parseInt(jobj.getString("keadaankhusus"))]);
                    lines.add(eachline);

                    for (int i = 0; i < key.length; i++) {
                        if (!jobj.getString(key[i]).equals("-1")) {
                            eachline = new HashMap<>();
                            eachline.put("line1", title[i]);
                            eachline.put("line2", jobj.getString(key[i]));
                            lines.add(eachline);
                        }
                    }

                    eachline = new HashMap<>();
                    eachline.put("line1", "Golongan darah");
                    eachline.put("line2", kodekeadaan2[Integer.parseInt(jobj.getString("goldar"))]);
                    lines.add(eachline);

                    String preja = "";
                    String kodejanin[] = {
                            "Belum terdeteksi",
                            "Tidak terdeteksi",
                            "Lintang",
                            "Sungsang",
                            "Punggung kiri",
                            "Punggung kanan",
                            "Kepala belum masuk pintu atas panggul",
                            "Kepala sudah masuk pintu atas panggul"
                    };

                    if (!jobj.getString("presentasijanin").equals("[]")){
                        for (String x:toList(jobj.getString("presentasijanin"))) {
                            preja += kodejanin[Integer.parseInt(x)]+", ";
                        }
                    }

                    eachline = new HashMap<>();
                    eachline.put("line1", "Presentasi janin");
                    eachline.put("line2", preja);
                    lines.add(eachline);

                    eachline = new HashMap<>();
                    eachline.put("line1", "Detak jantung janin");
                    if(!jobj.getString("detakjantungjanin").equals("-1"))
                    eachline.put("line2", jobj.getString("detakjantungjanin"));
                    else
                    eachline.put("line2", "Belum terdeteksi");
                    lines.add(eachline);

                    String kodegerakjanin[] = {
                            "Belum terdeteksi",
                            "Tidak terdeteksi",
                            "Kurang dari 4 kali",
                            "Antara 4 - 10 kali",
                            "Lebih dari 10 kali",
                    };

                    eachline = new HashMap<>();
                    eachline.put("line1", "Gerak janin 2 jam terahir");
                    eachline.put("line2", kodegerakjanin[Integer.parseInt(jobj.getString("gerakjanin"))]);
                    lines.add(eachline);

                    String kodeprote[]={
                            "Tidak diperiksa",
                            "Negatif",
                            "+1",
                            "+2",
                            "Lebih dari+2",
                    };

                    eachline = new HashMap<>();
                    eachline.put("line1", "Proteinuri");
                    eachline.put("line2", kodeprote[Integer.parseInt(jobj.getString("proteinuri"))]);
                    lines.add(eachline);

                    String kodeglu[] = {
                            "Tidak diperiksa",
                            "Reduksi +",
                            "Reduksi -",
                    };

                    eachline = new HashMap<>();
                    eachline.put("line1", "Glukosa");
                    eachline.put("line2", kodeglu[Integer.parseInt(jobj.getString("glukosa"))]);
                    lines.add(eachline);

                    eachline = new HashMap<>();
                    eachline.put("line1", "Pemeriksaan Hemoglobin");
                    eachline.put("line2", jobj.getString("pemeriksaanhb"));
                    lines.add(eachline);

                    if (!jobj.getString("pemeriksaanlablain").equals("-1")) {
                        eachline = new HashMap<>();
                        eachline.put("line1", "Pemeriksaan lainnya");
                        eachline.put("line2", jobj.getString("pemeriksaanlablain"));
                        lines.add(eachline);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(
                            getActivity(),
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

        RequestHttp.Setelah setdatatindakan = new RequestHttp.Setelah() {
            @Override
            public void Lakukan(String s) {
                Log.d("PHP", "Lakukan: "+s);
                try {
                    JSONObject jobj = new JSONObject(s);
                    List<HashMap<String,String>> lines = new ArrayList<>();
                    HashMap<String,String> eachline = new HashMap<>();

                    String kodeimun[] = {
                            "Imunisais TT1",
                            "Imunisais TT2",
                            "Imunisais TT3",
                            "Imunisais TT4",
                            "Imunisais TT5",
                    };
                    String imuntt = "";
                    if (!jobj.getString("imunTT").equals("[]")){
                        for (String x:toList(jobj.getString("imunTT"))) {
                            imuntt += kodeimun[Integer.parseInt(x)]+", ";
                        }
                    }

                    eachline = new HashMap<>();
                    eachline.put("line1", "Pemberian imunisasi");
                    eachline.put("line2", imuntt);
                    lines.add(eachline);

                    eachline = new HashMap<>();
                    eachline.put("line1", "Imunisasi lain");
                    eachline.put("line2", jobj.getString("imunLain"));
                    lines.add(eachline);


                    if (!jobj.getString("tabletFE").equals("-1")) {
                        eachline = new HashMap<>();
                        eachline.put("line1", "Pemberian Tablet FE");
                        eachline.put("line2", jobj.getString("tabletFE"));
                        lines.add(eachline);
                    }

                    eachline = new HashMap<>();
                    eachline.put("line1", "Tindakan lain");
                    eachline.put("line2", jobj.getString("tindaklain"));
                    lines.add(eachline);

                    eachline = new HashMap<>();
                    eachline.put("line1", "Saran");
                    eachline.put("line2", jobj.getString("saran"));
                    lines.add(eachline);

                    SimpleAdapter adapter = new SimpleAdapter(
                            getActivity(),
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

        switch (position){
            case 1:
                new RequestHttp(data,
                        "http://sahabatbundaku.org/request_android/get_pemeriksaan.php",
                        setriawayatkehamilan,
                        pd)
                        .execute();
                break;
            case 2:
                new RequestHttp(data,
                        "http://sahabatbundaku.org/request_android/get_penyakit.php",
                        setdatapenyakit,
                        pd)
                        .execute();
                break;
            case 3:
                new RequestHttp(data,
                        "http://sahabatbundaku.org/request_android/get_keluhan.php",
                        setdatakeluhan,
                        pd)
                        .execute();
                break;
            case 4:
                new RequestHttp(data,
                        "http://sahabatbundaku.org/request_android/get_umum.php",
                        setdatapemeriksaan,
                        pd)
                        .execute();
                break;
            case 5:
                new RequestHttp(data,
                        "http://sahabatbundaku.org/request_android/get_tindakan.php",
                        setdatatindakan,
                        pd)
                        .execute();
                break;


        }

        return rootView;

    }
}
