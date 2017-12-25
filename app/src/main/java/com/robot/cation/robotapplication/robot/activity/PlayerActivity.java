package com.robot.cation.robotapplication.robot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.robot.cation.robotapplication.R;
import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.player.EasyVideo;
import com.robot.cation.robotapplication.robot.player.EasyVideoPlayer;
import com.robot.cation.robotapplication.robot.push.broadcast.PushCallBack;
import com.robot.cation.robotapplication.robot.push.broadcast.PushMessageManager;
import com.robot.cation.robotapplication.robot.utils.ScreenUtils;

public class PlayerActivity extends AppCompatActivity {

    private EasyVideoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_layout);
        hideBottomUIMenu();
        player = (EasyVideoPlayer) findViewById(R.id.player);
        assert player != null;
        player.setCallback(new EasyVideo());
        PushMessageManager.getInstance().addCallBack(new PushCallBack() {
            @Override
            public void push(String message) {
                player.showOrderForm(message);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
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
        ScreenUtils.setFullScreen(this);
    }
}
