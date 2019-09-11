package com.jcflash.imagemin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageMin extends CordovaPlugin {

    private CallbackContext callbackContext = null;
    private JSONObject options = null;

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
      this.callbackContext = callbackContext;
        if (action.equals("compress")) {
            String srcBase64 = args.getString(0);
            options = new JSONObject(args.getString(1));
            compressImage(cordova.getContext(), srcBase64);
            return true;
        }
        return false;
    }

    private void compressImage(Context context, String srcBase64) {
        try {
            Bitmap imgBitmap = base64ToBitmap(srcBase64);
            String resBase64 = bitmapToBase64(resBitmap);
            JSONObject json = new JSONObject();
            try {
                json.put("data", resBase64);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Response Data", json.toString());
            callbackContext.success(json);
        } catch (IOException e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            callbackContext.error(error);
        }
    }

    private Bitmap base64ToBitmap(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imageType = Bitmap.CompressFormat.JPEG;
        Integer encodeQuality = 80;
        if (options != null && options.has("outputImgType")) {
            try {
                String imgType = options.getString("outputImgType");
                if (imgType.equals("png") || imgType.equals("PNG")) {
                    imageType = Bitmap.CompressFormat.PNG;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Image Type", json.toString());
        if (options != null && options.has("encodeQuality")) {
            try {
                Integer quality = options.getInt("encodeQuality");
                if (quality > 0 && quality < 100) {
                    encodeQuality = quality;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Encode Quality", encodeQuality.toString());
        bitmap.compress(imageType, encodeQuality, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
