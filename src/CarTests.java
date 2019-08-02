
import static org.junit.Assert.*;

import org.junit.Test;


public class CarTests {

	@Test
	public void testGeneration() {
		Car c = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,5,"Red");
		
		assertTrue("XYZ123".equals(c.getId()));
		assertTrue("Nissan".equals(c.getMake()));
		assertTrue("XTerra".equals(c.getModel()));
		assertTrue(2004 == c.getYear());
		assertTrue(16500.00 == c.getPrice());
		assertTrue(24.0 == c.getMpg());
		assertTrue(5 == c.getCondition());
		assertTrue(2300.00 == c.getService());
		assertTrue("Red".equals(c.getColor()));
	}
	
	@Test
	public void testToString(){
		/*
		 * Tests for a single digit remainder Ex: 2000.50 or 5000.40
		 */
		Car c = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,5,"Red");
		
		assertTrue(c.toString(),"XYZ123 - 2004:Nissan:XTerra($16,500.00)".equals(c.toString()));
		
		/*
		 * This part tests for a double digit remainder.
		 */
		Car d = new Car("XYZ123","Nissan","XTerra",16500.99,2300.00,24.0,2004,5,"Red");
		
		assertTrue(d.toString(),"XYZ123 - 2004:Nissan:XTerra($16,500.00)".equals(c.toString()));
	}
	
	@Test
	public void testTotalCost(){
		Car c = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,5,"Red");
		
		assertTrue(""+c.getTotalCost(1,15000),20362.5 == c.getTotalCost(1,15000));
	}
	
	@Test
	public void testToWrite(){
		Car c = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,5,"Red");
		
		assertTrue(c.toWrite('|'),"XYZ123|Nissan|XTerra|16500.0|2300.0|24.0|2004|5|Red".equals(c.toWrite('|')));
	}
	
	@Test
	public void testReadIn(){
		String carStr = "XYZ123|Nissan|XTerra|16500.0|2300.0|24.0|2004|5|Red";
		Car c = new Car("","","",0,0,0,0,0,"red");
		c.readIn(carStr,'|');
		
		assertTrue(c.getId(),"XYZ123".equals(c.getId()));
		assertTrue(c.getMake(),"Nissan".equals(c.getMake()));
		assertTrue(c.getModel(),"XTerra".equals(c.getModel()));
		assertTrue(c.getYear()+"",2004 == c.getYear());
		assertTrue(c.getPrice()+"",16500.00 == c.getPrice());
		assertTrue(c.getMpg()+"",24.0 == c.getMpg());
		assertTrue(c.getCondition()+"",5 == c.getCondition());
		assertTrue(c.getService()+"",2300.00 == c.getService());
		assertTrue(c.getColor(),"Red".equals(c.getColor()));
	}

}
