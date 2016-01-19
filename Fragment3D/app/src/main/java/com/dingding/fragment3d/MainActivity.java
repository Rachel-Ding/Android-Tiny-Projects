package com.dingding.fragment3d;

/*
* 3.用3D 翻转的效果切换Fragment
* 用3D 翻转的效果切换Fragment
* On 2016-1-19
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //载入FragmentOne
        getFragmentManager().beginTransaction().add(R.id.fragment, new FragmentOne()).commit();
    }
}
