package com.robot.cation.robotapplication.robot.player;

import com.danikula.videocache.file.FileNameGenerator;
import com.robot.cation.robotapplication.robot.http.DownloadUtil;

/**
 * Created by THINK on 2017/12/20.
 */

public class BaseFileNameGenerator implements FileNameGenerator {
    @Override
    public String generate(String url) {
        return DownloadUtil.getNameFromUrl(url);
    }
}
