package com.dingding.smsreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChooseTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnContent;
    private Button btnAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type);

        btnContent = (Button) findViewById(R.id.btnContent);
        btnAddress = (Button) findViewById(R.id.btnAddress);

        btnContent.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContent:
                Intent intentContent = new Intent(ChooseTypeActivity.this, InterceptBodyActivity.class);
                startActivity(intentContent);
                break;
            case R.id.btnAddress:
                Intent intentAddress = new Intent(ChooseTypeActivity.this, InterceptAddressActivity.class);
                startActivity(intentAddress);
                break;
            default:
                break;
        }
    }
}
