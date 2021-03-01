import java.util.Arrays;

public class BoardMethods {

	
	private static int[][] copyMatrix(int[][] original) {
		int[][] copy = new int[original.length][original.length];
		
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original.length; j++) {
				
				copy[i][j] = original[i][j];
			}
		}
		
		return copy;
	}
	
	public static Gameboard copyBoard(Gameboard board) {
		
		Gameboard copy = new Gameboard(board.size);
		copy.matrix = copyMatrix(board.matrix);
		
		return copy;
	}

	public static boolean solve(Gameboard board) {
		
		if (board.size > 3) {
			System.out.println("Cannot solve board larger than size 3");
			return false;
		}
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				
				if (board.matrix[i][j] == 0) {
					for (int k = 1; k <= board.length; k++) {
						
						if (isPossible(k, i, j, board)) {
							board.matrix[i][j] = k;
							
							if (solve(board)) {
								return true;
							} else {
								board.matrix[i][j] = 0;
							
							}
						}
					}
					return false;
				}
			}
		}
		//no empty spaces so return true;
		return true;
	}
	
	//a function that returns a boolean as to whether it is possible
	//to add a number to a space, checks row, column, and square
	//updated to only use one loop, O(n) runtime instead of 0(n^3)
	
	public static boolean isPossible(int num, int x, int y, 
			Gameboard board) {
		
		for(int i = 0; i < board.length; i++) {
            if(board.matrix[i][y] == num || board.matrix[x][i] == num
              || squarePossible(num, x, y, board) == false){
            	return false;
            }
            
        }
		
        return true;
	}
	/*
	private static boolean rowPossible(int num, int row, Gameboard board) {
		
		for (int i = 0; i < board.length; i++) {
			
			if (board.matrix[row][i] == num) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean colPossible(int num, int col, Gameboard board) {
		
		for (int i = 0; i < board.length; i++) {
			
			if (board.matrix[i][col] == num) {
				return false;
			}
		}
		return true;
	}
	*/
	
	private static boolean squarePossible(int num, int x, int y,
			Gameboard board) {
		int row = x - (x % board.size);
		int col = y - (y % board.size);
		
		for (int i = row; i < row + board.size; i++) {
			for (int j = col; j < col + board.size; j++) {
				if (board.matrix[i][j] == num) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	//generates a random number less than the length of the board
	public static int randomNum(int length) {
		return (int) (length * Math.random()) ;
	}
	
	
	// this COULD get into an infinite loop potentially, be careful
	public static void addRanNum(Gameboard board) {
		//boolean representing that a number has not yet been added
		boolean notDone = true;
		
		while (notDone) {
			
			int x = randomNum(board.length);
			int y = randomNum(board.length);
			
			//will only add if space is empty
			//if not, try another number
			if (board.matrix[x][y] != 0) {
				continue;
			}
			
			int i = randomNum(board.length);
			
			if (isPossible(i, x, y, board)) {

				//everything is good, we can add a num
				board.matrix[x][y] = i;
				notDone = false;
			}
			
		}
		
	}

	//takes a board size and two coordinates. Tells which square of the 
	//board the coordinates are in. Squares are counted left to right,
	//top to bottom. First square is top left, 0, last is bottom right.
	//Number of squares is the same as the length
	//last square is always (size * size) - 1
	//should work for any size board
	//THIS ONE WAS TRICKY!
	public static int whichSquare(int x, int y, int size) {
		
		int square = 0;
		
		square += (y % size);
		square += (size * (x % size));
		
		return square;
		
	}

	
	public static void randomBoard(Gameboard board, int level) {
		
		int current = 0;
		if (level == 1) {
			//45 percent of the board will be filled
			current = board.length * board.length * 25 / 100;
		} else if (level == 2) {
			current = board.length * board.length * 20 / 100;
			
		} else if (level == 3) {
			current = board.length * board.length * 15 / 100;
		} else {
			System.out.println("Sorry, that is not a valid "
					+ "difficulty level.");
			return;
		}
		
		//add random numbers to the board
		while (current > 0) {
			addRanNum(board);
			current--;
			board.numEmpty--;
		}
	}
	
	//remove random nums
	private static void removeNums(Gameboard board, int level) {
		int current = 0;
		
		current = board.length * board.length * level / 10;
		//add random numbers to the board
		while (current > 0) {
			removeNum(board);
			current--;
			board.numEmpty--;
		}
	}
	
	private static void removeNum(Gameboard board) {
		boolean incomplete = true;
		
		while(incomplete) {
			int x = randomNum(board.length);
			int y = randomNum(board.length);
			
			if (board.matrix[x][y] == 0) {
				continue;
			} else {
				board.matrix[x][y] = 0;
				incomplete = false;
			}
		}
	}
	
	
	public static Gameboard[] betterBoardGenerator(int difficulty, int size) {
		if (size > 3) {
			System.out.println("Sorry does not work on boards larger than 9x9");
		}
		Gameboard board = new Gameboard(size);
		randomBoard(board, 2);
		solve(board);
		Gameboard solved = copyBoard(board);
		
		removeNums(board, difficulty);
		
		return new Gameboard[]{board, solved};
		
	}
}
