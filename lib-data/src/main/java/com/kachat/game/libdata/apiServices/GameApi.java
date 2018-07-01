package com.kachat.game.libdata.apiServices;

import android.support.annotation.StringRes;
import android.util.Log;

import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.LivesBean;
import com.kachat.game.libdata.model.RankListBean;
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
    public static Subscription requestGameList(Observer<BaseBean<GamesBean>> observer){
        return setSubscribe(mGameService.getGameListImpl(),observer);
    }

    //房间列表
    public static Subscription getToyRooms(Observer<BaseBean<ToyRoomsBean>> observer){
        return setSubscribe(mGameService.getToyRooms(),observer);
    }

    //获取体力
    public static Subscription postHp(String game, String user, Observer<BaseBean<MessageBean>> observer){
        return setSubscribe(mGameService.postHp(game,user),observer);
    }

    //统计
    public static Subscription postStatPages(String type,Observer<BaseBean<MessageBean>> observer){
        return setSubscribe(mGameService.postStatPages(type,uid()),observer);
    }

    //查询商品类别
    public static Subscription getCategories(Observer<BaseBean<CategoryTypeBean>> observer){
        return setSubscribe(mGameService.getCategories(),observer);
    }

    //查询商品类别详情列表
    public static Subscription getGoods(int index, Observer<BaseBean<CategoryListBean>> observer){
        return setSubscribe(mGameService.getGoods(index),observer);
    }

    //购买商品
    public static Subscription postGoods(int goodId,int amount, Observer<BaseBean<MessageBean>> observer){
        return setSubscribe(mGameService.postGoods(token(),goodId,amount),observer);
    }

    //魅力排行榜
    public static Subscription getCharm(int type,Observer<BaseBean<RankingListBean>> observer){
        return setSubscribe(mGameService.getCharm(token(),type),observer);
    }

    //经验排行榜
    public static Subscription getExperience(Observer<BaseBean<RankingListBean>> observer){
        return setSubscribe(mGameService.getExperience(token()),observer);
    }

    //游戏排行榜
    public static Subscription getGameRankList(int gameIndex, int type,Observer<BaseBean<RankListBean>> observer){
        return setSubscribe(mGameService.getGameRankList(token(),gameIndex, type),observer);
    }

    //人物遮罩
    public static Subscription getUserLives(Observer<BaseBean<LivesBean>> observer) {
        Log.i("GraduateSchoolActivity", "uid: "+uid());
        return setSubscribe(mGameService.getUserLives(uid()), observer);
    }
}
