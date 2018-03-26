package com.duncan.read;

/**
 * Base Presenter to receive activity/fragment life cycle methods
 * <p/>
 * Created by Sohail on 3/1/2016.
 */
public abstract class BasePresenter {

    /**
     * Should be called from fragment/activity onPause
     */
    public abstract void onPause();

    /**
     * Should be called from fragment/activity onResume
     */
    public abstract void onResume(BaseView view);

    protected abstract void showLoading();

    protected abstract void hideLoading();
}
