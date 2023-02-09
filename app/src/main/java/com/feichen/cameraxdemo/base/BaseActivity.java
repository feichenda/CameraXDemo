package com.feichen.cameraxdemo.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Author: chenhao
 * Date: 2023/2/9 0009 下午 09:08:15
 * Describe:
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private int mResLayout;

    public BaseActivity(@LayoutRes int mResLayout) {
        this.mResLayout = mResLayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResLayout);
        findView();
        initView();
    }

    protected abstract void findView();

    protected abstract void initView();

    protected void showToast(@NonNull CharSequence content) {
        runOnUiThread(()->{
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        });
    }
}
