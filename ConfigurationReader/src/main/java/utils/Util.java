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
}
