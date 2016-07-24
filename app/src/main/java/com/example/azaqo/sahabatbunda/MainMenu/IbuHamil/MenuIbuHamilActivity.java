package com.example.azaqo.sahabatbunda.MainMenu.IbuHamil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.example.azaqo.sahabatbunda.MainMenu.IbuHamil.DataPemeriksaan.DaftarKehamilan;
import com.example.azaqo.sahabatbunda.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuIbuHamilActivity extends AppCompatActivity {

    @OnClick(R.id.card_datakehamilan)
    public void gotoibuhamil(){
        startActivity(new Intent(this,DaftarKehamilan.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ibu_hamil);
        ButterKnife.bind(this);
    }
}
