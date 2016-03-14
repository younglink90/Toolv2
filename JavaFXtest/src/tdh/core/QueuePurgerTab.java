package tdh.core;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import tdh.tools.queuepurger.QueuePurgerTool;
import tdh.tools.xml.Address;
import tdh.tools.xml.Feed;
import tdh.tools.xml.LDAP;
import tdh.tools.xml.Queue;

@SuppressWarnings({ "unchecked"})
public class QueuePurgerTab {

	public StackPane root;
	public static final Queue ALL_QUEUES = new Queue("All queues");
	private static final Queue QUEUE_NOT_FOUND = new Queue("Queue not found");
	
	public QueuePurgerTab(StackPane root) {
		this.root = root;
	}
		
	public Tab getTab() {
		Tab tab = new Tab();
		tab.setText("Queue Purger");

		GridPane gridPane = new GridPane();
		gridPane.setId("gridQueuePurge");
		gridPane.setVgap(30);
		gridPane.setHgap(10);
		
		// ------------- PANEL WITH FEEDS START --------------------
		Pane paneQueues = new Pane();
		paneQueues.getStyleClass().add("panelQueuePurger");
		paneQueues.setMinWidth(500);

		Label labelQueues = new Label();
		labelQueues.getStyleClass().add("labelQueuePurger");
		labelQueues.setText("  Queue to purge  ");
		labelQueues.setLayoutY(-10);
		labelQueues.setLayoutX(10);

		ComboBox<Feed> comboFeed = new ComboBox<>();
		comboFeed.setId("comboboxQueuePurger_Feed");
		comboFeed.setLayoutY(15);
		comboFeed.setLayoutX(10);
		createActionHandlerQueuePurgerFeed(comboFeed);

		ComboBox<Queue> comboQueues = new ComboBox<>();
		comboQueues.setId("comboboxQueuePurger_Queue");
		comboQueues.setLayoutY(15);
		comboQueues.setLayoutX(280);
		
		paneQueues.getChildren().addAll(labelQueues, comboFeed, comboQueues);
		// ------------- PANEL WITH FEEDS ENDS --------------------
		
		// ------------ PANEL WITH ADDRESS START ------------------
		Pane paneAddress = new Pane();
		paneAddress.getStyleClass().add("panelQueuePurger");
		paneAddress.setMinWidth(500);
		
		Label labelLDAP = new Label();
		labelLDAP.getStyleClass().add("labelQueuePurger");
		labelLDAP.setText("  Address  ");
		labelLDAP.setLayoutY(-10);
		labelLDAP.setLayoutX(10);

		ComboBox<LDAP> comboLDAP = new ComboBox<>();
		comboLDAP.setId("comboboxQueuePurger_LDAP");
		comboLDAP.setLayoutY(15);
		comboLDAP.setLayoutX(10);
		createActionHandlerQueuePurgerLDAP(comboLDAP);

		ComboBox<Address> comboAddress = new ComboBox<>();
		comboAddress.setId("comboboxQueuePurger_Address");
		comboAddress.setLayoutY(15);
		comboAddress.setLayoutX(280);
		
		paneAddress.getChildren().addAll(labelLDAP, comboLDAP, comboAddress);
		// ------------ PANEL WITH ADDRESS START ------------------

		Button btn = new Button();
		btn.setText("Purge Queue");
		btn.setId("buttonPurgeQueue");
		createActionHandlerQueuePurgerButtonPurge(btn);
		
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(btn);
		
		gridPane.add(paneQueues, 0, 1);
		gridPane.add(paneAddress, 0, 3);
		gridPane.add(hbox, 0, 5);
		gridPane.setGridLinesVisible(false);

		tab.setContent(gridPane);
		tab.setClosable(false);
		
		return tab;
	}
	
	public void createActionHandlerQueuePurgerFeed(ComboBox<Feed> cbQueuePurger_Feed) {
		cbQueuePurger_Feed.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ComboBox<Queue> cbQueuePurger_Queue = (ComboBox<Queue>) root.lookup("#comboboxQueuePurger_Queue");
				cbQueuePurger_Queue.getItems().clear();

				cbQueuePurger_Queue.getItems().addAll(cbQueuePurger_Feed.getValue().getQueueList());
				
				if(!cbQueuePurger_Queue.getItems().contains(QUEUE_NOT_FOUND)) {
					cbQueuePurger_Queue.getItems().add(ALL_QUEUES);
				}

				cbQueuePurger_Queue.setValue(cbQueuePurger_Queue.getItems().get(0));
			}
		});
	}
	
	public void createActionHandlerQueuePurgerLDAP(ComboBox<LDAP> cbQueuePurger_LDAP) {
		cbQueuePurger_LDAP.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ComboBox<Address> cbQueuePurger_Address = (ComboBox<Address>) root.lookup("#comboboxQueuePurger_Address");
				cbQueuePurger_Address.getItems().clear();
				
				cbQueuePurger_Address.getItems().addAll(cbQueuePurger_LDAP.getValue().getAddressList());
				cbQueuePurger_Address.setValue(cbQueuePurger_Address.getItems().get(0));
			}
		});
	}
	
	public void createActionHandlerQueuePurgerButtonPurge(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ComboBox<Feed> cbQueuePurger_Feed = (ComboBox<Feed>) root.lookup("#comboboxQueuePurger_Feed");
				ComboBox<Queue> cbQueuePurger_Queue = (ComboBox<Queue>) root.lookup("#comboboxQueuePurger_Queue");
				ComboBox<LDAP> cbQueuePurger_LDAP = (ComboBox<LDAP>) root.lookup("#comboboxQueuePurger_LDAP");
				ComboBox<Address> cbQueuePurger_Address = (ComboBox<Address>) root.lookup("#comboboxQueuePurger_Address");
				
				if(!cbQueuePurger_Queue.getValue().equals(QUEUE_NOT_FOUND)){ 
					String result = QueuePurgerTool.run(	
										cbQueuePurger_LDAP.getValue(),
										cbQueuePurger_Feed.getValue(),
										cbQueuePurger_Queue.getValue(), 
										cbQueuePurger_Address.getValue()
									);
					JOptionPane.showMessageDialog(null, result);
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning!");
					alert.setHeaderText("Invalid queue selected");
					alert.setContentText("Try again with a valid queue");
					alert.showAndWait();
				}
			}
		});		
	}
	


}
