package com.example.haoyue.onlineshoppingorderslist.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.haoyue.onlineshoppingorderslist.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    /** abstract 创建新的fragment*/
    protected abstract Fragment createFragment();

    /**从xml中找到容器，如果容器中没有fragment，创建一个新的fragment并放入容器中*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}
