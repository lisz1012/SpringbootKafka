package com.lisz.service;

public interface IMessageSender {
    public void send(String topic, String key, String message);
}
