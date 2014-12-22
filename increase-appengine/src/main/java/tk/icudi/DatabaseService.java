package tk.icudi;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class DatabaseService {

	public void save(String postData) {

		Key key = KeyFactory.createKey("RawData", "RawData");
		
		Entity entity = new Entity("RawData", key);
		entity.setProperty("json", postData);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);


	}

	public String load() {
		
	    Key key = KeyFactory.createKey("RawData", "RawData");
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("RawData", key);
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	    if (entities.isEmpty()) {
	    	return "noch nüscht";
	    }
	    
	    return (String) entities.get(0).getProperty("json");
	    
	}

}
