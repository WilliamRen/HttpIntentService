package com.akshay.http.service.builders;

import java.net.URLEncoder;

import com.akshay.http.service.HttpIntentService;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.ResultReceiver;
import android.text.TextUtils;

public class ServiceIntentBuilder {

    public static final String SYNC_INTENT_EXTRA_RECEIVER = "sync_intent_extra_receiver";
    public static final String SYNC_INTENT_EXTRA_SERVICE_TYPE = "sync_intent_extrac_service_type";
    public static final String SYNC_INTENT_EXTRA_PARAM = "sync_intent_extra_param";

    private final Intent intent;

    public ServiceIntentBuilder(Application app){
        intent = new Intent(app.getApplicationContext(), HttpIntentService.class);
    }
    
    public ServiceIntentBuilder setHttpType(int type){
        intent.putExtra(SYNC_INTENT_EXTRA_SERVICE_TYPE, type);
        return this;
    }

    public ServiceIntentBuilder setResultReceiver(ResultReceiver receiver){
        intent.putExtra(SYNC_INTENT_EXTRA_RECEIVER, receiver);
        return this;
    }

    public ServiceIntentBuilder withParam(String key, String value){
        try{
            String extra = intent.getStringExtra(SYNC_INTENT_EXTRA_PARAM);
            if (TextUtils.isEmpty(extra)){
                extra = "?" + key + "=" + URLEncoder.encode(value,"UTF-8");
            } else {
                extra = extra + "&" + key + "=" + URLEncoder.encode(value,"UTF-8");
            }
            intent.putExtra(SYNC_INTENT_EXTRA_PARAM, extra);
        } catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public ServiceIntentBuilder setData(Uri uri){
        intent.setData(uri);
        return this;
    }

    public Intent build(){
        return this.intent;
    }


}
