package helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import objects.PathPoint;

public class LoadSave {
	
	//return path to user home directory
	public static String homePath = System.getProperty("user.home"); 
    //name of the created folder
	public static String saveFolder = "TD";
    //name of the file with starting map
	public static String levelFile = "level.txt";
    //File.separator ensures that path works on any system '/' and '\' problems it does it automatically
	public static String filePath = homePath + File.separator + saveFolder + File.separator + levelFile;
    
	private static File lvlFile = new File(filePath);
 
	
    public static void CreateFolder() {
    	File folder = new File(homePath + File.separator + saveFolder);
    	folder.mkdir();
    }
    
	//Load sprite
	public static BufferedImage getSpriteAtlas() {
		
		BufferedImage img = null;

		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("SpritesNew.png");
		try {
			img = ImageIO.read(is);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		return img;
	}
	
	public static BufferedImage getHeartsAtlas() {
		
		BufferedImage img = null;
		
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("Hearts.png");
		try {
			img = ImageIO.read(is);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return img;	
	}
	

	

	//Generate map
	public static void CreateLevel(int[] idArr) {
		
		if(lvlFile.exists())
		{
			System.out.println("File " + lvlFile + " already Exist, can't create");
			return;
		}
		else {
			try {
				lvlFile.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			WriteToFile(lvlFile, idArr, new PathPoint(0,0), new PathPoint(0,0));
		}
	}

	//Save to the file
	public static void WriteToFile(File f, int[] idArr, PathPoint start, PathPoint end) {

		try {
			PrintWriter pw = new PrintWriter(f);
			for (Integer i : idArr)
				pw.println(i);

			pw.println(start.getXCord());
			pw.println(start.getYCord());
			pw.println(end.getXCord());
			pw.println(end.getYCord());
			
			
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	private static void WriteToFile(int[] idArr, PathPoint start, PathPoint end) {
	
		try {
			PrintWriter pw = new PrintWriter(lvlFile);
			for(Integer i : idArr)
				pw.println(i);
		pw.println(start.getXCord());
		pw.println(start.getYCord());
		pw.println(end.getXCord());
		pw.println(end.getYCord());
		pw.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void SaveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {

		if(lvlFile.exists()) {
			WriteToFile(Utils.matrixToArray(idArr), start, end);
		}else {
			System.out.println("File: "+ lvlFile + " does not exist!");
			return;
		}
	}
	
	//Load map from the file
	private static ArrayList<Integer> ReadFromFile(File file) {

		ArrayList<Integer> list = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(lvlFile);

			while (sc.hasNextLine()) {
				list.add(Integer.parseInt(sc.nextLine()));
			}
			sc.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		return list;
	}

	public static ArrayList<PathPoint> GetLevelPathPoints(){
		
		if(lvlFile.exists()) {
			
			ArrayList<Integer> list = ReadFromFile(lvlFile);
			ArrayList<PathPoint> points = new ArrayList<>();
			
			points.add(new PathPoint(list.get(400), list.get(401)));
			points.add(new PathPoint(list.get(402), list.get(403)));
			
			return points;
		}
		else {
			
			System.out.println("File: "+ lvlFile + " does not exist!");
			return null;
			
		}
		
	}
	
	
	public static int[][] GetLevelData() {

		if(lvlFile.exists()) {
			ArrayList<Integer> list = ReadFromFile(lvlFile);
			return Utils.arrayToMatrix(list, 20, 20);
		}
		else {
			System.out.println("File: "+ lvlFile + " does not exist!");
			return null;
		}
		
	}
	

	
	//For edit background creation and scenes 


	
	public static void CreateLevelE(String name, int[] idArr) {
		File newLevel = new File("resources/" + name + ".txt");
		
		if (newLevel.exists()) {
			System.out.println("File: " + name + " already exists!");
			return;
		} else {
			try {
				newLevel.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			WriteToFileE(newLevel, idArr);
		}

	}
	
	private static void WriteToFileE(File f, int[] idArr) {
		try {
			PrintWriter pw = new PrintWriter(f);
			for (Integer i : idArr)
				pw.println(i);

			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public static void SaveLevelE(String name, int[][] idArr) {
		File levelFile = new File("resources/" + name + ".txt");

		if (levelFile.exists()) {
			WriteToFileE(levelFile, Utils.matrixToArray(idArr));
		} else {
			CreateLevelE(name, Utils.matrixToArray(idArr));
			System.out.println("File: " + name + " does not exists! ");
			return;
		}
	}
	
	private static ArrayList<Integer> ReadFromFileE(File file) {
		ArrayList<Integer> list = new ArrayList<>();

		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				list.add(Integer.parseInt(sc.nextLine()));
			}

			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}
	public static int[][] GetLevelDataE(String name) {
		
		File lvlsFile = new File("resources/" + name + ".txt");

		if (lvlsFile.exists()) {
			ArrayList<Integer> list = ReadFromFileE(lvlsFile);
			return Utils.arrayToMatrix(list, 25, 20);

		} else {
			System.out.println("File: " + name + " does not exists! ");
			return null;
		}

	}
	
	
	
}