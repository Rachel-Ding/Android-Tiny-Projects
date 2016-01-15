package com.dingding.baiduwaimai.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingding.baiduwaimai.R;

/**
 * 订单界面
 */
public class DingdanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dingdan, container, false);

        return rootView;
    }
}
