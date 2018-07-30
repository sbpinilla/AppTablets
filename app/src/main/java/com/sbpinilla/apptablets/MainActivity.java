package com.sbpinilla.apptablets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.list_item) != null) {

            mTwoPane = true;

            if (savedInstanceState == null) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_item, new ItemsFragment(),"ELEMENTO")
                        .commit();
            }else{
                mTwoPane=false;
            }

        }


    }
}
