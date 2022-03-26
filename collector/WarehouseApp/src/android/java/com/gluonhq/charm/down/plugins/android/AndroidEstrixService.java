package com.gluonhq.charm.down.plugins.android;

import android.content.pm.ActivityInfo;
import android.view.WindowManager;
import com.gluonhq.impl.charm.down.plugins.DefaultEstrixService;
import javafxports.android.FXActivity;

public class AndroidEstrixService extends DefaultEstrixService {

    @Override
    public String checkSystem() {
        return "android";
    }

    public void switchOffLock() {
        FXActivity.getInstance().runOnUiThread(() -> {
            FXActivity.getInstance().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        });
    }

    public void switchOnLock() {
        FXActivity.getInstance().runOnUiThread(() -> {
            FXActivity.getInstance().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        });
    }

//    @Override
    public void setOnLandscape() {
        try {
            FXActivity.getInstance().runOnUiThread(() -> {
                FXActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            });
        } catch (Exception e){

        }
    }
    public void setOnPortrait() {
        try {
            FXActivity.getInstance().runOnUiThread(() -> {
                FXActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            });
        } catch (Exception e){

        }
    }
}
