package com.robot.cation.robotapplication.robot.http;

import android.support.annotation.NonNull;

import com.robot.cation.robotapplication.robot.utils.CloseUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadUtil {

    public static final int DOWN_FILE_BYTE_SIZE = 2048;
    public static final int NAME_END_INDEX = 50;
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static DownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                LogUtils.w(e);
                if (listener != null) {
                    listener.onDownloadFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[DOWN_FILE_BYTE_SIZE];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    String fromUrl = getNameFromUrl(url);
                    File file = new File(savePath, fromUrl);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                    }
                    fos.flush();
                    // 下载完成
                    if (listener != null) {
                        listener.onDownloadSuccess(file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    LogUtils.w(e);
                    if (listener != null) {
                        listener.onDownloadFailed();
                    }
                } finally {
                    CloseUtils.closeIO(is, fos);
                }
            }
        });
    }


    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    public static String getNameFromUrl(String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        if (name.length() > NAME_END_INDEX) {
            return name.substring(0, NAME_END_INDEX);
        } else {
            return name;
        }
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String filePath);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}