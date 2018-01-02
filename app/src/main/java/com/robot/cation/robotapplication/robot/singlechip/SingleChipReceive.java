package com.robot.cation.robotapplication.robot.singlechip;


import android.widget.TextView;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.constant.ComConfig;
import com.robot.cation.robotapplication.robot.constant.SingleChipConstant;
import com.robot.cation.robotapplication.robot.crc.CRC16X25Util;
import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.push.broadcast.PushMessageManager;
import com.robot.cation.robotapplication.robot.utils.HexUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.ADDRESS_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.DATA_LENGTH;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.END_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.FUNCTION_CODE_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.HEAD_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_CLOSE_GATE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_OPEN_GATE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_PRODUCE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_PRODUCE_END;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_ROTATING_ARM_DESTINATION;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_ROTATING_ARM_GATE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.START_USER_TAKES;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.CHOCOLATE_POSITION;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.CLOSE_DOOR;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.ELECTRICALLY_OPERATED_GATE_;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.FLOW_ONE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.FOLLING_CUP_MACHINE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.GATE_LOCATION;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.ICE_CREAM_POSITION;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.MECHANICAL_ARM;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.MILK_TEA_POSITION;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.OPEN_DOOR;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.RESTORATION;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ADVERTISING;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.COFFEE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ELECTRICALLY_OPERATED_GATE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ICE_CREAM;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.POWER_BANK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TEA_WITH_MILK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TISSUE;

/**
 * 负责接收单片机传过来的数据
 */
public class SingleChipReceive {

    //数据长度位置
    public static final int DATA_LENGTH_INDEX = HEAD_SIZE + ADDRESS_SIZE + FUNCTION_CODE_SIZE;
    //数据的基础长度值
    private static final int PACKAGE_BASE_LENGTH = DATA_LENGTH_INDEX + DATA_LENGTH + END_SIZE;
    //====================================配置串口===================================================
    private static int BAUD_RATE = ComConfig.Backdates.BACKDATE_115200.getValue();
    private static byte DATA_BIT = (byte) ComConfig.DataBits.DataBits_8.getValue();
    private static byte STOP_BIT = (byte) ComConfig.StopBits.SHOPLIFTS_1.getValue();
    private static byte PARITY = (byte) ComConfig.Parity.PARITY_NONE_NONE.getValue();
    private static byte FLOW_CONTROL = (byte) ComConfig.FlowControl.FLOW_CONTROL_NONE.getValue();

    //每次读取数据的长度
    private static final int READ_BYTE_SIZE = 500;

    //每一次读取多留出来的数据整备下一次整合 在判断
    private static List<byte[]> redundantData = new ArrayList<>();

    //设备否是开启
    private static boolean isOpen;

    //装在发出去的指令类型
    private static List<Integer> singleTypes = Collections.synchronizedList(new ArrayList());

    public static PushBean pushBean;
    //监听流程
    private static FlowListener flowListener;

    //制作的类型
    public static int type;

    public static int STEP;

    public static PushBean.DataBean.OrderGoodsBean orderGoodsBean;

    public static int index;

    public static boolean MCOB;

    /**
     * 开始接收数据
     * 不需要任何参数
     */
    public static boolean startReceive(TextView info) {
        return setParameterApparatus(BAUD_RATE, DATA_BIT, STOP_BIT, PARITY, FLOW_CONTROL, info);
    }

    /**
     * 打开设备和配置设备
     *
     * @param baudRate
     * @param dataBit
     * @param stopBit
     * @param parity
     * @param flowControl
     */
    private static boolean setParameterApparatus(int baudRate, byte dataBit, byte stopBit, byte parity, byte flowControl, TextView info) {
        boolean is_ok = false;
        int rectal = BaseApplication.driver.ResumeUsbList();
        // ResumeUsbList方法用于枚举CH34X设备以及打开相关设备
        if (rectal == -1) {
            info.append("打开设备失败!\n");
        } else if (rectal == 0) {
            //对串口设备进行初始化操作
            if (!BaseApplication.driver.UartInit()) {
                info.append("设备初始化失败!......................................\n");
            } else {
                info.append("打开设备成功!\n");
                isOpen = true;
                //配置串口波特率
                if (isOpen && BaseApplication.driver.SetConfig(baudRate, dataBit, stopBit, parity,
                    flowControl)) {
                    info.append("串口设置成功!.........................................\n");
                } else {
                    info.append("串口设置失败!............................................\n");
                }
                readSerialData();//开启读线程读取串口接收的数据
            }
        } else {
            info.append("没有发现设备，请检查你的设备是否正常................................\n");
        }
        return is_ok;
    }

