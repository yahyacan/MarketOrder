package com.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.order.listener.ConfirmListener;
import com.order.utils.SPKeys;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.swRememberMe) Switch swRememberMe;
    @BindView(R.id.btnLogin) Button btnLogin;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        if (preferences.getBoolanValue(SPKeys.REMEMBER_ME,false)){
            if (getString(R.string.static_username).equals(preferences.getStringValue(SPKeys.USERNAME,""))
                    && getString(R.string.static_password).equals(preferences.getStringValue(SPKeys.PASSWORD,""))){
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        }


    }

    public void onLogin(View view){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(username)){
            showmessageBox(getString(R.string.warning), getString(R.string.fill_username), getString(R.string.ok), SweetAlertDialog.WARNING_TYPE, new ConfirmListener() {
                @Override
                public void confirm(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    return;
                }
            });
        }else if (TextUtils.isEmpty(password)){
            showmessageBox(getString(R.string.warning), getString(R.string.fill_password), getString(R.string.ok), SweetAlertDialog.WARNING_TYPE, new ConfirmListener() {
                @Override
                public void confirm(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    return;
                }
            });
        }else {
            if (getString(R.string.static_username).equals(username) && getString(R.string.static_password).equals(password)){
                if (swRememberMe.isChecked()) {
                    preferences.setValue(SPKeys.REMEMBER_ME,true);
                    preferences.setValue(SPKeys.USERNAME,username);
                    preferences.setValue(SPKeys.PASSWORD,password);
                }else{
                    preferences.setValue(SPKeys.REMEMBER_ME,false);
                    preferences.setValue(SPKeys.USERNAME,"");
                    preferences.setValue(SPKeys.PASSWORD,"");
                }
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }else{
                showmessageBox(getString(R.string.error), getString(R.string.wrong_username_or_password), getString(R.string.ok), SweetAlertDialog.WARNING_TYPE, new ConfirmListener() {
                    @Override
                    public void confirm(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        return;
                    }
                });
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.login;
    }

}
