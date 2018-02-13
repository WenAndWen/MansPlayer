package com.wen.mansplayer.Bean;
import cn.bmob.v3.BmobUser;

/**
 * Created by wen on 2018/1/22.
 */

public class User extends BmobUser {
    private Boolean vip;

    public void setVip(Boolean vip)
    {
        this.vip=vip;
    }
    public Boolean getVip(){
        return vip;
    }

}
