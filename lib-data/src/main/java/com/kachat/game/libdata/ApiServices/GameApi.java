package com.kachat.game.libdata.apiServices;

import android.support.annotation.StringRes;
import android.util.Log;

import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.ToyRoomsBean;

import java.util.Objects;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class GameApi extends HttpManager{

    private static final String TAG = "ShopActivity";
    
    private static GameService mGameService  = HttpManager.getInstance().create(GameService.class);
    private static String token(){return Objects.requireNonNull(DaoQuery.queryUserData()).getToken();}
    private static int uid() {return Objects.requireNonNull(DaoQuery.queryUserData()).getUid();}

    //游戏列表
    public static Subscription requestGameList(Observer<GamesBean> observer){
        return setSubscribe(mGameService.getGameListImpl(),observer);
    }

    //房间列表
    public static Subscription getToyRooms(Observer<ToyRoomsBean> observer){
        return setSubscribe(mGameService.getToyRooms(),observer);
    }

    //获取体力
    public static Subscription postHp(String game, String user, Observer<MessageBean> observer){
        return setSubscribe(mGameService.postHp(game,user),observer);
    }

    //统计
    public static Subscription postStatPages(String type, String user, Observer<MessageBean> observer){
        return setSubscribe(mGameService.postStatPages(type,user),observer);
    }

    //查询商品类别
    public static Subscription getCategories(Observer<CategoryTypeBean> observer){
        return setSubscribe(mGameService.getCategories(),observer);
    }

    //查询商品类别详情列表
    public static Subscription getGoods(int index, Observer<CategoryListBean> observer){
        return setSubscribe(mGameService.getGoods(index),observer);
    }

    //购买商品
    public static Subscription postGoods(int goodId,int amount, Observer<MessageBean> observer){

        return setSubscribe(mGameService.postGoods(token(),goodId,amount),observer);
    }

    //魅力排行榜
    public static Subscription getCharm(Observer<RankingListBean> observer){
        return setSubscribe(mGameService.getCharm(),observer);
    }

    //经验排行榜
    public static Subscription getExperience(Observer<RankingListBean> observer){
        return setSubscribe(mGameService.getExperience(),observer);
    }

    //游戏排行榜
    public static Subscription getGameRankList(int gameIndex, int type,Observer<GameRankBean> observer){
        return setSubscribe(mGameService.getGameRankList(gameIndex, type),observer);
    }
}
