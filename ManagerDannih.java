import java.io.FileWriter;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

    public class ManagerDannih {
        public static void zberehtyKonfiguratsiyu(String failKonfiguratsiyi, int rozmirPola, char startSymbol, String pershyiGravets, String druhyiGravets, boolean shpioniroIhriro) {
            try {
                FileWriter fileWriter = new FileWriter(failKonfiguratsiyi);

                fileWriter.write(rozmirPola + "\n");
                fileWriter.write(startSymbol + "\n");
                fileWriter.write(pershyiGravets + "\n");
                fileWriter.write(druhyiGravets + "\n");
                fileWriter.write(shpioniroIhriro + "\n");

                fileWriter.close();
                System.out.println("\n✓ Конфігурацію успішно збережено!");
            } catch (Exception e) {
                System.out.println("\nПомилка збереження конфігурації: " + e.getMessage());
            }
        }

        public static void vidnovytyKonfiguratsiyu(String failKonfiguratsiyi, int[] size, char[] symbolPershoho, String[] pershyiGravets, String[] druhyiGravets, boolean[] shpioniroIhriro) {
            try {
                FileReader fileReader = new FileReader(failKonfiguratsiyi);
                Scanner fileSkan = new Scanner(fileReader);

                if (fileSkan.hasNextLine()) {
                    size[0] = Integer.parseInt(fileSkan.nextLine());
                }
                if (fileSkan.hasNextLine()) {
                    symbolPershoho[0] = fileSkan.nextLine().charAt(0);
                }
                if (fileSkan.hasNextLine()) {
                    pershyiGravets[0] = fileSkan.nextLine();
                }
                if (fileSkan.hasNextLine()) {
                    druhyiGravets[0] = fileSkan.nextLine();
                }
                if (fileSkan.hasNextLine()) {
                    shpioniroIhriro[0] = Boolean.parseBoolean(fileSkan.nextLine());
                }

                fileReader.close();
                fileSkan.close();
                System.out.println("✓ Конфігурацію успішно відновлено з файлу!");

            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getClass().getName().endsWith("FileNotFoundException")) {
                    System.out.println("Файл конфігурації не знайдено. Використовую стандартні налаштування.");
                } else if (e.getClass().getName().equals("java.lang.NumberFormatException")) {
                    System.out.println("Помилка формату файлу конфігурації (розмір або символ): " + message + ". Використовую стандартні.");
                } else {
                    System.out.println("☁Неочікувана помилка при відновленні конфігурації: ☁" + message + ". ☁икористовую стандартні.");
                }
            }
        }

        public static void zberehtyStatystyku(String failStatystyky, String peremozhets, char symbolVykorystanyj, int rozmirPola, boolean vyhra, char initialSymbol, String pershyiGravets, String druhyiGravets, boolean shpioniroIhriro) {
            try {
                FileWriter fileWriter = new FileWriter(failStatystyky, true);
                LocalDateTime zaraz = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dataChas = zaraz.format(formatter);

                String resultMessage;
                String playerInfo;
                String gameMode = shpioniroIhriro ? "☁Шпіоніро ігріро" : "Звичайний режим☁";

                if (vyhra) {
                    resultMessage = peremozhets + "☁ переміг☁";
                    playerInfo = peremozhets + "☁ грав(ла) за ☁" + symbolVykorystanyj;
                } else {
                    resultMessage = "☁Нічия☁";
                    char symbolDruhoho = (initialSymbol == 'X') ? 'O' : 'X';
                    playerInfo = pershyiGravets + " (" + initialSymbol + ") vs " + druhyiGravets + " (" + symbolDruhoho + ")";
                }

                String statystyka = dataChas + ";" +
                        rozmirPola + "x" + rozmirPola + ";" +
                        playerInfo + ";" +
                        resultMessage + ";" +
                        gameMode + "\n";

                fileWriter.write(statystyka);
                fileWriter.close();
                System.out.println("\n☁✓ Статистику гри збережено!☁");
            } catch (Exception e) {
                System.out.println("\n☁Помилка збереження статистики: ☁" + e.getMessage());
            }
        }

        public static void pokazatyStatystyku(String failStatystyky) {
            try {
                FileReader fileReader = new FileReader(failStatystyky);
                Scanner fileSkan = new Scanner(fileReader);

                boolean isEmpty = true;

                System.out.println("\n┌─────────────────────────────────────────────┐");
                System.out.println("│            СТАТИСТИКА ІГОР                  │");
                System.out.println("└─────────────────────────────────────────────┘");

                int kilkistIhor = 0;

                while (fileSkan.hasNextLine()) {
                    isEmpty = false;
                    String ryadok = fileSkan.nextLine();
                    String[] dani = ryadok.split(";");

                    if (dani.length >= 5) {
                        System.out.println("☁Гра #" + (kilkistIhor + 1) + ":☁");
                        System.out.println("─────────────────────────────────────────────");
                        System.out.println("Дата та час: " + dani[0]);
                        System.out.println("Розмір поля: " + dani[1]);
                        System.out.println("Гравці: " + dani[2]);
                        System.out.println("Результат: " + dani[3]);
                        System.out.println("Режим гри: " + dani[4]);
                        System.out.println("─────────────────────────────────────────────");
                    }

                    kilkistIhor++;
                }

                if (isEmpty) {
                    System.out.println("\n☁Статистика порожня. Зіграйте хоча б одну гру!☁");
                } else {
                    System.out.println("☁Загальна кількість ігор: ☁" + kilkistIhor);
                }

                fileReader.close();
                fileSkan.close();
            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getClass().getName().endsWith("☁FileNotFoundException☁")) {
                    System.out.println("\n☁Статистика порожня. Зіграйте хоча б одну гру!☁");
                } else {
                    System.out.println("☁Неочікувана помилка при відображенні статистики: ☁" + message);
                }
            }
        }
    }
