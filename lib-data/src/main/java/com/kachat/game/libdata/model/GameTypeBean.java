package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.List;


public class GameTypeBean implements Serializable {

    private List<GamesBean> games;

    public List<GamesBean> getGames() {
        return games;
    }

    public void setGames(List<GamesBean> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
