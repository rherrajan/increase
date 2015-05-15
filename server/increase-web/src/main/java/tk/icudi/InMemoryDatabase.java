package tk.icudi;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase implements Database {

	private List<Unit> agents = new ArrayList<Unit>();

	@Override
	public void save(Schema schema, Object toSave) {
		this.agents.add((Unit) toSave);
	}

	@Override
	public List<Unit> load(Schema schema) {
		return agents;
	}

}
