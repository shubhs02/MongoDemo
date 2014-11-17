Connecting MongoDB with Java
==================================================================

About Mongo DB:
==================================================================
MongoDB is a cross-platform, document oriented database that provides, high performance, high availability, and easy scalability. MongoDB works on concept of collection and document.

Database:
==================================================================
Database is a physical container for collections. Each database gets its own set of files on the file system. A single MongoDB server typically has multiple databases.

Collection:
==================================================================
Collection is a group of MongoDB documents. It is the equivalent of an RDBMS table. A collection exists within a single database. Collections do not enforce a schema. Documents within a collection can have different fields. Typically, all documents in a collection are of similar or related purpose.

Document:
==================================================================
A document is a set of key-value pairs. Documents have dynamic schema. Dynamic schema means that documents in the same collection do not need to have the same set of fields or structure, and common fields in a collection's documents may hold different types of data.

Connecting To Mongo DB:
==================================================================

MongoDB java driver is required to connect MongoDB with Java.

For old drivers (depreciating): 

Mongo mongo = new Mongo("localhost", 27017);

For new Drivers: 

MongoClient mongo = new MongoClient("localhost", 27017);

localhost is mongo server *27017 is port , you can use without port also

Connecting to DB (DataBase):
==================================================================
For Reading or Creating new DataBase:

DB dbObj = mongo.getDB( “DataBaseName”);

Get All DB Names in MongoDB
==================================================================
For getting All DataBase Names:

List dbs = mongo.getDatabaseNames();

Connecting to Collections (Like tables in RDBMS)
==================================================================
For Reading (or Creating) new Collections:

DBCollection dbCollectionObj = dbObj.getCollection( “CollectionName”);

Get All Collection Names in DB
==================================================================
For getting All Collections in a DB:

Set collections = dbObj.getCollectionNames();

Filling up of Data in BasicDBObject:
==================================================================
BasicDBObject is essential because we perform all the operations like insert, find.. etc through it:

BasicDBObject basicDBObj= new BasicDBObject();

basicDBObj.append(“Key1”, “value1”);

basicDBObj.append(“Key2”, “value2”);

basicDBObj.append(“Key3”, “value3”);

Data will be like { “Key1”, “value1”, “Key2”, “value2”, “Key3”, “value3”} Which will be inserted like a row or can used to search all the rows having same key & value pair.

Creating Collections:
==================================================================
For creating Collections with exclusive data: 

DBCollection dbCollectionObj = dbObj.createCollection(“CollectionName”,basicDBObj);

For Inserting Data in a Collection:
==================================================================
For inserting Data: 

dbCollectionObj.insert(basicDBObj);

Updating a Single record in a Row:
==================================================================
For updating a single record in a row:

updateDocument.append("$set", new BasicDBObject().append(“KeyToBeAppend “,”NewValue”));

*$inc can also be used instead of $set.

Updating multiple records in a row:
==================================================================
For updating multiple records in a row:

BasicDBObject updateQuery = new BasicDBObject();

updateQuery.append("$set", new BasicDBObject().append("KeyToBeAppend", "NewValue"));

BasicDBObject searchQuery = new BasicDBObject();

searchQuery.append("KeyToBeAppend", "OldValue");

collection.updateMulti(searchQuery, updateQuery);

Deleting a record:
==================================================================
For deleting a record:

BasicDBObject searchQuery = new BasicDBObject();

searchQuery.append("KeyToBeDeleted", "ValueToBeDeleted");

DBCursor cursor = collection.find(searchQuery );

if (cursor.hasNext()) {

     for (int i = 0; i <= cursor.size(); i++) {

            collection.remove(queryObj);

            System.out.println("Deleted Row: " + queryObj);

        }

    }
Printing All Records:
==================================================================
For Printing All records:

DBCursor cursor = collection.find();

while (cursor.hasNext()) {

System.out.println(cursor.next());

}

Searching and Print the search data:
==================================================================
For searching and printing search data:

BasicDBObject searchQuery = new BasicDBObject();

searchQuery.append("Key_ToBeSearched", "Value_ToBeSearched");

DBCursor cursor = collection.find(searchQuery );

while (cursor.hasNext()) {

System.out.println(cursor.next());

}
