package com.example.be.openglesdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.be.openglesdemo.View.MyGLSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_Revoke)
    Button btRevoke;
    @BindView(R.id.bt_Clear)
    Button btClear;
    @BindView(R.id.glView)
    MyGLSurfaceView glView;
    @BindView(R.id.addNode)
    Button addNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_Revoke, R.id.bt_Clear,R.id.addNode})
    public void onClick(View view) {
        switch (view.getId()) {
            //撤销操作
            case R.id.bt_Revoke:
                glView.revoke();
                break;
            //清空元素
            case R.id.bt_Clear:
                glView.clear();
                break;
            //新增节点
            case R.id.addNode:
                glView.addNode();
                break;
        }
    }

    @OnClick(R.id.addNode)
    public void onClick() {
    }
}
