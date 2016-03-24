package tdh.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tdh.tools.xml.Address;
import tdh.tools.xml.Environment;
import tdh.tools.xml.Feed;
import tdh.tools.xml.LDAP;
import tdh.tools.xml.Queue;
import tdh.tools.xml.jaxb.XMLReader;

@SuppressWarnings("unchecked")
public class Main extends Application {

	public StackPane root;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		TabPane tabPane = new TabPane();
		
		root = new StackPane();
		root.getChildren().add(tabPane);
		root.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		
		tabPane.getTabs().add(new QueuePurgerTab(root).getTab());
		tabPane.getTabs().add(new FeedExecutionTab(root).getTab());
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("TDHTool");
		primaryStage.setScene(new Scene(root, 600, 350));
		
		setQueuePurgerValuesIntoNodes();
		setFeedExecutionValuesIntoNodes();
		primaryStage.show();
	}
	
	private void setQueuePurgerValuesIntoNodes() {
		ComboBox<Feed> cbQueuePurger_Feed = (ComboBox<Feed>) root.lookup("#comboboxQueuePurger_Feed");
		cbQueuePurger_Feed.getItems().addAll(XMLReader.getTDHData().getFeedList());
		cbQueuePurger_Feed.setValue(cbQueuePurger_Feed.getItems().get(0));

		ComboBox<Queue> cbQueuePurger_Queue = (ComboBox<Queue>) root.lookup("#comboboxQueuePurger_Queue");
		cbQueuePurger_Queue.getItems().addAll(cbQueuePurger_Feed.getValue().getQueueList());
		cbQueuePurger_Queue.setValue(cbQueuePurger_Queue.getItems().get(0));
		
		ComboBox<LDAP> cbQueuePurger_LDAP = (ComboBox<LDAP>) root.lookup("#comboboxQueuePurger_LDAP");
		cbQueuePurger_LDAP.getItems().addAll(XMLReader.getTDHData().getUtilities().getLdapList());
		cbQueuePurger_LDAP.setValue(cbQueuePurger_LDAP.getItems().get(0));
		
		ComboBox<Address> cbQueuePurger_Address = (ComboBox<Address>) root.lookup("#comboboxQueuePurger_Address");
		cbQueuePurger_Address.getItems().addAll(cbQueuePurger_LDAP.getValue().getAddressList());
		cbQueuePurger_Address.setValue(cbQueuePurger_Address.getItems().get(0));		
	}
	
	private void setFeedExecutionValuesIntoNodes() {
		ComboBox<Feed> cbFeedExecution_Feed = (ComboBox<Feed>) root.lookup("#comboboxFeedExecution_Feed");
		cbFeedExecution_Feed.getItems().addAll(XMLReader.getTDHData().getFeedList());
		cbFeedExecution_Feed.setValue(cbFeedExecution_Feed.getItems().get(0));

		ComboBox<Environment> cbFeedExecution_Environment = (ComboBox<Environment>) root.lookup("#comboboxFeedExecution_Environment");
		cbFeedExecution_Environment.getItems().addAll(XMLReader.getTDHData().getUtilities().getEnvironmentList());
		cbFeedExecution_Environment.setValue(cbFeedExecution_Environment.getItems().get(0));		
	}
}
