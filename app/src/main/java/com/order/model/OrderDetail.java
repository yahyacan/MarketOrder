package com.order.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class OrderDetail implements Parcelable {

    private String orderDetail;
    private BigDecimal summaryPrice;

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public BigDecimal getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(BigDecimal summaryPrice) {
        this.summaryPrice = summaryPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderDetail);
        dest.writeSerializable(this.summaryPrice);
    }

    public OrderDetail() {
    }

    protected OrderDetail(Parcel in) {
        this.orderDetail = in.readString();
        this.summaryPrice = (BigDecimal) in.readSerializable();
    }

    public static final Parcelable.Creator<OrderDetail> CREATOR = new Parcelable.Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };
}
