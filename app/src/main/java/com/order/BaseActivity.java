package com.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.widget.Toast;

import com.order.listener.ConfirmListener;
import com.order.utils.SharedPreferenceUtils;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseActivity extends AppCompatActivity{

    SweetAlertDialog progressDialog;
    public SharedPreferenceUtils preferences;
    int width=0,height=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();  // deprecated
        height = display.getHeight();  // deprecated


        ButterKnife.bind(this);
        preferences = SharedPreferenceUtils.getInstance(this);

        onViewReady(savedInstanceState, getIntent());
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this);
        super.onDestroy();
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected void showProgressDialog(){
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected SweetAlertDialog showmessageBox(String title, String message, String confirmButtonText, int messageType, final ConfirmListener confirmListener) {
        SweetAlertDialog messageBox =  new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(confirmButtonText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        confirmListener.confirm(sDialog);
                    }
                });
        messageBox.show();
        return messageBox;
    }

    protected abstract int getContentView();

}