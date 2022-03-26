package com.gluonhq.charm.down.plugins;

public interface EstrixService {

    int intMethod(int in);

    String checkSystem();

    //https://www.w3.org/TR/wake-lock-use-cases//

    /**
     * https://www.w3.org/TR/wake-lock-use-cases/
     * http://cadabracorp.com/blog/2013/08/23/keeping-your-mobile-screen-active-ios-and-android/
     * <pre></>- (void)applicationDidBecomeActive:(UIApplication *)application {
     // stop screen from sleeping
     [[UIApplication sharedApplication] setIdleTimerDisabled:YES];
     }<pre/>
     */
    void switchOffLock();

    void switchOnLock();

    void setOnLandscape();
    void setOnPortrait();
}