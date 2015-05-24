package tk.icudi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDatabase implements Database {

	private Map<String, Unit> agents = new HashMap<String, Unit>();

	@Override
	public void save(Schema schema, Identifyable toSave) {
		this.agents.put(toSave.getIdentification(), ((Unit) toSave));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> load(Schema schema, Class<T> clazz) {
		return new ArrayList<T>((Collection<? extends T>) agents.values());
	}

	@Override
	public void delete(Schema schema, int itemsToKeep) {
		agents = new HashMap<String, Unit>();
	}

}
