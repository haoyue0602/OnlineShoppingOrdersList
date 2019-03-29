package com.example.haoyue.onlineshoppingorderslist.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haoyue.onlineshoppingorderslist.Activity.OrderListActivity;

import com.example.haoyue.onlineshoppingorderslist.DialogFragment.DatePickerFragment;
import com.example.haoyue.onlineshoppingorderslist.OrderLab;
import com.example.haoyue.onlineshoppingorderslist.R;
import com.example.haoyue.onlineshoppingorderslist.model.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class OrderEditFragment extends Fragment  {
    private static final String ARG_ORDER_ID = "order_id";

    private Order mOrder;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mIsFinished;
    private EditText mTrackingNumber;
    private Button mSave;

    private UUID currentOrderId;
    private boolean needUpdate = false;

    private OrderLab orderLab = OrderLab.get(getActivity());

    /** 创建orderFragment时，通过此方法返回一个fragment，同时设置一个Argument，这其中包含由ARG_ORDER_ID构成的键。
     * 使得在OrderFragment中可以通过ARG_ORDER_ID找到传入order的id*/
    public static OrderEditFragment newInstance(UUID id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER_ID,id);

        OrderEditFragment fragment = new OrderEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveIntanceState) {
        super.onCreate(saveIntanceState);
        currentOrderId = (UUID)getArguments().getSerializable(ARG_ORDER_ID);
        if(currentOrderId != null){
            //edit pattern
            mOrder = orderLab.getOrder(currentOrderId);
            needUpdate = true;
        }
        else{
            //create pattern
            mOrder = new Order();
        }
    }

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle savedInstandceState){
        View view = infalter.inflate(R.layout.fragment_order_edit,container,false);

        mTitleField = view.findViewById(R.id.order_name);
        mDateButton = view.findViewById(R.id.order_date);
        mIsFinished = view.findViewById(R.id.order_finished);
        mTrackingNumber = view.findViewById(R.id.tracking_number);
        mSave = view.findViewById(R.id.save);

        /**编辑页面填充数据*/
        if(needUpdate){
            mTitleField.setText(mOrder.getOrderTitle());
            mDateButton.setText(new SimpleDateFormat("yyyy-MM-dd").format(mOrder.getDate()));
            mIsFinished.setChecked(mOrder.isFinished());
            mTrackingNumber.setText(mOrder.getTrackingNum());
        }

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrder.setOrderTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**日期设置*/
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(getFragmentManager(), "datePicker");
                dpf.setOnDateInputListener((int year, int month, int day)->getDate(year,month,day));
            }
            public void getDate(int year,int month,int day){
                Calendar c = Calendar.getInstance();
                c.set(year,month,day);
                Date date = c.getTime();
                mOrder.setDate(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                mDateButton.setText(sdf.format(date));
            }
        });

        mIsFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrder.setFinished(isChecked);
            }
        });
        mTrackingNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrder.setTrackingNum(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** 1.更新数据库 2.提示用户 3.跳转页面至list*/
                if(currentOrderId != null){
                    //edit pattern
                    OrderLab.get(getActivity()).updateOrder(mOrder);
                }
                else{
                    //create pattern
                    OrderLab.get(getActivity()).addOrder(mOrder);
                }

                Toast.makeText(getActivity(),"Order saved",Toast.LENGTH_LONG);

                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
