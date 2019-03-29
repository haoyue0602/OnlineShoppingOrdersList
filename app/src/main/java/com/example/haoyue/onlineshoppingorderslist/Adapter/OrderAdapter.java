package com.example.haoyue.onlineshoppingorderslist.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.haoyue.onlineshoppingorderslist.Dialog.OrderListActivity_dialog;
import com.example.haoyue.onlineshoppingorderslist.OrderLab;
import com.example.haoyue.onlineshoppingorderslist.R;
import com.example.haoyue.onlineshoppingorderslist.Utils;
import com.example.haoyue.onlineshoppingorderslist.View.LeftSlideView;
import com.example.haoyue.onlineshoppingorderslist.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>
        implements LeftSlideView.IonSlidingButtonListener,OrderListActivity_dialog.DeleteBtnListener {

    private Context mContext;
    private List<Order> mOrders;

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private IonSlidingViewClickListener mISetBtnClickListener;

    private LeftSlideView mMenu = null;

    public OrderAdapter(Context context, Fragment fragment, List<Order> orders){
        mContext = context;
        mOrders = orders;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener)fragment;
        mISetBtnClickListener = (IonSlidingViewClickListener)fragment;
    }
    /** 在RecyclerView需要新的View视图来显示列表项时调用*/
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_order,parent,false);
        return new OrderHolder(view);
    }
    /** 1.把ViewHolder的View视图和模型层数据(Order)绑定 2.刷新视图*/
    @Override
    public void onBindViewHolder(@NonNull OrderHolder orderHolder, int position) {
        //绑定订单信息
        Order order = mOrders.get(position);
        orderHolder.bindOrder(order);

        //设置内容布局的宽为屏幕宽度
        orderHolder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);

        //item正文点击事件
        orderHolder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = orderHolder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }
            }
        });
        //左滑设置点击事件
        orderHolder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = orderHolder.getLayoutPosition();
                closeMenu();
                //实现在Fragment中
                mISetBtnClickListener.onEditBtnCilck(view, n);
            }
        });
        //左滑删除点击事件
        orderHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = orderHolder.getLayoutPosition();
                closeMenu();
                //实现在Fragment中
                mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
            }
        });
    }
    /**获取当前item的id*/
    public UUID getId(int position){
        return mOrders.get(position).getMid();
    }
    /**
     * 删除item
     * @param position
     */
    public void deleteOrder(int position) {
        //数据库中删除
        new OrderLab(mContext).deleteOrder(getId(position));
        //adapter中数据更新
        mOrders.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }
    /**
     * 滑动或者点击了Item监听
     *
     * @param leftSlideView
     */
    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }
    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }
    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public void updateList(List<Order> orders){
        mOrders = orders;
    }

    /**
     * 注册接口的方法：点击事件。在OrderListFragment.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);//点击item正文

        void onDeleteBtnCilck(View view, int position);//点击“删除”

        void onEditBtnCilck(View view, int position);//点击“设置”
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        /** order中有三个属性 title date isFinished*/
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mIsFinishedCheckBox;

        private TextView mBtnEdit;
        private TextView mBtnDelete;
        private ViewGroup layout_content;

        private Order mOrder = null;

        public OrderHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.list_item_order_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_order_date_text_view);
            mIsFinishedCheckBox = itemView.findViewById(R.id.list_item_order_is_finished_check_box);

            mBtnEdit = itemView.findViewById(R.id.order_item_edit);
            mBtnDelete = itemView.findViewById(R.id.order_item_delete);
            layout_content = itemView.findViewById(R.id.layout_content);
            ((LeftSlideView) itemView).setSlidingButtonListener(OrderAdapter.this);
        }
        /** 绑定数据*/
        public void bindOrder(Order order){
            mOrder = order;
            mTitleTextView.setText(mOrder.getOrderTitle());
            mDateTextView.setText(getDateString(mOrder.getDate()));
            mIsFinishedCheckBox.setChecked(mOrder.isFinished());
        }
        /** 处理日期*/
        protected String getDateString(Date date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }
}

