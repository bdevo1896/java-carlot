
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * An instance of this class will create a window with a GUI for the
 * car lot. 
 * @author Bryce DeVaughn
 *
 */
public class MainFrame extends JFrame {

	private JTextField makeBox,modelBox,priceBox,serviceBox,totalBox,averageBox,cheapBox;
	private JSpinner yearBox;
	private JLabel starsBox;
	private JSlider mpgSlider;
	private JScrollPane listDisplay;
	private JComboBox<String> colorBox,filterBox;
	private JButton addB,removeB,updateB;
	private CarLot lot;
	private Dealer dealer;
	private DealerListModel dealerList;
	private JList<Car> displayList;
	private Car currentCar;
	private ImageIcon[] starIconList;
	private JPanel centerPanel;
	private static String[] COLORS = new String[]{"Red","Blue","Black","Green","Yellow","Orange","Magenta","White"};
	private static String[] FILTER_COLORS = new String[]{"Red","Blue","Black","Green","Yellow","Orange","Magenta","White","ALL"};

	private static int MAX_X = 800,MAX_Y = 500;
	private int currentCon = 0;


	@SuppressWarnings("unchecked")
	public MainFrame(){
		this.setTitle("Car Lot Manager");
		this.setMaximumSize(new Dimension(MAX_X,MAX_Y));
		this.setMinimumSize(new Dimension(MAX_X,MAX_Y));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		lot = new CarLot();
		dealer = new Dealer(lot);
		dealer.readInCars();
		dealerList = new DealerListModel();
		starIconList = this.createStarIconList();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.white);

		this.getContentPane().add(mainPanel);

		/*
		 * Creating the inventory panel at the south part of the screen.
		 */
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		TitledBorder invT = new TitledBorder("Inventory");
		invT.setBorder(new LineBorder(Color.black));
		invT.setTitleFont(new Font("Arial",Font.BOLD,15));
		southPanel.setBorder(invT);

		JLabel totalT = new JLabel("Total");
		totalT.setFont(new Font("Arial",Font.PLAIN,15));
		southPanel.add(totalT);
		totalBox = new JTextField(15);
		totalBox.setEditable(false);
		southPanel.add(totalBox);

		JLabel averageT = new JLabel("Average");
		averageT.setFont(new Font("Arial",Font.PLAIN,15));
		southPanel.add(averageT);
		averageBox = new JTextField(15);
		averageBox.setEditable(false);
		southPanel.add(averageBox);

		JLabel cheapT = new JLabel("Cheapest");
		cheapT.setFont(new Font("Arial",Font.PLAIN,15));
		southPanel.add(cheapT);
		cheapBox = new JTextField(25);
		cheapBox.setEditable(false);
		southPanel.add(cheapBox);

		mainPanel.add(southPanel,BorderLayout.SOUTH);//Adds 'southPanel' to the southern part of the window.

		/**
		 * Creating the Panel that will hold the list of the Car Lot.
		 */
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setMaximumSize(new Dimension(400,400));
		eastPanel.setMinimumSize(new Dimension(400,400));
		eastPanel.setBorder(new EmptyBorder(5,5,5,5));

		JLabel lotTitle = new JLabel("Bryce's Car Lot");
		lotTitle.setFont(new Font("Arial",Font.PLAIN,20));
		eastPanel.add(lotTitle,BorderLayout.NORTH);

		JPanel middleEastPanel = new JPanel();
		middleEastPanel.setLayout(new BorderLayout());

