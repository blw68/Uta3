//by Brian Wong and Laszlo Glant

package SongLibView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.SongLib;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController implements Initializable {

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
		// press ok, delete, then add

		// 1. check input

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
			// year is left blank, add -1 as year
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
		System.out.println("index in EditController " + index);

		try {
			// 2. delete old song (user pressed ok, so no turning back at this point
			
			// store info
			Song s2 = Song.songList.get(index);
			
			// delete at index
			Boolean didDelete = Song.deleteSong(Song.songList, SongViewController.obsList, index);

			// 3. add updated song in correct place
			Boolean didAdd = Song.addInAbcOrder(Song.songList, SongViewController.obsList, s);
			
			
			if (didAdd) {
				System.out.println("in didAdd");
//				System.out.println("add at index "+Song.addIndex);
//				// added new song successfully then delete the unedited version
//				if(Song.addIndex<index) {
//					Song.deleteSong(Song.songList, SongViewController.obsList, index+1);
//				} else {
//					Song.deleteSong(Song.songList, SongViewController.obsList, index);
//				}
//				Song.sortAbcStrings(SongViewController.obsList);
//				Song.sortAbcAL(Song.songList);

				Song.output(Song.songList);

			} else {
				// was duplicate, did not add, open up pop up that was duplicate, add back deleted song (s2)
				didAdd = Song.addInAbcOrder(Song.songList, SongViewController.obsList, s2);
				SongLib.showDuplicatePopupScene();
			}	

			Song.printBothLists(Song.songList, SongViewController.obsList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// close pop up window just like cancel does
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();


	}

	@FXML
	private void okButtonEvent0(ActionEvent event) {
		try {
			/*
			 * Requirements:
			 * 1. title and artist cant be empty (ok)
			 * 2. if change album or year, allow
			 */

			System.out.println("in ok button event, index " + SongViewController.index);

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
				// year is left blank, add -1 as year
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
				System.out.println("add at index "+Song.addIndex);
				// added new song successfully then delete the unedited version
				if(Song.addIndex<index) {
					Song.deleteSong(Song.songList, SongViewController.obsList, index+1);
				} else {
					Song.deleteSong(Song.songList, SongViewController.obsList, index);
				}
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("in new initialize");

		title.setText(Song.songList.get(SongViewController.index).songName);
		artist.setText(Song.songList.get(SongViewController.index).artist);
		album.setText(Song.songList.get(SongViewController.index).album);
		year.setText(Song.songList.get(SongViewController.index).year+"");

	}
}
