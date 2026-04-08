package com.example.photohook;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class PhotoHook implements IXposedHookLoadPackage {
    private static final String TAG = "PhotoHook";
    private static final String TARGET_PACKAGE = "com.cnten.productionoperation";
    private static final String REPLACE_IMAGE_PATH = "/sdcard/DCIM/photo_to_upload.jpg";
    
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(TARGET_PACKAGE)) {
            return;
        }
        
        Log.i(TAG, "目标APP已加载: " + TARGET_PACKAGE);
        
        // Hook onActivityResult - 拦截拍照返回
        XposedHelpers.findAndHookMethod(
            "android.app.Activity",
            lpparam.classLoader,
            "onActivityResult",
            int.class, int.class, Intent.class,
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Intent data = (Intent) param.args[2];
                    
                    if (data != null && data.getAction() != null && 
                        data.getAction().equals("android.media.action.IMAGE_CAPTURE")) {
                        
                        Log.i(TAG, "拦截到拍照请求，替换图片: " + REPLACE_IMAGE_PATH);
                        
                        // 创建新的 Intent，指向我们的图片
                        Uri newUri = Uri.parse("file://" + REPLACE_IMAGE_PATH);
                        Intent newIntent = new Intent();
                        newIntent.setData(newUri);
                        
                        param.args[2] = newIntent;
                    }
                }
            }
        );
        
        Log.i(TAG, "Hook 完成");
    }
}
