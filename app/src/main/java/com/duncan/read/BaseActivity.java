package com.duncan.read;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.duncan.read.domain.presentation.navigator.Navigator;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Base Activity for all activities in the app
 * <p/>
 * Created by Duncan on 23/3/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {


    //public AppPreference appPreference = new AppPreference(this);

    public Navigator navigator= new Navigator();

   // public Logger logger;

    public EventBus eventBus = EventBus.getDefault();;
   // private ApplicationComponent applicationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        applicationComponent = ((NukashApplication) getApplication()).getApplicationComponent();
//        applicationComponent.inject(this);


    }

    public void showToast(@NonNull String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

//    public ApplicationComponent getApplicationComponent() {
//        return applicationComponent;
//    }

    protected void replaceFragment(@IdRes int container, Fragment fragment, boolean addToBackStack) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(container, fragment, fragment.getClass().getName());

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.commit();
    }

    /**
     * Pops fragment from backstack if any
     */
    protected void popFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {

            fragmentManager.popBackStack();
        }
    }



    protected void bindViews() {
        ButterKnife.bind(this);
    }
}
