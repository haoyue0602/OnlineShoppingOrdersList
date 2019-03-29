package com.example.haoyue.onlineshoppingorderslist.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.haoyue.onlineshoppingorderslist.Fragment.OrderFragment;
import com.example.haoyue.onlineshoppingorderslist.OrderLab;
import com.example.haoyue.onlineshoppingorderslist.R;
import com.example.haoyue.onlineshoppingorderslist.model.Order;

import java.util.List;
import java.util.UUID;

public class OrderPagerActivity extends AppCompatActivity {
    private static final String EXTRA_ORDER_ID = "order.uuid";

    private ViewPager mViewPager;
    private List<Order> mOrders;

    /** 当要跳转到orderActivity时，调用这个方法创建intent，创建名为EXTRA_ORDER_ID的键存入intent中。
     * 在下面的方法中用EXTRA_ORDER_ID来获取id*/
    public static Intent newIntent(Context packageContext, UUID id){
        Intent intent = new Intent(packageContext,OrderPagerActivity.class);
        intent.putExtra(EXTRA_ORDER_ID,id);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pager);

        mViewPager = findViewById(R.id.activity_order_pager_view_pager);

        mOrders = OrderLab.get(this).getOrders();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Order order = mOrders.get(position);
                return OrderFragment.newInstance(order.getMid());
            }

            @Override
            public int getCount() {
                return mOrders.size();
            }
        });

        UUID currentOrderId = (UUID)getIntent().getSerializableExtra(EXTRA_ORDER_ID);
        for(int i=0;i<mOrders.size();i++){
            if(mOrders.get(i).getMid().equals(currentOrderId)){
                mViewPager.setCurrentItem(i);
            }

        }
    }
}
