//package com.gluonhq.charm.down.plugins.android;
//
//import com.gluonhq.charm.down.Services;
//import com.gluonhq.charm.down.plugins.AdViewService;
//import com.gluonhq.charm.down.plugins.LifecycleEvent;
//import com.gluonhq.charm.down.plugins.LifecycleService;
//import javafx.beans.property.StringProperty;
//import javafxports.android.FXActivity;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import android.view.Gravity;
//import android.widget.LinearLayout;
//
//public class AndroidAdViewService implements AdViewService {
//
//    private AdView adView;
//
//    @Override
//    public void setAdUnit(String unitId, StringProperty stringProperty) {
//        stringProperty.setValue("Android");
//        FXActivity.getInstance().runOnUiThread(() -> {
//            LinearLayout layout = new LinearLayout(FXActivity.getInstance());
//            layout.setVerticalGravity(Gravity.BOTTOM);
//            layout.setOrientation(LinearLayout.VERTICAL);
//
//            adView = new AdView(FXActivity.getInstance());
//            adView.setAdSize(AdSize.SMART_BANNER);
//            adView.setAdUnitId(unitId);
//
//            AdRequest adRequest;
////            if (test) {
////                adRequest = new AdRequest.Builder()
////                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
//////                        .addTestDevice(testDeviceId)        // from logcat!
////                        .build();
////            } else {
//                adRequest = new AdRequest.Builder().build();
////            }
//
//            adView.loadAd(adRequest);
//            adView.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                }
//            });
//
//            layout.addView(adView);
//
//            FXActivity.getViewGroup().addView(layout);
//        });
//
//        Services.get(LifecycleService.class).ifPresent(service -> {
//            service.addListener(LifecycleEvent.RESUME, () -> FXActivity.getInstance().runOnUiThread(() -> adView.resume()));
//            service.addListener(LifecycleEvent.PAUSE, () -> FXActivity.getInstance().runOnUiThread(() -> adView.pause()));
//        });
//
//    }
//
//    @Override
//    public String getUnit(String dataAdClient, String dataAdSlot) {
//        return "Android";
//    }
//
//}