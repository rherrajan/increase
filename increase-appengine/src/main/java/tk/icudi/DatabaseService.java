package tk.icudi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class DatabaseService {

	public void save(String postData) {

		Text json = new Text(postData);
		
		Key key = KeyFactory.createKey("RawData", "RawData");
		
		Entity entity = new Entity("RawData", key);
		entity.setProperty("json", json);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);

	}

	public List<String> load() {
		
	    Key key = KeyFactory.createKey("RawData", "RawData");
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("RawData", key);
//	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    
	    System.out.println(" Loading " + entities.size() + " raw-files...");
		 
	    List<String> data = new ArrayList<String>();
	    for (Entity entity : entities) {
	    	Text text = (Text) entity.getProperty("json");
	    	data.add(text.getValue());
		}
   
	    return data;
	}

}
