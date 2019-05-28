package com.ceshi.ha.utils.h5;


import com.ceshi.ha.utils.h5.inter.BridgeHandler;
import com.ceshi.ha.utils.h5.inter.CallBackFunction;

public class DefaultHandler implements BridgeHandler {

	String TAG = "DefaultHandler";
	
	@Override
	public void handler(String data, CallBackFunction function) {
		if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

}
