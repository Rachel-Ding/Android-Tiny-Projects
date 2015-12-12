package com.dingding.guessnumber;

/*
 *  2.实现一个猜数字游戏
 *      1.程序生成0到100之间的整数
 *      2.用户输入0到100之间的整数
 *      3.程序验证用户输入是否正确，给予提示，如视频所示
 *      On 2015-11-26
 */
//------------修改版------------------------------------

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView3;//结果提示
    private EditText editText;//输入框
    private Button btnSubmit;//提交按钮
    private Button btnChange;//重新生成数按钮
    private Integer number;//生成的随机数
    private Integer guessNum;//输入的数字
    private String result;//结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //生成随机数
        Random random=new Random();
        number = random.nextInt(100);

        init();//初始化

        //提交按钮
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessNum = Integer.parseInt(editText.getText().toString());

                showResult(guessNum);//显示结果

            }
        });

        //换数字按钮
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                number = random.nextInt(100);

                editText.setText("");//置空
                textView3.setText("");//置空
            }
        });

    }

    private void init() {
        //已在布局文件中设置只能输入两位数以内的整数
        editText = (EditText) findViewById(R.id.editText);
        textView3 = (TextView) findViewById(R.id.textView3);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnChange = (Button) findViewById(R.id.btnChange);
    }

    //判断结果的大小
    private void showResult(Integer guessNum) {

        if(guessNum==number)
            result = "正确";
        else if(guessNum>number)
            result = "输入的数字大了";
        else if (guessNum<number)
            result = "输入的数字小了";

        textView3.setText(result);//显示结果
    }


}
