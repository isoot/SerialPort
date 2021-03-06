package com.robot.cation.robotapplication.robot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SilentInstall {

    /**
     * 静默安装APK
     *
     * @param apkPath
     */
    public static void becomeSilentInstall(String apkPath) {
        String[] args = {"pm", "install", "-r", apkPath};
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write("/n".getBytes(), 0, "/n".getBytes().length);
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (IOException e) {
            LogUtils.w(e);
        } catch (Exception e) {
            LogUtils.w(e);
        } finally {
            CloseUtils.closeIO(errIs, inIs);
            if (process != null) {
                process.destroy();
            }
        }
        LogUtils.w("安装结果:" + result);
    }
}  