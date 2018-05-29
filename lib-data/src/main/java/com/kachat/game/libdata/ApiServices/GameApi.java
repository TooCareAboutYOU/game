package com.kachat.game.libdata.ApiServices;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.http.HttpManager;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.GetCaptchaBean;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class GameApi extends HttpManager{

    private static GameService mGameService  = HttpManager.getInstance().create(GameService.class);


    //游戏列表
    public static Subscription requestGameList( Observer<BaseBean<GameTypeBean>> observer){
        return setSubscribe(mGameService.getGameTypeImpl(),observer);
    }

}
