package tk.icudi;

public enum Faction {
	blue, green;

	public static Faction valueOfString(String string) {
		if(string == null || string.isEmpty()){
			return null;
		}
		
		if(string.equals("RESISTANCE")){
			return blue;
		}
		
		if(string.equals("ENLIGHTENED")){
			return green;
		}
		
		throw new RuntimeException("unknown faction '" + string + "' ");
	}
}
