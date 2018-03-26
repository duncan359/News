package com.duncan.read.News.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


import com.duncan.read.BaseActivity;
import com.duncan.read.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class SplashScreen extends BaseActivity {
    Context context;

    public static Intent getCallingIntent(Context context) {

        return new Intent(context, SplashScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        this.context = this;
        if(!checkNetwork())
        Toast.makeText(this,R.string.txt_titleintenet+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean checkNetwork()
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SplashScreenFragment.EventGetResult event) {
        //TODO pass inbox item to activity
        if(event.reponse.equals("Success"))
        {
            finish();
            navigator.startMainPage(context);
        }
    }



}
