package tk.icudi;

import java.util.List;

public interface Database {

	static enum Schema {
		player, hackedAgents

	}

	void save(Schema schema, Identifyable toSave);

	// TODO: Typesave machen
	<T> List<T> load(Schema schema, Class<T> clazz);

	void delete(Schema schema, int itemsToKeep);

}
