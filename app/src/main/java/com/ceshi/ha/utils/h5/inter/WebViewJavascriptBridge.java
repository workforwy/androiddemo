package com.ceshi.ha.utils.h5.inter;


public interface WebViewJavascriptBridge {

    void send(String data);

    void send(String data, CallBackFunction responseCallback);

}
