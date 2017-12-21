package com.robot.cation.robotapplication.robot.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2017/12/20.
 */

public class VideoUrlConstants {
    public static final String VIDEO1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    public static final String VIDEO2 = "http://v6c.music.126.net/20171221154402/ccbde66d672eecbb3dc08eb92c16cf22/web/cloudmusic/JGQhMSMhMTAiJjMgMDExIg==/mv/==/5319/20140428175821/2428821185777187_720.mp4";
    public static final String VIDEO3 = "http://v4.music.126.net/20171221154441/78cebf57ef958ade5d72d5a8a296598a/web/cloudmusic/JDAzIiUyISAgNDAgIWBlIA==/mv/5398096/9f808af363efbc28fecd07c0d126cd05.mp4";
    public static final String VIDEO4 = "http://v4.music.126.net/20171221154526/a661e4a2aded625b82955a2b76b66315/web/cloudmusic/MSIwJCAyICQxODQgISI1YQ==/mv/5533012/716bc0c889c4d5da2d7f7321049ffe04.mp4";

    public static List<String> getUrl() {
        List list = new ArrayList();
        list.add(VIDEO1);
        list.add(VIDEO2);
        list.add(VIDEO3);
        list.add(VIDEO4);
        return list;
    }

}
