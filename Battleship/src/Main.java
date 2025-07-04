import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[][] player1Field = new String[][] {
            {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},        
            {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
        };
        
        String[][] player2Field = new String[][] {
            {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},        
            {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
        };
        
        String[][] player1FogField = createFogField();
        String[][] player2FogField = createFogField();
        
        String[] ships = {"Aircraft Carrier (5 cells)", "Battleship (4 cells)", 
                         "Submarine (3 cells)", "Cruiser (3 cells)", "Destroyer (2 cells)"};
        int[] shipLengths = {5, 4, 3, 3, 2};
        
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        placeShipsForPlayer(scanner, player1Field, ships, shipLengths);
        
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        scanner.nextLine();
        clearScreen();
        
        System.out.println("Player 2, place your ships to the game field");
        System.out.println();
        placeShipsForPlayer(scanner, player2Field, ships, shipLengths);
        
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        scanner.nextLine();
        clearScreen();
        
        boolean gameOver = false;
        boolean player1Turn = true;
        
        while (!gameOver) {
            if (player1Turn) {
                printFieldWithFog(player2FogField);
                System.out.println("---------------------");
                printField(player1Field);
                System.out.println();
                System.out.println("Player 1, it's your turn:");
                
                gameOver = takeTurn(scanner, player2Field, player2FogField);
                if (gameOver) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
            } else {
                printFieldWithFog(player1FogField);
                System.out.println("---------------------");
                printField(player2Field);
                System.out.println();
                System.out.println("Player 2, it's your turn:");
                
                gameOver = takeTurn(scanner, player1Field, player1FogField);
                if (gameOver) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
            }
            
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            scanner.nextLine();
            clearScreen();
            player1Turn = !player1Turn;
        }
        
        scanner.close();
    }
    
    private static String[][] createFogField() {
        String[][] fogField = new String[11][11];
        fogField[0] = new String[]{" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        for (int i = 1; i <= 10; i++) {
            fogField[i][0] = String.valueOf((char)('A' + i - 1));
            for (int j = 1; j <= 10; j++) {
                fogField[i][j] = "~";
            }
        }
        return fogField;
    }
    
    private static void placeShipsForPlayer(Scanner scanner, String[][] field, String[] ships, int[] shipLengths) {
        printField(field);
        
        for (int shipIndex = 0; shipIndex < ships.length; shipIndex++) {
            boolean validPlacement = false;
            
            while (!validPlacement) {
                System.out.println("Enter the coordinates of the " + ships[shipIndex] + ":");
                System.out.print("> ");
                String firstCoord = scanner.next();
                String secondCoord = scanner.next();

                String firstChar = firstCoord.substring(0, 1);
                int firstNum = Integer.parseInt(firstCoord.substring(1));
                String secondChar = secondCoord.substring(0, 1);
                int secondNum = Integer.parseInt(secondCoord.substring(1));

                if (!firstChar.equals(secondChar) && firstNum != secondNum) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    continue;
                }
                
                if (firstNum < 1 || firstNum > 10 || secondNum < 1 || secondNum > 10) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue;
                }
                
                char firstCharLetter = firstChar.charAt(0);
                char secondCharLetter = secondChar.charAt(0);
                if (firstCharLetter < 'A' || firstCharLetter > 'J' || 
                    secondCharLetter < 'A' || secondCharLetter > 'J') {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue;
                }

                int length;
                if (firstChar.equals(secondChar)) {
                    length = Math.abs(firstNum - secondNum) + 1;
                } else {
                    length = Math.abs(firstCharLetter - secondCharLetter) + 1;
                }
                
                if (length != shipLengths[shipIndex]) {
                    System.out.println("Error! Wrong length of the " + ships[shipIndex].split(" \\(")[0] + "! Try again:");
                    continue;
                }
                
                if (!isValidPlacement(field, firstChar, firstNum, secondChar, secondNum)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    continue;
                }
                
                placeShip(field, firstChar, firstNum, secondChar, secondNum);
                validPlacement = true;
            }
            
            System.out.println();
            printField(field);
        }
        System.out.println();
    }
    
    private static boolean takeTurn(Scanner scanner, String[][] opponentField, String[][] opponentFogField) {
        System.out.print("> ");
        String shotCoord = scanner.next();
        
        String shotChar = shotCoord.substring(0, 1);
        int shotNum = Integer.parseInt(shotCoord.substring(1));
        
        char shotCharLetter = shotChar.charAt(0);
        if (shotCharLetter < 'A' || shotCharLetter > 'J' || shotNum < 1 || shotNum > 10) {
            System.out.println("Error! You entered wrong coordinates! Try again:");
            return false;
        }
        
        int shotRow = shotCharLetter - 'A' + 1;
        int shotCol = shotNum;
        
        if (opponentField[shotRow][shotCol].equals("O")) {
            opponentField[shotRow][shotCol] = "X";
            opponentFogField[shotRow][shotCol] = "X";
            
            if (isShipSunk(opponentField, shotRow, shotCol)) {
                if (allShipsSunk(opponentField)) {
                    return true;
                } else {
                    System.out.println("You sank a ship!");
                }
            } else {
                System.out.println("You hit a ship!");
            }
        } else if (opponentField[shotRow][shotCol].equals("X")) {
            System.out.println("You hit a ship!");
        } else if (opponentField[shotRow][shotCol].equals("M")) {
            System.out.println("You missed!");
        } else {
            opponentField[shotRow][shotCol] = "M";
            opponentFogField[shotRow][shotCol] = "M";
            System.out.println("You missed!");
        }
        
        return false;
    }
    
    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    private static void printField(String[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
                if (j < field[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    private static void printFieldWithFog(String[][] fogField) {
        printField(fogField);
    }
    
    private static boolean isValidPlacement(String[][] field, String firstChar, int firstNum, 
                                          String secondChar, int secondNum) {
        int startRow, endRow, startCol, endCol;
        
        if (firstChar.equals(secondChar)) {
            startRow = endRow = firstChar.charAt(0) - 'A' + 1;
            startCol = Math.min(firstNum, secondNum);
            endCol = Math.max(firstNum, secondNum);
        } else {
            startRow = Math.min(firstChar.charAt(0), secondChar.charAt(0)) - 'A' + 1;
            endRow = Math.max(firstChar.charAt(0), secondChar.charAt(0)) - 'A' + 1;
            startCol = endCol = firstNum;
        }
        
        for (int row = startRow - 1; row <= endRow + 1; row++) {
            for (int col = startCol - 1; col <= endCol + 1; col++) {
                if (row >= 1 && row <= 10 && col >= 1 && col <= 10) {
                    if (field[row][col].equals("O")) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    private static void placeShip(String[][] field, String firstChar, int firstNum, 
                                String secondChar, int secondNum) {
        if (firstChar.equals(secondChar)) {
            int row = firstChar.charAt(0) - 'A' + 1;
            int start = Math.min(firstNum, secondNum);
            int end = Math.max(firstNum, secondNum);
            for (int col = start; col <= end; col++) {
                field[row][col] = "O";
            }
        } else {
            int col = firstNum;
            char start = (char) Math.min(firstChar.charAt(0), secondChar.charAt(0));
            char end = (char) Math.max(firstChar.charAt(0), secondChar.charAt(0));
            for (char c = start; c <= end; c++) {
                int row = c - 'A' + 1;
                field[row][col] = "O";
            }
        }
    }
    
    private static boolean isShipSunk(String[][] field, int hitRow, int hitCol) {
        int left = hitCol;
        int right = hitCol;
        int top = hitRow;
        int bottom = hitRow;
        
        while (left > 1 && (field[hitRow][left - 1].equals("X") || field[hitRow][left - 1].equals("O"))) {
            left--;
        }
        while (right < 10 && (field[hitRow][right + 1].equals("X") || field[hitRow][right + 1].equals("O"))) {
            right++;
        }
        while (top > 1 && (field[top - 1][hitCol].equals("X") || field[top - 1][hitCol].equals("O"))) {
            top--;
        }
        while (bottom < 10 && (field[bottom + 1][hitCol].equals("X") || field[bottom + 1][hitCol].equals("O"))) {
            bottom++;
        }
        
        if (left != right) {
            for (int col = left; col <= right; col++) {
                if (field[hitRow][col].equals("O")) {
                    return false;
                }
            }
            return true;
        }
        
        if (top != bottom) {
            for (int row = top; row <= bottom; row++) {
                if (field[row][hitCol].equals("O")) {
                    return false;
                }
            }
            return true;
        }
        
        return true;
    }
    
    private static boolean allShipsSunk(String[][] field) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if (field[i][j].equals("O")) {
                    return false;
                }
            }
        }
        return true;
    }
}