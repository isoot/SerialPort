package com.robot.cation.robotapplication.robot.player;

import android.net.Uri;

import com.robot.cation.robotapplication.robot.constant.VideoUrlConstants;

import java.util.List;

/**
 * Created by THINK on 2017/12/20.
 */

public class PlayerCalculator {
    static int count;
    /**
     * 获取播放URI
     * @return
     */
    public static Uri getPlayerUri(){
        ++count;
        Uri uri =null;
        List<String> url = VideoUrlConstants.getUrl();
        if (count < url.size()) {
        } else {
            count = 0;
        }
        return  Uri.parse(url.get(count));
    }

    public static Uri onErrorUri(String uri){
        return null;
    }
}
