package tk.icudi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDatabase implements Database {

	private Map<String, Unit> agents = new HashMap<String, Unit>();

	@Override
	public void save(Schema schema, Identifyable toSave) {
		this.agents.put(toSave.getIdentification(), ((Unit) toSave));
	}

	@Override
	public List<Unit> load(Schema schema) {
		return new ArrayList<Unit>(agents.values());
	}

	@Override
	public void delete(Schema schema, int itemsToKeep) {
		agents = new HashMap<String, Unit>();
	}

}
