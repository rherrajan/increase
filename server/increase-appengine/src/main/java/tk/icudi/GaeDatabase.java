package tk.icudi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;

public class GaeDatabase implements Database {

	final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	@Override
	public void save(Schema schema, Identifyable toSave) {

		String jsonString = new Gson().toJson(toSave);

		Entity entity = new Entity(schema.name(), toSave.getIdentification());
		
		Map<String, Object> indexes = toSave.getIndexes();
		if( indexes != null){
			for (Entry<String, Object> entry : indexes.entrySet()) {
				entity.setProperty(entry.getKey(), entry.getValue());
			}
		}
		
		entity.setProperty("json", jsonString);

		datastore.put(entity);
	}

	@Override
	public List<Unit> load(Schema schema) {

		Query query = new Query(schema.name());
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

		System.out.println(" Loading " + entities.size() + " " + schema.name() + "...");

		Gson gson = new Gson();

		List<Unit> result = new ArrayList<Unit>();

		for (Entity entity : entities) {
			String jsonString = (String) entity.getProperty("json");
			Unit obj = gson.fromJson(jsonString, Unit.class);

			result.add(obj);
		}

		return result;
	}

	@Override
	public void delete(Schema schema, int itemsToKeep) {

		final Query query = new Query(schema.name()).addSort("time", SortDirection.DESCENDING);
		query.setKeysOnly();
		final List<Key> keys = new ArrayList<Key>();
		for (final Entity entity : datastore.prepare(query).asIterable(FetchOptions.Builder.withLimit(100000))) {
			keys.add(entity.getKey());
		}
		System.out.println(keys.size() + " " +  schema.name() + " found");
		
		if(itemsToKeep < 0){
			return;
		}
		
		if(keys.size() > itemsToKeep){
			List<Key> toDelete = new ArrayList<Key>();
			for(int i=itemsToKeep; i<keys.size(); i++){
				toDelete.add(keys.get(i));
			}
			
			System.out.println("delete " + keys.size() + " " +  schema.name());
			datastore.delete(toDelete);
		} else {
			System.out.println("too few " +  schema.name() + " to delete");
		}

	}

}
