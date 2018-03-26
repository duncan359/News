package com.duncan.read;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Base Fragment contains all shared functions for fragments
 * Created by Duncan on 3/1/2016.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retain instance on activity recreation
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);
    }

    private void bindViews(View view) {

        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumePresenter();
    }

    @Override
    public void onPause() {
        super.onPause();
        onPausePresenter();
    }

    /**
     * Call Presenter onResume in this method to set the view
     */
    protected abstract void onResumePresenter();

    /**
     * Call Presenter's onPause in this method
     */
    protected abstract void onPausePresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showToast(@NonNull String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public final static boolean isValidEmail(CharSequence target) {

        return ((target != null) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }
}
