package com.kachat.game.libdata.apiServices;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.CategoriesBean;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.ToyRoomsBean;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class GameApi extends HttpManager{

    private static GameService mGameService  = HttpManager.getInstance().create(GameService.class);


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
    public static Subscription getCategories(Observer<CategoriesBean> observer){
        return setSubscribe(mGameService.getCategories(),observer);
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
