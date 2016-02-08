package com.dingding.preferenceactivity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Dingding on 2016/2/8.
 */
public class MyPreferenceActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    PreferenceManager manager;
    CheckBoxPreference checkBoxPreference;
    ListPreference listPreference;
    EditTextPreference editTextPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示界面布局
        addPreferencesFromResource(R.xml.mypreference);

        manager = getPreferenceManager();

        checkBoxPreference = (CheckBoxPreference) manager.findPreference("checkbox");//对应的key
        Toast.makeText(getApplicationContext(), "当前状态为：" + checkBoxPreference.isChecked(), Toast.LENGTH_SHORT).show();

        listPreference = (ListPreference) manager.findPreference("list");
        Toast.makeText(getApplicationContext(), listPreference.getEntry() + " 所在洲：" + listPreference.getValue(), Toast.LENGTH_SHORT).show();

        editTextPreference = (EditTextPreference) manager.findPreference("edittext");
        //summary中显示输入的文字
        editTextPreference.setSummary(editTextPreference.getText());
        //事实监听，动态改变summary
        editTextPreference.setOnPreferenceChangeListener(this);
        Toast.makeText(getApplicationContext(), "输入的姓名为：" + editTextPreference.getText(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        editTextPreference.setSummary(newValue.toString());
        return true;
    }

}
