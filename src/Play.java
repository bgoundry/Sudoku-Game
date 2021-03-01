import java.util.Scanner;

public class Play {

	private static int getSize(Scanner getInput) {
		System.out.println("Welcome to Sudoku. Please select a board "
				+ "size: 2, 3, 4, 5, or 6: ");
		
		int size = getInput.nextInt();
		
		while (size < 2 || size > 6) {
			
			System.out.println("Sorry, that is not a valid input \n"
					+ "Please select a board size: 2, 3, 4, 5, or 6: ");
			
			size = getInput.nextInt();
		}
		return size;
	}
	
	private static int getDifficulty(Scanner getInput, boolean bigBoard) {
		
		int difficulty = 0;
		
		if (bigBoard) {
			System.out.println("Please select a difficulty of 1, 2, or 3: \n");
			difficulty = getInput.nextInt();
			while (difficulty > 3 || difficulty < 1) {
				
				System.out.println("Sorry, that is not a valid difficulty \n"
						+ "Please select a difficulty level: 1, 2 or 3: ");
				
				difficulty = getInput.nextInt();
			}
		} else {
			System.out.print("Please select a difficulty from 1 to 9: ");
			difficulty = getInput.nextInt();
			while (difficulty > 9 || difficulty < 1) {
				
				System.out.println("Sorry, that is not a valid difficulty \n"
						+ "Please select a difficulty level from 1 to 9: ");
				
				difficulty = getInput.nextInt();
			
			}
		}
		return difficulty;
		
	}
	
	private static Gameboard makeBoard(int difficulty, int size) {
		Gameboard game = new Gameboard(size);
		BoardMethods.randomBoard(game, difficulty);
		
		
		
		game.printBoard();
		
		System.out.println("Here is your board, size " + size + 
				" difficulty level " + difficulty);
		
		return game;
	}
	
	private static Gameboard solveBoard(Gameboard game) {
		Gameboard solved = BoardMethods.copyBoard(game);
		BoardMethods.solve(solved);
		return solved;
	}
	
	
	private static void bigBoardGame(Scanner getInput, int size,
			int difficulty) {
		
		System.out.println("Creating a board of size " + size + 
				" and difficulty " + difficulty);
		
		System.out.println("Big boards cannot be automatically"
				+ " solved, however,"
				+ "you are free to try and solve it anyway!");
		
		System.out.println("Enter -1 to end game");
		
		
		int x = -1, y = -1, current = -10, numLeft;
		
		Gameboard unsolved, original; 
		
		unsolved = new Gameboard(size);
		BoardMethods.randomBoard(unsolved, difficulty);
		original = BoardMethods.copyBoard(unsolved);
		numLeft = numLeft(original);
		
		while (current != -1 && numLeft > 0) {
			unsolved.printBoard();
			
			System.out.println("Enter an x coordinate: \n");
			current = getInput.nextInt();
			
			if (current ==  -1) {
				break;
			}
			if (current < 0 || current > unsolved.length) {
				System.out.println("Sorry, that is an invalid input."
						+ " Please enter"
						+ " a number in between 0 and " + 
						(unsolved.length - 1));
				
			} else {
				x = current;
				System.out.println("Enter a y coordinate: \n");
				current = getInput.nextInt();
				
				if (current == -1) {
					break;
				}
				if (current < 0 || current > unsolved.length) {
					System.out.println("Sorry, that is an invalid"
							+ " input. Please enter"
							+ " a number in between 0 and "
							+ (unsolved.length - 1));
				} else {
					y = current;
					
					if (original.matrix[x][y] != 0) {
						System.out.println("Sorry, that location is part"
								+ " of the original board and"
								+ " cannot be changed. Please try"
								+ " another cooridnate");
						
					} else {
						
						System.out.println("Ok, please enter the number you"
								+ " would like to place in"
								+ " [" + x + "," + y + "]");
						 
						current = getInput.nextInt();
						
						if (BoardMethods.isPossible(current, x, y, unsolved)) {
							unsolved.matrix[x][y] = current;
							System.out.println("Ok, the board will be updated"
									+ " with " + current + " in" + 
									" [" + x + "," + y + "]");
						} else {
							System.out.println("Sorry, that value already "
									+ "exists in the row, column, or square."
									+ " Please try another value or another"
									+ " location!");
						}
					}
					
				}
			}
		}
		unsolved.printBoard();
		System.out.println("GAME OVER\nThanks for playing!");
	}
	
