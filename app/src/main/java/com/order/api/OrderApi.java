package com.order.api;

import java.util.List;

import com.order.model.Order;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderApi {

    @GET("/")
    Call<List<Order>> findAllOrder();

}