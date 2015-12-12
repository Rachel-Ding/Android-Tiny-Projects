package com.dingding.learncontext;

import android.app.Application;

/**
 * Created by Dingding on 2015/11/25.
 */
public class App extends Application {

    private String textData = "default";

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getTextData() {
        return textData;
    }


}
