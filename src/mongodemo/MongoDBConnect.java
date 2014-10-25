package mongodemo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MongoDBConnect {

    private static DB dbObj;

    /**
     * Selecting/Creating Database
     */
    public static DB getDB(Mongo mongo, String name)
            throws UnknownHostException {
        // mongo = new Mongo();

        if (dbObj == null) {
            dbObj = mongo.getDB(name);
            System.out.println("database: " + name + " selected successfully");

        }
        return dbObj;
    }

    /**
     * Selecting Collections
     */
    public static DBCollection getCollection(DB dbObj, String dbName)
            throws UnknownHostException {
        // mongo = new Mongo();
        DBCollection dbCollectionObj;
        if (dbObj != null) {
            dbCollectionObj = dbObj.getCollection(dbName);
            System.out.println("Collection: " + dbName + " selected successfully");

        } else {
            dbCollectionObj = null;
        }
        return dbCollectionObj;
    }

    /**
     * Creating Collection
     */
    public static DBCollection createCollection(DB dbObj, BasicDBObject basicDBObj, String collectionName)
            throws UnknownHostException {
        // mongo = new Mongo();
        DBCollection dbCollectionObj;
        dbCollectionObj = dbObj.createCollection(collectionName, basicDBObj);
        System.out.println("Collection: " + collectionName + " created successfully");

        return dbCollectionObj;
    }

    /**
     * inserting data
     */
    public static DBCollection InsertDataMongo(DBCollection dbCollectionObj, List<DataCarry> listDataInsert) {
        BasicDBObject basicDBObj = returnBasicObject(listDataInsert);
        try {

            dbCollectionObj.insert(basicDBObj);
            System.out.println("Data inserted successfully");
            return dbCollectionObj;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * return BasicObject From List
     */
    public static BasicDBObject returnBasicObject(List<DataCarry> listDataToBasicObjects) {
        BasicDBObject doc = new BasicDBObject();
        for (DataCarry dc : listDataToBasicObjects) {
            doc.append(dc.getColumnName(), dc.getColumnValue());
        }
        System.out.println("List Data Converted to BasicDBObject ");
        return doc;
    }

    /**
     * return list of BasicObject From List of search data
     */
    public static List<BasicDBObject> returnSetBasicObject(List<DataCarry> listDataToBasicSetObjects) {

        List<BasicDBObject> listBasicDBObject = new ArrayList<BasicDBObject>();
        for (DataCarry dc : listDataToBasicSetObjects) {
            BasicDBObject doc = new BasicDBObject();
            doc.append("$set", new BasicDBObject().append(dc.getColumnName(), dc.getColumnValue()));
            listBasicDBObject.add(doc);
        }

        System.out.println("List Data Converted to List of BasicDBObject wit Set Command   ");
        return listBasicDBObject;
    }

    /**
     * update Data in Mongo for Single Entry
     */
    public static DBCollection updataDataMongoSingleEntry(DBCollection collection, List<DataCarry> searchQueryList, String keyTobeUpdated, String newValueForKey) {

        BasicDBObject searchQueryDBObj = MongoDBConnect.returnBasicObject(searchQueryList);
        BasicDBObject updateDocument = new BasicDBObject();
        updateDocument.append("$set", new BasicDBObject().append(keyTobeUpdated, newValueForKey));

        collection.update(searchQueryDBObj, updateDocument);
        System.out.println("Single Value Updated");
        return collection;

    }

    /**
     * update Data in Mongo for Multiple Entry
     */
    public static DBCollection updataDataMongoMultiEntry(DBCollection collection, List<DataCarry> searchQueryList, List<DataCarry> updateQueryList) {

        BasicDBObject searchQueryDBObj = MongoDBConnect.returnBasicObject(searchQueryList);
        List<BasicDBObject> listUpdateQueryDBObj = MongoDBConnect.returnSetBasicObject(updateQueryList);

        for (BasicDBObject updateQueryDBObj : listUpdateQueryDBObj) {
            collection.update(searchQueryDBObj, updateQueryDBObj);
        }
        System.out.println("Multiple Values Updated");

        return collection;

    }

    /**
     *print List of BasicObjects
     */
    public static void printListBasicObjects(List<BasicDBObject> basicObjList) {
        System.out.println("Printing Data");

        for (BasicDBObject i : basicObjList) {
            Iterator entries = i.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();
                Object key = thisEntry.getKey();
                Object value = thisEntry.getValue();
                System.out.println(key + ":" + value);
            }

            System.out.println("");

        }

        // ...
    }

      /**
     *get data from Collection
     */
    public static List<BasicDBObject> getMongoData(DBCollection collection, BasicDBObject queryObj) {
        DBCursor cursor;
        if (queryObj != null) {
            cursor = collection.find(queryObj);
        } else {
            cursor = collection.find();
        }
        List<BasicDBObject> basicObj = new ArrayList<>();

        while (cursor.hasNext()) {

            basicObj.add((BasicDBObject) cursor.next());

        }
        System.out.println("Data from Collection retrieved in Table");
        return basicObj;

        // ...
    }

     /**
     *delete data from Collection 
     */
    public static DBCollection deleteMongoData(DBCollection collection, BasicDBObject queryObj) {
        DBCursor cursor;
        if (queryObj != null) {
            cursor = collection.find(queryObj);
        } else {
            cursor = collection.find();
        }

        if (cursor.hasNext()) {
            for (int i = 0; i <= cursor.size(); i++) {
                collection.remove(queryObj);
                System.out.println("Deleted Row: " + queryObj);
            }
        }

        return collection;

        // ...
    }

     /**
     *start Mongo DBServer from code
     */
    
    public static boolean startMongoDBServer(String MongodBDrive, String DBPathDrive, String MongoDBBinPath, String DBPath) {
        boolean isSucess = false;
        String cmd = new String(MongodBDrive.toUpperCase() + ": && cd " + MongoDBBinPath + ">mongod && " + DBPathDrive.toUpperCase() + ": && mongod --dbpath \"" + DBPath + "\"");
        if (isServiceRunning(cmd)) {
            isSucess = runCmd(cmd);
            System.out.println("Instance of Mongo DB Started now");
        } else {
            System.out.println("Instance of Mongo DB Already Running");
            isSucess = true;
        }
        if (!isSucess) {
            System.out.println("Mongo DB Failed");
        }
        return isSucess;
    }

     /**
     *run cmd commands from code
     */
    
    
    public static boolean runCmd(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(new String[]{"cmd.exe", "/c", cmd});
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("cmd commands: " + line);
            }

            System.out.println("Cmd Command execution started !!!!!!!");
            return true;
        } catch (Exception e) {
            System.out.println("Cmd Command execution failed");
            return false;
        }
    }

    
     /**
     *check if service is running or not
     */
    
    public static boolean isServiceRunning(String cmd) {
        try {

            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(new String[]{"cmd.exe", "/c", cmd});
            p.waitFor();
            return (p.exitValue() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

     /**
     *shut down of db
     */
    
    public static void shutDownDB(DB db) {
        System.out.println("Executing: use " + db.getName() + " && db.shutdownServer()");

        runCmd("use " + db.getName() + " && db.shutdownServer()");
    }

     /**
     *drop collection
     */
    
    public static void dropCollection(DBCollection collection) {
        System.out.println("Dropping Collection:" + collection);
        collection.drop();
    }

     /**
     *Drop DB from code
     */
    
    public static void dropDB(DB db) {
        System.out.println("Dropping DB" + db);
        db.dropDatabase();
    }

}
