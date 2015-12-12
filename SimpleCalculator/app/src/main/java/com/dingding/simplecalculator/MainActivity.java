package com.dingding.simplecalculator;

/*
* 3.实现一个简单的计算器
*   a)能够支持加减乘除运算
*   b)不用很复杂，只要能够支持整数运算即可
*  On 2015-11-30
*/
/*---------------修改版---------------------
 *  修改数据类型（Interger转为Long型）；
 *  修正除数为0的情况；
 *  增加科学计数法；
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private TextView textView;
    private Button num0;
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;
    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;
    private Button btnClear;
    private Button btnEqual;
    private Long result=0L;//计算结果
    private Long firstNum=0L,secondNum=0L;//前一个数值和当下显示的数值
    private Integer method=0;//判断加减乘除
    private Integer isNum=1;//判断是否连续输入数字
    private Integer errorNum=0;//判断错误

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化

        initEvent();//初始化监听器

    }

    private void initView() {

        textView = (TextView) findViewById(R.id.textView);
        num0 = (Button) findViewById(R.id.num0);
        num1 = (Button) findViewById(R.id.num1);
        num2 = (Button) findViewById(R.id.num2);
        num3 = (Button) findViewById(R.id.num3);
        num4 = (Button) findViewById(R.id.num4);
        num5 = (Button) findViewById(R.id.num5);
        num6 = (Button) findViewById(R.id.num6);
        num7 = (Button) findViewById(R.id.num7);
        num8 = (Button) findViewById(R.id.num8);
        num9 = (Button) findViewById(R.id.num9);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnEqual = (Button) findViewById(R.id.btnEqual);

    }

    private void initEvent() {
        //设置监听器
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            //清除按钮
            case R.id.btnClear:
                result=0L;
                firstNum=0L;
                secondNum=0L;
                method=0;
                isNum=0;
                break;

            //加减乘除运算
            case R.id.btnAdd:
                isNum=0;
                method = 1;
                secondNum = result;
                break;
            case R.id.btnSub:
                isNum=0;
                method = 2;
                secondNum = result;
                break;
            case R.id.btnMul:
                isNum=0;
                method = 3;
                secondNum = result;
                break;
            case R.id.btnDiv:
                isNum=0;
                method = 4;
                secondNum = result;
                break;

            /*当按下数字时，若前面一个也是数字，那么则是将数字按位数相连
            *否则进行计算
            */
            case R.id.num0:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+0;
                Calculate();
                break;
            case R.id.num1:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+1;
                Calculate();
                break;
            case R.id.num2:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+2;
                Calculate();
                break;
            case R.id.num3:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+3;
                Calculate();
                break;
            case R.id.num4:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+4;
                Calculate();
                break;
            case R.id.num5:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+5;
                Calculate();
                break;
            case R.id.num6:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+6;
                Calculate();
                break;
            case R.id.num7:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+7;
                Calculate();
                break;
            case R.id.num8:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+8;
                Calculate();
                break;
            case R.id.num9:
                firstNum=firstNum*isNum+result*(1-isNum);
                secondNum=secondNum*10*isNum+9;
                Calculate();
                break;

            //‘=’等于按钮，令当下数值为计算结果
            case R.id.btnEqual:
                firstNum=secondNum;
                secondNum=result;
                isNum=0;
                method=0;
                break;

            default:
                break;
                }
        if(1==errorNum) {
            textView.setText("Error");//报错
            errorNum = 0;
        } else if(secondNum>10e10)
            //当数值大于10e10时，用科学计数法表示
            textView.setText(String.valueOf(new DecimalFormat("00E0").format(secondNum)));
            else
            textView.setText(String.valueOf(secondNum));//显示当下数字
        }

    //计算
    public void Calculate() {
        if(0==method){
            result=secondNum;
        }else {
            switch (method) {
                case 1:
                    result = firstNum + secondNum;
                    break;
                case 2:
                    result = firstNum - secondNum;
                    break;
                case 3:
                    result = firstNum * secondNum;
                    break;
                case 4:
                    //考虑除数为0的情况
                    if(0==secondNum) {
                        firstNum = result = 0L;
                        errorNum = 1;
                    } else
                        result = firstNum / secondNum;

                    break;
                default:
                    break;
            }
        }
        isNum=1;
    }

}
