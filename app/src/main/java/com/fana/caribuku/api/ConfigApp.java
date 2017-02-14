package com.fana.caribuku.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dyangalih on 4/6/16.
 */
public class ConfigApp {
    private static ConfigApp instance;
    private static Map<String, String> config;


    public static ConfigApp getInstance() {
        if(instance==null){
            instance = new ConfigApp();
            setConfig();
        }
        return instance;
    }

    private static void setConfig(){
        config = new HashMap();
        config.put("address", "http://niya.ga/cari-buku/bo/");
        config.put("salt", config.get("address") + "?mod=login_default&sub=ReqSalt&act=Do&typ=json");
        config.put("login", config.get("address") + "?mod=login_default&sub=login&act=do&typ=json");
        config.put("logout", config.get("address") + "?mod=login_default&sub=logout&act=do&typ=json");
        config.put("check_login", config.get("address") + "?mod=login_default&sub=CheckLogin&act=do&typ=json");
    }

    public String getSalt() {
        return config.get("salt");
    }

    public String getLogin() {
        return config.get("login");
    }

    public String getLogout() {
        return config.get("logout");
    }

    public String getCheckLogin(){
        return config.get("check_login");
    }
}
