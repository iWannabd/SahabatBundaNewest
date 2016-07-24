package com.example.azaqo.sahabatbunda;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentSignup extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private HashMap<String,String> data = new HashMap<>();
    OnNextClicked callback;

    public PlaceholderFragmentSignup() {
    }

    public interface OnNextClicked{
        void onClick();
        void saveData(HashMap<String,String> data);
        void upload();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragmentSignup newInstance(int sectionNumber) {
        PlaceholderFragmentSignup fragment = new PlaceholderFragmentSignup();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (OnNextClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnNextButtonPressed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int position = getArguments().getInt(ARG_SECTION_NUMBER);

        switch (position){
            case 1:
                final View rootView = inflater.inflate(R.layout.fragment_signup_ibu, container, false);
                Button btn = (Button) rootView.findViewById(R.id.btn_next);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!validatesaveformdataibu(rootView))
                            Toast.makeText(getActivity(),R.string.data_not_valid,Toast.LENGTH_SHORT).show();
                        else {
                            callback.saveData(data);
                            callback.onClick();
                        }
                    }
                });
                return rootView;
            case 2:
                final View rootView1 = inflater.inflate(R.layout.fragment_signup_ayah, container, false);
                Button btn1 = (Button) rootView1.findViewById(R.id.btn_login);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!validateformdataayah(rootView1))
                            Toast.makeText(getActivity(),R.string.data_not_valid,Toast.LENGTH_SHORT).show();
                        else {
                            callback.saveData(data);
                            callback.upload();
                        }
                    }
                });
                return rootView1;
        }

        return null;
    }

    public boolean validatesaveformdataibu(View rootView){
        String momkeys[] = {"email","username","passwd","nama","umur","sukubangsa","agama","rtrw","kecamatan","pendidikan","pekerjaan","nomorhp","alamat","keludes","kota"};
        EditText momsdata[] = {
            (EditText) rootView.findViewById(R.id.email)
            ,(EditText) rootView.findViewById(R.id.username)
            ,(EditText) rootView.findViewById(R.id.password)
            ,(EditText) rootView.findViewById(R.id.namaIbu)
            ,(EditText) rootView.findViewById(R.id.umuribu)
            ,(EditText) rootView.findViewById(R.id.sukBangibu)
            ,(EditText) rootView.findViewById(R.id.agamaibu)
            ,(EditText) rootView.findViewById(R.id.rtrwibu)
            ,(EditText) rootView.findViewById(R.id.kecamatanibu)
            ,(EditText) rootView.findViewById(R.id.pendidikanibu)
            ,(EditText) rootView.findViewById(R.id.pekerjaanibu)
            ,(EditText) rootView.findViewById(R.id.noHPibu)
            ,(EditText) rootView.findViewById(R.id.alamatibu)
            ,(EditText) rootView.findViewById(R.id.kelurahandesa)
            ,(EditText) rootView.findViewById(R.id.kotaibu)
        };

        for (int i = 0; i < momsdata.length; i++) {
            if (!momsdata[i].getText().toString().isEmpty())
            data.put(momkeys[i],momsdata[i].getText().toString());
            else return false;
        }
        return true;
    }

    public boolean validateformdataayah(View rootView){
        String dadkeys[] = {"namaayah","umurayah","sukBangayah","agamaayah","rtrwayah","kecamatanayah","pendidikanayah","pekerjaanayah","noHPayah","alamatayah","kelurahanayah","kotaayah"};
        EditText dadsdata[] = {
                (EditText) rootView.findViewById(R.id.namaayah),
                (EditText) rootView.findViewById(R.id.umurayah),
                (EditText) rootView.findViewById(R.id.sukBangayah),
                (EditText) rootView.findViewById(R.id.agamaayah),
                (EditText) rootView.findViewById(R.id.rtrwayah),
                (EditText) rootView.findViewById(R.id.kecamatanayah),
                (EditText) rootView.findViewById(R.id.pendidikanayah),
                (EditText) rootView.findViewById(R.id.pekerjaanayah),
                (EditText) rootView.findViewById(R.id.noHPayah),
                (EditText) rootView.findViewById(R.id.alamatayah),
                (EditText) rootView.findViewById(R.id.kelurahanayah),
                (EditText) rootView.findViewById(R.id.kotaayah)
        };
        for (int i = 0; i < dadsdata.length; i++) {
            if(!dadsdata[i].getText().toString().isEmpty())
            data.put(dadkeys[i],dadsdata[i].getText().toString());
            else return false;
        }
        return true;
    }

}
