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
		top.setLng(0);
		top.setLat(10);
		
		assertThat(top.getDirectionFrom(center), is(Direction.N));
	}
	
	@Test
	public void test_should_find_south() {
		
		Point center = new Point();
		center.setLat(0);
		center.setLng(0);
		
		Point top = new Point();
		top.setLng(0);
		top.setLat(-10);
		
		assertThat(top.getDirectionFrom(center), is(Direction.S));
	}
	
	@Test
	public void test_should_find_south_east() {
		
		Point center = new Point();
		center.setLng(0);
		center.setLat(0);
		
		Point se = new Point();
		se.setLng(10);
		se.setLat(-10);
		
		assertThat(se.getDirectionFrom(center), is(Direction.SE));
	}
	
	@Test
	public void test_should_find_north_west() {
		
		Point center = new Point();
		center.setLng(0);
		center.setLat(0);
		
		Point se = new Point();
		se.setLng(-10);
		se.setLat(10);
		
		assertThat(se.getDirectionFrom(center), is(Direction.NW));
	}
	
	@Test
	public void test_should_find_west() {
		
		Point referenc = new Point();
		referenc.setLng(0);
		referenc.setLat(0);
		
		Point west = new Point();
		west.setLng(referenc.getLng()-10);
		west.setLat(referenc.getLat());
		
		assertThat(west.getDirectionFrom(referenc), is(Direction.W));
	}
	
	@Test
	public void test_should_find_krumbach() {
		
		Point odenhausen = new Point();
		odenhausen.setLng(8724732);
		odenhausen.setLat(50664071);
		
		Point krumbach = new Point();
		krumbach.setLng(8611887);
		krumbach.setLat(50668246);
		
		assertThat(krumbach.getDirectionFrom(odenhausen), is(Direction.W));
	}

}
