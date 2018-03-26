package com.duncan.read.News.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.duncan.read.BaseActivity;
import com.duncan.read.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {
    Context context;
    public static Intent getCallingIntent(Context context) {

        return new Intent(context, MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        // TODO: Remove the redundant calls to getSupportActionBar()
        //       and use variable actionBar instead
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(this.getString(R.string.txt_title));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainFragment.EventGetResult event) {
        //TODO pass inbox item to activity
        if(event.reponse.equals("Success"))
        {
            //finish();
            navigator.startComments(context,event.result);
        }
    }
}
