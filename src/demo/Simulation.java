package demo;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
 
public class Simulation extends Application {
	static Logger log = Logger.getLogger(Simulation.class.getName());
	private static SimulationData simulationData = new SimulationData();
	private RowData pointer = new RowData("0", "0", "Truck", "0", "0", "300", "150", "$500,000.00", "$0.00", "$0.00", 
    		"$500,000.00", "$0.00", "$0.00", "$500,000.00", "$0.00", "$500,000.00", "0", "0", "0", "0", "0", "0");
    private TableView<RowData> table = new TableView<RowData>();
    private final ObservableList<RowData> data =
            FXCollections.observableArrayList(pointer);
    final HBox hb = new HBox();
    private static double cashInterest = 0.0;
	DecimalFormat decimalFormat = new DecimalFormat("000,000.00");
	DecimalFormat decimalFormat2 = new DecimalFormat("0,000,000.00");
 
    public static void main(String[] args) {
        launch(args);
        //System.exit(0);
    }
    
    public void initGame() {
    	DayData dayZero = new DayData(0, 0, "Truck", 20, 0, 300, 150, 500000.0, 0, 0, 
    			500000.0, 0.0, 0.0, 0.0, 0, 500000.0, 0, 0, 0, 0, 0);
    	simulationData.setDayDataForADay(0, dayZero);
    	System.out.println(dayZero.getHeader());
    	log.info(dayZero.getHeader());
    	System.out.println(dayZero);
    	log.info(dayZero);
    	// Cash Interest
    	cashInterest = simulationData.getCashInterest() / 382.0;
    }
    public void runADay(int day, int expandBy, int threshold, int batchSize, String transportationMode) {
    	DayData prevDay =  simulationData.getDayDataForADay(day - 1);
    	double endCash = prevDay.getEndCash();
    	double interest = 0.0;
    	
    	if (day > 730) {
    		batchSize = 200;
    		threshold = 400;
    		//simulationData.setCapacity(30);
    	}
    	double newEndCash = endCash + interest;
    	int demand = simulationData.getDemandDataForADay(day);
    	int truck = prevDay.getTruck();
    	int mail = prevDay.getMail();
    	
    	int deliveriesToday = simulationData.anyDeliveriesToday(day); 	
    	int warehouse = prevDay.getWarehouse();
    	log.debug(warehouse);;
    	if (deliveriesToday > 0) {
    		truck -= deliveriesToday;
    		warehouse += deliveriesToday;
    		log.debug(deliveriesToday + " deliveries today");
    	} else {
    		log.debug("No deliveries today");
    	}
    	log.debug("Initial stock " + warehouse);
    	
    	double salesToday = 0.0;
    	if (warehouse >= demand) {
    		salesToday = demand * (simulationData.getDrumPrice() - simulationData.getFulfillingPrice());
    		log.debug("Sales = " + demand + " * (" + simulationData.getDrumPrice() + " - " +  simulationData.getFulfillingPrice() + " = " + salesToday);
    		warehouse = warehouse - demand;
    		log.debug("Warehouse = " + warehouse);
    	} else {
    		log.debug("No sales today");
    	}
    	
    	double bankCash = endCash + salesToday;
    	log.debug("Bank cash = " + bankCash);
    	int capacity = prevDay.getCapacity();
    	
    	int batchGoal = simulationData.getBatchGoal();
    	
    	int warehousePlus = prevDay.getWarehousePlus();
    	
    	double factoryCost = 0.0;
    	double transportCost = 0.0;
    	int factory = prevDay.getFactory();
    	
    	boolean finishedToday = false;
    	if (batchGoal > 0){
    		factory += capacity;
    		if (factory >= batchGoal) {
    			int originalBatchGoal = batchGoal;
    			finishedToday = true;
    			simulationData.addDelivery(originalBatchGoal, day + simulationData.getDaysForTruck(), "Truck");
    			batchGoal = 0;
    			simulationData.setBatchGoal(batchGoal);
    			truck += originalBatchGoal;
    			factory -= originalBatchGoal;
    			warehousePlus += originalBatchGoal;
    		}	
    	}
    	if (batchGoal == 0) {
    		if (warehousePlus < threshold) {
    			double calculatedFactoryCost = simulationData.getBatchFixedCost() + (batchSize * simulationData.getBatchVariableCost());
    			int numberOfTrucks = (batchSize / simulationData.getTruckMax()) + 1;
    			double calculatedTransportCost = numberOfTrucks * simulationData.getTruckCost();
    			if (bankCash > calculatedFactoryCost + calculatedTransportCost) {
    				factoryCost = calculatedFactoryCost;
    				transportCost = calculatedTransportCost;
    				simulationData.setBatchGoal(batchSize);
    				if (!finishedToday) {
    					factory += capacity;
    				}
    			}
    			
    		}
    	} 
    	
    	double totalCash = newEndCash + salesToday - factoryCost - transportCost;
    	double insurance = 0.0;
    	
		// Calculate interest
    	interest = totalCash * cashInterest;
		log.debug("Interest = " + prevDay.getEndCash() + " * " + cashInterest + " = " + interest);
		
    	warehousePlus = warehouse + truck + mail;
    	
		insurance = simulationData.getDrumInsurance() * warehousePlus /365;
    	
		newEndCash = totalCash + interest - insurance;

    	DayData newDay = new DayData(day, demand, transportationMode, capacity, expandBy, threshold, batchSize,
    			endCash, interest, salesToday, bankCash, factoryCost, transportCost, totalCash, insurance, newEndCash, factory, truck, mail, warehouse, warehousePlus);
    	
    	String initialCashAsString = decimalFormat2.format(endCash);
    	initialCashAsString = cleanString(initialCashAsString);
    	String interestAsString = decimalFormat.format(interest);
    	interestAsString = cleanString(interestAsString);
    	String saleAsString = decimalFormat.format(salesToday);
    	saleAsString = cleanString(saleAsString);
    	String bankAsString = decimalFormat2.format(bankCash);
    	bankAsString = cleanString(bankAsString);
    	String factoryCostAsString = decimalFormat.format(factoryCost);
    	factoryCostAsString = cleanString(factoryCostAsString);
    	String transportCostAsString = decimalFormat.format(transportCost);
    	transportCostAsString = cleanString(transportCostAsString);
    	String totalCostAsString = decimalFormat2.format(totalCash);
    	totalCostAsString = cleanString(totalCostAsString);
    	String insuranceAsString = decimalFormat.format(insurance);
    	insuranceAsString = cleanString(insuranceAsString);
    	String endCashAsString = decimalFormat2.format(endCash);
    	endCashAsString = cleanString(endCashAsString);

    	RowData r = new RowData(day + "", demand + "", "Truck", capacity + "", expandBy + "", threshold + "", batchSize + "", initialCashAsString, interestAsString, saleAsString, 
    			bankAsString, factoryCostAsString, transportCostAsString, totalCostAsString, insuranceAsString, endCashAsString, factory + "", truck + "", mail + "", warehouse + "", warehousePlus + "", "");
    	 data.add(r);
    	System.out.println(newDay);
    	log.info(newDay);
    	simulationData.setDayDataForADay(day, newDay);
    }
    
