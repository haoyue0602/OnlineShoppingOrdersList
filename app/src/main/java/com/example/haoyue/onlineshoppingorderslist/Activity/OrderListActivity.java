package com.example.haoyue.onlineshoppingorderslist.Activity;

import android.support.v4.app.Fragment;

import com.example.haoyue.onlineshoppingorderslist.Fragment.OrderListFragment;

public class OrderListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new OrderListFragment();
    }
}
