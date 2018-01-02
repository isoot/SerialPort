package com.robot.cation.robotapplication;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.activity.PlayerActivity;
import com.robot.cation.robotapplication.robot.baidu.BaiduTTS;
import com.robot.cation.robotapplication.robot.http.CallBack;
import com.robot.cation.robotapplication.robot.http.Repository;
import com.robot.cation.robotapplication.robot.model.InitBean;
import com.robot.cation.robotapplication.robot.singlechip.SingleChipReceive;
import com.robot.cation.robotapplication.robot.utils.ActivityUtils;
import com.robot.cation.robotapplication.robot.utils.AppUtils;
import com.robot.cation.robotapplication.robot.utils.DeviceUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;
import com.robot.cation.robotapplication.robot.utils.SPUtils;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";

    private TextView sample_text;

    protected Handler mainHandler = new Handler(Looper.getMainLooper()) {
        /*
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            sample_text.append(what + ":" + (CharSequence) msg.obj + "\n");
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sample_text = findViewById(R.id.sample_text);
        int versionCode = AppUtils.getAppVersionCode();
        sample_text.append("版本" + versionCode + ":开始初始化设备 请稍等!................................................\n");

        //=======================baidu======================
        BaiduTTS.getInstance().initialTts(mainHandler);

        // SilentInstall.becomeSilentInstall(FileUtils.getBaseFileAPKPath() + "/new.apk");

        remoteInit();

        //========================ch34==========================================
        BaseApplication.driver = new CH34xUARTDriver(
            (UsbManager) getSystemService(Context.USB_SERVICE), getApplicationContext(),
            ACTION_USB_PERMISSION);
        // 判断系统是否支持USB HOST
        if (BaseApplication.driver.UsbFeatureSupported()) {
            boolean startReceive = SingleChipReceive.startReceive(sample_text);
            if (!startReceive) {
                ActivityUtils.startActivity(PlayerActivity.class);
                return;
            }
        } else {
            sample_text.append("设备不支持CH34!.....................\n");
            return;
        }

    }


    /**
     * 初始化
     */
    private void remoteInit() {

        if (true) {
//            if (SPUtils.getInstance().getBoolean("installed")) {
            SPUtils.getInstance().put("installed", true);
            InitBean initBean = new InitBean();
            initBean.setDeviceNumber(DeviceUtils.getAndroidID());
            initBean.setMac(DeviceUtils.getMacAddress());
            Repository.getInstance().init(initBean, new CallBack() {
                @Override
                public void onSuccess(String body) {
                    LogUtils.w("初始化成功:" + body);
                }

                @Override
                public void onError(String body) {
                    LogUtils.w("初始化失败:" + body);
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        BaseApplication.driver.CloseDevice();
        BaiduTTS.getInstance().release();
        super.onDestroy();
    }

}