    public String cleanString(String original) {
    	if (original.startsWith("000,00")) {
    		original = original.substring(6);
    	} else if (original.startsWith("000,0")) {
    		original = original.substring(5);
    	} else if (original.startsWith("000,")) {
    		original = original.substring(4);
    	} else if (original.startsWith("000")) {
    		original = original.substring(3);
    	} else if (original.startsWith("00")) {
    		original = original.substring(2);
    	} else if (original.startsWith("0,0")) {
    		original = original.substring(3);
    	} else if (original.startsWith("0,")) {
    		original = original.substring(2);
    	} else if (original.startsWith("0")) {
    		original = original.substring(1);
    	}
    	
    	return "$" + original;
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void start(Stage stage) {
    	initGame();
        for (int day = 1; day <= 1460; day++) {
	        runADay(day, 0, 300, 150, "Truck");
        }
        Scene scene = new Scene(new Group());
        stage.setTitle("Simulation Program");
        stage.setWidth(1800);
        stage.setHeight(550);
 
        final Label label = new Label("Simulation - Day 0");
        label.setFont(new Font("Arial", 16));
 
        table.setEditable(true);
 
        TableColumn dayCol = new TableColumn("Day");
        dayCol.setMinWidth(10);
        dayCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("day"));
 
        TableColumn demandCol = new TableColumn("Demand");
        demandCol.setMinWidth(10);
        demandCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("demand"));
 
