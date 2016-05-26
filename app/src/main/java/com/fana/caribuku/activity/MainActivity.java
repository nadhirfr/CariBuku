package com.fana.caribuku.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fana.caribuku.R;
import com.fana.caribuku.api.ConfigApp;
import com.gamatechno.gtfwm.Gtfw;
import com.gamatechno.gtfwm.log.SysLog;
import com.gamatechno.gtfwm.network.Rest;
import com.gamatechno.gtfwm.security.response.Auth;
import com.google.gson.Gson;

import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    TextView et_username;
    @BindView (R.id.et_password)
    TextView et_passowrd;
    private Rest rest;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysLog.getInstance().sendLog("Main Activity", "test");
        ButterKnife.bind(this);
        gson = new Gson();
        checkLogin();
        SysLog.getInstance().sendLog("Main Activity", Gtfw.getInstance().getAppKey(getApplicationContext()));
    }

    private void emptyForm(){
        et_username.setText("");
        et_passowrd.setText("");
    }

    private void checkLogin() {
        rest = new Rest(MainActivity.this) {
            @Override
            protected void onRestResponse(String response) {
                SysLog.getInstance().sendLog("Status Login", response);
            }

            @Override
            protected void onRestError(String error) {
                SysLog.getInstance().sendLog("Status Login", error);
            }

            @Override
            protected Map<String, String> setParams() {
                return null;
            }
        };

        rest.get(ConfigApp.getInstance().getCheckLogin());
    }

    @OnClick(R.id.btn_logout)
    void onLogout() {
        (new Auth(getApplicationContext()) {

            @Override
            protected void onSuccess(Boolean status) {
                if (status) {
                    SysLog.getInstance().sendLog("Auth", "Logout Success");
                    checkLogin();
                } else {
                    SysLog.getInstance().sendLog("Auth", "Logout Failed");
                }
            }

            @Override
            protected void onFailed(String error) {
                SysLog.getInstance().sendLog("Auth", "Logout Failed");
            }
        }).setLogout(ConfigApp.getInstance().getLogout());
    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {
        (new Auth(MainActivity.this) {
            @Override
            protected void onSuccess(Boolean status) {
                if (status) {
                    SysLog.getInstance().sendLog("Auth", "Login Success");
                    checkLogin();
                    emptyForm();
                } else {
                    SysLog.getInstance().sendLog("Auth", "Login Failed");
                }
            }

            @Override
            protected void onFailed(String error) {
                SysLog.getInstance().sendLog("Auth", "Logout Failed");
            }
        }).setLogin(ConfigApp.getInstance().getSalt(), ConfigApp.getInstance().getLogin(), et_username.getText().toString(), et_passowrd.getText().toString());
    }
}
