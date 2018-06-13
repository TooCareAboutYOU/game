package com.kachat.game.libdata.apiServices;

import com.kachat.game.libdata.http.HttpManager;
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
}
