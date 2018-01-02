package com.robot.cation.robotapplication.robot.singlechip2;


import com.robot.cation.robotapplication.robot.BaseApplication;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 发送指令者
 */
public class CommandSend {


    public static void sendCommand(final byte[] command, final Interceptor interceptor) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Receiver.addInterceptor(interceptor);
                int real = BaseApplication.driver.WriteData(command, command.length);
                if (real <= 0) {
                    Receiver.removeInterceptor(interceptor);
                    interceptor.getCallBack().onFailedSendCommand(real);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }
}
