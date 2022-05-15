package com.sl.finger.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

public final class FingerUtils {
    /**
     * 判断是否支持指纹识别
     */
    public static boolean supportFingerprint(Context mContext) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        } else {
            KeyguardManager keyguardManager = mContext.getSystemService(KeyguardManager.class);
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(mContext);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(mContext, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
                Toast.makeText(mContext, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(mContext, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
