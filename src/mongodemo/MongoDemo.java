package mongodemo;

import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.util.ArrayList;
import java.util.List;

public class MongoDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ThreadClass T1 = new ThreadClass("initialiseMongoDB");
            T1.start();

            ThreadClass T2 = new ThreadClass("processOperations");
            T2.start();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void processOperations() throws UnknownHostException {
        Mongo mongo = new Mongo("localhost", 27017);

        DB db = MongoDBConnect.getDB(mongo, "company4");

        DBCollection collection = MongoDBConnect.getCollection(db, "employees2");

        List<DataCarry> insertListData = new ArrayList();
        insertListData.add(new DataCarry("title", "MongoDB"));
        insertListData.add(new DataCarry("description", "ww"));
        insertListData.add(new DataCarry("likes", "11"));
        insertListData.add(new DataCarry("url", "sefasdfas"));
        insertListData.add(new DataCarry("by", "tutorials point"));
        collection = MongoDBConnect.InsertDataMongo(collection, insertListData);

        List<DataCarry> searchQuerySingle = new ArrayList();
        searchQuerySingle.add(new DataCarry("description", "ww"));
        collection = MongoDBConnect.updataDataMongoSingleEntry(collection, searchQuerySingle, "title", "wgrt");

        List<DataCarry> searchQuerySingle2 = new ArrayList();
        searchQuerySingle.add(new DataCarry("likes", "11"));
        List<DataCarry> updateQueryMulti = new ArrayList();
        updateQueryMulti.add(new DataCarry("description", "ww"));
        insertListData.add(new DataCarry("likes", "11"));
        collection = MongoDBConnect.updataDataMongoMultiEntry(collection, searchQuerySingle2, updateQueryMulti);

        List<DataCarry> deleteListData = new ArrayList();
        deleteListData.add(new DataCarry("title", "MongoDB"));
        deleteListData.add(new DataCarry("description", "ww"));
        deleteListData.add(new DataCarry("likes", "11"));
        BasicDBObject deleteBasicDBObj = MongoDBConnect.returnBasicObject(deleteListData);
        collection = MongoDBConnect.deleteMongoData(collection, deleteBasicDBObj);
        /**
         * print all data
         */
        List<BasicDBObject> listBasicDBObject = MongoDBConnect.getMongoData(collection, null);
        MongoDBConnect.printListBasicObjects(listBasicDBObject);
        /**
         * prints only those records which have same key & pair that are in
         * deleteBasicDBObj
         */
        List<BasicDBObject> listBasicDBObject2 = MongoDBConnect.getMongoData(collection, deleteBasicDBObj);
        MongoDBConnect.printListBasicObjects(listBasicDBObject2);

        System.out.println("The Search Query has Executed!");

        MongoDBConnect.shutDownDB(db);

        MongoDBConnect.dropCollection(collection);
        MongoDBConnect.dropDB(db);
        mongo.close();

    }

    public static void initialiseMongoDB() {
        MongoDBConnect.startMongoDBServer("D", "D", "D:\\mongo\\bin", "D:\\mongo\\data\\db");
    }

}
