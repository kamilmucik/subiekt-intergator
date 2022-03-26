package pl.estrix.warehouse.core;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationSensor {

    private LinkedHashSet<String> messages = new LinkedHashSet<>();
    private int messageCounter = 0;
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final List<NotificationSensorListener> listeners = new ArrayList<>();
    private boolean isActive = true;

    public NotificationSensor() {
        service.submit(new Runnable() {
            @Override
            public void run() {
                while (isActive){
                    try{
                        Thread.sleep(1000);
                    } catch( InterruptedException e){
                        //do nothing
                    }
                    if (messages.size() != messageCounter){
                        fireChangeEvent();
                        messageCounter = messages.size();
                    }
                }
            }

            private void fireChangeEvent() {
                for (NotificationSensorListener listener : listeners){
                    listener.onReadingChange();
                }
            }
        });
    }

    public LinkedHashSet<String> getMessages() {
        return messages;
    }

    public void addListener(NotificationSensorListener listener){
        listeners.add(listener);
    }

    public void shutdown(){
        isActive = false;
        service.shutdown();
    }
}