    /**
     * 开始读取数据
     */
    private static void readSerialData() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                while (true) {
                    byte[] buffer = new byte[READ_BYTE_SIZE];
                    int length = BaseApplication.driver.ReadData(buffer, READ_BYTE_SIZE);
                    if (length > 0) {
                        //截取数组有效长度
                        buffer = Arrays.copyOfRange(buffer, 0, length);
                        disposeData(buffer, length);
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

    /**
     * 处理读取到的数据
     *
     * @param buffer 获取的byte数组
     * @param length 有效长度
     */
    private static void disposeData(byte[] buffer, int length) {
        while (true) {
            //读取这第一包数据的长度
            byte[] len = Arrays.copyOfRange(buffer, DATA_LENGTH_INDEX, DATA_LENGTH_INDEX + DATA_LENGTH);
            //补成四个字节的数据
            byte[] int_len = new byte[]{0, 0, 0, len[0]};
            int dataLength = HexUtil.byteArrayToInt(int_len);
            //如果这组数据等于零则数据无效
            if (dataLength == 0) {
                break;
            }
            //整个包的数据长度 由数据的长度加上两位校验值长度
            int packageLength = dataLength + END_SIZE;
            byte[] packageData = Arrays.copyOfRange(buffer, 0, packageLength);
            //CRC校验部分
            boolean passCRC = CRC16X25Util.isPassCRC(packageData, dataLength);
            if (passCRC) {
                circulationData(packageData);//校验通过
            } else {
                LogUtils.w("校验失败数据:" + Arrays.toString(packageData));
            }
            //控制循环条件
            if (buffer.length > packageLength + PACKAGE_BASE_LENGTH) {
                //还有其他数据
                buffer = Arrays.copyOfRange(buffer, packageLength, buffer.length);
            } else if (buffer.length > packageLength) {
                //TODO 多余的数据处理放在下一次处理中
                buffer = Arrays.copyOfRange(buffer, packageLength, buffer.length);
                break;
            } else {
                break;
            }
        }
    }

    /**
     * 处理接收到的数据
     */
    private static void circulationData(byte[] packageData) {
        //根据数据匹配可以做一个拦截
        byte address = packageData[HEAD_SIZE];
        byte functionCode = packageData[HEAD_SIZE + ADDRESS_SIZE];
        if (address == TEA_WITH_MILK.getValue()) {
            singleTypes.remove(Integer.valueOf(TEA_WITH_MILK.getValue()));
            if (singleTypes.contains(TEA_WITH_MILK.getValue())) {
                if (STEP == START_PRODUCE) {
                    //制作完成 开始调用机械臂送到门口
                    STEP = START_PRODUCE_END;
                    byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                        SingleChipTips.mainpulatorConfig.get(GATE_LOCATION), 1);
                    SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                        , null);
                }
            } else {
                //主动发过来的数据
            }
        } else if (address == TISSUE.getValue()) {
            //纸巾机
            if (singleTypes.contains(TISSUE.getValue())) {
                singleTypes.remove(Integer.valueOf(TISSUE.getValue()));
                if (flowListener != null) {
                    flowListener.complete(Arrays.asList(TISSUE.getValue()), Arrays.asList("纸巾出货完成请取走的您商品!"));
                }
            } else {

            }
        } else if (address == ICE_CREAM.getValue()) {
            //冰淇淋和机械臂
            if (singleTypes.contains(ICE_CREAM.getValue())) {
                singleTypes.remove(Integer.valueOf(ICE_CREAM.getValue()));
                //当接收到的是机械臂数据的流程
                if (functionCode == SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM)) {
                    if (STEP == SingleChipConstant.START_ROTATING_ARM) {
                        STEP = SingleChipConstant.START_FOLLING_CUP;
                        //如果STEP == SingleChipConstant.START_ROTATING_ARM 表示第一步接收到的数据 接下来开始发起落杯子的流程
                        start_destination();
                    } else if (STEP == START_ROTATING_ARM_DESTINATION) {
                        //机械臂已经把杯子送到指定位置 接下来可以发送生成指令了
                        STEP = START_PRODUCE;
                        start_produce();
                    } else if (STEP == START_PRODUCE_END) {
                        //机械臂已经把杯子送到门口 然后机械臂复位
                        STEP = START_ROTATING_ARM_GATE;
                        byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                            SingleChipTips.mainpulatorConfig.get(RESTORATION), 1);
                        SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                            , null);
                        //flowListener.complete(Arrays.asList(ICE_CREAM.getValue()), Arrays.asList("冰淇淋制作完成请取走的您商品!"));
                    } else if (STEP == START_ROTATING_ARM_GATE) {
                        //机械臂已经复位 通知开门
                        STEP = START_OPEN_GATE;
                        byte[] command = Command.assembleCommand(ELECTRICALLY_OPERATED_GATE.getValue(),
                            SingleChipTips.electrically_operated_gate_fun.get(ELECTRICALLY_OPERATED_GATE_),
                            SingleChipTips.electrically_operated_gate_data.get(OPEN_DOOR), 1);
                        SingleChipSend.sendSingleChip2(command, ELECTRICALLY_OPERATED_GATE.getValue(),
                            SingleChipTips.electrically_operated_gate_fun.get(ELECTRICALLY_OPERATED_GATE_), flowListener
                            , null);
                    }
                } else {
                    //
                }
            }
        } else if (address == POWER_BANK.getValue()) {
            if (singleTypes.contains(POWER_BANK.getValue())) {
                singleTypes.remove(Integer.valueOf(POWER_BANK.getValue()));
                if (flowListener != null) {
                    flowListener.complete(Arrays.asList(POWER_BANK.getValue()), Arrays.asList("充电宝已经弹出请取走的您商品!"));
                }
            } else {
                //
            }
        } else if (address == ADVERTISING.getValue()) {
            if (singleTypes.contains(ADVERTISING.getValue())) {
                singleTypes.remove(Integer.valueOf(ADVERTISING.getValue()));
                if (flowListener != null) {
                    flowListener.complete(Arrays.asList(POWER_BANK.getValue()), Arrays.asList("您购买的广告已经生效!"));
                }
            } else {
                //
            }
        } else if (address == COFFEE.getValue()) {
            if (singleTypes.contains(COFFEE.getValue())) {
                singleTypes.remove(Integer.valueOf(COFFEE.getValue()));
                if (flowListener != null) {
                    flowListener.complete(Arrays.asList(POWER_BANK.getValue()), Arrays.asList("咖啡制作完成请取走的您商品!"));
                }
            } else {
                //
            }
        } else if (address == ELECTRICALLY_OPERATED_GATE.getValue()) {
            if (singleTypes.contains(ELECTRICALLY_OPERATED_GATE.getValue())) {
                singleTypes.remove(Integer.valueOf(ELECTRICALLY_OPERATED_GATE.getValue()));
                if (functionCode == SingleChipTips.electrically_operated_gate_fun.get(FOLLING_CUP_MACHINE)) {
                    if (STEP == SingleChipConstant.START_FOLLING_CUP) {
                        //杯子已经落下
                        STEP = START_ROTATING_ARM_DESTINATION;
                        start_folling_cup_ed();
                    } else if (STEP == START_OPEN_GATE) {
                        //们已经打开通知用户取走商品
                        STEP = START_USER_TAKES;
                        product_end();
                    } else if (STEP == START_USER_TAKES) {
                        //用户取走了一杯奶茶
                        STEP = START_CLOSE_GATE;
                        byte[] command = Command.assembleCommand(ELECTRICALLY_OPERATED_GATE.getValue(),
                            SingleChipTips.electrically_operated_gate_fun.get(ELECTRICALLY_OPERATED_GATE_),
                            SingleChipTips.electrically_operated_gate_data.get(CLOSE_DOOR), 1);
                        SingleChipSend.sendSingleChip2(command, ELECTRICALLY_OPERATED_GATE.getValue(),
                            SingleChipTips.electrically_operated_gate_fun.get(ELECTRICALLY_OPERATED_GATE_), flowListener
                            , null);
                    } else if (STEP == START_CLOSE_GATE) {
                        FlowManager.startFlow2(flowListener);
                    }
                }
            }
        }

    }

    private static void product_end() {
        int addressNumber = orderGoodsBean.getFunctionNumber();
        int functionNumber = orderGoodsBean.getGoodsNumber();
        if (type == TEA_WITH_MILK.getValue()) {
            flowListener.complete(Arrays.asList(TEA_WITH_MILK.getValue()), Arrays.asList(SingleChipTips.getEndTips(addressNumber, functionNumber)));
        } else if (type == ICE_CREAM.getValue()) {
            flowListener.complete(Arrays.asList(ICE_CREAM.getValue()), Arrays.asList(SingleChipTips.getEndTips(addressNumber, functionNumber)));
        } else if (type == COFFEE.getValue()) {
            flowListener.complete(Arrays.asList(COFFEE.getValue()), Arrays.asList(SingleChipTips.getEndTips(addressNumber, functionNumber)));
        }
    }

    //机械臂到相应的位置准备开始出东西
    private static void start_folling_cup_ed() {
        if (type == TEA_WITH_MILK.getValue()) {
            byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                SingleChipTips.mainpulatorConfig.get(MILK_TEA_POSITION), 1);
            SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                , null);
        } else if (type == ICE_CREAM.getValue()) {
            byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                SingleChipTips.mainpulatorConfig.get(ICE_CREAM_POSITION), 1);
            SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                , null);
        } else if (type == COFFEE.getValue()) {
            byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                SingleChipTips.mainpulatorConfig.get(CHOCOLATE_POSITION), 1);
            SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                , null);
        }
    }

    //开始生产
    private static void start_produce() {
        if (type == TEA_WITH_MILK.getValue()) {
            int addressNumber = orderGoodsBean.getFunctionNumber();
            int functionNumber = orderGoodsBean.getGoodsNumber();
            //开始制作奶茶
            byte[] command = Command.assembleCommand(addressNumber, functionNumber, 1, SingleChipConstant.OTHER_LENGTH);
            //发送指令
            SingleChipSend.sendSingleChip2(command, addressNumber, functionNumber, flowListener, null);
        } else if (type == ICE_CREAM.getValue()) {
            int addressNumber = orderGoodsBean.getFunctionNumber();
            int functionNumber = orderGoodsBean.getGoodsNumber();
            //开始制作奶茶
            byte[] command = Command.assembleCommand(addressNumber, functionNumber, 1, SingleChipConstant.OTHER_LENGTH);
            //发送指令
            SingleChipSend.sendSingleChip2(command, addressNumber, functionNumber, flowListener, null);
        } else if (type == COFFEE.getValue()) {
            int addressNumber = orderGoodsBean.getFunctionNumber();
            int functionNumber = orderGoodsBean.getGoodsNumber();
            //开始制作奶茶
            byte[] command = Command.assembleCommand(addressNumber, functionNumber, 1, SingleChipConstant.OTHER_LENGTH);
            //发送指令
            SingleChipSend.sendSingleChip2(command, addressNumber, functionNumber, flowListener, null);
        }
    }

    //开始落杯子(调用落杯器)
    private static void start_destination() {
        byte[] command = Command.assembleCommand(ELECTRICALLY_OPERATED_GATE.getValue(), SingleChipTips.electrically_operated_gate_fun.get(FOLLING_CUP_MACHINE),
            SingleChipTips.folling_cup_machine_data.get(FLOW_ONE), 1);
        SingleChipSend.sendSingleChip2(command, ELECTRICALLY_OPERATED_GATE.getValue(), SingleChipTips.electrically_operated_gate_fun.get(FOLLING_CUP_MACHINE),
            flowListener, null);
    }

    /**
     * 添加数据类型处理
     */

    public static void addDisposeSingleType(List<Integer> list) {
        flowListener = PushMessageManager.getInstance().getFlowListener();
        singleTypes.addAll(list);
    }

    /**
     * 添加数据类型处理
     */
    public static void clearSingleType() {
        flowListener = null;
        singleTypes.clear();
    }
}
