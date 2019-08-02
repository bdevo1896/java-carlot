

/**
 * An instance of this class will create a representation or model of a car to be
 * put into a car lot. There are instance variables to store the make, model, ID,
 * year, service price, initial price, MPG, and condition of the car.
 * @author Bryce DeVaughn
 *
 */
public class Car {

	private String id,make,model;
	private double price,service,mpg;
	private int year,condition;
	private String color;

	public Car(String id, String make, String model, double price,
			double service, double mpg, int year, int condition, String color) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.price = price;
		this.service = service;
		this.mpg = mpg;
		this.year = year;
		this.condition = condition;
		this.color = color;
	}

	/**
	 * This method will return a handle for a String to represent the current
	 * car.
	 */
	public String toString(){
		super.toString();
		String rtnStr = "";

		rtnStr = this.getId()+" - "+this.getYear()+":"+this.getMake()+":"+this.getModel();

		int fPartPrice = (int)this.getPrice()/1000;//This variable holds the thousands part of the price.
		int mPartPrice = (int)this.getPrice()-fPartPrice*1000;//This variable holds the hundreds to the point part of the price.
		int lPartPrice = 0;
		if(price > 0.0)
			lPartPrice = (int)this.getPrice()%(fPartPrice*1000+mPartPrice);//This variable holds the remainder of the price.
		
		rtnStr+="($"+fPartPrice+",";
		/*
		 * This if statement will check if the remainder is a single digit, if so, it adds a 0.
		 */
		if(mPartPrice==0)
			rtnStr+=mPartPrice+"00.";
		else
			rtnStr+=mPartPrice+".";
		
		if(lPartPrice < 10)
			rtnStr+=lPartPrice+"0)";
		else
			rtnStr+=lPartPrice+")";

		return rtnStr;

	}

	/**
	 * This method returns the total cost for car by summing together: initial
	 * price + (service price * years used) + (estimated price of gas for the 
	 * year * years used).
	 * @return
	 */
	public double getTotalCost(int yearsUsed, int miles){
		double rtnVal = 0;
		double gasPrice = 2.5;

		rtnVal += this.getPrice()+(this.getService()*yearsUsed)+(yearsUsed*(gasPrice*(15000/this.getMpg())));

		return rtnVal;
	}
	
	/**
	 * This method will create a String handle for writing it to a file. It will be in my preference
	 * of: ID|Make|Model|Price|Service|MPG|Year|Condition|Color
	 * @return String
	 */
	public String toWrite(char sep){
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(sep+make);
		sb.append(sep+model);
		sb.append(sep);
		sb.append(price);
		sb.append(sep);
		sb.append(service);
		sb.append(sep);
		sb.append(mpg);
		sb.append(sep);
		sb.append(year);
		sb.append(sep);
		sb.append(condition);
		sb.append(sep+color);
		String rtnStr = sb.toString();
		
		return rtnStr;
	}
	
	/**
	 * This method will take the inputed String and set this car to the same settings as described by
	 * the String. The String is expected to have the same format as the file-ready String created 
	 * by the 'toWrite' method.
	 */
	public void readIn(String str,char sep){
		String[] settings = str.split("["+sep+"]");//This list will contain all of the settings for the car in each element. (Expected length of the array is 9)
		if(settings.length==0||settings.length!=9)return;//This statement will make sure the formating of the inputed String is correct.
		id = settings[0];
		make = settings[1];
		model = settings[2];
		price = Double.parseDouble(settings[3]);
		service = Double.parseDouble(settings[4]);
		mpg = Double.parseDouble(settings[5]);
		year = Integer.parseInt(settings[6]);
		condition = Integer.parseInt(settings[7]);
		color = settings[8];
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getService() {
		return service;
	}

	public void setService(double service) {
		this.service = service;
	}

	public double getMpg() {
		return mpg;
	}

	public void setMpg(double mpg) {
		this.mpg = mpg;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}



}
