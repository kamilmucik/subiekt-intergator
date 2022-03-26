package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.impl.charm.down.plugins.DefaultEstrixService;

public class DesktopEstrixService extends DefaultEstrixService {

    @Override
    public String checkSystem() {
        return "desktop";
    }

    @Override
    public void switchOffLock() {
        System.out.println("switchOffLock");
    }

    @Override
    public void switchOnLock() {
        System.out.println("switchOnLock");
    }

    @Override
    public void setOnLandscape() {
        System.out.println("setOnLandscape");
    }
    @Override
    public void setOnPortrait() {
        System.out.println("setOnPortrait");
    }
}
