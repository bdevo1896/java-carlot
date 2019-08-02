
import java.util.LinkedList;
import java.util.List;

/**
 * An instance of this class will hold a LinkedList of 5 predetermined Car objects. This class will allow the list to be modified with the
 * addCar,removeCar,and findCar methods.
 * @author Bryce DeVaughn.
 *
 */
public class CarLot {
	
	private LinkedList<Car> cars;
	
	public CarLot(){
		cars = new LinkedList<Car>();
		//cars = testInit();
	}
	
	/**
	 * This method just adds dummy cars to the list for testing purposes.
	 * @return
	 */
	public void testInit(){
		LinkedList<Car> rtnList = new LinkedList<Car>();
		
		Car a = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,4,"Red");
		rtnList.add(a);
		
		Car b = new Car("DV11GG","Nissan","XTerra",16500.00,1500.5,24.0,2004,3,"Blue");
		rtnList.add(b);
		
		Car c = new Car("REALOG","Nissan","XTerra",120500.00,2300.00,24.0,2004,5,"Green");
		rtnList.add(c);
		
		Car d = new Car("HELLO?","Nissan","XTerra",16500.00,3500.00,24.0,2004,2,"Yellow");
		rtnList.add(d);
		
		Car e = new Car("1","Nissan","XTerra",7500.00,5000.0,24.0,2004,1,"Orange");
		rtnList.add(e);
		
		cars = rtnList;
	}
	
	/**
	 * This method will add a number of cars to help test.
	 */
	public void addDummyCars(){
		Car a = new Car("XYZ123","Nissan","XTerra",16500.00,2300.00,24.0,2004,4,"Red");
		cars.add(a);
		
		Car b = new Car("DV11GG","Nissan","XTerra",16500.00,1500.5,24.0,2004,3,"Blue");
		cars.add(b);
		
		Car c = new Car("REALOG","Nissan","XTerra",120500.00,2300.00,24.0,2004,5,"Green");
		cars.add(c);
		
		Car d = new Car("HELLO?","Nissan","XTerra",16500.00,3500.00,24.0,2004,2,"Yellow");
		cars.add(d);
		
		Car e = new Car("1","Nissan","XTerra",7500.00,5000.0,24.0,2004,1,"Orange");
		cars.add(e);
	}
	
	/**
	 * This method will return a list of cars matching the inputed color.
	 */
	public List<Car> filterCarsByColor(String colorStr){
		LinkedList<Car> rtnList = new LinkedList<Car>();
		
		for(Car c: cars){
			if(colorStr.equalsIgnoreCase(c.getColor())){
				rtnList.add(c);
			}
		}
		return rtnList;
		
	}
	
	/**
	 * Adds the specified car to the list.
	 * @param c
	 */
	public void addCar(Car c){
		cars.add(c);
	}
	
	/**
	 * Removes a specific car from the list.
	 * @param c
	 */
	public void removeCar(Car c){
		cars.remove(c);
	}
	
	/**
	 * Finds the car with the specified Id.
	 * @param Id
	 * @return
	 */
	public Car findCar(String id){
		Car c = null;
		
		for(Car fCar: cars){
			if(id.equals(fCar.getId())){
				c = fCar;
			}
		}
		
		return c;
	}

	public LinkedList<Car> getCars() {
		return cars;
	}
	
	
}
