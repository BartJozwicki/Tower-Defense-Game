package helper;

import java.util.ArrayList;

public class Utils {

	//Both methods used to help with map generation, they transform from one to two dimensional arrays and back 
	
	public static int[][] arrayToMatrix(ArrayList<Integer> list, int ySize, int xSize){
		
		int[][] newArray = new int[ySize][xSize];
		
		for(int j = 0; j < newArray.length; j++)
			for(int i = 0; i < newArray[j].length; i++) {
				int index = j*ySize + i;
				newArray[j][i] = list.get(index);
			}
		return newArray;
	}
	
	
	public static int[] matrixToArray(int[][] twoArr) {
		int[] newArray = new int[twoArr.length * twoArr[0].length];
		
		for(int j = 0; j <twoArr.length; j++)
			for(int i = 0; i < twoArr[j].length; i++) {
				int index = j*twoArr.length + i;
				newArray[index] = twoArr[j][i];
			}
		
		return newArray;
	}
	
	//Simple hypotenuse calculation
	public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
		
		float xDiff = Math.abs(x1-x2);
		float yDiff = Math.abs(y1-y2);
		
		return (int)Math.hypot(xDiff, yDiff);
	}
}
