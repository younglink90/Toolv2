package tdh.core;

import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class FeedExecutionTab {

	
	public StackPane root;
	
	public FeedExecutionTab(StackPane root) {
		this.root = root;
	}
	
	public Tab getTab() {
		Tab tab = new Tab();
		tab.setText("Feed Execution");
		tab.setClosable(false);

		GridPane gridPane = new GridPane();
		gridPane.setId("gridFeedExecution");
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		return tab;
	}
}