		JPanel filterPanel  = new JPanel();
		filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		filterBox = new JComboBox<String>(FILTER_COLORS);
		filterBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dealer.filterCarsByColor(filterBox.getSelectedItem().toString());
				dealerList.anounceChange();
			}

		});

		JLabel filterLbl = new JLabel("Filter Cars ");
		this.setLabelLook(filterLbl);

		filterPanel.add(filterLbl);
		filterPanel.add(filterBox);

		middleEastPanel.add(filterPanel,BorderLayout.NORTH);


		/*
		 * These statements create and add the scrool pane with the car list.
		 */
		displayList = new JList<Car>(dealerList);
		displayList.addListSelectionListener(new CarSelectionListener());
		listDisplay = new JScrollPane(displayList);
		middleEastPanel.add(listDisplay,BorderLayout.CENTER);
		eastPanel.add(middleEastPanel,BorderLayout.CENTER);

		JPanel eastButtonPanel = new JPanel();
		eastButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		eastButtonPanel.setBorder(new LineBorder(Color.black));

		addB = new JButton("Add");
		/*
		 * Adding an actionlistener to the button.
		 */
		addB.addActionListener(new AddListener());
		eastButtonPanel.add(addB);

		removeB = new JButton("Remove");
		/*
		 * Adding an actionlistener to the button.
		 */
		removeB.addActionListener(new RemoveListener());
		eastButtonPanel.add(removeB);
		eastPanel.add(eastButtonPanel,BorderLayout.SOUTH);

		mainPanel.add(eastPanel,BorderLayout.WEST);


		/*
		 * Creating the centerPanel to display the car information.
		 */
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(20,20,20,20));

		JPanel ccPanel = new JPanel();
		ccPanel.setLayout(new BoxLayout(ccPanel,BoxLayout.Y_AXIS));

		JLabel makeL,modelL,yearL,priceL,mpgL,serviceL,conditionL,colorL;
		makeL = new JLabel("Make");
		modelL = new JLabel("Model");
		yearL = new JLabel("Year");
		priceL = new JLabel("Price");
		mpgL = new JLabel("MPG");
		serviceL = new JLabel("Service/Yr $");
		conditionL = new JLabel("Condition");
		colorL = new JLabel("Color");

		this.setLabelLook(makeL);
		this.setLabelLook(modelL);
		this.setLabelLook(yearL);
		this.setLabelLook(priceL);
		this.setLabelLook(mpgL);
		this.setLabelLook(serviceL);
		this.setLabelLook(conditionL);
		this.setLabelLook(colorL);

		/*
		 * Creating the text fields for the panel.
		 */
		makeBox = setTextBoxLook();
		priceBox = setTextBoxLook();
		serviceBox = setTextBoxLook();
		modelBox = setTextBoxLook();


		JPanel b1 = new JPanel();
		b1.setLayout(new FlowLayout(FlowLayout.LEFT));
		b1.add(makeL);
		b1.add(makeBox);
		ccPanel.add(b1);

		JPanel b2 = new JPanel();
		b2.setLayout(new FlowLayout(FlowLayout.LEFT));
		b2.add(modelL);
		b2.add(modelBox);
		ccPanel.add(b2);

		JPanel b3 = new JPanel();
		b3.setLayout(new FlowLayout(FlowLayout.LEFT));
		b3.add(yearL);
		yearBox = new JSpinner();
		yearBox.setMaximumSize(new Dimension(50,20));
		yearBox.setMinimumSize(new Dimension(50,20));
		yearBox.setPreferredSize(new Dimension(50,20));
		yearBox.setValue(1000);
		b3.add(yearBox);
		ccPanel.add(b3);

		JPanel b4 = new JPanel();
		b4.setLayout(new FlowLayout(FlowLayout.LEFT));
		b4.add(priceL);
		b4.add(priceBox);
		ccPanel.add(b4);

		JPanel b5 = new JPanel();
		b5.setLayout(new FlowLayout(FlowLayout.LEFT));
		b5.add(mpgL);
		mpgSlider = new JSlider(0,100,0);
		mpgSlider.setMajorTickSpacing(20);
		mpgSlider.setMinorTickSpacing(5);
		mpgSlider.setPaintTicks(true);
		mpgSlider.setPaintTrack(true);
		mpgSlider.setPaintLabels(true);
		b5.add(mpgSlider);
		ccPanel.add(b5);

		JPanel b6 = new JPanel();
		b6.setLayout(new FlowLayout(FlowLayout.LEFT));
		b6.add(serviceL);
		b6.add(serviceBox);
		ccPanel.add(b6);

		JPanel b7 = new JPanel();
		b7.setLayout(new FlowLayout(FlowLayout.LEFT));
		b7.add(conditionL);
		starsBox = new JLabel(starIconList[0]);
		starsBox.addMouseListener(new StarsListener());
		b7.add(starsBox);
		ccPanel.add(b7);

		JPanel b8 = new JPanel();
		b8.setLayout(new FlowLayout(FlowLayout.LEFT));
		b8.add(colorL);
		colorBox = new JComboBox<String>(COLORS);
		b8.add(colorBox);
		ccPanel.add(b8);
		centerPanel.add(ccPanel,BorderLayout.CENTER);

		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		updateB = new JButton("Update");
		updateB.addActionListener(new UpdateListener());
		centerButtonPanel.add(updateB);
		centerPanel.add(centerButtonPanel,BorderLayout.SOUTH);

		mainPanel.add(centerPanel,BorderLayout.CENTER);

		setInventory();
		centerPanel.setVisible(false);
		displayList.setSelectedIndex(0);
		/*
		 * This method will add more dummy cars
		 */
		//dealer.addDummyCars();
		dealer.filterCarsByColor(filterBox.getSelectedItem().toString());
		this.pack();
	}

	/**
	 * This method will create a list of ImageIcons for the star label.
	 * @return
	 */
	public ImageIcon[] createStarIconList(){
		ImageIcon[] rtnList = new ImageIcon[6];
		for(int i = 0; i < 6; i++){
			ImageIcon icon = null;
			try {
				icon = new ImageIcon(ImageIO.read(getClass().getResource("stars/stars"+i+".png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rtnList[i] = icon;
		}

		return rtnList;
	}

	/**
	 * This will rest the values for the inventory.
	 */
	public void setInventory(){
		if(dealer.getLot().getCars().size()!=0){
			totalBox.setText(dealer.totalValue()+"");
			averageBox.setText(dealer.averageCarPrice()+"");
			cheapBox.setText(dealer.findCheapestCar()+"");
		}
	}

	/**
	 * This method will run a save sequence while updating the displayed list of cars
	 */
	public void save(){
		if(dealer.getCurrentCars().size()>0)
			filterBox.setSelectedItem(dealer.getCurrentCars().get(0).getColor());
		else
			filterBox.setSelectedItem("ALL");
		dealer.filterCarsByColor(filterBox.getSelectedItem().toString());
		dealer.writeToFile();
		dealerList.anounceChange();
	}

	/**
	 * This method will show a input dialog to the use for a new ID. After that the
	 * Dealer object will create a new Car object and put it into the lot.
	 */
	public void onAdd(){
		String id = JOptionPane.showInputDialog("Enter the new car's ID.");
		if(id == null)return;
		if(id.trim().length() == 0)return;
		try{
			dealerList.addCar(id);
			filterBox.setSelectedItem("Black");
			dealer.filterCarsByColor("Black");
			displayList.setSelectedValue(dealer.carById(id.toUpperCase()),true);
			save();
			setInventory();
			repaint();
		}catch(DuplicateCarIdException e){
			JOptionPane.showMessageDialog(this, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}catch(CarNotFoundException e){
			JOptionPane.showMessageDialog(this, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * This method will set the values of the centerPanel to the currently selected car.
	 */
	public void onUpdate(){
		try{
			dealer.updateCar(currentCar.getId(),makeBox.getText(),modelBox.getText(),Double.parseDouble(priceBox.getText()),Double.parseDouble(serviceBox.getText()),mpgSlider.getValue(),(int)yearBox.getValue(), currentCon,colorBox.getSelectedItem().toString());
			save();
			setInventory();
			repaint();
			JOptionPane.showMessageDialog(this, "Car with ID: "+currentCar.getId()+" has been updated.","Car Updated",JOptionPane.PLAIN_MESSAGE);
		}catch(CarNotFoundException e){
			JOptionPane.showMessageDialog(this, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * This method will set the center panel values to the currently selected car.
	 */
	public void onSelection(){
		currentCon = currentCar.getCondition();
		makeBox.setText(currentCar.getMake());
		modelBox.setText(currentCar.getModel());
		yearBox.setValue(currentCar.getYear());
		priceBox.setText(currentCar.getPrice()+"");
		mpgSlider.setValue((int)currentCar.getMpg());
		serviceBox.setText(currentCar.getService()+"");
		for(int i = 0; i < COLORS.length; i++){
			if(COLORS[i].equalsIgnoreCase(currentCar.getColor().toString())){
				colorBox.setSelectedIndex(i);
			}
		}
		starsBox.setIcon(starIconList[currentCar.getCondition()]);
		hidePanel(false);
		repaint();
	}

	/**
	 * This method will set the star label to the new amount if it is correctly inputted.
	 */
	public void onStarClick(){
		String newCondition = JOptionPane.showInputDialog(this,"What is the car's condition? (0-5)");
		if(newCondition.trim().length()==0){
			JOptionPane.showMessageDialog(this, "Invalid condition rating.","Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(newCondition != null){
			int con = Integer.parseInt(newCondition);
			if(con < 0 || con > 5){
				JOptionPane.showMessageDialog(this, "Invalid condition rating.","Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			currentCon = con;
			starsBox.setIcon(starIconList[con]);
			repaint();
		}
	}

	/**
	 * This method will set the look of the inserted JLabel object.
	 * @param l
	 */
	private void setLabelLook(JLabel l){
		l.setFont(new Font("Arial",Font.BOLD,17));
		l.setHorizontalAlignment(SwingConstants.RIGHT);
		l.setMaximumSize(new Dimension(200,20));
		l.setMinimumSize(new Dimension(200,20));
		l.setPreferredSize(new Dimension(200,20));
	}

	/**
	 * Sets a certain look for a JTextField object.
	 * @return
	 */
	private JTextField setTextBoxLook(){
		JTextField rtnBox = new JTextField(15);
		rtnBox.setEditable(true);
		return rtnBox;
	}

	private void hidePanel(boolean visible){
		centerPanel.setVisible(!visible);
	}

	/**
	 * This method will remove the selected car from the list.
	 */
	public void onRemove(){
		try {
			dealerList.removeCar(currentCar.getId());
			centerPanel.setVisible(false);
			save();
			setInventory();
			JOptionPane.showMessageDialog(this, "Car with the ID: "+currentCar.getId()+" has been removed.","Car Removed",JOptionPane.PLAIN_MESSAGE);
		} catch (CarNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * This class will be used to perform the action of adding a car to the list.
	 */
	private class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==addB)
				onAdd();
		}

	}

	/**
	 * This class will be used to perform the action of updating a car in the list.
	 * @author User
	 *
	 */
	private class UpdateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==updateB)
				onUpdate();
		}

	}

	/**
	 * This class will be used to perform the action of removing a car from the list.
	 * @author User
	 *
	 */
	private class RemoveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == removeB)
				onRemove();
		}

	}

	/**
	 * This class will be used to check when the user selects a car on the list.
	 */
	private class CarSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			setCurrentCar();
			onSelection();
		}

		public void setCurrentCar(){
			if(displayList.getSelectedValue()!=null)
				currentCar = displayList.getSelectedValue();
			else
				hidePanel(true);
		}

	}

	/**
	 * This class will listen for when the user clicks on the star label.
	 */
	private class StarsListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			onStarClick();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * This class will allow for the cars to be displayed on the list.
	 */
	private class DealerListModel extends AbstractListModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getElementAt(int i) {
			return dealer.getCurrentCars().get(i);
		}

		public void removeCar(String id) throws CarNotFoundException {
			dealer.removeCar(id);
			dealer.writeToFile();
			displayList.setSelectedIndex(0);
			dealerList.anounceChange();
		}

		@Override
		public int getSize() {
			return dealer.getCurrentCars().size();
		}

		public void addCar(String str) throws DuplicateCarIdException{
			dealer.addNewCar(str);
			this.fireContentsChanged(this, 0, dealer.getCurrentCars().size());
		}

		public void anounceChange(){
			this.fireContentsChanged(this, 0, dealer.getCurrentCars().size());
		}

	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
	}

}
