package com.example.haoyue.onlineshoppingorderslist.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.haoyue.onlineshoppingorderslist.Activity.OrderEditActivity;
import com.example.haoyue.onlineshoppingorderslist.Activity.OrderPagerActivity;
import com.example.haoyue.onlineshoppingorderslist.Adapter.OrderAdapter;
import com.example.haoyue.onlineshoppingorderslist.Dialog.OrderListActivity_dialog;
import com.example.haoyue.onlineshoppingorderslist.OrderLab;
import com.example.haoyue.onlineshoppingorderslist.R;
import com.example.haoyue.onlineshoppingorderslist.model.Order;
import java.util.List;

public class OrderListFragment extends Fragment implements OrderAdapter.IonSlidingViewClickListener{

    private RecyclerView mOrderRecyclerView;
    private OrderAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle savedInstandceState){
        View view = infalter.inflate(R.layout.fragment_order_list,container,false);

        mOrderRecyclerView = view.findViewById(R.id.order_recycler_view);
        mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//设置布局管理器
        mOrderRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置控制Item增删的动画

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_order_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_order:
                //跳转到新建activity
                Intent intent = OrderEditActivity.newIntent(getActivity(),null);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        OrderLab orderLab = OrderLab.get(getActivity());
        List<Order> orders = orderLab.getOrders();

        if(mAdapter == null){
            mAdapter = new OrderAdapter(getContext(),this,orders);
            mOrderRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.updateList(orders);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //跳转到OderPager
        Intent intent = OrderPagerActivity.newIntent(getActivity(),mAdapter.getId(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        //确认框
        new OrderListActivity_dialog(getActivity(),mAdapter,position).showDeleteDialog();
    }

    @Override
    public void onEditBtnCilck(View view, int position) {
        Intent intent = OrderEditActivity.newIntent(getActivity(),mAdapter.getId(position));
        startActivity(intent);
    }
}
