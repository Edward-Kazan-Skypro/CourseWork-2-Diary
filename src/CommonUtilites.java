import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CommonUtilites {
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate convertStringToDate(String string) {
        LocalDate date = LocalDate.now();
        try {
            date = LocalDate.parse(string, dateFormat);
        } catch (RuntimeException ex) {
            System.out.println("Данные введены неправильно. Попробуйте ввести еще раз.");
        }
        return date;
    }

    public static String convertDateToString(LocalDate date) {
        return date.format(dateFormat);
    }

    public static String inputString() {
        String string = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (string.length() == 0) {
            System.out.print("Введите текст ---> ");
            try {
                string = bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("Ошибка ввода с клавиатуры");
            }
        }
        return string;
    }

    public static boolean selectChoice() {
        Scanner scanner = new Scanner(System.in);
        int select = 2;
        while (select > 1) {
            System.out.print("Ведите вариант ответа (цифру 0 или 1): 1 - да, 0 - нет ----> ");
            select = scanner.nextInt();
            if (select == 1) {
                return true;
            }
        }
        return false;
    }

    public static String selectTypeTask() {
        String typeTask = "";
        int select;
        boolean selected = true;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Выберите тип задачи 'личная' или 'рабочая'. Введите цифру:");
            System.out.print("1 личная  2 рабочая ----> ");
            select = scanner.nextInt();
            switch (select) {
                case 1:
                    typeTask = "личная";
                    System.out.println("Выбрана тип задачи 'личная'");
                    selected = true;
                    break;
                case 2:
                    typeTask = "рабочая";
                    System.out.println("Выбрана тип задачи 'рабочая'");
                    selected = true;
                    break;
            }
        } while (!selected);
        return typeTask;
    }

    public static Task.Periodicity selectPeriodicity() {
        Task.Periodicity periodicity = Task.Periodicity.ONCE;
        System.out.println("Выберите периодичность выполнения задачи:");
        System.out.println("1 разово  2 ежедневно  3 еженедельно  4 ежемесячно  5 ежегодно");
        Scanner scanner = new Scanner(System.in);
        int select = scanner.nextInt();
        switch (select) {
            case 1: //1 однократно
                periodicity = Task.Periodicity.ONCE;
                System.out.println("Выбрана РАЗОВАЯ периодичность");
                break;
            case 2: //2 ежедневно
                periodicity = Task.Periodicity.DAILY;
                System.out.println("Выбрана ЕЖЕДЕНЕВНАЯ периодичность");
                break;
            case 3: //3 еженедельно
                periodicity = Task.Periodicity.WEEKLY;
                System.out.println("Выбрана ЕЖЕНЕДЕЛЬНАЯ периодичность");
                break;
            case 4: //4 ежемесячно
                periodicity = Task.Periodicity.MONTHLY;
                System.out.println("Выбрана ЕЖЕМЕСЯЧНАЯ периодичность");
                break;
            case 5: //5 ежегодно
                periodicity = Task.Periodicity.ANNUALLY;
                System.out.println("Выбрана ЕЖЕГОДНАЯ периодичность");
                break;
        }
        if (select < 1 || select > 5) {
            System.out.println("Не выбран ни один предложенных вариантов");
            System.out.println("Периодичность задачи будет установлена как РАЗОВАЯ");
        }
        return periodicity;
    }

    public static LocalDate inputTimeAndDate() {
        LocalDate timeAndDate = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String bufferTimeAndDate = "";
        while (bufferTimeAndDate.length() == 0) {
            System.out.println("Введите дату, на которую запланирована задача");
            System.out.println("Пожалуйста, вводите данные так - 31.12.2022");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                bufferTimeAndDate = bufferedReader.readLine();
                timeAndDate = LocalDate.parse(bufferTimeAndDate, dateFormat);
                if (timeAndDate.isBefore(LocalDate.now())) {
                    System.out.println("Указанная дата уже истекла");
                    System.out.println("Пожалуйста, укажите новую дату.");
                } else {
                    return timeAndDate;
                }
                bufferTimeAndDate = "";
            } catch (RuntimeException ex) {
                System.out.println("Данные введены неправильно. Попробуйте ввести еще раз.");
            } catch (IOException e) {
                System.out.println("Ошибка ввода с клавиатуры");
            }
        }
        return timeAndDate;
    }
}