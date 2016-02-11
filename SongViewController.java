// by Brian Wong and Laszlo Glant

package SongLibView;

import java.io.File;
import java.io.IOException;


import app.SongLib;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SongViewController {

	private SongLib songlib;
	public static int index;
	File f = new File("output.txt");



	// main scene text fields (artist, album, year)
	@FXML
	private TextField artist;
	@FXML
	private TextField album;
	@FXML
	private TextField year;
	
	
	
	
	@FXML 
	public ListView<String> listView;

	public static ObservableList<String> obsList = FXCollections.observableArrayList();

	@FXML
	private void homeScene() throws IOException {
		songlib.showMainView();
	}

	@FXML
	private void addButton() throws IOException {
		SongLib.showAddScene();
	}

	@FXML
	private void editButton() throws IOException {
		SongLib.showEditScene();
	}

	@FXML
	private void deleteButton() throws IOException {
		index = listView.getSelectionModel().getSelectedIndex();
		if (Song.songList.size() == 0) {
			return;
		}
		SongLib.showDeleteScene();
		listView.getSelectionModel().select(index);
	}
	
	@FXML
	public void initialize(){
		
		// take contents from File f (output.txt) and load into both songList and obsList
		Song.input(Song.songList, obsList, f);
				
		// display entries in obsList
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		//Song.printList(Song.songList);
		//Song.printObsList(obsList);
		
		//System.out.println(artist.getText());
	}
	
}
