package com.robot.cation.robotapplication.robot.greenbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by THINK on 2017/12/21.
 */
@Entity
public class PlayerVideoUrl {
    @Id(autoincrement = true)
    long id;
    //播放地址
    String playerUrl;
    //播放频率
    String playerFrequency;
    //播放有效时间
    String  validTime;
    @Generated(hash = 1154293999)
    public PlayerVideoUrl(long id, String playerUrl, String playerFrequency,
            String validTime) {
        this.id = id;
        this.playerUrl = playerUrl;
        this.playerFrequency = playerFrequency;
        this.validTime = validTime;
    }
    @Generated(hash = 1019256341)
    public PlayerVideoUrl() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPlayerUrl() {
        return this.playerUrl;
    }
    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }
    public String getPlayerFrequency() {
        return this.playerFrequency;
    }
    public void setPlayerFrequency(String playerFrequency) {
        this.playerFrequency = playerFrequency;
    }
    public String getValidTime() {
        return this.validTime;
    }
    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

}
