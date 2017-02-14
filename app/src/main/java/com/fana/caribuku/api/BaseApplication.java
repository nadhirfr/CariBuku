package com.fana.caribuku.api;

import com.fana.caribuku.BuildConfig;
import com.gamatechno.gtfwm.Gtfw;

/**
 * Created by arifcebe on 5/25/16.
 */
public class BaseApplication extends Gtfw {

    @Override
    public void onCreate() {
        super.onCreate();
        gtfw = this;
        this.setSSLOn();

        if(BuildConfig.DEBUG){
            Gtfw.getInstance().setDebug(true);
        }else{
            Gtfw.getInstance().setDebug(false);
        }
        this.setKey();
    }
}
