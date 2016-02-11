//by Brian Wong and Laszlo Glant

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

public class EditController {
	
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
			
			// check if title and artist are empty or not, can't be empty
			if (title.getText().length() == 0 || artist.getText().length() == 0) {
				Stage stage = (Stage) okButton.getScene().getWindow();
				stage.close();
				return;
			}
			
			// check if year is valid or not
			boolean validYear = isYear(year.getText());
			
			if (validYear == false) {
				// year is not valid, should not add, close window
				Stage stage = (Stage) okButton.getScene().getWindow();
				stage.close();
				return;
			}
			
			Song s;
			if (year.getText().length() == 0) {
				// yeal is left blank, add -1 as year
				s = new Song(title.getText(), artist.getText(), album.getText(), -1);
			} else if (validYear == true) {
				// year is valid, create Song instance to add in normally
				System.out.println("valid song");
				s = new Song(title.getText(), artist.getText(), album.getText(), Integer.parseInt(year.getText()));				
			} else {
				// year is not valid, make different Song instance to add in
				System.out.println("invalid song");
				s = new Song(title.getText(), artist.getText(), album.getText(), -1);
			}
			int index = SongViewController.index;
			System.out.println("index is before add: "+index);
			Boolean didAdd = Song.addInAbcOrder(Song.songList, SongViewController.obsList, s);
			System.out.println("index is after add: "+index);
			if (didAdd) {
				System.out.println("in didAdd");
				// added new song successfully then delete the unedited version
				Song.deleteSong(Song.songList, SongViewController.obsList, index);
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
	
	/**
	 * return true if String year is valid (ex. 1234), false if invalid (ex. 1234a)
	 * @param year String taken from text field in pop up window
	 * @return
	 */
	private boolean isYear(String year) {
		if (year.length() == 0) {
			// empty
			System.out.println("year is empty");
			return true;
		}
		
		boolean allNums = true;
		
		for (int i = 0; i < year.length(); i++) {
			if (isNum(year.charAt(i)) == false) {
				// there is a character that is not a number (ex. c, ~, etc), not valid year
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks whether char c is a space or a number, returns true if so, return false if c is not a valid character in year
	 * @param c one character in year
	 * @return
	 */
	private boolean isNum(char c) {
		if (c == ' ' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
			return true;
		} else {
			return false;
		}
	}
	
	//@FXML
	private boolean isDigit(TextField year) {
		if (!(year.getText() == null || year.getText().length() == 0)) {
			try {
	            // Do all the validation you need here such as
	            Integer.parseInt(year.getText());
	            return true;
	        } catch (NumberFormatException e) { 
	        	return false;
	        }
		}
		return false;
	}
}
