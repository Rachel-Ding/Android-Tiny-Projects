package com.dingding.animatefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dingding on 2016/1/18.
 */
public class FragmentOne extends Fragment implements View.OnClickListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_layout,null);

        rootView.findViewById(R.id.btnAnotherFragment).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.animator_enter,R.animator.animator_exit,R.animator.animator_enter,R.animator.animator_exit)
                .addToBackStack("OtherFragment")
                .replace(R.id.fragment, new OtherFragment())
                .commit();
    }
}
