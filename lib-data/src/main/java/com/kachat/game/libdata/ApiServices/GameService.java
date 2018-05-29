package com.kachat.game.libdata.ApiServices;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.UserBean;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface GameService {

    @GET("/games")
    Observable<BaseBean<GameTypeBean>> getGameTypeImpl();

}
