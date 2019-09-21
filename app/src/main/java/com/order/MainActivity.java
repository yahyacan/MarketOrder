package com.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.order.adapter.OrderAdapter;
import com.order.api.ApiClient;
import com.order.api.OrderApi;
import com.order.listener.ConfirmListener;
import com.order.listener.ItemClickListener;
import com.order.model.Order;
import com.order.utils.SPKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv) RecyclerView recyclerView;
    @BindView(R.id.btnMyOrders) Button btnMyOrders;
    @BindView(R.id.btnLogout) Button btnLogout;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        findAllOrder();
    }

    public void onMyOrders(View v){
        findAllOrder();
    }

    public void onLogout(View v){

        showChoiceMessageBox(getString(R.string.warning), getString(R.string.sure_logout), getString(R.string.yes), getString(R.string.no), SweetAlertDialog.WARNING_TYPE,
                new ConfirmListener() {
                    @Override
                    public void confirm(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        preferences.setValue(SPKeys.REMEMBER_ME,false);
                        preferences.setValue(SPKeys.USERNAME,"");
                        preferences.setValue(SPKeys.PASSWORD,"");
                        finish();
                    }
                },
                new ConfirmListener() {
                    @Override
                    public void confirm(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    @Override
    protected int getContentView() {
        return R.layout.main;
    }

    private void findAllOrder(){
        showProgressDialog();
        OrderApi orderApi = ApiClient.getClient(getApplicationContext()).create(OrderApi.class);
        Call<List<Order>> call = orderApi.findAllOrder();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();

                    List<String> uniqueKeyList = new ArrayList<>();
                    for (int i = 0; i < orders.size(); i++) {
                        Order order = orders.get(i);
                        String key = order.getMonth() + order.getDate();
                        if (uniqueKeyList.contains(key)){
                            order.setVisible(false);
                        }else{
                            order.setVisible(true);
                            uniqueKeyList.add(key);
                        }
                    }

                    Collections.sort(orders, new SortDate());

                    OrderAdapter adapter = new OrderAdapter(MainActivity.this, orders, new ItemClickListener() {
                        @Override
                        public void onDetail(View view, int position) {
                        RelativeLayout rlDetail = view.findViewById(R.id.rlDetail);
                        if (rlDetail.getVisibility() == View.VISIBLE){
                            rlDetail.setVisibility(View.GONE);
                        }else{
                            rlDetail.setVisibility(View.VISIBLE);
                        }
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    class SortDate implements Comparator<Order>{
        public int compare(Order a, Order b){
            return a.getFullDate().compareTo(b.getFullDate());
        }
    }
}
