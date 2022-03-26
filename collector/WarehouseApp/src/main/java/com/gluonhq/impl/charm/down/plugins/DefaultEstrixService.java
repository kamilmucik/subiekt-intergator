package com.gluonhq.impl.charm.down.plugins;

import com.gluonhq.charm.down.plugins.EstrixService;

public abstract class DefaultEstrixService implements EstrixService {

    public DefaultEstrixService() {
    }

    @Override
    public String checkSystem() {
        return "default";
    }

    @Override
    public int intMethod(int in) {
        return 0;
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

    }
    @Override
    public void setOnPortrait() {

    }
}
