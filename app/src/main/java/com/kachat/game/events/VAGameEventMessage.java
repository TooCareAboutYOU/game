package com.kachat.game.events;


/**
 *
 */
public class VAGameEventMessage {

    public enum VAGameEvent {
        VAGAME_SUCCESS,
        VAGAME_FAILED,
        VAGAME_DISCONNECTED,
        VAGAME_JOIN_SUCCESS,
        VAGAME_JOIN_FAILED,
        VAGAME_LEAVE,
        VAGAME_MATCH_SUCCESS,
        VAGAME_MESSAGE,
        VAGAME_VIDEO_START,
        VAGAME_VIDEO_FAILED,
        VAGAME_VIDEO_STOP
    }

    String msg;
    VAGameEvent mType;

    public VAGameEventMessage(VAGameEvent type) {
        mType = type;
    }

    public VAGameEventMessage(VAGameEvent type, String msg) {
        this.msg = msg;
        mType = type;
    }

    public VAGameEvent getType() {  return mType;  }

    public String getMsg() {  return msg;  }

}
