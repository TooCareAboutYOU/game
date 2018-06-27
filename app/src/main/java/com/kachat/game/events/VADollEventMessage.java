package com.kachat.game.events;

import com.dnion.DollRoomSignaling;
import com.dnion.VADollSignaling;

/**
 *
 */
public class VADollEventMessage {

    public enum VADollEvent{
        VADOLL_CONNECT_SUCCESS,
        VADOLL_CONNECT_FAILED,
        VADOLL_DISCONNECTED,
        VADOLL_JOIN_SUCCESS,
        VADOLL_JOIN_FAILED,
        VADOLL_LEAVE,
        VADOLL_READY,
        VADOLL_RESULT,
        VADOLL_QUEUE_UPDATE,
        VACHAT_START_SUCCESS,
        VACHAT_START_FAILED,
        VACHAT_REFRESH_ROOM_INFO,
        VACHAT_REMOTE_JOIN,
        VACHAT_USER_UPDATE,
        VACHAT_REMOTE_LEAVE,
        VACHAT_GOT_VIDEO,
        VACHAT_LOST_VIDEO,
        VACHAT_MESSAGE
    }

    private VADollEvent event;
    private String msg;
    private DollRoomSignaling.DOLLRoomInfo dollRoomInfo;
    private int result;
    private long startGame;
    private VADollSignaling.RoomInfo roomInfo;
    DollRoomSignaling.DOLLVAUserInfo dollvaUserInfo;
    private VADollSignaling.UserInfo userInfo;
    private String msg2;

    public VADollEventMessage() {
    }

    public VADollEventMessage(VADollEvent event) {
        this.event = event;
    }

    public VADollEventMessage(VADollEvent event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    public VADollEventMessage(VADollEvent event, DollRoomSignaling.DOLLRoomInfo dollRoomInfo) {
        this.event = event;
        this.dollRoomInfo = dollRoomInfo;
    }

    public VADollEventMessage(VADollEvent event, String msg, String msg2) {
        this.event = event;
        this.msg = msg;
        this.msg2 = msg2;
    }

    public VADollEventMessage(VADollEvent event, int result) {
        this.event = event;
        this.result = result;
    }

    public VADollEventMessage(VADollEvent event, DollRoomSignaling.DOLLRoomInfo dollRoomInfo,
                              DollRoomSignaling.DOLLVAUserInfo dollvaUserInfo) {
        this.event = event;
        this.dollRoomInfo = dollRoomInfo;
        this.dollvaUserInfo = dollvaUserInfo;
    }

    public VADollEventMessage(VADollEvent event, long startGame) {
        this.event = event;
        this.startGame = startGame;
    }

    public VADollEventMessage(VADollEvent event, String msg, long startGame) {
        this.event = event;
        this.msg = msg;
        this.startGame = startGame;
    }

    public VADollEventMessage(VADollEvent event, VADollSignaling.RoomInfo roomInfo) {
        this.event = event;
        this.roomInfo = roomInfo;
    }

    public VADollEventMessage(VADollEvent event, VADollSignaling.UserInfo userInfo) {
        this.event = event;
        this.userInfo = userInfo;
    }

    public VADollEvent getEvent() {
        return event;
    }

    public void setEvent(VADollEvent event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DollRoomSignaling.DOLLRoomInfo getDollRoomInfo() {
        return dollRoomInfo;
    }

    public void setDollRoomInfo(DollRoomSignaling.DOLLRoomInfo dollRoomInfo) {
        this.dollRoomInfo = dollRoomInfo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getStartGame() {
        return startGame;
    }

    public void setStartGame(long startGame) {
        this.startGame = startGame;
    }

    public VADollSignaling.RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(VADollSignaling.RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    public DollRoomSignaling.DOLLVAUserInfo getDollvaUserInfo() {
        return dollvaUserInfo;
    }

    public void setDollvaUserInfo(DollRoomSignaling.DOLLVAUserInfo dollvaUserInfo) {
        this.dollvaUserInfo = dollvaUserInfo;
    }

    public VADollSignaling.UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(VADollSignaling.UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"event\":")
                .append(event);
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append(",\"dollRoomInfo\":")
                .append(dollRoomInfo);
        sb.append(",\"result\":")
                .append(result);
        sb.append(",\"startGame\":")
                .append(startGame);
        sb.append(",\"roomInfo\":")
                .append(roomInfo);
        sb.append(",\"dollvaUserInfo\":")
                .append(dollvaUserInfo);
        sb.append(",\"userInfo\":")
                .append(userInfo);
        sb.append(",\"msg2\":\"")
                .append(msg2).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
