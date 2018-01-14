package service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.ConfRecord;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajaee on 1/14/18.
 */
public class WebDemoService {
    private static final Logger logger = LoggerFactory.getLogger(WebDemoService.class);
    private String connectionString;

    public WebDemoService(String connectionString) {
        this.connectionString = connectionString;
    }

    public List<ConfRecord> findAll() {
        return getConfRecords();
    }

    public List<ConfRecord> search(String searchText) {
        List<ConfRecord> list = new ArrayList<>();
        MongoCollection<Document> collection = Util.getCollection("mongodb://admin:admin@ds247357.mlab.com:47357/confdb");
        BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("Name",
                new BasicDBObject("$regex", ".*" + searchText + ".*")
                        .append("$options", "i"));
        MongoCursor<Document> mongoCursor = collection.find(regexQuery).iterator();
        try {
            while (mongoCursor.hasNext()) {
                Document doc = mongoCursor.next();
                list.add(createConfRecord(doc));
            }
        } finally {
            mongoCursor.close();
        }
        return list;
    }

    public void save(ConfRecord confRecord) {
        confRecord.setId(getNewRecordId());
        Document document = createDocument(confRecord);
        MongoCollection<Document> collection = Util.getCollection(connectionString);
        collection.insertOne(document);
    }

    public void update(ConfRecord confRecord) {
        Document document = createDocument(confRecord);
        Document updateQuery = new Document(Constants.PROPERTY_ID, confRecord.getId());
        MongoCollection<Document> collection = Util.getCollection(connectionString);
        collection.updateOne(updateQuery, new Document("$set", document));
    }

    public void delete(Integer id) {
        MongoCollection<Document> collection = Util.getCollection(connectionString);
        collection.deleteOne(new Document(Constants.PROPERTY_ID, id));
    }

    public ConfRecord findById(Integer id) {
        List<ConfRecord> confRecords = getConfRecords();
        for (ConfRecord cr : confRecords) {
            if (cr.getId().equals(id)) {
                return cr;
            }
        }
        return null;
    }

    public boolean isExist(String name) {
        List<ConfRecord> confRecords = getConfRecords();
        for (ConfRecord confRecord : confRecords) {
            if (confRecord.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private List<ConfRecord> getConfRecords() {
        MongoCursor<Document> mongoCursor = Util.getDocumentsIterator(this.connectionString);
        List<ConfRecord> list = new ArrayList<ConfRecord>();
        if (mongoCursor != null) {
            try {
                while (mongoCursor.hasNext()) {
                    Document doc = mongoCursor.next();
                    ConfRecord confRecord = createConfRecord(doc);
                    list.add(confRecord);
                }
            } catch (Exception e) {
                logger.error("Error while getting all records.", e);
            } finally {
                mongoCursor.close();
            }
        }
        return list;
    }

    private ConfRecord createConfRecord(Document doc) {
        ConfRecord confRecord = null;
        try {
            Integer id = Integer.parseInt(doc.get(Constants.PROPERTY_ID).toString());
            String name = doc.get(Constants.PROPERTY_NAME).toString();
            String type = doc.get(Constants.PROPERTY_TYPE).toString();
            String value = doc.get(Constants.PROPERTY_VALUE).toString();
            String isActive = doc.get(Constants.PROPERTY_IS_ACTIVE).toString();
            String applicationName = doc.get(Constants.PROPERTY_APPLICATION_NAME).toString();
            confRecord = new ConfRecord(id, name, type, value, isActive, applicationName);
        } catch (NumberFormatException e) {
            logger.error("Error while converting Document to ConfRecord.", e);
        }
        return confRecord;
    }

    private Document createDocument(ConfRecord confRecord) {
        if(confRecord.getType().equals("boolean")){
            confRecord.setValue(confRecord.getValue().toLowerCase());
        }
        return new Document(Constants.PROPERTY_ID, confRecord.getId())
                .append(Constants.PROPERTY_NAME, confRecord.getName())
                .append(Constants.PROPERTY_TYPE, confRecord.getType())
                .append(Constants.PROPERTY_VALUE, confRecord.getValue())
                .append(Constants.PROPERTY_IS_ACTIVE, confRecord.getIsActive())
                .append(Constants.PROPERTY_APPLICATION_NAME, confRecord.getApplicationName());
    }

    private int getNewRecordId() {
        List<ConfRecord> confRecords = getConfRecords();
        int id = 0;
        for (ConfRecord confRecord : confRecords) {
            if (confRecord.getId() > id) {
                id = confRecord.getId();
            }
        }
        return ++id;
    }
}
