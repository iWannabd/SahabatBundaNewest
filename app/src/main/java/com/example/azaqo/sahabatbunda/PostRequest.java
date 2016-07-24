package com.example.azaqo.sahabatbunda;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by azaqo on 6/24/2016.
 */
public class PostRequest extends AsyncTask<String,Void,String> {
    HashMap<String,String> data;
    String url;

    public PostRequest(HashMap<String, String> data, String url) {
        this.data = data;
        this.url = url;
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
}
