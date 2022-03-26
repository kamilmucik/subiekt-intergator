package pl.estrix.warehouse.util;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.estrix.warehouse.MainApp;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionManager {


    public static Map<String,String> properties = new ConcurrentHashMap<>();
    public static Map<String,String> customsProperties = new ConcurrentHashMap<>();
    private final Properties props = new Properties();
    public final String SESSION_PROPERTIES_FILENAME;
    private File path;
    public static String SERVICE_URL = "http://192.168.11.2";
    public static String SERVICE_PORT = "8080";


    public static BooleanProperty errorStatus = new SimpleBooleanProperty(Boolean.FALSE);
    public static StringProperty errorMessage = new SimpleStringProperty("jakis blad");

    private static SessionManager instance = null;

    public SessionManager() {
        try {
            path = PlatformFactory.getPlatform().getPrivateStorage();
        } catch (IOException e) {
            String tmp = System.getProperty("java.io.tmpdir");
            path = new File(tmp);
        }
        this.SESSION_PROPERTIES_FILENAME = "warehouse_setting.properties";
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void saveSession() {
        try {
            for (Map.Entry<String, String> entry : customsProperties.entrySet()) {
                props.setProperty(entry.getKey(), entry.getValue());
            }
            File file=new File(path,SESSION_PROPERTIES_FILENAME);
            props.store(new FileWriter(file), SESSION_PROPERTIES_FILENAME);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int restoreSession() {
        Reader reader = null;
        try {
            File file=new File(path,SESSION_PROPERTIES_FILENAME);
            reader = new FileReader(file);
            props.load(reader);

            for ( Map.Entry entry : props.entrySet()){
                if(!customsProperties.containsKey(entry.getKey())) {
                    customsProperties.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        } catch (FileNotFoundException ignored) {
            if(!customsProperties.containsKey("serverAddress")) {
                customsProperties.put("serverAddress", SERVICE_URL);
            }
            if(!customsProperties.containsKey("serverPort")) {
                customsProperties.put("serverPort", SERVICE_PORT);
            }
            return -1;
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public void loadProperties(String path) {
        try {
            final Properties prop = new Properties();
            try (final InputStream stream = MainApp.class.getResourceAsStream(path)) {
                prop.load(stream);
            }

            for (Map.Entry entry : prop.entrySet()){
                properties.put(entry.getKey().toString(),entry.getValue().toString());
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
}
