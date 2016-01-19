package com.dingding.fragment3d;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dingding on 2016/1/19.
 */
public class FragmentTwo extends Fragment implements View.OnClickListener {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_two, null);
        rootView.findViewById(R.id.button2).setOnClickListener(this);

        return rootView;
    }

    /*两个Fragment逆时针转入转出*/
    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.animator_one_enter, R.animator.animator_two_exit)
                .replace(R.id.fragment, new FragmentOne())
                .commit();
    }
}
