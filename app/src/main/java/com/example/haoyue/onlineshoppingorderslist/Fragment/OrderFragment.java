package com.example.haoyue.onlineshoppingorderslist.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.haoyue.onlineshoppingorderslist.OrderLab;
import com.example.haoyue.onlineshoppingorderslist.R;
import com.example.haoyue.onlineshoppingorderslist.model.Order;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class OrderFragment extends Fragment {
    private static final String ARG_ORDER_ID = "order_id";
    public static final int EDITED_ORDER = 49;

    private Order mOrder;
    private TextView mTitleField;
    private TextView mDateField;
    private CheckBox mIsFinished;
    private TextView mTrackingNum;

    private OrderLab orderLab = OrderLab.get(getActivity());

    /** 创建orderFragment时，通过此方法返回一个orderFragment，同时设置一个Argument，这其中包含由ARG_ORDER_ID构成的键。
     * 使得在OrderFragment中可以通过ARG_ORDER_ID找到传入order的id*/
    public static OrderFragment newInstance(UUID id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER_ID,id);

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveIntanceState) {
        super.onCreate(saveIntanceState);
        UUID orderId = (UUID)getArguments().getSerializable(ARG_ORDER_ID);
        mOrder = orderLab.getOrder(orderId);
    }

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle savedInstandceState){
        View view = infalter.inflate(R.layout.fragment_order_show,container,false);

        mTitleField = view.findViewById(R.id.order_name);
        mDateField = view.findViewById(R.id.order_date);
        mIsFinished = view.findViewById(R.id.order_finished);
        mTrackingNum = view.findViewById(R.id.order_tracking_number);

        mTitleField.setText(mOrder.getOrderTitle());
        mDateField.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mOrder.getDate()));
        mIsFinished.setChecked(mOrder.isFinished());
        mTrackingNum.setText(mOrder.getTrackingNum());

        return view;
    }
}
