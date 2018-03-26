package com.duncan.read;

/**
 * Base view for all the views contains shared functions
 * <p/>
 * Created by Duncan on 21/3/2018.
 */
public interface BaseView {

    void onResume();

    void onPause();

    void showProgress();

    void hideProgress();
}