        TableColumn shippingMethodCol = new TableColumn("Shipping\n Method");
        shippingMethodCol.setMinWidth(10);
        shippingMethodCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("shippingMethod"));
 
        TableColumn capacityCol = new TableColumn("Capacity");
        capacityCol.setMinWidth(10);
        capacityCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("capacity"));
 
        TableColumn increaseCol = new TableColumn("Increase");
        increaseCol.setMinWidth(10);
        increaseCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("increase"));
 
        TableColumn orderPointCol = new TableColumn("Order\n Point");
        orderPointCol.setMinWidth(10);
        orderPointCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("orderPoint"));
 
        TableColumn quantityCol = new TableColumn("Quantity");
        quantityCol.setMinWidth(10);
        quantityCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("quantity"));
 
        TableColumn initialCashCol = new TableColumn("Initial\nCash");
        initialCashCol.setMinWidth(120);
        initialCashCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("initialCash"));
 
        TableColumn salesCashCol = new TableColumn("Sale");
        salesCashCol.setMinWidth(40);
        salesCashCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("sale"));
 
        TableColumn bankCashCol = new TableColumn("Bank");
        bankCashCol.setMinWidth(40);
        bankCashCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("bank"));
 
        TableColumn factoryCostCol = new TableColumn("Factory\n  Cost");
        factoryCostCol.setMinWidth(40);
        factoryCostCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("factoryCost"));
 
        TableColumn transportCostCol = new TableColumn("Transport\n  Cost");
        transportCostCol.setMinWidth(40);
        transportCostCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("transportCost"));
 
        TableColumn totalCostCol = new TableColumn("Total\nCost");
        totalCostCol.setMinWidth(120);
        totalCostCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("totalCost"));
 
        TableColumn interestCashCol = new TableColumn("Interest");
        interestCashCol.setMinWidth(10);
        interestCashCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("interest"));
 
        TableColumn insuranceCol = new TableColumn("Insurance");
        insuranceCol.setMinWidth(40);
        insuranceCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("insurance"));
 
        TableColumn endCashCol = new TableColumn("End Cash");
        endCashCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("endCash"));
 
        TableColumn factoryCol = new TableColumn("Factory");
        factoryCol.setMinWidth(40);
        factoryCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("factory"));
 
        TableColumn truckCol = new TableColumn("Truck");
        truckCol.setMinWidth(40);
        truckCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("truck"));
 
        TableColumn mailCol = new TableColumn("Mail");
        mailCol.setMinWidth(40);
        mailCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("mail"));
 
        TableColumn warehouseCol = new TableColumn("Wharehouse");
        warehouseCol.setMinWidth(40);
        warehouseCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("warehouse"));
 
        TableColumn warehouseMinusCol = new TableColumn("Warehouse\n  Only");
        warehouseMinusCol.setMinWidth(40);
        warehouseMinusCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("warehouseMinus"));
 
        TableColumn warehousePlusCol = new TableColumn("Warehouse\n+ in Route");
        warehousePlusCol.setMinWidth(40);
        warehousePlusCol.setCellValueFactory(new PropertyValueFactory<RowData, String>("warehousePlus"));
 
        table.setItems(data);
        table.getColumns().addAll(dayCol, demandCol, shippingMethodCol, capacityCol, increaseCol, orderPointCol, 
        		quantityCol, initialCashCol, salesCashCol, bankCashCol, factoryCostCol, 
        		transportCostCol, totalCostCol, interestCashCol, insuranceCol, endCashCol, factoryCol, truckCol, mailCol, 
        		warehouseCol, warehouseMinusCol, warehousePlusCol);
 /*
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(dayCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(demandCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(shippingMethodCol.getPrefWidth());
        addEmail.setPromptText("Email");
*/ 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	 RowData r = new RowData("1", "0", "Truck", "0", "0", "300", "150", "$500,000.00", "0", "0", 
                 		"0", "$500,000.00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
            	 data.add(r);
                pointer.day = new SimpleStringProperty("CHANGED");
            }
        });

        hb.getChildren().addAll(addButton);
        hb.setSpacing(3); 
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    
    public static class RowData {
 
        private SimpleStringProperty day;
        private SimpleStringProperty demand;
        private SimpleStringProperty shippingMethod;
        private SimpleStringProperty capacity;
        private SimpleStringProperty increase;
        private SimpleStringProperty orderPoint;
        private SimpleStringProperty quantity;
        private SimpleStringProperty initialCash;
        private SimpleStringProperty interest;
        private SimpleStringProperty sale;
        private SimpleStringProperty bank;
        private SimpleStringProperty factoryCost;
        private SimpleStringProperty transportCost;
        private SimpleStringProperty totalCost;
        private SimpleStringProperty insurance;
        private SimpleStringProperty endCash;
        private SimpleStringProperty factory;
        private SimpleStringProperty truck;
        private SimpleStringProperty mail;
        private SimpleStringProperty warehouse;
        private SimpleStringProperty warehouseMinus;
        private SimpleStringProperty warehousePlus;
        
        public RowData(String day, String demand, String shippingMethod,
				String capacity, String increase, String orderPoint,
				String quantity, String initialCash, String interest,
				String sale, String bank, String factoryCost,
				String transportCost, String totalCost, String insurance,
				String endCash, String factory, String truck,
				String mail, String warehouse, String warehouseMinus,
				String warehousePlus) {
			super();
			this.day = new SimpleStringProperty(day);
			this.demand = new SimpleStringProperty(demand);
			this.shippingMethod = new SimpleStringProperty(shippingMethod);
			this.capacity = new SimpleStringProperty(capacity);
			this.increase = new SimpleStringProperty(increase);
			this.orderPoint = new SimpleStringProperty(orderPoint);
			this.quantity = new SimpleStringProperty(quantity);
			this.initialCash = new SimpleStringProperty(initialCash);
			this.interest = new SimpleStringProperty(interest);
			this.sale = new SimpleStringProperty(sale);
			this.bank = new SimpleStringProperty(bank);
			this.factoryCost = new SimpleStringProperty(factoryCost);
			this.transportCost = new SimpleStringProperty(transportCost);
			this.totalCost = new SimpleStringProperty(totalCost);
			this.insurance = new SimpleStringProperty(insurance);
			this.endCash = new SimpleStringProperty(endCash);
			this.factory = new SimpleStringProperty(factory);
			this.truck = new SimpleStringProperty(truck);
			this.mail = new SimpleStringProperty(mail);
			this.warehouse = new SimpleStringProperty(warehouse);
			this.warehouseMinus = new SimpleStringProperty(warehouseMinus);
			this.warehousePlus = new SimpleStringProperty(warehousePlus);
		}

		public String getDay() {
			return day.get();
		}

		public String getDemand() {
			return demand.get();
		}

		public String getShippingMethod() {
			return shippingMethod.get();
		}

		public String getCapacity() {
			return capacity.get();
		}

		public String getIncrease() {
			return increase.get();
		}

		public String getOrderPoint() {
			return orderPoint.get();
		}

		public String getQuantity() {
			return quantity.get();
		}

		public String getInitialCash() {
			return initialCash.get();
		}

		public String getInterest() {
			return interest.get();
		}

		public String getSale() {
			return sale.get();
		}

		public String getBank() {
			return bank.get();
		}

		public String getFactoryCost() {
			return factoryCost.get();
		}

		public String getTransportCost() {
			return transportCost.get();
		}

		public String getTotalCost() {
			return totalCost.get();
		}

		public String getInsurance() {
			return insurance.get();
		}

		public String getEndCash() {
			return endCash.get();
		}

		public String getFactory() {
			return factory.get();
		}

		public String getTruck() {
			return truck.get();
		}

		public String getMail() {
			return mail.get();
		}

		public String getWarehouse() {
			return warehouse.get();
		}

		public String getWarehouseMinus() {
			return warehouseMinus.get();
		}

		public String getWarehousePlus() {
			return warehousePlus.get();
		}        
    }
} 