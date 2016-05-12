package demo;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class TableViewSample extends Application {
 
    private TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data =
        FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
        );
   
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);
 
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
 
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
 
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));
 
        TableColumn aCol = new TableColumn("A");
        aCol.setMinWidth(200);
        aCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("a"));
 
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, aCol);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    public static class Person {
 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty a = new SimpleStringProperty("a");
 
        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }
 
        public String getFirstName() {
            return firstName.get();
        }
 
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
 
        public String getLastName() {
            return lastName.get();
        }
 
        public void setLastName(String fName) {
            lastName.set(fName);
        }
 
        public String getEmail() {
            return email.get();
        }
 
        public void setEmail(String fName) {
            email.set(fName);
        }
    }
    
    public static class RowData {
   	 
        private final SimpleStringProperty day;
        private final SimpleStringProperty demand;
        private final SimpleStringProperty shippingMethod;
        private final SimpleStringProperty capacity;
        private final SimpleStringProperty increase;
        private final SimpleStringProperty orderPoint;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty initialCash;
        private final SimpleStringProperty interest;
        private final SimpleStringProperty sale;
        private final SimpleStringProperty bank;
        private final SimpleStringProperty costFactory;
        private final SimpleStringProperty costTransport;
        private final SimpleStringProperty totalCost;
        private final SimpleStringProperty insurance;
        private final SimpleStringProperty endCash;
        private final SimpleStringProperty factory;
        private final SimpleStringProperty truck;
        private final SimpleStringProperty mail;
        private final SimpleStringProperty warehouse;
        private final SimpleStringProperty warehouseMinus;
        private final SimpleStringProperty warehousePlus;
        
        public RowData(String day, String demand, String shippingMethod,
				String capacity, String increase, String orderPoint,
				String quantity, String initialCash, String interest,
				String sale, String bank, String costFactory,
				String costTransport, String totalCost, String insurance,
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
			this.costFactory = new SimpleStringProperty(costFactory);
			this.costTransport = new SimpleStringProperty(costTransport);
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

		public String getCostFactory() {
			return costFactory.get();
		}

		public String getCostTransport() {
			return costTransport.get();
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