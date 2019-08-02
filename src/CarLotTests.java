
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class CarLotTests {

	@Test
	public void testGeneration() {
		CarLot c = new CarLot();
		
		assertTrue(c.getCars() != null);
		//assertTrue(c.getCars().size() == 5);
	}
	
	@Test
	public void testAddCar(){
		CarLot c = new CarLot();
		
		Car dummy = new Car("111111","0","0",0,0,0,0,0,"Black");
		
		c.addCar(dummy);
		
		boolean found = false;
		for(Car t: c.getCars()){
			if(t == dummy){
				found = true;
			}
		}
		
		assertTrue(found);
	}
	
	@Test
	public void testRemoveCar(){
		CarLot c = new CarLot();
		
		Car dummy = new Car("111111","0","0",0,0,0,0,0,"Black");
		
		c.addCar(dummy);
		c.removeCar(dummy);
		for(Car t: c.getCars()){
			if(t == dummy){
				fail("Found a car that was supposed to be removed.");
			}
		}
	}
	
	@Test
	public void testFindCar(){
		CarLot c = new CarLot();
		Car carT = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,5,"Red");
		c.addCar(carT);
		
		Car testCar = c.findCar("XYZ123");
		
		assertTrue("XYZ123".equals(testCar.getId()));
	}
	
	@Test
	public void testFilterCarsByColor(){
		CarLot c = new CarLot();
		/*
		 * Adding dummy car to test the filtering
		 */
		c.testInit();
		c.addDummyCars();
		c.addDummyCars();
		
		List<Car> filteredCars = c.filterCarsByColor("Red");
		
		for(Car carD: filteredCars){
			assertTrue("Red".equals(carD.getColor()));
		}
	}
}
