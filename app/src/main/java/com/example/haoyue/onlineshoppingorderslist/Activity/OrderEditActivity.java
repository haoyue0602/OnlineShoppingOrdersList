package com.example.haoyue.onlineshoppingorderslist.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.haoyue.onlineshoppingorderslist.Fragment.OrderEditFragment;
import java.util.UUID;

public class OrderEditActivity extends SingleFragmentActivity {
    /** transport information of Order*/
    private static final String EXTRA_ORDER_ID = "order.uuid";

    /** 当要跳转到orderEditActivity时，调用这个方法创建intent，创建名为EXTRA_ORDER_ID的键存入intent中。
     * 在下面的方法中用EXTRA_ORDER_ID来获取id,如果id为null，进入创建界面，否则进入编辑界面*/
    public static Intent newIntent(Context packageContext, UUID id){
        Intent intent = new Intent(packageContext, OrderEditActivity.class);
        intent.putExtra(EXTRA_ORDER_ID,id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_ORDER_ID);
        if(id != null){
            //edit pattern
            return OrderEditFragment.newInstance(id);
        }
        else{
            //create pattern
            return OrderEditFragment.newInstance(null);
        }

    }
}
