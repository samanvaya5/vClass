package com.theevilbees.vclass;

import android.app.Application;

public class Global extends Application {
    private String email;
    public void setemail(String s){
        email=s;
    }
    public String getemail()
    {
        return email;
    }
}
