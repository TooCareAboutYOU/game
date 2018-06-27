package com.kachat.game.events;


/**
 *
 */
public class VAChatEventMessage {

    public enum VAChatEvent{
        VACHAT_SUCCESS,
        VACHAT_FAILED,
        VACHAT_DISCONNECTED,
        VACHAT_FINISH,
        VACHAT_START,
        VACHAT_TERMINATE,
        VACHAT_CANCEL,
        VACHAT_GOT_DOLL,
        VACHAT_GOT_VIDEO,
        VACHAT_LOST_VIDEO
    }

    private VAChatEvent eventType;
    private String msg;
    private long dollId;
    private int i;

    public VAChatEventMessage() {
    }

    public VAChatEventMessage(VAChatEvent eventType) {
        this.eventType = eventType;
    }

    public VAChatEventMessage(VAChatEvent eventType, String msg) {
        this.eventType = eventType;
        this.msg = msg;
    }
    public VAChatEventMessage(VAChatEvent eventType, long dollId) {
        this.eventType = eventType;
        this.dollId = dollId;
    }

    public VAChatEventMessage(VAChatEvent eventType, int i) {
        this.eventType = eventType;
        this.i = i;
    }

    public VAChatEvent getEventType() {
        return eventType;
    }

    public void setEventType(VAChatEvent eventType) {
        this.eventType = eventType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getDollId() {
        return dollId;
    }

    public void setDollId(long dollId) {
        this.dollId = dollId;
    }


}
