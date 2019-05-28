# Android与H5交互文档

一、JS -> Native方法

可注册Java Handler方法，供JS调用

//Java注册方法示例，函数名submitFromWeb【java代码】

    webView.registerHandler("submitFromWeb", new BridgeHandler() {
    @Override
    public void handler(String data, CallBackFunction function) {
        Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
        function.onCallBack("submitFromWeb exe, response data from Java");
    }
    });


//JS调用注册方法submitFromWeb【JS代码】

    WebViewJavascriptBridge.callHandler(
     'submitFromWeb'
        , {'param': str1}
        , function(responseData) {
            document.getElementById("show").innerHTML = "send get responseData from java, data = " + responseData
        }
    );

二、	Native -> JS方法

可注册JS Handler方法，供java调用。注意callHandler方法需要在主线程中调用

//JS注册方法示例，方法名functionInJs【JS代码】

    WebViewJavascriptBridge.registerHandler("functionInJs", function(data, responseCallback) {
        document.getElementById("show").innerHTML = ("data from Java: = " + data);
        var responseData = "Javascript Says Right back aka!";
        responseCallback(responseData);
    });

//Java调用注册的方法functionInJs【Java代码】

    webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
        @Override
        public void onCallBack(String data) {
    }
    });

三、	JS与JAVA调用默认Handler

JSBridge提供了默认方法供JS和JAVA相互调用，避免注册的步骤。

JAVA注册默认hander，在不使用assigned handlerName情况下，供js发送消息给java

//java代码。lib中提供DefaultHandler来处理接收到的js消息

webView.setDefaultHandler(new DefaultHandler());

//js代码

    window.WebViewJavascriptBridge.send(data,function(responseData) {
            document.getElementById("show").innerHTML = "repsonseData from java, data = " + responseData
        }
    );

JS调用初始化方法，设置默认handler，在不使用assigned handlerName情况下，供java发送消息给js

//JS代码

    bridge.init(function(message, responseCallback) {
        console.log('JS got a message', message);
        var data = {
            'Javascript Responds': 'Wee!'
        };
        console.log('JS responding with', data);
        responseCallback(data);
    });

//Java代码，console将输出'JS got a message hello' and 'JS responding with'

    webView.send("hello");

init方法在WebviewJavascriptBridge.js中实现，对应方法如下。该方法中初始化了WebViewJavascriptBridge.\_messageHandler，即默认的handler，该变量在下一章节将源码实现时出现在\_dispatchMessageFromNative中。

    //set default messageHandler
    function init(messageHandler) {
        if (WebViewJavascriptBridge._messageHandler) {
            throw new Error('WebViewJavascriptBridge.init called twice');
        }
        WebViewJavascriptBridge._messageHandler = messageHandler;
        var receivedMessages = receiveMessageQueue;
        receiveMessageQueue = null;
        for (var i = 0; i < receivedMessages.length; i++) {
            _dispatchMessageFromNative(receivedMessages[i]);
        }
    }


四、JS初始化注意事项

JSBridge在运行中为window对象注入了WebViewJavascriptBridge对象，在使用前需要判断对象是否存在。如果WebViewJavascriptBridge对象不存在，则可以监听WebViewJavascriptBridgeReady 事件，代码示例如下：

    if (window.WebViewJavascriptBridge) { 
    //do your work here  
    } else {
    document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                //do your work here
            },
            false
    );
    }