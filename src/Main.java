import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Создадим несколько задач для тестирования программы
        Task task_1 = new Task();
        task_1.setHeader("Стрижка в парикмахерской");
        task_1.setDescriptionTask("Стрижка в парикмахерской возле дома");
        task_1.setPeriodicity(Task.Periodicity.MONTHLY);
        task_1.setTypeTask("личная");
        //Добавим начальную дату запланированной задачи
        task_1.getDatesList().add(CommonUtilites.convertStringToFullDate("10:00 26.11.2022"));
        //Добавим в список дат задачи остальные даты, в зависимости от периодичности
        //Ранее мы создали буферный список. Заполним этот список новыми датами.
        Task.TasksUtilites.addDatesToList(task_1.getDatesList(), task_1.getPeriodicity());
        //Архивная задача или нет - при создании не указываем, т.к. какой смысл создавать неактуальную задачу?
        //Добавим задачу в список
        Task.TasksUtilites.taskMap.put(task_1.getID_number(), task_1);

        Task task_2 = new Task();
        task_2.setHeader("Встреча Нового года");
        task_2.setDescriptionTask("Встреча Нового года с семьей");
        task_2.setPeriodicity(Task.Periodicity.ANNUALLY);
        task_2.setTypeTask("личная");
        task_2.getDatesList().add(CommonUtilites.convertStringToFullDate("23:59 31.12.2022"));
        Task.TasksUtilites.addDatesToList(task_2.getDatesList(), task_2.getPeriodicity());
        Task.TasksUtilites.taskMap.put(task_2.getID_number(), task_2);

        Task task_3 = new Task();
        task_3.setHeader("Планерка");
        task_3.setDescriptionTask("Планерка по понедельникам");
        task_3.setPeriodicity(Task.Periodicity.WEEKLY);
        task_3.setTypeTask("рабочая");
        task_3.getDatesList().add(CommonUtilites.convertStringToFullDate("09:15 28.11.2022"));
        Task.TasksUtilites.addDatesToList(task_3.getDatesList(), task_3.getPeriodicity());
        Task.TasksUtilites.taskMap.put(task_3.getID_number(), task_3);

        Task task_4 = new Task();
        task_4.setHeader("Спектакль Снегурочка");
        task_4.setDescriptionTask("Пойти с семьей на спектакль");
        task_4.setPeriodicity(Task.Periodicity.ONCE);

        task_4.setTypeTask("личная");
        task_4.getDatesList().add(CommonUtilites.convertStringToFullDate("14:15 03.12.2022"));
        Task.TasksUtilites.addDatesToList(task_4.getDatesList(), task_4.getPeriodicity());
        Task.TasksUtilites.taskMap.put(task_4.getID_number(), task_4);

        Task task_5 = new Task();
        task_5.setHeader("Принять лекарство");
        task_5.setDescriptionTask("Принять таблетки, выписанные онкологом");
        task_5.setPeriodicity(Task.Periodicity.DAILY);
        task_5.setTypeTask("личная");
        task_5.getDatesList().add(CommonUtilites.convertStringToFullDate("09:00 26.11.2022"));
        Task.TasksUtilites.addDatesToList(task_5.getDatesList(), task_5.getPeriodicity());
        Task.TasksUtilites.taskMap.put(task_5.getID_number(), task_5);

        Menu.showMenu();
    }

    public static class Menu {
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
                            Task.addTask();
                            break;
                        case 2:
                            System.out.println("Выбран пункт меню 'Удалить задачу'");
                            Task.TasksUtilites.viewTasksWithID();
                            if (Task.TasksUtilites.taskMap.size() > 0) {
                                int selectedID = Integer.parseInt(CommonUtilites.inputString());
                                Task.deleteTaskByID(selectedID);
                            }
                            break;
                        case 3:
                            System.out.println("Выбран пункт меню 'Редактировать задачу'");
                            System.out.println("Введите номер задачи");
                            Task.TasksUtilites.viewTasksWithID();
                            if (Task.TasksUtilites.taskMap.size() > 0) {
                                int selectedID = Integer.parseInt(CommonUtilites.inputString());
                                Task.modifyTask(selectedID);
                            }
                            break;
                        case 4:
                            System.out.println("Выбран пункт меню 'Посмотреть все задачи'");
                            Task.TasksUtilites.viewFullTaskList();
                            break;
                        case 5:
                            System.out.println("Выбран пункт меню 'Посмотреть задачи на сегодня'");
                            Task.TasksUtilites.viewCurrentDayTask();
                            break;
                        case 6:
                            System.out.println("Выбран пункт меню 'Посмотреть задачи на выбранную дату'");
                            Task.TasksUtilites.viewTasksByDate();
                            break;
                        case 7:
                            System.out.println("Выбран пункт меню 'Посмотреть удаленные (архивные) задачи'");
                            Task.TasksUtilites.viewDeletedTasks();
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
}