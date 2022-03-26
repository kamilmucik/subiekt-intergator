package pl.estrix.warehouse.core;


import pl.estrix.warehouse.controller.RootController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupSensor {

    private static Boolean popupResult;

    private static PopupSensor instance = null;
    private final List<PopupSensorListener> listeners = new ArrayList<>();
    private final Map<Object, PopupSensorListener> listenerMap = new HashMap<>();

    public static synchronized PopupSensor getInstance() {
        if (instance == null) {
            instance = new PopupSensor();
        }
        return instance;
    }

    public void fireChangeEvent(Boolean answer) {
        for (PopupSensorListener listener : listeners){
            listener.onReadingChange(answer);
        }
    }

    public void fireChangeEvent(Object object, Boolean answer) {
        listenerMap.get(object).onReadingChange(answer);
//        listenerMap.forEach( (k,v) ->{
////            System.out.println("k: " + k.getClass() +" : " + v);
//            if (k instanceof AboutController) {
////                System.out.println("AboutController");
//                listenerMap.get(k).onReadingChange(answer);
//            }
//            if (k instanceof SearchController) {
////                System.out.println("AboutController");
//                listenerMap.get(k).onReadingChange(answer);
//            }
//        });
    }

    public void open(Object object) {
        RootController.getInstance().blurPaneOn(object);
    }

    public void open(Object object, Object clazz){
        RootController.getInstance().blurPaneOn(object, clazz);
    }

    public void addListener(PopupSensorListener listener){
        listeners.add(listener);
    }
    public void addListener(PopupSensorListener listener, Object clazz){
        listenerMap.put(clazz, listener);
    }

}