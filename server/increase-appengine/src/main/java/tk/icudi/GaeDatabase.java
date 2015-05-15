package tk.icudi;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.gson.Gson;

public class GaeDatabase implements Database {

	@Override
	public void save(Schema schema, Object toSave) {
		
		String jsonString = new Gson().toJson(toSave);		
		Key key = KeyFactory.createKey(schema.name(), schema.name());
		
		System.out.println(" --- save jsonString: " + jsonString);
		
		Entity entity = new Entity(schema.name(), key);
		entity.setProperty("json", new Text(jsonString));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);

	}
	
	@Override
	public List<Unit> load(Schema schema) {
		
		Key key = KeyFactory.createKey(schema.name(), schema.name());
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query(schema.name(), key);
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		    
		System.out.println(" Loading " + entities.size() + " " + schema.name() + "...");
		
		Gson gson = new Gson();	
		
		List<Unit> result = new ArrayList<Unit>();
		
	    for (Entity entity : entities) {
	    	Text text = (Text) entity.getProperty("json");
	    	String jsonString = text.getValue();
	    	
	    	System.out.println(" --- load jsonString: " + jsonString);
	    	Unit obj = gson.fromJson(jsonString, Unit.class);
	    	
	    	result.add(obj);
		}
	    
		return result;
	}

}
