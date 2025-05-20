import java.util.Scanner;

public class GraManager {
    public static char[][] createBoard(int currentSize, boolean shpioniroIhriro) {
        int boardSize = currentSize * 2 + 1;
        char[][] board = new char[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        board[i][j] = shpioniroIhriro ? ' ' : '+';
                    } else {
                        board[i][j] = shpioniroIhriro ? ' ' : '-';
                    }
                } else {
                    if (j % 2 == 0) {
                        board[i][j] = shpioniroIhriro ? ' ' : '|';
                    } else {
                        board[i][j] = ' ';
                    }
                }
            }
        }

        board[0][0] = ' ';
        for (int i = 1; i < boardSize; i += 2) {
            board[i][0] = board[0][i] = (char) ('0' + (i / 2) + 1);
        }

        return board;
    }

    public static char[][] creaShadowBoard(int currentSize) {
        int boardSize = currentSize * 2 + 1;
        char[][] shadowBoard = new char[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                shadowBoard[i][j] = ' ';
            }
        }

        return shadowBoard;
    }

    public static void copyShadow(char[][] shadowBoard, char[][] visibleBoard) {
        for (int i = 0; i < shadowBoard.length; i++) {
            for (int j = 0; j < shadowBoard[i].length; j++) {
                if (i % 2 != 0 && j % 2 != 0) {
                    if (shadowBoard[i][j] != ' ') {
                        visibleBoard[i][j] = shadowBoard[i][j];
                    }
                }
            }
        }
    }

    public static void displayBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[] makeMove(Scanner egg, char[][] board, char[][] shadowBoard, int currentSize, char currentPlayerSymbol, char initialSymbol, String pershyiGravets, String druhyiGravets, boolean shpioniroIhriro) {
        int row = -1, col = -1;
        boolean validMove = false;

        String currentPlayerName;
        if (currentPlayerSymbol == initialSymbol) {
            currentPlayerName = pershyiGravets;
        } else {
            currentPlayerName = druhyiGravets;
        }

        while (!validMove) {
            System.out.println(currentPlayerName + " (" + currentPlayerSymbol + "), введіть номер рядка (1-" + currentSize + "): ");
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }
            row = egg.nextInt() - 1;

            System.out.println("Введіть номер стовпця (1-" + currentSize + "): ");
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }
            col = egg.nextInt() - 1;

            if (row >= 0 && row < currentSize && col >= 0 && col < currentSize) {
                row = row * 2 + 1;
                col = col * 2 + 1;

                if (shadowBoard[row][col] == ' ') {
                    shadowBoard[row][col] = currentPlayerSymbol;
                    if (!shpioniroIhriro) {
                        board[row][col] = currentPlayerSymbol;
                    }

                    validMove = true;
                } else {
                    System.out.println("Помилка! Клітинка зайнята. Спробуйте ще раз.^_____^");
                }
            } else {
                System.out.println("Помилка! Неправильний хід. Введіть номер рядка та стовпця від 1 до " + currentSize + ". ಠ﹏ಠ");
            }
        }

        return new int[]{row, col};
    }

    public static boolean checkWin(char[][] board, char playerSymbol) {
        int boardSize = board.length;
        for (int i = 1; i < boardSize; i += 2) {
            for (int j = 1; j < boardSize; j += 2) {
                if (j + 4 < boardSize && board[i][j] == playerSymbol && board[i][j+2] == playerSymbol && board[i][j+4] == playerSymbol) {
                    return true;
                }
                if (i + 4 < boardSize && board[i][j] == playerSymbol && board[i+2][j] == playerSymbol && board[i+4][j] == playerSymbol) {
                    return true;
                }
            }
        }
        for (int i = 1; i < boardSize; i += 2) {
            for (int j = 1; j < boardSize; j += 2) {
                if (i + 4 < boardSize && j + 4 < boardSize && board[i][j] == playerSymbol && board[i+2][j+2] == playerSymbol && board[i+4][j+4] == playerSymbol) {
                    return true;
                }
                if (i + 4 < boardSize && j - 4 >= 0 && board[i][j] == playerSymbol && board[i+2][j-2] == playerSymbol && board[i+4][j-4] == playerSymbol) {
                    return true;
                }
            }
        }
        return false;
    }
}