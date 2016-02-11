// by Brian Wong and Laszlo Glant

package SongLibView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.SongLib;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SongViewController implements Initializable{

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
		listView.getSelectionModel().select(index);
	}

	@FXML
	private void editButton() throws IOException {
		SongLib.showEditScene();
		if(Song.songList.size() == 0){
			return;
		}
		listView.getSelectionModel().select(index);
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
		System.out.println("in initialize");
		// take contents from File f (output.txt) and load into both songList and obsList
		Song.input(Song.songList, obsList, f);

		// display entries in obsList
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		//Song.printList(Song.songList);
		//Song.printObsList(obsList);

		//artist.setText("Hello");
		//System.out.println(artist.getText());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// take contents from File f (output.txt) and load into both songList and obsList
		System.out.println("in new initialize");
		Song.input(Song.songList, obsList, f);

		// display entries in obsList
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);

		artist.setText("Hello");
		album.setText("Album");
		year.setText("1234");
	}

}
