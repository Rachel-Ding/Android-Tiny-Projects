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
public class FragmentOne extends Fragment implements View.OnClickListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_one, null);
        //按钮监听
        rootView.findViewById(R.id.button1).setOnClickListener(this);
        return rootView;
    }

    /*两个Fragment顺时针转入转出*/
    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.animator_two_enter, R.animator.animator_one_exit)
                .replace(R.id.fragment, new FragmentTwo())
                .commit();

    }

}
