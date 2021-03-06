package com.robot.cation.robotapplication.robot.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 时间相关常量
 */
public final class TimeConstants {

    /**
     * 秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60 * SEC;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 60 * MIN;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 24 * HOUR;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
