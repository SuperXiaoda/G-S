package com.juda.gs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.juda.gs.Constants;
import com.juda.gs.R;
import com.juda.gs.util.ToastUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @Description: 闪屏页面
 * @author: CodingDa
 * @Date : 2020/4/2 14:25
 */
public class SplashActivity extends Activity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final int VERIFICATION_JURISDICTION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 检验权限
        if (verificationJurisdiction()) {
            // 倒计时三秒隐藏图片显示登陆布局
            new CountDownTimer(2 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }.start();
        } else {
            getPermissions();

        }

    }

    // 验证权限
    private boolean verificationJurisdiction() {
        return EasyPermissions.hasPermissions(this, Constants.PERMISSIONS);
    }

    // 获取权限
    @AfterPermissionGranted(VERIFICATION_JURISDICTION)
    private void getPermissions() {
        // Ask for both permissions
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.apply_jurisdiction_explain),
                VERIFICATION_JURISDICTION,
                Constants.PERMISSIONS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (verificationJurisdiction()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastUtil.showShort(R.string.limits_of_authority);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d("SplashActivity", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("SplashActivity", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        new AppSettingsDialog.Builder(this).setTitle("提示").setRationale("请在设置-权限中开启存储权限，以正常使用相关功能").build().show();
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d("SplashActivity", "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d("SplashActivity", "onRationaleDenied:" + requestCode);
    }
}
