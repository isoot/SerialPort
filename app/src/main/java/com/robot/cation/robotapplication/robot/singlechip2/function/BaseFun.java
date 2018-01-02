package com.robot.cation.robotapplication.robot.singlechip2.function;


import com.robot.cation.robotapplication.robot.singlechip2.CommandAssemble;
import com.robot.cation.robotapplication.robot.singlechip2.CommandSend;
import com.robot.cation.robotapplication.robot.singlechip2.Interceptor;
import com.robot.cation.robotapplication.robot.singlechip2.TipsAndType;

import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ICE_CREAM;
import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.ELECTRICALLY_OPERATED_GATE;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.ELECTRICALLY_OPERATED_GATE_;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.FOLLING_CUP_MACHINE;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.MECHANICAL_ARM;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.OPEN_DOOR;

public class BaseFun implements Ifun {

    @Override
    public void mechanicalArm(int location, Interceptor.DataCallBack callBack) {
        byte[] command = CommandAssemble.assembleCommand(ICE_CREAM.getValue(), TipsAndType.mainpulatorConfigType.get(MECHANICAL_ARM)
            , location, 1);
        Interceptor interceptor = new Interceptor(ICE_CREAM.getValue(), TipsAndType.mainpulatorConfigType.get(MECHANICAL_ARM), callBack);
        CommandSend.sendCommand(command, interceptor);
    }

    @Override
    public void follCupMachine(Interceptor.DataCallBack callBack) {
        byte[] command = CommandAssemble.assembleCommand(ELECTRICALLY_OPERATED_GATE.getValue()
            , TipsAndType.electrically_operated_gate_fun.get(FOLLING_CUP_MACHINE)
            , 1, 1);
        Interceptor interceptor = new Interceptor(ELECTRICALLY_OPERATED_GATE.getValue()
            , TipsAndType.electrically_operated_gate_fun.get(FOLLING_CUP_MACHINE), callBack);
        CommandSend.sendCommand(command, interceptor);
    }

    @Override
    public void electricallyOperatedGate(Interceptor.DataCallBack callBack) {
        byte[] command = CommandAssemble.assembleCommand(ELECTRICALLY_OPERATED_GATE.getValue()
            , TipsAndType.electrically_operated_gate_fun.get(ELECTRICALLY_OPERATED_GATE_)
            , TipsAndType.electrically_operated_gate_data.get(OPEN_DOOR), 1);
        Interceptor interceptor = new Interceptor(ELECTRICALLY_OPERATED_GATE.getValue()
            , TipsAndType.electrically_operated_gate_fun.get(ELECTRICALLY_OPERATED_GATE_), callBack);
        CommandSend.sendCommand(command, interceptor);
    }
}
