package jar;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by rajaee on 1/8/18.
 */
public class ConfigurationReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationReader.class);

    private String applicationName;
    private String connectionString;
    private int refreshTimerIntervalInMs;

    Map<String, Object> confMap;

    public ConfigurationReader(String applicationName, String connectionString, int refreshTimerIntervalInMs) {
        this.applicationName = applicationName;
        this.connectionString = connectionString;
        this.refreshTimerIntervalInMs = refreshTimerIntervalInMs;
        confMap = new HashMap<String, Object>();
        initMap();
        createInitMapTask();
    }

    private void createInitMapTask(){
        Runnable runnable = new Runnable() {
            public void run() {
                initMap();
            }
        };
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(runnable , refreshTimerIntervalInMs, refreshTimerIntervalInMs, TimeUnit.MILLISECONDS);
    }

    private void initMap() {
        MongoCursor<Document> mongoCursor = Util.getDocumentsIterator(this.connectionString);
        if (mongoCursor != null) {
            try {
                while (mongoCursor.hasNext()) {
                    Document doc = mongoCursor.next();
                    if (doc.get(Constants.PROPERTY_APPLICATION_NAME).toString().equals(this.applicationName)) {
                        String propertyName = doc.get(Constants.PROPERTY_NAME).toString();
                        String propertyIsActive = doc.get(Constants.PROPERTY_IS_ACTIVE).toString();
                        if (propertyIsActive.equals("1")) {
                            putToMap(doc);
                        } else {
                            if (confMap.containsKey(propertyName)) {
                                confMap.remove(propertyName);
                            }
                        }
                    }
                }
            }catch (Exception e){
                logger.error("Error while initializing configuration map.", e);
            }
            finally {
                mongoCursor.close();
            }
        }
    }

    private void putToMap(Document doc){
        String propertyName = doc.get(Constants.PROPERTY_NAME).toString();
        String propertyType = doc.get(Constants.PROPERTY_TYPE).toString();
        String propertyValue = doc.get(Constants.PROPERTY_VALUE).toString();
        Object convertedVal = Util.parseValue(propertyType, propertyValue);
        confMap.put(propertyName, convertedVal);
    }

    @SuppressWarnings("unchecked")
    public <T> T GetValue(String key) {
        return (T) confMap.get(key);
    }
}
