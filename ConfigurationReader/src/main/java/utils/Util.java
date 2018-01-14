package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import jar.ConfigurationReader;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rajaee on 1/14/18.
 */
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static MongoCursor<Document> getDocumentsIterator(String connectionString) {
        MongoCursor<Document> iterator = null;
        try {
            MongoCollection<Document> confs = getCollection(connectionString);
            iterator = confs.find().iterator();
        } catch (Exception e) {
            logger.error("Error while getting collection from database.", e);
        }
        return iterator;
    }

    public static MongoCollection<Document> getCollection(String connectionString) {
        MongoClientURI uri = new MongoClientURI(connectionString);
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        return db.getCollection(Constants.COLLECTION_NAME);
    }

    public static Object parseValue(String propertyType, String propertyValue) {
        Object convertedVal = null;
        try {
            switch (propertyType) {
                case "boolean":
                    convertedVal = propertyValue.toLowerCase().equals("true") || propertyValue.toLowerCase().equals("false")
                            ? Boolean.parseBoolean(propertyValue.toLowerCase()) : null;
                    break;
                case "double":
                    convertedVal = Double.parseDouble(propertyValue);
                    break;
                case "integer":
                    convertedVal = Integer.parseInt(propertyValue);
                    break;
                case "string":
                    convertedVal = propertyValue;
                    break;
            }
        } catch (NumberFormatException e) {
            logger.error("Error while parsing property value.", e);
        }
        return convertedVal;
    }
}
