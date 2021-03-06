package com.sl.finger.activity;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.sl.finger.R;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class FingerActivity extends AppCompatActivity {
    private static final String TAG = "FingerActivity";

    private static final String DEFAULT_KEY_NAME = "default_key";

    KeyStore keyStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
//        findViewById(R.id.btn_finger).setOnClickListener(this::verifyFinger);
        findViewById(R.id.btn_finger).setOnClickListener(this::verifyFinger2);
    }

    private void verifyFinger2(View view) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
        boolean hardwareDetected = fingerprintManagerCompat.isHardwareDetected();
        Log.d(TAG, "verifyFinger2: " + hardwareDetected);
        boolean hasEnrolledFingerprints = fingerprintManagerCompat.hasEnrolledFingerprints();
        Log.d(TAG, "verifyFinger2: " + hasEnrolledFingerprints);
        if (supportFingerprint()) {
            initKey();
            initCipher();
        }
    }

    FingerprintManagerCompat.AuthenticationCallback mSelfCancelled = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //??????????????????????????????????????????????????????????????????????????????????????????
            //errorCode??????????????????
            Log.d(TAG, "onAuthenticationError: ");
            Toast.makeText(FingerActivity.this, "???????????????????????????????????? " + errString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            //????????????????????????????????????????????????????????????????????????????????????
            Log.d(TAG, "onAuthenticationHelp: ");
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            FingerprintManagerCompat.CryptoObject cryptoObject = result.getCryptoObject();
            Log.d(TAG, "onAuthenticationSucceeded: " + cryptoObject);
        }

        @Override
        public void onAuthenticationFailed() {
            //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            Log.d(TAG, "onAuthenticationFailed: ");
        }
    };

    private void verifyFinger(View view) {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("????????????")
                .setDescription("????????????????????????")
                .setNegativeButtonText("??????")  //????????????
                .setSubtitle("????????????")
                .build();
        new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //???????????? ??????
                Toast.makeText(FingerActivity.this, errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //?????????????????????
                Toast.makeText(FingerActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(FingerActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        }).authenticate(promptInfo);
    }

    @TargetApi(23)
    private void initCipher() {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
            fingerprintManagerCompat.authenticate(new FingerprintManagerCompat.CryptoObject(cipher),
                    0,
                    new CancellationSignal(),
                    mSelfCancelled, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "???????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    private void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
