package com.wen.mansplayer.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wen on 2018/1/4.
 */

public class FirstUrl extends BmobObject {
    private Boolean open;
    private Boolean update3;
    public Boolean getOpen(){
        return open;
    }
    public void setOpen(Boolean open){
        this.open=open;
    }
    public Boolean getUpdate3(){
        return update3;
    }
    public void setUpdate3(Boolean update3){
        this.update3=update3;
    }
}
