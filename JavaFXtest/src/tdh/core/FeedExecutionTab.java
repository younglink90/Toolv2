package tdh.core;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import tdh.tools.xml.Environment;
import tdh.tools.xml.Feed;

public class FeedExecutionTab {

	
	public StackPane root;
	
	public FeedExecutionTab(StackPane root) {
		this.root = root;
	}
	
	public Tab getTab() {
		Tab tab = new Tab();
		tab.setText("Feed Execution");
		tab.setClosable(false);
		
		// ---------- START FEED VALUES ------------
		GridPane gridPane = new GridPane();
		gridPane.setId("gridFeedExecution");
		gridPane.setVgap(30);
		gridPane.setHgap(30);
		gridPane.setGridLinesVisible(false);
		
		ComboBox<Feed> comboFeed = new ComboBox<>();
		comboFeed.setId("comboboxFeedExecution_Feed");
		createActionHandlerComboBoxFeed(comboFeed);
		
		ComboBox<Environment> comboEnvironment = new ComboBox<>();
		comboEnvironment.setId("comboboxFeedExecution_Environment");
		
		Label labelCob = new Label("COB=");
		TextField textFieldCob = new TextField();
		textFieldCob.setId("textfieldFeedExecution_COB");
		textFieldCob.copy();
		HBox hboxCob = new HBox();
		hboxCob.setMinSize(0, 50);
		hboxCob.setAlignment(Pos.BOTTOM_CENTER);
		hboxCob.getChildren().addAll(labelCob, textFieldCob);
		
		HBox hboxButtonRun = new HBox();
		Button buttonRun = new Button("Run");
		hboxButtonRun.getChildren().add(buttonRun);
		hboxButtonRun.setAlignment(Pos.CENTER);
		createActionHandlerButton(buttonRun);
		
		gridPane.add(comboFeed, 1, 1);
		gridPane.add(comboEnvironment, 1, 3);
		gridPane.add(hboxCob, 1, 4);
		gridPane.add(hboxButtonRun, 1, 5);
		// ---------- FINISH FEED VALUES ------------
		
		// ---------- START FEED COMMANDS  ------------
		Pane paneBox = new Pane();
		paneBox.setLayoutY(100);
		paneBox.setId("paneBoxFeedExecution");
		
		gridPane.add(paneBox, 2, 0, 1, 7);
		// ---------- FINISH FEED COMMANDS  ------------
		
		tab.setContent(gridPane);
		
		return tab;
	}
	
	private void createActionHandlerComboBoxFeed(ComboBox<Feed> comboFeed) { 
		comboFeed.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//TODO
				
			}
		});
	}
	
	private void createActionHandlerButton(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
