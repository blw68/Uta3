// by Brian Wong and Laszlo Glant

package SongLibView;

import app.SongLib;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopupController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField artist;
	@FXML
	private TextField album;
	@FXML
	private TextField year;
	@FXML
	public Button cancelButton, okButton;
	
	TextField yearDigits = new TextField();
		
	@FXML
	private void cancelButtonEvent(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void okButtonEvent(ActionEvent event) {
		try {
			/*System.out.println("title is " + title.getText());
			System.out.println("artist is " + artist.getText());
			System.out.println("album is " + album.getText());
			System.out.println("year is " + year.getText());*/
			
			// check if title and artist are empty or not, can't be
			
			Song s = new Song(title.getText(), artist.getText(), album.getText(), Integer.parseInt(year.getText()));
					
//			Song.addInAbcOrder(Song.songList, s);
//			Song.output(Song.songList);
			
			Boolean didAdd = Song.addInAbcOrder(Song.songList, SongViewController.obsList, s);
			//Boolean digit =	isDigit(year);
			if (didAdd) {
				// added new song successfully
				Song.sortAbcStrings(SongViewController.obsList);
				Song.sortAbcAL(Song.songList);
				
				Song.output(Song.songList);
				
			} else {
				// was duplicate, did not add, open up pop up that was duplicate
				SongLib.showDuplicatePopupScene();
			}	
			
			Song.printBothLists(Song.songList, SongViewController.obsList);
			
			
		} catch (Exception e) {
			System.out.println("exception in ok button event");
			e.printStackTrace(System.out);
		}

		// close pop up window just like cancel does
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}
	//@FXML
	private boolean isDigit(TextField year) {
		if (!(year.getText() == null || year.getText().length() == 0)) {
			try {
	            // Do all the validation you need here such as
	            Integer.parseInt(year.getText());
	            return true;
	        }catch (NumberFormatException e) { 
	        	return false;
	        }
		}
		return false;
	}
}
