
package mongodemo;

import static mongodemo.MongoDemo.initialiseMongoDB;
import static mongodemo.MongoDemo.processOperations;

/**
 *
 * @author 433282
 */

class ThreadClass extends Thread {
   private Thread t;
   private String threadName;
   
   ThreadClass( String name){
       threadName = name;
       System.out.println("Creating " +  threadName );
   }
   public void run() {
      System.out.println("Running " +  threadName );
      try {
//         for(int i = 4; i > 0; i--) {
//            System.out.println("Thread: " + threadName + ", " + i);
//            // Let the thread sleep for a while.
//            Thread.sleep(50);
//         }
          if(threadName.equals("initialiseMongoDB")){
              initialiseMongoDB();
               System.out.println("Thread " +  threadName + " Running.");
          }
          
          if(threadName.equals("processOperations")){
              processOperations();
               System.out.println("Thread " +  threadName + " Running.");
          }
          
     } catch (Exception e) {
         System.out.println("Thread " +  threadName + " interrupted.");
     }
        if(threadName.equals("initialiseMongoDB")){
            
        }
     System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}