	private static int numLeft(Gameboard board) {
		
		int answer = 0;  
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				
				if (board.matrix[i][j] == 0) {
					answer++;
				}
			}
		}
		
		return answer;
	}
	
	private static void getHint(int x, int y, Gameboard solved,
			Gameboard unsolved) {
		unsolved.matrix[x][y] = solved.matrix[x][y];
	}
	
	private static void regBoardGame(Scanner getInput, int size,
			int difficulty) {
		System.out.println("Creating a board of size " + size + 
				" and difficulty " + difficulty + " \n");
		
		Gameboard[] boards = 
				BoardMethods.betterBoardGenerator(difficulty, size);
		Gameboard unsolved = boards[0];
		Gameboard solved = boards[1];
		unsolved.printBoard();
		
		System.out.println("Try and solve this board for yourself!"
				+ " Enter an x index, a y index, and then the number"
				+ " you think goes\nin that position. If it is correct,"
				+ " it will be added. If you get stuck, type board "
				+ " coordinates\nand then 10 to fill in one spot."
				+ " If you want to solve the entire board and quit,"
				+ " type -1 at any time");
		
		int numLeft = numLeft(unsolved);
		int current = -20;
		int y = -1;
		int x = -1;
		while(numLeft > 0 && current != -1) {
			
			System.out.println("Enter an x coordinate: ");
			current = getInput.nextInt();
			
			if (current == -1) {
				break;
			}
			
			if (current < 0 || current > unsolved.length) {
				System.out.println("Sorry that is not a valid input"
						+ " x must be greater than 0 and less than"
						+ " board length. Enter -1 to solve board and quit");
				unsolved.printBoard();
			} else {
				x = current;
				System.out.println("Enter a y coordinate: ");
				current = getInput.nextInt();
				
				if (current == -1) {
					break;
				}
				
				if (current < 0 || current > unsolved.length) {
					System.out.println("Sorry that is not a valid input,"
							+ " y must be greater than 0 and less than "
							+ "board length. Enter -1 to"
							+ " solve board and quit");
					unsolved.printBoard();
							
				} else {
					y = current;
					
					if (unsolved.matrix[x][y] != 0) {
						System.out.println("Sorry, that space is"
								+ " already filled in, try a different space!");
						unsolved.printBoard();
						continue;
					}
					
					System.out.println("Enter the value you think goes in " + x +
							", " + y + " or type 10 for the answer");
					current = getInput.nextInt();
					if (current == -1 ) {
						break;
					}
					
					if (current == 10) {
						System.out.println("Ok that square will be"
								+ " filled with the correct value");
						getHint(x, y, solved, unsolved);
						numLeft--;
						System.out.println("Here is the updated board!");
						unsolved.printBoard();
						continue;
					}
					
					if (solved.matrix[x][y] != current) {
						System.out.println("Sorry that is not the correct value "
								+ "for that position! Try again!");
					} else {
						System.out.println("Correct! Updating the board now!");
						numLeft--;
						unsolved.matrix[x][y] = solved.matrix[x][y];
						unsolved.printBoard();
					}
				}
			}
			
		}
		
		solved.printBoard();
		System.out.println("GAME OVER \n Thanks for playing!");
	}
	
	public static void startGame() {
		
		Scanner getInput = new Scanner(System.in);
		
		int size = getSize(getInput);
		int difficulty = 0;

		
		if (size > 3) {
			//big board has three difficulties
			difficulty = getDifficulty(getInput, true);
			bigBoardGame(getInput, size, difficulty);
		} else {
			//reg board has 9 difficulties
			difficulty = getDifficulty(getInput, false);
			regBoardGame(getInput, size, difficulty);
		}
		
		
		getInput.close();
		
		
	}
}
