package com.kachat.game.libdata.dbmodel;

import com.alibaba.fastjson.JSON;
import com.kachat.game.libdata.gen.FloatConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 */
@Entity
public class DbLive2DBean {
    @Id
    private Long id;

    private String liveFilePath;

    @NotNull
    private String liveFileName;

    private String bgFilePath;

    @NotNull
    private String bgFileName;

    @Convert(columnType = String.class, converter = FloatConverter.class)
    private List<Float> chatMask;

    @Convert(columnType = String.class, converter = FloatConverter.class)
    private List<Float> gameMask;

    private int pitchLevel;

    @Generated(hash = 2116295053)
    public DbLive2DBean(Long id, String liveFilePath, @NotNull String liveFileName,
            String bgFilePath, @NotNull String bgFileName, List<Float> chatMask,
            List<Float> gameMask, int pitchLevel) {
        this.id = id;
        this.liveFilePath = liveFilePath;
        this.liveFileName = liveFileName;
        this.bgFilePath = bgFilePath;
        this.bgFileName = bgFileName;
        this.chatMask = chatMask;
        this.gameMask = gameMask;
        this.pitchLevel = pitchLevel;
    }


    @Generated(hash = 751318048)
    public DbLive2DBean() {
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getLiveFilePath() {
        return this.liveFilePath;
    }


    public void setLiveFilePath(String liveFilePath) {
        this.liveFilePath = liveFilePath;
    }


    public String getLiveFileName() {
        return this.liveFileName;
    }


    public void setLiveFileName(String liveFileName) {
        this.liveFileName = liveFileName;
    }


    public String getBgFilePath() {
        return this.bgFilePath;
    }


    public void setBgFilePath(String bgFilePath) {
        this.bgFilePath = bgFilePath;
    }


    public String getBgFileName() {
        return this.bgFileName;
    }


    public void setBgFileName(String bgFileName) {
        this.bgFileName = bgFileName;
    }


    public List<Float> getChatMask() {
        return this.chatMask;
    }


    public void setChatMask(List<Float> chatMask) {
        this.chatMask = chatMask;
    }


    public List<Float> getGameMask() {
        return this.gameMask;
    }


    public void setGameMask(List<Float> gameMask) {
        this.gameMask = gameMask;
    }


    public int getPitchLevel() {
        return this.pitchLevel;
    }


    public void setPitchLevel(int pitchLevel) {
        this.pitchLevel = pitchLevel;
    }


}
