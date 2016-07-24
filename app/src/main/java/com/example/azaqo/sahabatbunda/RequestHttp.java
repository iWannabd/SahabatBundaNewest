package com.example.azaqo.sahabatbunda;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by azaqo on 6/25/2016.
 */
public class RequestHttp extends AsyncTask<String,Void,String> {
    HashMap<String,String> data;
    String url;
    Setelah listen;
    ProgressDialog pd = null;

    public interface Setelah{
        void Lakukan(String s);
    }

    public RequestHttp(HashMap<String, String> data, String url, Setelah listen) {
        this.data = data;
        this.url = url;
        this.listen = listen;
    }

    public RequestHttp(HashMap<String, String> data, String url, Setelah listen, ProgressDialog pd) {
        this.data = data;
        this.url = url;
        this.listen = listen;
        this.pd = pd;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (pd!=null) pd.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Log.d("PHP", "posted data: "+data);
            Okdeh ok  = new Okdeh();
            return ok.doPostRequestData(url,data);
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("PHP", "HTtpRequest: "+s);
            listen.Lakukan(s);
        if(pd!=null) pd.dismiss();
    }
}
