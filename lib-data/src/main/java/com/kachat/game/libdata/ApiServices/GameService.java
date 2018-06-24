package com.kachat.game.libdata.apiServices;

import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.LivesBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.ToyRoomsBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    // 统计进入游戏主页次数
    @FormUrlEncoded
    @POST("/stats/pages")
    Observable<MessageBean> postStatPages( @Field("user") String user, @Field("type") String type);

    //查询商品类别列表  http://api.e3webrtc.com:8004/v2/shop/categories
    @GET("v2/shop/categories")
    Observable<CategoryTypeBean> getCategories();

    //查询商品列表 http://api.e3webrtc.com:8004/v2/shop/goods?category=1
    @GET("v2/shop/goods")
    Observable<CategoryListBean> getGoods(@Query("category") int category);

    // http://api.e3webrtc.com:8004/shop/orders
    //购买商品
    @FormUrlEncoded
    @POST("/shop/orders")
    Observable<MessageBean> postGoods(@Header("Authorization") String token, @Field("good") int good_id, @Field("amount") int amount);

    //魅力排行榜  http://api.e3webrtc.com:8004/ranks/charm
    @GET("/ranks/charm")
    Observable<RankingListBean> getCharm(@Header("Authorization") String token);

    //魅力排行榜  http://api.e3webrtc.com:8004/ranks/exp
    @GET("/ranks/exp")
    Observable<RankingListBean> getExperience(@Header("Authorization") String token);


    //  http://api.e3webrtc.com:8004/ranks/game/901?type=1
    /**游戏排行榜
     * game_index：游戏编号 900-搭房子，901-消灭星星，902-六芒星
     * 排行榜分类 0-总排行榜，1-周排行榜
     */
     @GET("/ranks/game/{game_index}")
    Observable<GameRankBean> getGameRankList(@Header("Authorization") String token,@Path("game_index") int game_index, @Query("type") int type);

    //  http://api.e3webrtc.com:8004/users/19/lives
    //人物遮罩
    @GET("/users/{uid}/lives")
    Observable<LivesBean> getUserLives(@Path("uid") int uid);
}
