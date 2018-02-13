package com.wen.mansplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.wen.mansplayer.Bean.FirstUrl;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by wen on 2018/1/9.
 */

public class LogoActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "a7d4ca4ec65ecfdd554819eb7165776b");
        readBmob();
}

    public void readBmob(){
        //查找FirstUrl表里面id为Eb5c888B的数据
        BmobQuery<FirstUrl> bmobQuery = new BmobQuery<FirstUrl>();
        bmobQuery.getObject("vUP6444J", new QueryListener<FirstUrl>() {
            @Override
            public void done(FirstUrl object,BmobException e) {
                if (e == null) {
                    //两个都满足
                    if (object.getOpen() && object.getUpdate3()) {
                        Intent intent = new Intent();
                        intent.setClass(LogoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    //更新未打开
             else if (!(object.getUpdate3())) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(LogoActivity.this);
                    builder.setTitle("更新提示");
                    builder.setMessage("此版本已经失效，请加入官方群更新！\nQQ群：524939471" );
                    builder.setCancelable(false);
                    builder.create().show();
            }

                }}});

    }}

