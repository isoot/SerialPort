package com.robot.cation.robotapplication.robot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.robot.cation.robotapplication.R;
import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.player.EasyVideo;
import com.robot.cation.robotapplication.robot.player.EasyVideoPlayer;
import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.push.broadcast.PushMessageManager;
import com.robot.cation.robotapplication.robot.singlechip.FlowListener;
import com.robot.cation.robotapplication.robot.singlechip.SingleChipReceive;
import com.robot.cation.robotapplication.robot.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements FlowListener {

    private EasyVideoPlayer player;

    private List<Integer> singleChipType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_layout);
        hideBottomUIMenu();
        player = findViewById(R.id.player);
        assert player != null;
        player.setCallback(new EasyVideo());
        PushMessageManager.getInstance().setFlowListener(this);
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

    @Override
    public void startFlow(PushBean pushBean) {
        singleChipType.clear();
        SingleChipReceive.pushBean = pushBean;
        player.showOrderForm(pushBean);
    }

    @Override
    public void sendCommand(List<Integer> singleChipType, List<String> tips) {
        if (tips != null) {
            player.showOtherInfo(tips);
        }
        this.singleChipType.addAll(singleChipType);
        SingleChipReceive.addDisposeSingleType(singleChipType);
    }

    @Override
    public void complete(List<Integer> singleChipType, List<String> tips) {
        this.singleChipType.addAll(singleChipType);
        player.showOtherInfo(tips);
        if (singleChipType.size() == 0) {
            player.hintInfoDelay();
        }
    }

    @Override
    public void onFailed(List<Integer> singleChipType, List<String> tips) {
        this.singleChipType.addAll(singleChipType);
        player.showOtherInfo(tips);
        if (singleChipType.size() == 0) {
            player.hintInfoDelay();
        }
    }

    @Override
    public void nextFlows(List info) {
        player.showNextOrder(info);
    }
}
