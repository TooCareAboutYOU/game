package com.kachat.game.libdata.apiServices;

import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.ToyRoomsBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface GameService {

    // http://101.132.144.196:6004/games
    @GET("/games")
    Observable<GamesBean> getGameListImpl();

    //玩家获取宝箱

    // 玩游戏扣除体力
    @FormUrlEncoded
    @POST("/games/play")
    Observable<MessageBean> postHp(@Field("game") String game, @Field("user") String user);


    //使用道具
//    @FormUrlEncoded
//    @POST("/games/props/use")
//    Observable<> postUseProps(@Field("user") String user,@Field("prop") String prop);

    //获取房间列表
    @GET("/games/toy/rooms")
    Observable<ToyRoomsBean> getToyRooms();

}
