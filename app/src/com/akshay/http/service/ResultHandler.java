package com.akshay.http.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.akshay.http.service.constants.HttpStatusCodes;
import com.akshay.http.service.utils.ServiceUtilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public abstract class ResultHandler {

    private final ResultReceiver receiver;

    public ResultHandler() {
        receiver = new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                try {
                    switch (resultCode) {
                    case HttpStatusCodes.OK:
                        onSuccess(resultData.getByteArray(SyncService.SERVICE_RESPONSE));
                        break;
                    default:
                        onError(resultCode, resultData.getByteArray(SyncService.SERVICE_RESPONSE));
                        break;
                    }
                } catch (Exception e) {
                    onFailure(resultCode, e);
                }
            }
        };
    }

    public JsonNode getJsonNode(byte[] array) throws IOException, JsonParseException, JsonMappingException {
        return ServiceUtilities.getObjectMapper().readValue(getStringFromArray(array), JsonNode.class);
    }

    public String getStringFromArray(byte[] array) {
        return new String(array);
    }

    public InputStream getInputStream(byte[] array) {
        return IOUtils.toInputStream(getStringFromArray(array));
    }

    public Bitmap getBitmap(byte[] result) {
        return BitmapFactory.decodeByteArray(result, 0, result.length);

    }

    public ResultReceiver getResultReceiver() {
        return receiver;
    }

    public abstract void onSuccess(byte[] bs) throws Exception;

    public abstract void onError(int resultCode, byte[] bs) throws Exception;

    public abstract void onFailure(int resultCode, Exception e);

}