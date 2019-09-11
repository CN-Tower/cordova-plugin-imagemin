package com.jcflash.imagemin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ImageMin extends CordovaPlugin {

    private CallbackContext callbackContext = null;
    private JSONObject options = null;

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
      this.callbackContext = callbackContext;
        if (action.equals("compress")) {
            String srcBase64 = args.getString(0);
            options = new JSONObject(args.getString(1));
            compressImage(srcBase64);
            return true;
        }
        return false;
    }

    private void compressImage(String srcBase64) {
        Bitmap imgBitmap = base64ToBitmap(srcBase64);
        String resBase64 = bitmapToBase64(imgBitmap);
        JSONObject json = new JSONObject();
        try {
            json.put("data", resBase64);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Response Data", json.toString());
        callbackContext.success(json);
    }

    private Bitmap base64ToBitmap(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.NO_WRAP);
        if (options != null && options.has("bitmapConfig")) {
            Bitmap.Config bitmapConfig = null;
            try {
                String config = options.getString("bitmapConfig");
                if (config.equals("RGB_565")) {
                    bitmapConfig = Bitmap.Config.RGB_565;
                } else if (config.equals("ARGB_8888")) {
                    bitmapConfig = Bitmap.Config.ARGB_8888;
                } else if (config.equals("ARGB_4444")) {
                    bitmapConfig = Bitmap.Config.ARGB_4444;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (bitmapConfig != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = bitmapConfig;
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            }
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imageType = Bitmap.CompressFormat.JPEG;
        Integer encodeQuality = 60;
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
        Log.i("Image Type", imageType.toString());
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
