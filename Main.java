import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final String failKonfiguratsiyi = "konfiguratsiyi.txt";
    private static final String failStatystyky = "statystyka.txt";
    private static String pershyiGravets = "Гравець 1";
    private static String druhyiGravets = "Гравець 2";
    private static char symbolPershoho = 'X';
    private static int size = 3;
    private static boolean shpioniroIhriro = false;

    public static void main(String[] args) {
        Scanner egg = new Scanner(System.in);

        int[] sizeArr = new int[] {size};
        char[] symbolPershohoArr = new char[] {symbolPershoho};
        String[] pershyiGravetsArr = new String[] {pershyiGravets};
        String[] druhyiGravetsArr = new String[] {druhyiGravets};
        boolean[] shpioniroIhriroArr = new boolean[] {shpioniroIhriro};

        ManagerDannih.vidnovytyKonfiguratsiyu(failKonfiguratsiyi, sizeArr, symbolPershohoArr, pershyiGravetsArr, druhyiGravetsArr, shpioniroIhriroArr);

        size = sizeArr[0];
        symbolPershoho = symbolPershohoArr[0];
        pershyiGravets = pershyiGravetsArr[0];
        druhyiGravets = druhyiGravetsArr[0];
        shpioniroIhriro = shpioniroIhriroArr[0];

        while (true) {
            mainMenu();
            if (!egg.hasNextInt()) {
                System.out.println("☁Помилка! Введіть число. (►__◄)☁");
                egg.next();
                continue;
            }

            int scan = egg.nextInt();

            if (scan == 1) {
                playGame(egg, symbolPershoho, size);
            } else if (scan == 2) {
                int[] settings = setings(egg, symbolPershoho, size);
                size = settings[0];
                symbolPershoho = (char) settings[1];
                ManagerDannih.zberehtyKonfiguratsiyu(failKonfiguratsiyi, size, symbolPershoho, pershyiGravets, druhyiGravets, shpioniroIhriro);
            } else if (scan == 3) {
                ManagerDannih.pokazatyStatystyku(failStatystyky);
            } else if (scan == 4) {
                System.out.println("☁Дякуємо за гру! ＼（〇_ｏ）／☁");
                break;
            }
        }
    }

    public static void mainMenu() {
        System.out.println("☁ПРИВІТ!!! ༼ つ ◕_◕ ༽つ☁");
        System.out.println("Г☁оловне меню:☁");
        System.out.println("☁1. Нова гра☁");
        System.out.println("☁2. Налаштування☁");
        System.out.println("☁3. Статистика☁");
        System.out.println("☁4. Вихід☁");
    }

    public static int[] setings(Scanner egg, char currentStartPlayerSymbol, int currentSize) {
        char playerSymbolToStart = currentStartPlayerSymbol;
        int newSize = currentSize;

        while (true) {
            System.out.println("☁Налаштування:☁");
            System.out.println("☁1. Хто ходить першим (Зараз символ: " + playerSymbolToStart + " для гравця '" + pershyiGravets + "')☁");
            System.out.println("☁2. Розмір поля (Зараз: " + newSize + "x" + newSize + ")☁");
            System.out.println("☁3. Змінити ім'я першого гравця (Зараз: " + pershyiGravets + ")☁");
            System.out.println("☁4. Змінити ім'я другого гравця (Зараз: " + druhyiGravets + ")☁");
            System.out.println("☁5. Шпіоніро ігріро (Зараз: " + (shpioniroIhriro ? "Увімкнено" : "Вимкнено") + ")☁");
            System.out.println("☁6. Назад☁");

            if (!egg.hasNextInt()) {
                System.out.println("☁Помилка! Введіть число. (►__◄)☁");
                egg.next();
                continue;
            }
            int option = egg.nextInt();
            egg.nextLine();

            if (option == 1) {
                if (playerSymbolToStart == 'X') {
                    playerSymbolToStart = 'O';
                } else {
                    playerSymbolToStart = 'X';
                }
                System.out.println(pershyiGravets + "☁ тепер буде починати символом: ☁" + playerSymbolToStart);
            } else if (option == 2) {
                newSize = changeBoardSize(egg);
            } else if (option == 3) {
                System.out.print("☁Введіть нове ім'я для першого гравця: ☁");
                pershyiGravets = egg.nextLine();
                System.out.println("☁Ім'я першого гравця змінено на: ☁" + pershyiGravets);
            } else if (option == 4) {
                System.out.print("☁Введіть нове ім'я для другого гравця: ☁");
                druhyiGravets = egg.nextLine();
                System.out.println("☁Ім'я другого гравця змінено на: ☁" + druhyiGravets);
            } else if (option == 5) {
                shpioniroIhriro = !shpioniroIhriro;
                System.out.println("☁Шпіоніро ігріро тепер: " + (shpioniroIhriro ? "Увімкнено" : "Вимкнено☁"));
            } else if (option == 6) {
                break;
            }
        }
        return new int[] {newSize, playerSymbolToStart};
    }

    public static int changeBoardSize(Scanner egg) {
        while (true) {
            System.out.println("☁Виберіть розмір поля (3, 5, 7, 9): ☁");
            if (!egg.hasNextInt()) {
                System.out.println("☁Помилка! Введіть число. (►__◄)☁");
                egg.next();
                continue;
            }
            int newSizeChoice = egg.nextInt();
            if (newSizeChoice == 3 || newSizeChoice == 5 || newSizeChoice == 7 || newSizeChoice == 9) {
                return newSizeChoice;
            } else {
                System.out.println("☁Некоректний розмір.（︶^︶）☁");
            }
        }
    }

    public static char switchPlayer(char currentPlayerSymbol) {
        return (currentPlayerSymbol == 'X') ? 'O' : 'X';
    }

    public static void playGame(Scanner egg, char startingPlayerSymbol, int currentSize) {
        int moves = 0;
        boolean gameOn = true;
        char currentPlayerSymbol = startingPlayerSymbol;

        char[][] board = GraManager.createBoard(currentSize, shpioniroIhriro);
        char[][] shadowBoard = GraManager.creaShadowBoard(currentSize);

        while (gameOn) {
            GraManager.displayBoard(board);

            GraManager.makeMove(egg, board, shadowBoard, currentSize, currentPlayerSymbol, startingPlayerSymbol, pershyiGravets, druhyiGravets, shpioniroIhriro);
            moves++;

            boolean win = GraManager.checkWin(shadowBoard, currentPlayerSymbol);

            if (win) {
                gameOn = false;
                String winnerName;
                if (currentPlayerSymbol == startingPlayerSymbol) {
                    winnerName = pershyiGravets;
                } else {
                    winnerName = druhyiGravets;
                }
                System.out.println("☁Переміг " + winnerName + " (" + currentPlayerSymbol + ")! ☆*: .｡. o(≧▽≦)o .｡.:*☆☁");
                if (shpioniroIhriro) {
                    System.out.println("Справжня дошка:");
                    char[][] finalBoard = GraManager.createBoard(currentSize, false);
                    GraManager.copyShadow(shadowBoard, finalBoard);
                    GraManager.displayBoard(finalBoard);
                } else {
                    GraManager.displayBoard(board);
                }

                ManagerDannih.zberehtyStatystyku(failStatystyky, winnerName, currentPlayerSymbol, currentSize, true, startingPlayerSymbol, pershyiGravets, druhyiGravets, shpioniroIhriro);
            } else if (moves == currentSize * currentSize) {
                System.out.println("☁Нічия! (づ￣ 3￣)づ☁");
                gameOn = false;
                if (shpioniroIhriro) {
                    System.out.println("☁Справжня дошка:☁");
                    char[][] finalBoard = GraManager.createBoard(currentSize, false);
                    GraManager.copyShadow(shadowBoard, finalBoard);
                    GraManager.displayBoard(finalBoard);
                } else {
                    GraManager.displayBoard(board);
                }

                ManagerDannih.zberehtyStatystyku(failStatystyky, "☁Нічия☁", startingPlayerSymbol, currentSize, false, startingPlayerSymbol, pershyiGravets, druhyiGravets, shpioniroIhriro);
            } else {
                currentPlayerSymbol = switchPlayer(currentPlayerSymbol);
            }
        }
    }
}