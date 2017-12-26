package com.robot.cation.robotapplication.robot.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.robot.cation.robotapplication.R;
import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.player.EasyVideo;
import com.robot.cation.robotapplication.robot.player.EasyVideoPlayer;
import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.push.broadcast.PushCallBack;
import com.robot.cation.robotapplication.robot.push.broadcast.PushMessageManager;
import com.robot.cation.robotapplication.robot.utils.ScreenUtils;

public class PlayerActivity extends AppCompatActivity {

    public static final int DELAY_MILLIS = 1000;
    private EasyVideoPlayer player;

    private Handler handler = new Handler();

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
            public void push(PushBean pushBean) {
                player.showOrderForm(pushBean);
            }

            @Override
            public void inProduction(String message) {
                player.showOtherInfo(message);
            }

            @Override
            public void complete(String message) {
                player.showOtherInfo("谢谢的惠顾，欢迎下次光临!\n");
                player.showOtherInfo(message);
                player.hintInfoDelay();
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, DELAY_MILLIS);
            }

            @Override
            public void onFailed(String message) {
                player.showOtherInfo(message);
                player.hintInfoDelay();
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, DELAY_MILLIS);
            }
        });
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            PushMessageManager.getInstance().execute();
        }
    };

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
