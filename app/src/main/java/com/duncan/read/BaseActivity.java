package com.duncan.read;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.duncan.read.domain.navigator.Navigator;

import org.greenrobot.eventbus.EventBus;



public abstract class BaseActivity extends AppCompatActivity {



    public Navigator navigator= new Navigator();
    public EventBus eventBus = EventBus.getDefault();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(@NonNull String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public EventBus getEventBus() {
        return eventBus;
    }


}
