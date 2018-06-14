package me.jupdyke01.RoomDesigner.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class FileUtils {


	public FileUtils() {

	}

	public String readFile(String path) {
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			return stringBuffer.toString();
		} catch(IOException e) {
			crashedFile(e);
		}
		return "";
	}

	public String readFile(File ffile) {
		try {
			File file = ffile;
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			return stringBuffer.toString();
		} catch(IOException e) {
			crashedFile(e);
		}
		return "";
	}

	public void writeFile(String path, String text) {
		File highscorefile = new File(path);
		FileWriter fw;
		try {
			fw = new FileWriter(highscorefile);
			fw.write(text);
			fw.close();
		} catch (IOException e) {
			crashedFile(e);
		}		
	}

	public void writeFile(File file, String text) {
		File highscorefile = file;
		FileWriter fw;
		try {
			fw = new FileWriter(highscorefile);
			fw.write(text);
			fw.close();
		} catch (IOException e) {
			crashedFile(e);
		}		
	}

	public static void crashedFile(Exception e) {
		File crashfile = new File("Crash/crash.txt");

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(crashfile);
			PrintStream ps = new PrintStream(fos);
			e.printStackTrace(ps);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}		
}
