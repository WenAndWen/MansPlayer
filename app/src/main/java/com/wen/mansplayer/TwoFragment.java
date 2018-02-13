package com.wen.mansplayer;

/**
 * Created by wen on 2017/12/5.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wen.mansplayer.Bean.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import cn.bmob.v3.BmobUser;


public class TwoFragment extends Fragment {

    private int wenNumber=2;
    private Document doc;
    private Handler hander;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private String[] title ={};
    private String[] url ={};
    private ArrayList<String> mTitle=new ArrayList<>();
    private ArrayList<String> mUrl=new ArrayList<>();
    private FloatingActionButton mAdd;

    public static Fragment newInstance(){
        TwoFragment fragment = new TwoFragment();
        return fragment;
    }
    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,null);
        mRecyclerView = (RecyclerView)view. findViewById(R.id.my_recycler_view);
        mAdd=(FloatingActionButton) view.findViewById(R.id.fab);
        mAdd.setOnClickListener(new View.OnClickListener() {
            public User user;

            @Override
            public void onClick(View view) {
                user = BmobUser.getCurrentUser(User.class);
                if(BmobUser.getCurrentUser(User.class)==null){
                    Snackbar.make(view, "开通会员，享更多资源", Snackbar.LENGTH_LONG).setAction("开通", null).show();
                    //联系客服
                    try {
                        //可以跳转到添加好友，如果qq号是好友了，直接聊天
                        String url = "mqqwpa://im/chat?chat_type=wpa&uin=1453077492";//uin是发送过去的qq号码
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (!user.getVip()){
                    // mTextView.setText("普通用户");
                    Snackbar.make(view, "开通会员，享更多资源", Snackbar.LENGTH_LONG).setAction("开通", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //联系客服
                            try {
                                //可以跳转到添加好友，如果qq号是好友了，直接聊天
                                String url = "mqqwpa://im/chat?chat_type=wpa&uin=1453077492";//uin是发送过去的qq号码
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).show();
                }
                else {
                    //  mTextView.setText("会员用户");
                    wenNumber++;
//子线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            parse();

                        }
                    }).start();

            }}
        });
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        Collections.addAll(mTitle,title);
        Collections.addAll(mUrl,url);
        //为RecyclerView添加默认动画效果，测试不写也可以
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerViewAdapter=new RecyclerViewAdapter(getActivity(), mTitle,mUrl));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerViewAdapter.setClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(String vedioUrl, String picUrl,int position) {
                //Snackbar.make(view, vedioUrl, Snackbar.LENGTH_LONG)
                // .setAction("关闭", null).show();
                Intent intent=new Intent(getActivity(),PlayActivity.class);
                intent.putExtra("playUrl",vedioUrl);
                intent.putExtra("picUrl",picUrl);
                startActivity(intent);
            }});
        hander=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //Toast.makeText(getActivity(), msg.getData().getString("text2"), Toast.LENGTH_SHORT).show();
                //添加模拟数据到第一项
                mTitle.add(0,  msg.getData().getString("text2"));//接受msg传递过来的参数);
                mUrl.add(0,  msg.getData().getString("text1"));//接受msg传递过来的参数);
                //RecyclerView列表进行UI数据更新
                mRecyclerViewAdapter.notifyItemInserted(0);
                //如果在第一项添加模拟数据需要调用 scrollToPosition（0）把列表移动到顶端（可选）
                mRecyclerView.scrollToPosition(0);


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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




    public void parse() {

        try {
            doc = Jsoup.connect("https://www.3139v.com/Html/112/index-"+wenNumber+".html").get();
            Element singerListDiv = doc.getElementsByAttributeValue("class", "box movie_list").first();
            Elements links = singerListDiv.select("li");
            for (Element link : links) {
//或的标签为a的数据
                String linkHref = link.select("a").get(0).attr("href");

                String linkPhoto = link.select("img").get(0).attr("src");
                Message msg = Message.obtain();
                //  msg.obj = linkHref;
                // msg.obj=linkPhoto;
                Bundle bundle = new Bundle();
                bundle.putString("text1",linkHref);  //往Bundle中存放数据
                bundle.putString("text2",linkPhoto);  //往Bundle中存放数据
                msg.setData(bundle);
                hander.sendMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}