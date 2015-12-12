package com.dingding.multiplechoice;

/*
* 2.做一个多项选择题程序
* On 2015-11-29
*/
//--------修改版---------------

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbBeijing,cbShijiazhuang,cbLuoyang,cbWuhan,cbBaoding,cbXingtai,cbShanghai,cbChongqing,cbHandan;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//初始化

        //按钮监听，并弹出通知Toast
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!cbBeijing.isChecked())&&(!cbLuoyang.isChecked())&&(!cbWuhan.isChecked())&&(!cbShanghai.isChecked())&&(!cbChongqing.isChecked())
                &&cbShijiazhuang.isChecked()&&cbBaoding.isChecked()&&cbXingtai.isChecked()&&cbHandan.isChecked()){
                    Toast.makeText(MainActivity.this,"选择正确",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"选择错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //初始化所需正确CheckBox选项，以及提交按钮
    private void init() {

        cbBeijing = (CheckBox) findViewById(R.id.cbBeijing);
        cbShijiazhuang = (CheckBox) findViewById(R.id.cbShijiazhuang);
        cbLuoyang = (CheckBox) findViewById(R.id.cbLuoyang);
        cbWuhan = (CheckBox) findViewById(R.id.cbWuhan);
        cbBaoding = (CheckBox) findViewById(R.id.cbBaoding);
        cbXingtai = (CheckBox) findViewById(R.id.cbXingtai);
        cbShanghai = (CheckBox) findViewById(R.id.cbShanghai);
        cbChongqing = (CheckBox) findViewById(R.id.cbChongqing);
        cbHandan = (CheckBox) findViewById(R.id.cbHandan);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }
}
