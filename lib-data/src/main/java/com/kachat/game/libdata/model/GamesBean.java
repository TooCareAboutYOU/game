package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class GamesBean implements Serializable {

    private int game_id;
    private String name;
    private int price;
    private int category;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
