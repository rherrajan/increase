package tk.icudi;

public enum Direction {
	N, NE, E, SE, S, SW, W, NW;
	
	public static Direction valueOfAngle(double angle) {
		int index = (int) Math.round((((double) angle % 360) / 45));
		if(index == Direction.values().length){
			index = 0;
		}
		return Direction.values()[index];
	}
}
