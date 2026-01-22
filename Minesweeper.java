import java.util.Scanner;

public class Minesweeper {
    static int[][] minefield;
    static int[][] revealed;    
    final static int ROWS = 10;
    final static int COLS = 10;
    final static int EMPTY = 0;
    final static int MINE = -1;
    final static int HIDDEN = 0;
    final static int SHOWN = 1;   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        minefield = new int[ROWS][COLS];
        revealed = new int[ROWS][COLS];
        
        setupMines();
        calculateNumbers();
        
        System.out.println("Welcome to Minesweeper");
        System.out.println("Enter row and column (0-9) to reveal a cell.");
        for (int turn = 0; turn < ROWS * COLS; turn++) {
            printBoard();            
            System.out.print("Enter row: ");
            int row = scanner.nextInt();
            System.out.print("Enter col: ");
            int col = scanner.nextInt();            
            if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
                System.out.println("Invalid positionTry again.");
                turn--;
                continue;
            }
            
            if (revealed[row][col] == SHOWN) {
                System.out.println("Already revealedTry another cell");
                turn--;
                continue;
            }            
            if (minefield[row][col] == MINE) {
                System.out.println("\nBOOM! You hit a mine!");
                revealAll();
                printBoard();
                break;
            } else {
                revealCell(row, col);
                
                if (checkWin()) {
                    System.out.println("\nCongratulations! You won!");
                    printBoard();
                    break;
                }
            }
        }
        scanner.close();
    }   
    static void setupMines() {
        minefield[1][2] = MINE;
        minefield[3][4] = MINE;
        minefield[2][7] = MINE;
        minefield[5][1] = MINE;
        minefield[7][8] = MINE;
        minefield[4][5] = MINE;
        minefield[6][3] = MINE;
        minefield[8][6] = MINE;
        minefield[1][8] = MINE;
        minefield[9][2] = MINE;
        minefield[3][9] = MINE;
        minefield[6][6] = MINE;
        minefield[8][1] = MINE;
        minefield[4][8] = MINE;
        minefield[2][3] = MINE;
    }    
    static void calculateNumbers() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (minefield[i][j] != MINE) {
                    minefield[i][j] = countAdjacentMines(i, j);
                }
            }
        }
    }    
    static int countAdjacentMines(int row, int col) {
        int count = 0;        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS) {
                    if (minefield[newRow][newCol] == MINE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    static void revealCell(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return;
        }
        if (revealed[row][col] == SHOWN) {
            return;
        }
        revealed[row][col] = SHOWN;
        if (minefield[row][col] == EMPTY) {
            revealCell(row - 1, col);
            revealCell(row + 1, col);
            revealCell(row, col - 1);
            revealCell(row, col + 1);
            revealCell(row - 1, col - 1);
            revealCell(row - 1, col + 1);
            revealCell(row + 1, col - 1);
            revealCell(row + 1, col + 1);
        }
    }
    static void revealAll() {
        for (int i =0; i< ROWS; i++) {
            for (int j = 0;j < COLS; j++) {
                revealed[i][j] = SHOWN;
            }
        }
    }
    static boolean checkWin() {
        for (int i = 0; i< ROWS;i++) {
            for (int j = 0; j < COLS; j++) {
                if (minefield[i][j] != MINE && revealed[i][j] != SHOWN) {
                    return false;
                }
            }
        }
        return true;
    }
    static void printBoard() {
        System.out.println("\n   0 1 2 3 4 5 6 7 8 9");
        System.out.println("  ---------------");   
        for (int i = 0; i < ROWS; i++) {
            System.out.print(i + "| ");
            for (int j = 0; j < COLS; j++) {
                if (revealed[i][j] == SHOWN) {
                    if (minefield[i][j] == MINE) {
                        System.out.print("* ");
                    } else if (minefield[i][j] == EMPTY) {
                        System.out.print("  ");
                    } else {
                        System.out.print(minefield[i][j] + " ");
                    }
                } else {
                    System.out.print("#");
                }
            }
            System.out.println("|");
        }
        System.out.println("  --------------\n");
    }
}