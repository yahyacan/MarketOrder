package com.order.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Order implements Parcelable {

    private String date;
    private String month;
    private String marketName;
    private String orderName;
    private BigDecimal productPrice;
    private String productState;
    private OrderDetail productDetail;
    private boolean visible;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public OrderDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(OrderDetail productDetail) {
        this.productDetail = productDetail;
    }

    public String getFullDate(){
        return this.month + this.date;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.month);
        dest.writeString(this.marketName);
        dest.writeString(this.orderName);
        dest.writeSerializable(this.productPrice);
        dest.writeString(this.productState);
        dest.writeParcelable(this.productDetail, flags);
        dest.writeByte(this.visible ? (byte) 1 : (byte) 0);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.date = in.readString();
        this.month = in.readString();
        this.marketName = in.readString();
        this.orderName = in.readString();
        this.productPrice = (BigDecimal) in.readSerializable();
        this.productState = in.readString();
        this.productDetail = in.readParcelable(OrderDetail.class.getClassLoader());
        this.visible = in.readByte() != 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}
