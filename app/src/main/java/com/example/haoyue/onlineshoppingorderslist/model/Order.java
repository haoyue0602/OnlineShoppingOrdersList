package com.example.haoyue.onlineshoppingorderslist.model;

import java.util.Date;
import java.util.UUID;

public class Order {
    /**
        *id
        *订单号
        *商品名称
        *快递单号
        *创建日期
        订单完成情况
        *产品数量
        产品描述
        *订单总价
        *产品类型
        *卖家名称
     */
    private UUID mid;
    private String mOrderNum;//get set 缺失
    private String mOrderTitle;
    private String mTrackingNum;
    private Date mDate;
    private boolean mFinished;
    private int mCount;
    private String description;
    private double mPrice;
    private String mType;
    private String mSellerName;

    public Order(){
        this(UUID.randomUUID());
    }
    public Order(UUID id){
        mid = id;
        mDate = new Date();
        mFinished = false;
    }

    public Order(String orderNum, String orderTitle, String trackingNum, boolean finished, int mCount, String description, double price, String type, String sellerName) {
        this(UUID.randomUUID());
        mOrderNum = orderNum;
        mOrderTitle = orderTitle;
        mTrackingNum = trackingNum;
        mFinished = finished;
        this.mCount = mCount;
        this.description = description;
        mPrice = price;
        mType = type;
        mSellerName = sellerName;
    }

    public Order(UUID mid, String orderNum, String orderTitle, String trackingNum, Date date, boolean finished, int count, String description, double price, String type, String sellerName) {
        this.mid = mid;
        mOrderNum = orderNum;
        mOrderTitle = orderTitle;
        mTrackingNum = trackingNum;
        mDate = date;
        mFinished = finished;
        mCount = count;
        this.description = description;
        mPrice = price;
        mType = type;
        mSellerName = sellerName;
    }

    public UUID getMid() {
        return mid;
    }

    public String getOrderNum() {
        return mOrderNum;
    }

    public String getOrderTitle() {
        return mOrderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.mOrderTitle = orderTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date){
        mDate = date;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public void setFinished(boolean finished) {
        mFinished = finished;
    }

    public String getTrackingNum() {
        return mTrackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.mTrackingNum = trackingNum;
    }

    public int getCount() {
        return mCount;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getType() {
        return mType;
    }

    public String getSellerName() {
        return mSellerName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setSellerName(String sellerName) {
        mSellerName = sellerName;
    }

    public void setOrderNum(String orderNum) {
        mOrderNum = orderNum;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public void setPrice(double price) {
        mPrice = price;
    }
}
