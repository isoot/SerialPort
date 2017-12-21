package com.robot.cation.robotapplication.robot.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.robot.cation.robotapplication.R;
import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.constant.VideoUrlConstants;
import com.robot.cation.robotapplication.robot.player.EasyVideoCallback;
import com.robot.cation.robotapplication.robot.player.EasyVideoPlayer;
import com.robot.cation.robotapplication.robot.push.broadcast.PushCallBack;
import com.robot.cation.robotapplication.robot.push.broadcast.PushMessageManager;

import java.util.List;

public class PlayerActivity extends AppCompatActivity implements EasyVideoCallback, ViewTreeObserver.OnGlobalLayoutListener {

    private EasyVideoPlayer player;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_layout);
        hideBottomUIMenu();
        player = (EasyVideoPlayer) findViewById(R.id.player);
        assert player != null;
        player.setCallback(this);
        PushMessageManager.getInstance().addCallBack(new PushCallBack() {
            @Override
            public void push(String message) {
                player.showOrderForm(message);
            }
        });
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

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
        player.videoStart();
    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        //播放完成
//        player.seekTo(0);
//        player.start();
        ++count;
        List<String> url = VideoUrlConstants.getUrl();
        if (count < url.size()) {
            player.setSource(Uri.parse(url.get(count)));
        } else {
            count = 0;
            player.setSource(Uri.parse(url.get(count)));
        }
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

    @Override
    protected void onDestroy() {
        player.release();
        BaseApplication.getProxy(this).shutdown();
        super.onDestroy();
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        hideBottomUIMenu();
    }

    @Override
    public void onGlobalLayout() {
        // Do nothing
    }
}
