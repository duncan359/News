package com.duncan.read.News.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import com.duncan.read.BaseActivity;
import com.duncan.read.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class SplashScreenActivity extends BaseActivity {
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        this.context = this;
        if(!checkNetwork())
            showToast(this.getString(R.string.txt_titleintenet));
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
        if(event.reponse.equals("Success"))
        {
            finish();
            navigator.startMainPage(context);
        }
    }



}
