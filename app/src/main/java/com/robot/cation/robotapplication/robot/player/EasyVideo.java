package com.robot.cation.robotapplication.robot.player;

import android.net.Uri;

/**
 * Created by THINK on 2017/12/25.
 */

public class EasyVideo implements EasyVideoCallback {

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        //准备好 可以开始
        player.videoStart();
    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        //播放错误
        player.setSource(PlayerCalculator.onErrorUri(player.getmSource().toString()));
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        //播放完成
        player.setSource(PlayerCalculator.getPlayerUri());
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onClickVideoFrame(EasyVideoPlayer player) {

    }
}
