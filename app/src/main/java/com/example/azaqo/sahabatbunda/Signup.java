package com.example.azaqo.sahabatbunda;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;

public class Signup extends AppCompatActivity implements PlaceholderFragmentSignup.OnNextClicked {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        pd = new ProgressDialog(this);
        pd.setMessage("Sebentar...");

    }

    @Override
    public void onClick() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
    }

    HashMap<String,String> data = new HashMap<>();

    @Override
    public void saveData(HashMap<String, String> data) {
        this.data.putAll(data);
    }

    @Override
    public void upload() {
        new Req(data,"http://sahabatbundaku.org/request_android/registrasi.php").execute();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragmentSignup.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Data Ibu";
                case 1:
                    return "Data Suami";
            }
            return null;
        }
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
            super.onPostExecute(s);
            switch (s){
                case "1":
                    Toast.makeText(Signup.this,R.string.user_created,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(Signup.this,s,Toast.LENGTH_SHORT).show();
                    break;
            }
            pd.dismiss();
        }
    }
}
