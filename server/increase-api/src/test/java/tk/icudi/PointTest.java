package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

	@Test
	public void test_should_find_north() {
		
		Point center = new Point();
		center.setLat(0);
		center.setLng(0);
		
		Point top = new Point();
		top.setLat(10);
		top.setLng(0);
		
		Direction direction = top.getDirectionFrom(center);
		
		assertThat(direction, is(Direction.N));
	}
	
	@Test
	public void test_should_find_south_east() {
		
		Point center = new Point();
		center.setLat(0);
		center.setLng(0);
		
		Point se = new Point();
		se.setLat(-5);
		se.setLng(10);
		
		Direction direction = se.getDirectionFrom(center);
		
		assertThat(direction, is(Direction.E));
	}

}
