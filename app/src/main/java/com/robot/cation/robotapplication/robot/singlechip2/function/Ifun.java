package com.robot.cation.robotapplication.robot.singlechip2.function;


import com.robot.cation.robotapplication.robot.singlechip2.Interceptor;

/**
 * 功能结合
 */
public interface Ifun {

    /**
     * 机械臂
     *
     * @param location
     * @param callBack
     */
    void mechanicalArm(int location, Interceptor.DataCallBack callBack);

    /**
     * 落杯器
     *
     * @param callBack
     */
    void follCupMachine(Interceptor.DataCallBack callBack);

    /**
     * 电动门
     *
     * @param callBack
     */
    void electricallyOperatedGate(Interceptor.DataCallBack callBack);
}
