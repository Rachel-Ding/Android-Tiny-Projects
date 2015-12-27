package com.dingding.messageinputactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MessageInputActivity extends AppCompatActivity implements OnClickListener {

    private EditText editText;
    private Button btnConfirm;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_input);

        //初始化
        editText = (EditText) findViewById(R.id.editText);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //按钮监听
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //intent设置
        Intent intent = new Intent(MessageInputActivity.this, MainActivity.class);

        switch (v.getId()) {
            //确认按钮
            case R.id.btnConfirm:
                //若有输入内容，则传输到另一activity
                if (!TextUtils.isEmpty(editText.getText())) {
                    intent.putExtra("data", "您今天的愿望是:" + '\n' + editText.getText().toString());
                }
                setResult(2, intent);
                break;

            //取消按钮
            case R.id.btnCancel:
                editText.setText("");
                setResult(3, intent);
                break;
        }

        finish();
    }
}
