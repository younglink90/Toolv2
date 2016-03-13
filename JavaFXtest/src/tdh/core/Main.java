package tdh.core;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tdh.tools.xml.Address;
import tdh.tools.xml.Feed;
import tdh.tools.xml.LDAP;
import tdh.tools.xml.Queue;
import tdh.tools.xml.jaxb.XMLReader;

@SuppressWarnings({ "unchecked", "unused" })
public class Main extends Application {

	public StackPane root;
	private static final Queue ALL_QUEUES = new Queue("All queues");
	private static final Queue QUEUE_NOT_FOUND = new Queue("Queue not found");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		TabPane tabPane = new TabPane();
		tabPane.getTabs().add(createTabQueuePurger());

		root = new StackPane();
		root.getChildren().add(tabPane);
		root.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(new Scene(root, 600, 350));
		
		setQueuePurgerValuesIntoNodes();
		
		root.getChildren()
		.filtered(p -> p instanceof ComboBox)
		.forEach(p -> System.out.println(p));

		primaryStage.show();
	}

	public Tab createTabQueuePurger() {
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
		gridPane.setGridLinesVisible(true);

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
	
	public void createActionHandlerQueuePurgerButtonPurge(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Purged!");
			}
		});		
	}
	
	public void setQueuePurgerValuesIntoNodes() {
		ComboBox<Feed> cbQueuePurger_Feed = (ComboBox<Feed>) root.lookup("#comboboxQueuePurger_Feed");
		cbQueuePurger_Feed.getItems().addAll(XMLReader.getTDHData().getFeedList());
		cbQueuePurger_Feed.setValue(cbQueuePurger_Feed.getItems().get(0));

		ComboBox<Queue> cbQueuePurger_Queue = (ComboBox<Queue>) root.lookup("#comboboxQueuePurger_Queue");
		cbQueuePurger_Queue.getItems().addAll(cbQueuePurger_Feed.getValue().getQueueList());
		cbQueuePurger_Queue.setValue(cbQueuePurger_Queue.getItems().get(0));
		
		ComboBox<LDAP> cbQueuePurger_LDAP = (ComboBox<LDAP>) root.lookup("#comboboxQueuePurger_LDAP");
		cbQueuePurger_LDAP.getItems().addAll(XMLReader.getTDHData().getUtilities().getLdapList());
		cbQueuePurger_LDAP.setValue(cbQueuePurger_LDAP.getItems().get(0));
	}
}
