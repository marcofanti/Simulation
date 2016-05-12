package demo;

import java.text.DecimalFormat;

public class DayData {
	 
    private int day;
    private int demand;
    private String shippingMethod;
    private int capacity;
    private int increase;
    private int orderPoint;
    private int quantity;
    private double initialCash;
    private double interest;
    private double sale;
    private double bank;
    private double factoryCost;
    private double transportCost;
    private double totalCost;
    private double insurance;
    private double endCash;
    private int factory;
    private int truck;
    private int mail;
    private int warehouse;
    private int warehousePlus;
    
	public DayData(int day, int demand, String shippingMethod, int capacity, int increase, int orderPoint, int quantity,
			double initialCash, double interest, double sale, double bank, double factoryCost, double transportCost, double totalCost,
			double insurance, double endCash, int factory, int truck, int mail, int warehouse, int warehousePlus) {
		super();
		this.day = day;
		this.demand = demand;
		this.shippingMethod = shippingMethod;
		this.capacity = capacity;
		this.increase = increase;
		this.orderPoint = orderPoint;
		this.quantity = quantity;
		this.initialCash = initialCash;
		this.interest = interest;
		this.sale = sale;
		this.bank = bank;
		this.factoryCost = factoryCost;
		this.transportCost = transportCost;
		this.totalCost = totalCost;
		this.insurance = insurance;
		this.endCash = endCash;
		this.factory = factory;
		this.truck = truck;
		this.mail = mail;
		this.warehouse = warehouse;
		this.warehousePlus = warehousePlus;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getIncrease() {
		return increase;
	}

	public void setIncrease(int increase) {
		this.increase = increase;
	}

	public int getOrderPoint() {
		return orderPoint;
	}

	public void setOrderPoint(int orderPoint) {
		this.orderPoint = orderPoint;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getInitialCash() {
		return initialCash;
	}

	public void setInitialCash(double initialCash) {
		this.initialCash = initialCash;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getSale() {
		return sale;
	}

	public void setSale(double sale) {
		this.sale = sale;
	}

	public double getBank() {
		return bank;
	}

	public void setBank(double bank) {
		this.bank = bank;
	}

	public double getFactoryCost() {
		return factoryCost;
	}

	public void setFactoryCost(double factoryCost) {
		this.factoryCost = factoryCost;
	}

	public double getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(double transportCost) {
		this.transportCost = transportCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getInsurance() {
		return insurance;
	}

	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}

	public double getEndCash() {
		return endCash;
	}

	public void setEndCash(double endCash) {
		this.endCash = endCash;
	}

	public int getFactory() {
		return factory;
	}

	public void setFactory(int factory) {
		this.factory = factory;
	}

	public int getTruck() {
		return truck;
	}

	public void setTruck(int truck) {
		this.truck = truck;
	}

	public int getMail() {
		return mail;
	}

	public void setMail(int mail) {
		this.mail = mail;
	}

	public int getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(int warehouse) {
		this.warehouse = warehouse;
	}

	public int getWarehousePlus() {
		return warehousePlus;
	}

	public void setWarehousePlus(int warehousePlus) {
		this.warehousePlus = warehousePlus;
	}

	public String getHeader() {
			return (" Day  Demand Shipping Capacity Increase Order   Quantity    Initial     Sales      Bank      Factory  Transport   Total     Interest  Insurance    Final    Day  Factory  Truck  Mail  Warehouse  Warehouse  Day" + 
					"\n" +    
					"              Method                    Point                Cash                  Cash      Cost      Cost                    Cash                                                                  Plus      ");
	}
	
	@Override
	public String toString() {
		DecimalFormat decimalFormat = new DecimalFormat("000,000");
		DecimalFormat decimalFormat2 = new DecimalFormat("0,000,000");
		String initialCashAsString = decimalFormat2.format(initialCash);
		String interestAsString = decimalFormat.format(interest);
		String saleAsString = decimalFormat.format(sale);
		String bankAsString = decimalFormat2.format(bank);
		String factoryCostAsString = decimalFormat.format(factoryCost);
		String transportCostAsString = decimalFormat.format(transportCost);
		String totalCostAsString = decimalFormat2.format(totalCost);
		String insuranceAsString = decimalFormat.format(insurance);
		String endCashAsString = decimalFormat2.format(endCash);
		return String.format(
				"% 4d   % 4d   %s    % 4d       % 2d    % 4d     % 4d      %s   %s   %s   %s   %s   %s   %s   %s   %s  % 4d   % 4d    % 4d  % 4d     % 4d     % 4d    % 4d",
				day, demand, shippingMethod, capacity, increase, orderPoint, quantity, initialCashAsString, saleAsString,
				bankAsString, factoryCostAsString, transportCostAsString, totalCostAsString, interestAsString, insuranceAsString, endCashAsString, day, factory, truck, mail, warehouse,
				warehousePlus, day);
	}
}
