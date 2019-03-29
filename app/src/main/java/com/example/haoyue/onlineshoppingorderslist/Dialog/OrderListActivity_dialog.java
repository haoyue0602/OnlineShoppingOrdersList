package com.example.haoyue.onlineshoppingorderslist.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class OrderListActivity_dialog {
    private Context mContext;
    private DeleteBtnListener mListener;
    private int mDeletePosition;

    public OrderListActivity_dialog(Context context, DeleteBtnListener listener,int position){
        mContext = context;
        mListener = listener;
        mDeletePosition = position;
    }
    public void showDeleteDialog(){
        /** @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

        final AlertDialog.Builder DeleteDialog =
                new AlertDialog.Builder(mContext);
        DeleteDialog.setIcon(android.R.drawable.ic_delete);
        DeleteDialog.setTitle("订单记录删除");
        DeleteDialog.setMessage("确定要删除吗?");
        DeleteDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.deleteOrder(mDeletePosition);
                    }
                });
        DeleteDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
        DeleteDialog.show();
    }
    public interface DeleteBtnListener {
        void deleteOrder(int position);
    }
}
