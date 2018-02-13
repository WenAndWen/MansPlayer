package com.wen.mansplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.wen.mansplayer.Bean.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by luojing on 2016/12/9.
 */
public class PlayActivity extends AppCompatActivity  {

    private Document doc;
    private Handler hander1;
    private JCVideoPlayer videoController1;
    private TextInputLayout mSignNumberTextInputLayout;
    private TextInputLayout mSignPasswordTextInputLayout;
    private Button mSignBtn;
    private Button mLoginBtn;
    private String date1;
    private String date2;
    private TextView mText;
    private TextView mText1;
    private TextView mText2;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        mText=(TextView)findViewById(R.id.title);
        mText1=(TextView)findViewById(R.id.titleName);
        mText2=(TextView)findViewById(R.id.groupName);

        if(BmobUser.getCurrentUser(User.class)==null){
            //用户注册
            LayoutInflater inflaterSign = LayoutInflater.from(PlayActivity.this);
            final View layoutSign = inflaterSign.inflate(R.layout.dialog_sign, null);
            mSignNumberTextInputLayout = (TextInputLayout) layoutSign.findViewById(R.id.til_Number);
            mSignPasswordTextInputLayout = (TextInputLayout) layoutSign.findViewById(R.id.til_Password);
            mSignBtn = (Button) layoutSign.findViewById(R.id.signBtn);
            mSignBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    String number = mSignNumberTextInputLayout.getEditText().getText().toString();
                    String password = mSignPasswordTextInputLayout.getEditText().getText().toString();
                    mSignNumberTextInputLayout.setErrorEnabled(false);
                    mSignPasswordTextInputLayout.setErrorEnabled(false);
                    //验证用户名和密码
                    if (number.length() == 0) {
                        showError(mSignNumberTextInputLayout, "用户名不能为空");
                    } else if (password.length() == 0) {
                        showError(mSignPasswordTextInputLayout, "密码不能为空");
                    } else if (password.length() < 6 || password.length() > 11) {
                        showError(mSignPasswordTextInputLayout, "密码长度为6-18位");
                    } else {
                        // 使用BmobSDK提供的注册功能
                        User user = new User();
                        user.setVip(false);
                        user.setUsername(number);
                        user.setPassword(password);
                        user.signUp(new SaveListener<BmobUser>() {
                            @Override
                            public void done(BmobUser objectId, BmobException e1) {
                                if(e1==null){
                                    Snackbar.make(v, "恭喜你，注册成功！"+objectId, Snackbar.LENGTH_LONG)
                                            .setAction("关闭", null).show();
                                    //用户登录
                                    LayoutInflater inflaterLogin = LayoutInflater.from(PlayActivity.this);
                                    View layoutLogin = inflaterLogin.inflate(R.layout.dialog_login, null);
                                    mSignNumberTextInputLayout = (TextInputLayout) layoutLogin.findViewById(R.id.til_Number);
                                    mSignPasswordTextInputLayout = (TextInputLayout) layoutLogin.findViewById(R.id.til_Password);
                                    mLoginBtn = (Button) layoutLogin.findViewById(R.id.loginBtn);
                                    mLoginBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View v) {
                                            String number = mSignNumberTextInputLayout.getEditText().getText().toString();
                                            String password = mSignPasswordTextInputLayout.getEditText().getText().toString();
                                            mSignNumberTextInputLayout.setErrorEnabled(false);
                                            mSignPasswordTextInputLayout.setErrorEnabled(false);
                                            //验证用户名和密码
                                            if (number.length() == 0) {
                                                showError(mSignNumberTextInputLayout, "用户名不能为空");
                                            } else if (password.length() == 0) {
                                                showError(mSignPasswordTextInputLayout, "密码不能为空");
                                            } else if (password.length() < 6 || password.length() > 11) {
                                                showError(mSignPasswordTextInputLayout, "密码长度为6-18位");
                                            } else {
                                                final BmobUser bmobUser = new BmobUser();
                                                bmobUser.setUsername(number);
                                                bmobUser.setPassword(password);

                                                bmobUser.login( new SaveListener<BmobUser>() {
                                                    @Override
                                                    public void done(BmobUser bmobUser, BmobException e) {
                                                        User user = BmobUser.getCurrentUser(User.class);

                                                        //登录成功
                                                        Snackbar.make(v , "恭喜你，登录成功！", Snackbar.LENGTH_LONG)
                                                                .setAction("关闭", null).show();
                                                    }

                                                });

                                            }}});
                                    AlertDialog.Builder builderLogin =new AlertDialog.Builder(PlayActivity.this);
                                    builderLogin.setTitle("用户登录");
                                    builderLogin.setView(layoutLogin);
                                    builderLogin.create().show();

                                }else{
                                    Snackbar.make(v, "啊，注册失败！"+objectId, Snackbar.LENGTH_LONG)
                                            .setAction("关闭", null).show();
                                }
                            }});
                    }}});

            AlertDialog.Builder builderSign =new AlertDialog.Builder(PlayActivity.this);
            builderSign.setTitle("新用户注册");
            builderSign.setView(layoutSign);
            builderSign.create().show();
        }
        Intent intent = getIntent();
        date1 = intent.getStringExtra("playUrl");
        date2 = intent.getStringExtra("picUrl");

        videoController1 = (JCVideoPlayer) findViewById(R.id.videocontroller1);
        videoController1.setSkin(R.color.colorAccent, R.color.colorPrimary, R.drawable.skin_seek_progress,
                R.color.bottom_bg, R.drawable.skin_enlarge_video, R.drawable.skin_shrink_video);
        hander1 = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
               // Toast.makeText(PlayActivity.this, msg.getData().getString("text2"), Toast.LENGTH_SHORT).show();
                mText1.setText(msg.getData().getString("text2"));
                mText2.setText("未分类");

                videoController1.setUp( msg.getData().getString("text1"),
                        date2,
                        msg.getData().getString("text2"));
                super.handleMessage(msg);
            }
        };
        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                parse();

            }
        }).start();

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

    }


    public void parse() {
        try {
            doc = Jsoup.connect(date1).get();
            Element singerListDiv = doc.getElementsByAttributeValue("class", "downurl").first();
            Elements links = singerListDiv.getElementsByTag("a ");
            Element singerListDiv2 = doc.getElementsByAttributeValue("class", "film_title").first();
            Elements links2 = singerListDiv2.select("h1");
            for (Element link : links) {
//或的标签为a的数据
                String linkHref = link.attr("href");
                String linkTitle=links2.text();
                Message msg = Message.obtain();
                //  msg.obj = linkHref;
                // msg.obj=linkPhoto;
                Bundle bundle = new Bundle();
                bundle.putString("text1",linkHref);  //往Bundle中存放数据
                bundle.putString("text2",linkTitle);
                msg.setData(bundle);
                hander1.sendMessage(msg);
            }
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
}