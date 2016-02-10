package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.ObservableList;
import SongLibView.Song;
import SongLibView.SongViewController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SongLib extends Application {
	
	private static Stage primaryStage;
	private AnchorPane mainLayout;
	private static AnchorPane addNewSong, editSong, deleteSong;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		SongLib.primaryStage = primaryStage;
		SongLib.primaryStage.setTitle("Song Library");
		
		System.out.println("calling start");
		//ArrayList<Song> songList = new ArrayList<Song>();
		
		//File f = new File("output.txt"); //filling an arrayList for testing
		//Song.input(songList, f);
		
		//Song.printList(songList);
		
		//addInAbcOrder(songList, s);
		
		//This part should populate the listView (Title) field 
		/*ListView<Song> listView = new ListView<>();
		ObservableList<Song> obsList = FXCollections.observableArrayList(songList);
		System.out.println("printing obs list");
		Song.printObsList(obsList);
		listView.setItems(obsList);
		*/
		showMainView();
		
		System.out.println("end of start");
	}
	
	public void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/SongLibView/SongLibMainScene.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		try {
			String title = "Ode to Joy";
			String artist = "Beethoven";
						
//			obsList = FXCollections.observableArrayList();
//			obsList.add(s);
//			
//			listView = new ListView(obsList);
//			
//			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//				public void changed(ObservableValue ov, Object t, Object t1) {
//					title.setText((String) t1);
//				}
//			});
//			
////			listView.getSelectionModel().select(0);
////			
//			System.out.println("before set items in list view");
//			listView.setItems(obsList);
		} catch (Exception e) {
			System.out.println("exception in ok button event");
			e.printStackTrace(System.out);
		}
	}
	
	public static void showAddScene() throws IOException {
		// add button
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SongLib.class.getResource("/SongLibView/PopUpWindow.fxml"));
		addNewSong = loader.load();
		Stage addWindow = new Stage();
		addWindow.setTitle("Add New Song");
		addWindow.initModality(Modality.WINDOW_MODAL);
		addWindow.initOwner(primaryStage);
		Scene scene = new Scene (addNewSong);
		addWindow.setScene(scene);
		addWindow.showAndWait();
		
		// get text field for title
		//String songName = titlePopup;
	}
	
	public static void showEditScene() throws IOException {
		// edit button
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SongLib.class.getResource("/SongLibView/PopUpWindow.fxml"));
		editSong = loader.load();
		Stage addWindow = new Stage();
		addWindow.setTitle("Edit Song");
		addWindow.initModality(Modality.WINDOW_MODAL);
		addWindow.initOwner(primaryStage);
		Scene scene = new Scene (editSong);
		addWindow.setScene(scene);
		addWindow.showAndWait();
	}
	
	public static void showDeleteScene() throws IOException {
		// delete button
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SongLib.class.getResource("/SongLibView/DeletePopup.fxml"));
		deleteSong = loader.load();		// title of pop up
		Stage addWindow = new Stage();
		addWindow.setTitle("Delete Song");
		addWindow.initModality(Modality.WINDOW_MODAL);
		addWindow.initOwner(primaryStage);
		Scene scene = new Scene (deleteSong);
		addWindow.setScene(scene);
		addWindow.showAndWait();
		//System.out.println("end of delete scene, activates when hit x");
	}

	public static void main(String[] args) {
		// 1. create array list
		
		launch(args);
	}
}
