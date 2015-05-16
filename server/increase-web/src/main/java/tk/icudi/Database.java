package tk.icudi;

import java.util.List;

public interface Database {

	static enum Schema {
		player

	}

	void save(Schema schema, Identifyable toSave);

	// TODO: Typesave machen
	List<Unit> load(Schema schema);

	void delete(Schema schema, int itemsToKeep);

}
