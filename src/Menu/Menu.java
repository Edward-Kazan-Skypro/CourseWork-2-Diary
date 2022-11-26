package Menu;

import Utilites.CommonUtilites;
import Task.TaskUtilites;

import java.util.Scanner;

public class Menu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int select = 0;
        do {
            System.out.println("Введите цифру, соответствующую пункту меню:\n" +
                    "1. Добавить задачу\n" +
                    "2. Удалить задачу\n" +
                    "3. Редактировать задачу\n" +
                    "4. Посмотреть все задачи\n" +
                    "5. Посмотреть задачи на сегодня\n" +
                    "6. Посмотреть задачи на выбранную дату\n" +
                    "7. Посмотреть удаленные (архивные) задачи\n" +
                    "0. Выход");

            if (scanner.hasNextInt()) {
                select = scanner.nextInt();
                switch (select) {
                    case 1:
                        System.out.println("Выбран пункт меню 'Добавить задачу'");
                        TaskUtilites.addTask();
                        break;
                    case 2:
                        System.out.println("Выбран пункт меню 'Удалить задачу'");
                        TaskUtilites.viewTasksWithID();
                        if (TaskUtilites.taskMap.size() > 0) {
                            int selectedID = Integer.parseInt(CommonUtilites.inputString());
                            TaskUtilites.deleteTaskByID(selectedID);
                        }
                        break;
                    case 3:
                        System.out.println("Выбран пункт меню 'Редактировать задачу'");
                        System.out.println("Введите номер задачи");
                        TaskUtilites.viewTasksWithID();
                        if (TaskUtilites.taskMap.size() > 0) {
                            int selectedID = Integer.parseInt(CommonUtilites.inputString());
                            TaskUtilites.modifyTask(selectedID);
                        }
                        break;
                    case 4:
                        System.out.println("Выбран пункт меню 'Посмотреть все задачи'");
                        TaskUtilites.viewFullTaskList();
                        break;
                    case 5:
                        System.out.println("Выбран пункт меню 'Посмотреть задачи на сегодня'");
                        TaskUtilites.viewCurrentDayTask();
                        break;
                    case 6:
                        System.out.println("Выбран пункт меню 'Посмотреть задачи на выбранную дату'");
                        TaskUtilites.viewTasksByDate();
                        break;
                    case 7:
                        System.out.println("Выбран пункт меню 'Посмотреть удаленные (архивные) задачи'");
                        TaskUtilites.viewDeletedTasks();
                        break;
                    case 0:
                        System.out.println("Выбран пункт меню 'Выход'");
                        System.out.println("Выход из меню");
                        break;
                }
            } else {
                scanner.next();
                System.out.println("Выберите пункт меню из списка!");
            }
        } while (!(select == 0));
    }
}