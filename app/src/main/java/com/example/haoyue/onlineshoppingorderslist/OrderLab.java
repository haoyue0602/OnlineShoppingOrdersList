package com.example.haoyue.onlineshoppingorderslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.haoyue.onlineshoppingorderslist.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import database.OrderBaseHelper;
import database.OrderCursorWrapper;
import database.OrderDbSchema.OrderTable;

public class OrderLab {
    private static OrderLab sOrderLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /** 单例
     * Activity、Service、Application都是Context的子类*/
    public static OrderLab get(Context context){
        if(sOrderLab == null){
            sOrderLab = new OrderLab(context);
        }
        return sOrderLab;
    }

    public OrderLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new OrderBaseHelper(mContext).getWritableDatabase();
    }

    //create ContentValues which is able to be put into SQLite
    private static ContentValues getContentValues(Order order){
        ContentValues values = new ContentValues();
        values.put(OrderTable.Cols.UUID,order.getMid().toString());
        values.put(OrderTable.Cols.TITLE,order.getOrderTitle());
        values.put(OrderTable.Cols.DATE,order.getDate().getTime());
        values.put(OrderTable.Cols.FINISHED,order.isFinished()? 1 : 0);
        values.put(OrderTable.Cols.COUNT,order.getCount());
        values.put(OrderTable.Cols.ORDER_NUMBER,order.getOrderNum());
        values.put(OrderTable.Cols.DESCRIPTION,order.getDescription());
        values.put(OrderTable.Cols.PRICE,order.getPrice());
        values.put(OrderTable.Cols.SELLER_NAME,order.getSellerName());
        values.put(OrderTable.Cols.TRACKING_NUMBER,order.getTrackingNum());
        values.put(OrderTable.Cols.TYPE,order.getType());
        return values;
    }

    //add order
    public void addOrder(Order order){
        ContentValues values = getContentValues(order);
        mDatabase.insert(OrderTable.NAME,null,values);
    }

    //get orders
    public List<Order> getOrders(){

        List<Order> orders = new ArrayList<>();
        OrderCursorWrapper cursor = queryQrders(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                orders.add(cursor.getOrder());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return orders;
    }

    //get order by UUID
    public Order getOrder(UUID id){
        OrderCursorWrapper cursor = queryQrders(OrderTable.Cols.UUID + "=?",new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getOrder();
        }finally {
            cursor.close();
        }

    }

    public void deleteOrder(UUID id){
        mDatabase.delete(OrderTable.NAME,OrderTable.Cols.UUID + "=?",new String[]{id.toString()});
    }
    //update order
    public void updateOrder(Order order){
        String uuidString = order.getMid().toString();
        ContentValues values = getContentValues(order);
        mDatabase.update(OrderTable.NAME,values,OrderTable.Cols.UUID + "= ?",new String[]{uuidString});
    }

    /** query的构建*/
    private OrderCursorWrapper queryQrders(String whereClause,String[] whereArgs){
        Cursor cursor = mDatabase.query(
                OrderTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new OrderCursorWrapper(cursor);
    }
}
