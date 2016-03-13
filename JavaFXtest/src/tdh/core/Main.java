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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tdh.tools.xml.Feed;
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

		primaryStage.show();
	}

	public Tab createTabQueuePurger() {
		Tab tab = new Tab();
		tab.setText("Queue Purger");

		GridPane gridPane = new GridPane();
		gridPane.setId("gridQueuePurge");
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		Pane paneQueues = new Pane();
		paneQueues.getStyleClass().add("panelQueuePurger");
		paneQueues.setMinWidth(500);

		Label myLabel = new Label();
		myLabel.setId("labelQueuePurger_Queue");
		myLabel.setText("  Queue to purge  ");
		myLabel.setLayoutY(-10);
		myLabel.setLayoutX(10);

		ComboBox<Feed> comboFeed = new ComboBox<>();
		comboFeed.setId("comboboxQueuePurger_Feed");
		comboFeed.setLayoutY(15);
		comboFeed.setLayoutX(10);
		createActionHandlerQueuePurgerFeed(comboFeed);

		ComboBox<Queue> comboQueues = new ComboBox<>();
		comboQueues.setId("comboboxQueuePurger_Queue");
		comboQueues.setLayoutY(15);
		comboQueues.setLayoutX(280);

		paneQueues.getChildren().addAll(myLabel, comboFeed, comboQueues);

		Button btn = new Button();
		btn.setText("Purge Queue");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TO-DO dfs
			}
		});
		btn.setId("buttonPurgeQueue");
		btn.setAlignment(Pos.BOTTOM_CENTER);

		gridPane.add(paneQueues, 0, 3);
		gridPane.add(btn, 0, 4);

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
	
	public void setQueuePurgerValuesIntoNodes() {
		ComboBox<Feed> cbQueuePurger_Feed = (ComboBox<Feed>) root.lookup("#comboboxQueuePurger_Feed");
		cbQueuePurger_Feed.getItems().addAll(XMLReader.getTDHData().getFeedList());
		cbQueuePurger_Feed.setValue(cbQueuePurger_Feed.getItems().get(0));

		ComboBox<Queue> cbQueuePurger_Queue = (ComboBox<Queue>) root.lookup("#comboboxQueuePurger_Queue");
		cbQueuePurger_Queue.getItems().addAll(cbQueuePurger_Feed.getValue().getQueueList());
		cbQueuePurger_Queue.setValue(cbQueuePurger_Queue.getItems().get(0));
	}
}
