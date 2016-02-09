package SongLibView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.ObservableList;

public class Song {
	public String songName;
	public String artist;
	public String album;
	public int year;
	
	public Song(String songName, String artist, String album, int year) {
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.year = year;		
	}
	
	public String toString() {
		return songName + ", " + artist + ", " + album + ", " + year;
	}
	
	public String outString() {
		return songName + "~" + artist + "~" + album + "~" + year;
	}
	
	static ArrayList<Song> songList = new ArrayList<Song>();
	
	/**
	 * sort all entries in obsList in alphabetical order
	 * @param obsList observable list containing songs
	 */
	public static void sortAbc(ObservableList<Song> obsList) {
		Collections.sort(obsList, new Comparator<Song>() {
			@Override
			public int compare(Song s1, Song s2) {
				return s1.songName.compareToIgnoreCase(s2.songName);
			}
		});
	}
	
	/**
	 * sort all Strings in obsList in alphabetical order
	 * @param obsList observable list containing Strings
	 */
	public static void sortAbcStrings(ObservableList<String> obsList) {
		Collections.sort(obsList, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
	}
	
	/**
	 * add newSong in correct spot in songList
	 * @param songList list of songs, should always be in abc order
	 * @param newSong Song instance that is about to be added into songList
	 */
	public static void addInAbcOrder(ArrayList<Song> songList, Song newSong) {
		if (songList.isEmpty() == true) {
			// no songs, add songName here
			songList.add(newSong);
			return;
		}
		
		if (songList.get(0).songName.compareTo(newSong.songName) > 0) {
			// songName is even smaller than first element, add at very beginning
			songList.add(0, newSong);
			return;
		}
		
		if (songList.get(songList.size() - 1).songName.compareTo(newSong.songName) < 0) {
			// songName is even bigger than last element, add at very end
			songList.add(newSong);
			return;
		}
		
		for (int i = 0; i < songList.size(); i++) {
			// search for correct location
			if (songList.get(i).songName.equals(newSong.songName) && songList.get(i).artist.equals(newSong.artist)) {
				// duplicate, not allowed
				System.out.println(newSong.songName + " by " + newSong.artist + " is a duplicate");
				return;
			}
			if (songList.get(i).songName.equals(newSong.songName)) {
				// songName is already in list, but artist is different, so ok, add here
				songList.add(i, newSong);
				return;
			}
			if (songList.get(i).songName.compareTo(newSong.songName) > 0) {
				// insert in index i
				songList.add(i, newSong);
				return;
			}
		}
	}
	
	/**
	 * goes through every song in songList and stores in output.txt
	 * @param songList list of songs, should be in abc order
	 * @return true if success, false otherwise
	 */
	public static boolean output(ArrayList<Song> songList) {
		try {
			File o = new File("output.txt");
			o.createNewFile();
			
			PrintWriter out = new PrintWriter(new FileWriter(o, false), true);
			for (int i = 0; i < songList.size(); i++) {
				out.println(songList.get(i).outString());
			}
			out.close();
			return true;
		} catch (IOException e) {
			System.out.println("Error reading file in Control.output");
			return false;
		} catch (Exception e) {
			System.out.println("Exception in Control.output");
			return false;
		}
	}
	
	/**
	 * takes in emptyList and file (output.txt) and puts lines in file into emptyList
	 * @param emptyList list of songs, should be empty when this called
	 * @param o output.txt
	 * @return true if success, false otherwise
	 */
	public static boolean input(ArrayList<Song> emptyList, File o) {
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(o);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				stickIn1Line(emptyList, line);
			}
			
			bufferedReader.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		} catch (IOException e) {
			System.out.println("Error reading file");
			return false;
		} catch (Exception e) {
			System.out.println("Exception in");
			return false;
		}
	}
	
	/**
	 * create new Song instance with line (songName~artist~album~year) and add to emptyList
	 * @param emptyList list of songs, should be empty first time this method called
	 * @param line (songName~artist~album~year)
	 */
	public static void stickIn1Line(ArrayList<Song> emptyList, String line) {
		String[] strArr = line.split("~");
		
		emptyList.add(new Song(strArr[0], strArr[1], strArr[2], Integer.parseInt(strArr[3])));
	}
	
	/**
	 * prints out each song in songList, only used for testing purposes
	 * @param songList list of songs, should always be in abc order
	 */
	public static void printList(ArrayList<Song> songList) {
		for (int i = 0; i < songList.size(); i++) {
			System.out.println(i + " " + songList.get(i).toString());
		}
	}
	
	public static void printObsList(ObservableList<Song> obsList) {
		for (int i = 0; i < obsList.size(); i++) {
			System.out.println(i + " " + obsList.get(i).toString());
		}
	}
}

