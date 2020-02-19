package com.colliderlabs.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.view.View;

public class Collider extends CordovaPlugin {

	View view;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    	super.initialize(cordova, webView);
        try {
            view = (View) webView.getClass().getMethod("getView").invoke(webView);
        } catch(Exception e) {
            view = (View) webView;
        }
        view.setHapticFeedbackEnabled(false
	}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("vibrate")) {
        	vibrate(args);
        } else {
            return false;
        }
        return true;
    }

	void vibrate(JSONArray args) {
		try {
			view.performHapticFeedback(args.getInt(0));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
