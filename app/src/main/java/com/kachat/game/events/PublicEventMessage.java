package com.kachat.game.events;

/**
 *
 */
public class PublicEventMessage {

    public static class TransmitMobile{
        private String mobile;

        public TransmitMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }
    }

}
