package com.duncan.read;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    protected abstract void onResumePresenter();


    protected abstract void onPausePresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
