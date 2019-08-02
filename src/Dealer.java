import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;




/**
 * An instance of this class will act as a manager to a CarLot object that isn't managed by the GUI.
 * @author Bryce DeVaughn
 *
 */
public class Dealer {

	private CarLot lot;
	private List<Car> currentCars;

	public Dealer(CarLot lot) {
		this.lot = lot;
		currentCars = lot.filterCarsByColor("Red");
	}
	
	public List<Car> getCurrentCars() {
		return currentCars;
	}
	public CarLot getLot() {
		return lot;
	}
	
	/**
	 * This method will call a method to add dummy cars.
	 */
	public void addDummyCars(){
		lot.addDummyCars();
		writeToFile();
	}
	
	/**
	 * This method will update the list of current cars with cars of the specified color. But if the
	 * inputed String is "ALL" then all the cars will be added to the list of current cars.
	 */
	public void filterCarsByColor(String colorStr){
		currentCars.clear();
		if("ALL".equals(colorStr)){
			currentCars.addAll(lot.getCars());
		}else{
			currentCars = lot.filterCarsByColor(colorStr);
		}
	}

	/**
	 * This method will add a dummy car with the specified ID, but will have dummy values to be modified later.
	 * @throws DuplicateCarIdException 
	 */
	public void addNewCar(String id) throws DuplicateCarIdException{
		if(lot.findCar(id)==null){
			Car dummy = new Car(id,"0","0",0,0,0,0,0,"Black");
			lot.addCar(dummy);
		}else{
			throw new DuplicateCarIdException("Car with the same ID exists.");
		}
	}

	/**
	 * This method will remove a specified car from the lot.
	 * @throws CarNotFoundException 
	 */
	public Car removeCar(String id) throws CarNotFoundException{
		Car rCar = lot.findCar(id);
		if(rCar != null){
			lot.removeCar(rCar);
			currentCars.remove(rCar);
		}else{
			throw new CarNotFoundException("Car with "+id+" can't be found.");
		}

		return rCar;
	}

	/**
	 * This method will update the specified car with the same ID.
	 * @throws CarNotFoundException 
	 */
	public void updateCar(String id, String make, String model, double price,double service, double mpg, int year, int condition, String color) throws CarNotFoundException{
		Car upCar = lot.findCar(id);

		if(upCar != null){
			upCar.setMake(make);
			upCar.setModel(model);
			upCar.setPrice(price);
			upCar.setService(service);
			upCar.setMpg(mpg);
			upCar.setYear(year);
			upCar.setCondition(condition);
			upCar.setColor(color);

		}else{
			throw new CarNotFoundException("Car with "+id+" can't be found.");
		}
	}

	/**
	 * This method will return the cheapest price that can be found within the lot.
	 */
	public Car findCheapestCar(){
		Car rtnCar = null;
		double rtnVal = 0.0;
		rtnVal = lot.getCars().getFirst().getPrice();
		rtnCar = lot.getCars().getFirst();

		for(Car c: lot.getCars()){
			if(rtnVal > c.getPrice()){
				rtnVal = c.getPrice();
				rtnCar = c;
			}
		}

		return rtnCar;
	}

	/**
	 * This method will return the total value of all cars in the lot.
	 */
	public double totalValue(){

		double rtnVal = 0.0;

		for(Car c: lot.getCars()){
			rtnVal += c.getTotalCost(1,15000);
		}

		return rtnVal;
	}

	/**
	 * This method will return the average price of the current lot.
	 */
	public double averageCarPrice(){
		double rtnVal = 0.0;

		for(Car c: lot.getCars())
			rtnVal += c.getPrice();

		rtnVal /= lot.getCars().size();

		return rtnVal;
	}

	/**
	 * This method will find a car based on the given ID.
	 * @throws CarNotFoundException 
	 */
	public Car carById(String id) throws CarNotFoundException{
		Car c = lot.findCar(id);

		if(c == null){
			throw new CarNotFoundException("Car with "+id+" can't be found.");
		}

		return c;
	}

	/**
	 * This method will write all the cars in the car lot to a file to be saved.
	 * @throws IOException 
	 */
	public void writeToFile(){
		File f = new File("carlot.txt");//File containing the carlot.
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)))){
			if(lot.getCars().size()>0){
				for(Car c: lot.getCars()){
					pw.println(c.toWrite('|'));//Prints the cars' settings into the file
				}
			}else{
				pw.print("");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method will read in all the cars' settings from the correct file and then fill the lot
	 * with the correct cars.
	 */
	public void readInCars(){
		File f = new File("carlot.txt");//File containing the carlot.
		try(BufferedReader br = new BufferedReader(new FileReader(f))){
			String carStr = br.readLine();
			while(carStr!=null){
				Car newCar = new Car("LOL","0","0",0,0,0,0,0,"Black");
				newCar.readIn(carStr, '|');
				lot.addCar(newCar);

				carStr = br.readLine();//Sets the reader to the next line
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
