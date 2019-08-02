
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;


public class DealerTests {

	@Test
	public void testGeneration(){
		CarLot c = new CarLot();
		Dealer d = new Dealer(c);

		assertTrue(d.getLot() == c);
	}

	@Test
	public void testAddNewCar() throws DuplicateCarIdException {
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);
		
		d.addNewCar("111111");
		
		assertTrue(c.findCar("111111")!=null);
	}

	@Test
	public void testUpdateCar() throws CarNotFoundException{
		Car dummy = new Car("111111","0","0",0,0,0,0,0,"Black");
		
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);
		
		c.addCar(dummy);
		
		d.updateCar("111111","Nissan","XTerra",15000.0,2000.0,25.0,2010,5,"Black");
		
		Car testC = d.carById("111111");
		
		assertTrue(testC.toString(),"Nissan".equals(testC.getMake()));
		assertTrue(testC.toString(),"XTerra".equals(testC.getModel()));
		assertTrue(testC.toString(),15000.0 == testC.getPrice());
		assertTrue(testC.toString(),2000.0 == testC.getService());
		assertTrue(""+testC.getMpg(),25.0 == testC.getMpg());
		assertTrue(""+testC.getYear(),2010 == testC.getYear());
		assertTrue(""+testC.getCondition(),5 == testC.getCondition());
		assertTrue(""+testC.getColor(),"Black".equals(testC.getColor()));
	}

	@Test
	public void testRemoveCar() throws CarNotFoundException{
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);

		d.removeCar("XYZ123");

		assertTrue(c.findCar("XYZ123")==null);
	}

	@Test
	public void testCarById() throws CarNotFoundException{
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);

		Car testCar = d.carById("XYZ123");

		assertTrue("XYZ123".equals(testCar.getId()));

	}

	@Test
	public void testTotalValue(){
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);

		double testVal = 0.0;
		for(Car testCar: c.getCars()){
			testVal+=testCar.getTotalCost(1, 15000);
		}

		assertTrue(testVal == d.totalValue());
	}

	@Test
	public void testAverageCarPrice(){
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);
		
		double testVal = 35500.0;
		
		assertTrue(""+d.averageCarPrice(),testVal == d.averageCarPrice());
	}

	@Test
	public void testFindCheapestCar(){
		CarLot c = new CarLot();
		c.testInit();
		Dealer d = new Dealer(c);

		Car cheapest = c.getCars().getLast();

		Car testCar = d.findCheapestCar();

		assertTrue(testCar == cheapest);
	}

	@Test
	public void testFilterCarsByColor(){

	}
	
	@Test
	public void testWriteCar(){
		CarLot c = new CarLot();
		Dealer d = new Dealer(c);
		c.addCar(new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,5,"Red") );
		d.writeToFile();
		
		try(Scanner scan = new Scanner(new File("carlot.txt"));) {
			String str = scan.nextLine();
			assertTrue("XYZ123|Nissan|XTerra|16500.0|2300.0|24.0|2004|5|Red".equals(str));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadInCars() throws CarNotFoundException{
		CarLot c = new CarLot();
		Dealer d = new Dealer(c);
		d.readInCars();
		
		assertTrue(d.carById("XYZ123")!=null);
	}
	
	@Test
	public void testFilterCarsByColorWithAColor(){
		CarLot c = new CarLot();
		Dealer d = new Dealer(c);
		c.addDummyCars();
		c.addDummyCars();
		
		d.filterCarsByColor("Red");
		
		for(Car testCar: d.getCurrentCars()){
			assertTrue("Red".equals(testCar.getColor()));
		}
	}

}
