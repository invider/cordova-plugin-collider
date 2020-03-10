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
import android.media.AudioManager;
import android.view.SoundEffectConstants;
import android.provider.Settings;


public class Collider extends CordovaPlugin {

    View view;
    
    AudioManager audioManager;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    	super.initialize(cordova, webView);
        try {
            view = (View) webView.getClass().getMethod("getView").invoke(webView);
        } catch(Exception e) {
            view = (View) webView;
        }
        view.setHapticFeedbackEnabled(false);

        audioManager = (AudioManager) cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);
	}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("vibrate")) {
        	vibrate(args);
        } else if (action.equals("bing")) {
            bing();
        } else if (action.equals("config")) {
            getConfig(callbackContext);
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

    void bing() {
        audioManager.playSoundEffect(SoundEffectConstants.CLICK);
    }

    void getConfig(CallbackContext callbackContext) {
		ContentResolver cr = cordova.getActivity().getContentResolver();

        // get feedback configuration settings
        // 1: allowed
        // 0: disabled
        // -1: unspecified
		int haptic = Settings.System.getInt(cr, Settings.System.HAPTIC_FEEDBACK_ENABLED, -1);
		int acoustic = Settings.System.getInt(cr, Settings.System.SOUND_EFFECTS_ENABLED, -1);
		
		JSONObject response = new JSONObject();
		try {
            if (haptic >= 0) response.put("haptic", haptic == 1);
            if (acoustic >= 0) response.put("acoustic", acoustic == 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		callbackContext.success(response);
	}
}
