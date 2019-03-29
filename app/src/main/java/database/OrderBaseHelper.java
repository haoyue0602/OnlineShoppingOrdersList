package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.OrderDbSchema.OrderTable;

public class OrderBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATEBASE_NAME = "orderBase.db";

    public OrderBaseHelper(Context context){
        super(context,DATEBASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table orders
        db.execSQL("create table " + OrderTable.NAME + "(" + "_id integer primary key autoincrement," +
                OrderTable.Cols.UUID + " NOT NULL," +
                OrderTable.Cols.TITLE + "," +
                OrderTable.Cols.ORDER_NUMBER + "," +
                OrderTable.Cols.DATE + "," +
                OrderTable.Cols.FINISHED + "," +
                OrderTable.Cols.TRACKING_NUMBER + "," +
                OrderTable.Cols.COUNT + "," +
                OrderTable.Cols.DESCRIPTION + " ," +
                OrderTable.Cols.PRICE + "," +
                OrderTable.Cols.TYPE + " ," +
                OrderTable.Cols.SELLER_NAME + ")"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
