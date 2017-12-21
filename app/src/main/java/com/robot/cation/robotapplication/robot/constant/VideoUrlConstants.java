package com.robot.cation.robotapplication.robot.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2017/12/20.
 */

public class VideoUrlConstants {
    public static final String VIDEO1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    public static final String VIDEO2 = "http://v4.music.126.net/20171221040747/5e32e8c274dfb7282976d9db0648b660/web/cloudmusic/MCA0IyAhM2EwITAhMiAwMA==/mv/5432420/8f740514387ab59eb7b98de16efc7381.mp4";
    public static final String VIDEO3 = "http://v6c.music.126.net/20171220211730/1b75c4ac4fab7edf7d82d5d7a60bac71/web/cloudmusic/MDJkIDIgMDAwZjA1IDAgNg==/mv/5590153/ce9966119fdaf0708a7255adf10d878c.mp4";
    public static final String VIDEO4 = "http://v4.music.126.net/20171220211855/b73a0f1d5878a08a01e788b8593460b1/web/cloudmusic/MzA2MzEzOTI=/433d39d06c20821188e3d6a0675198e0/abfd6e6dd326844453e242ecbadc33a6.mp4";

    public static List<String> getUrl() {
        List list = new ArrayList();
        list.add(VIDEO1);
        list.add(VIDEO2);
        list.add(VIDEO3);
        list.add(VIDEO4);
        return list;
    }

}
