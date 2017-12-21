package com.robot.cation.robotapplication.robot.controller;

import com.robot.cation.robotapplication.robot.greenbean.DaoMaster;
import com.robot.cation.robotapplication.robot.greenbean.DaoSession;
import com.robot.cation.robotapplication.robot.greenbean.PlayerVideoUrl;
import com.robot.cation.robotapplication.robot.utils.Utils;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * Created by THINK on 2017/12/21.
 */

public class LocalDataManipulation {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    private static LocalDataManipulation instance;

    private LocalDataManipulation() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Utils.getApp(), ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("robot2017") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static LocalDataManipulation getInstance() {
        if (null == instance) {
            instance = new LocalDataManipulation();
        }
        return instance;
    }

    /**
     * 插入播放内容
     *
     * @param playerVideoUrl
     */
    public void insertPlayerVideoUrl(PlayerVideoUrl playerVideoUrl) {
        daoSession.getPlayerVideoUrlDao().insertInTx(playerVideoUrl);
    }

    /**
     * 查询所有播放
     *
     * @return
     */
    public List<PlayerVideoUrl> queryPlayerVideoUrlAll() {
        return daoSession.getPlayerVideoUrlDao().queryBuilder().build().list();
    }

    /**
     * 删除播放数据 其中的一条
     *
     * @param playerVideoUrl
     */
    public void deletePlayerVideoUrl(PlayerVideoUrl playerVideoUrl) {
        daoSession.getPlayerVideoUrlDao().delete(playerVideoUrl);
    }
}
