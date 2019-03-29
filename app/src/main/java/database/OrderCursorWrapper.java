package database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.haoyue.onlineshoppingorderslist.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import database.OrderDbSchema.OrderTable;

public class OrderCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public OrderCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Order getOrder(){
        String uuidString = getString(getColumnIndex(OrderTable.Cols.UUID));
        String title = getString(getColumnIndex(OrderTable.Cols.TITLE));
        String orderNumber = getString(getColumnIndex(OrderTable.Cols.ORDER_NUMBER));
        long date = getLong(getColumnIndex(OrderTable.Cols.DATE));
        int isFinished = getInt(getColumnIndex(OrderTable.Cols.FINISHED));
        String trackingNumber = getString(getColumnIndex(OrderTable.Cols.TRACKING_NUMBER));
        int count = getInt(getColumnIndex(OrderTable.Cols.COUNT));
        String description = getString(getColumnIndex(OrderTable.Cols.DESCRIPTION));
        double price = getDouble(getColumnIndex(OrderTable.Cols.PRICE));
        String type = getString(getColumnIndex(OrderTable.Cols.TYPE));
        String sellerName = getString(getColumnIndex(OrderTable.Cols.SELLER_NAME));

        //wrap
        Order result = new Order(UUID.fromString(uuidString),orderNumber,title,
                trackingNumber,new Date(date),(isFinished != 0),count,description,price,type,sellerName);
        return result;
    }
}
