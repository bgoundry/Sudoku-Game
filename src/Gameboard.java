import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Gameboard {

	
	int length, numEmpty, size;
	int[][] matrix;
	
	
	Gameboard(int size) {
		
		if (size < 1 || size > 6) {
			System.out.println("We are sorry, but we do not support"
					+ " that size gameboard");
		} else {
			this.size = size;
			length = size * size;
			matrix = new int[length][length];
			numEmpty = length * length;
		}
	}
	
	//useful for hex-duko and greater games
	private void printHelper(int num)  {
		
		char[] symbols = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z', '$'};
		

		if (num > 9) {
			System.out.print(symbols[num - 9] + " ");
		} else {
			System.out.print(num + " ");
		}
	}
	public void printBoard() {
		
		int colCounter = 0;
		int rowCounter = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				colCounter++;
				printHelper(matrix[i][j]);
				
				if (colCounter == size) {
					System.out.print(" ");
					colCounter = 0;
				}
			}
			rowCounter++;
			System.out.println();
			if (rowCounter == size) {
				System.out.println();
				rowCounter = 0;
			}
		}
	}
	
	

}
