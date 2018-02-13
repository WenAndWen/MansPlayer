package com.wen.mansplayer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.wen.mansplayer.Bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public static final String []sTitle = new String[]{"欧洲性爱","亚洲色情","无码在线"};
    private View mHeaderLayout;
    private ImageView exitImageView;
    private TextView mTextView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //默认初始化
        Bmob.initialize(this, "a7d4ca4ec65ecfdd554819eb7165776b");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mHeaderLayout=navigationView.inflateHeaderView(R.layout.nav_header_main);
        mTextView=(TextView)mHeaderLayout.findViewById(R.id.textView);
        exitImageView=(ImageView)mHeaderLayout.findViewById(R.id.exit);
        exitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu(v);
            }
        });


        if(BmobUser.getCurrentUser(User.class)==null) {
            //用户注册
            //
            user = BmobUser.getCurrentUser(User.class);
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("暂未登录，请登录\n请点击左上角的图标");
            builder.create();
            builder.setCancelable(false);
            builder.setPositiveButton("我知道了", null);
            builder.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // 充值
          openVip();
        } else if (id == R.id.nav_gallery) {
            //历史记录
            Toast.makeText(MainActivity.this, "内测中...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            try {
                //可以跳转到添加好友，如果qq号是好友了，直接聊天
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=1453077492";//uin是发送过去的qq号码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_share) {
            //版本更新
            Toast.makeText(MainActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            //加入官方群
            joinQQGroup("MX_7Ge0xASGs6SborAmwHcBkAWNVUznP");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //开通会员
    private void openVip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("会员功能:\n①资源丰富\n②播放加速\n③可下载视频\n联系QQ1453077492，马上开通会员！\n会员15元永久\n开发不易，请赞助！");
        builder.setPositiveButton("我要开通", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1453077492";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("不开了", null);
        builder.setCancelable(false);
        builder.show();
    }
    //
    private void openQuestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("本软件账号实行一机一号制\n请勿将号码转借他人\n若转借他人，进行封号处理\n请自觉遵守！");
        builder.setPositiveButton("我知道了", null);
        builder.setCancelable(false);
        builder.show();
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]));
        //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]));
        //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[5]));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(TwoFragment.newInstance());
        fragments.add(MoreFragment.newInstance());


        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(sTitle));
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void pay(){
        final EditText payEd = new EditText(MainActivity.this);
        payEd.setHint("请输入会员卡密");
        final AlertDialog payDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("详情")
                .setView(payEd)
                .setPositiveButton("充值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        payEd.setError("卡密错误" );
                    }
                })
                .setNegativeButton("取消",null)
                .setNegativeButton("购买卡密", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        joinQQGroup("AineFHEclHwJX4WMdpuXEtcUClmrECHa");
                    }
                })
                .create();
        payDialog.show();


    }
    public void popupMenu(final View v){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        //添加点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public AlertDialog.Builder builderLogin;
            public AlertDialog.Builder builderSign;
            public Button mLoginBtn;
            public TextInputLayout mSignPasswordTextInputLayout;
            public TextInputLayout mSignNumberTextInputLayout;
            public Button mSignBtn;

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sign:
                        //用户注册
                        LayoutInflater inflaterSign = LayoutInflater.from(MainActivity.this);
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
                                        }else{
                                            Snackbar.make(v, "啊，注册失败！"+objectId, Snackbar.LENGTH_LONG)
                                                    .setAction("关闭", null).show();
                                        }
                                    }});
                            }}});
                        builderSign =new AlertDialog.Builder(MainActivity.this);
                        builderSign.setTitle("新用户注册");
                        builderSign.setView(layoutSign);
                        builderSign.create().show();
                        break;
                    case R.id.login:
                        //用户登录
                        LayoutInflater inflaterLogin = LayoutInflater.from(MainActivity.this);
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

                                            if (!user.getVip()){
                                                mTextView.setText("普通用户");
                                            }
                                            else {
                                                mTextView.setText("会员用户");
                                            }
                                            //登录成功
                                            Snackbar.make(v , "恭喜你，登录成功！", Snackbar.LENGTH_LONG)
                                                    .setAction("关闭", null).show();
                                        }

                                    });
                                }}});
                        builderLogin =new AlertDialog.Builder(MainActivity.this);
                        builderLogin.setTitle("用户登录");
                        builderLogin.setView(layoutLogin);
                        builderLogin.create().show();
                        break;
                    case R.id.exit:
                        //注销账号
                        break;
                    case R.id.contactQQ:
                        //联系客服
                        try {
                            //可以跳转到添加好友，如果qq号是好友了，直接聊天
                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=1453077492";//uin是发送过去的qq号码
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.questions:
                        //问题解答
                        openQuestion();
                        break;
                        default:
                }
                return false;
            }

        });
        menuInflater.inflate( R.menu.popup_menu_items,popupMenu.getMenu());
        popupMenu.show();
    }

    /****************
     *
     * 发起添加群流程。群号：爱播官方群(691126236) 的 key 为： 3LEhozlbrTfULisCdAgnd38TjLyVCevp
     * 调用 joinQQGroup(3LEhozlbrTfULisCdAgnd38TjLyVCevp) 即可发起手Q客户端申请加群 爱播官方群(691126236)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }
    /**
     * 显示错误提示，并获取焦点
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
}


