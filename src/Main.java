import Enums.Periodicity;
import Menu.Menu;
import Task.Task;
import Task.TaskUtilites;
import Utilites.CommonUtilites;


public class Main {

    public static void main(String[] args) {
        //Создадим несколько задач для тестирования программы
        Task task_1 = new Task();
        task_1.setHeader("Стрижка в парикмахерской");
        task_1.setDescriptionTask("Стрижка в парикмахерской возле дома");
        task_1.setPeriodicity(Enums.Periodicity.MONTHLY);
        task_1.setTypeTask("личная");
        //Добавим начальную дату запланированной задачи
        task_1.getDatesList().add(CommonUtilites.convertStringToFullDate("10:00 26.11.2022"));
        //Добавим в список дат задачи остальные даты, в зависимости от периодичности
        //Ранее мы создали буферный список. Заполним этот список новыми датами.
        TaskUtilites.addDatesToList(task_1.getDatesList(), task_1.getPeriodicity());
        //Архивная задача или нет - при создании не указываем, т.к. какой смысл создавать неактуальную задачу?
        //Добавим задачу в список
        TaskUtilites.taskMap.put(task_1.getID_number(), task_1);

        Task task_2 = new Task();
        task_2.setHeader("Встреча Нового года");
        task_2.setDescriptionTask("Встреча Нового года с семьей");
        task_2.setPeriodicity(Periodicity.ANNUALLY);
        task_2.setTypeTask("личная");
        task_2.getDatesList().add(CommonUtilites.convertStringToFullDate("23:59 31.12.2022"));
        TaskUtilites.addDatesToList(task_2.getDatesList(), task_2.getPeriodicity());
        TaskUtilites.taskMap.put(task_2.getID_number(), task_2);

        Task task_3 = new Task();
        task_3.setHeader("Планерка");
        task_3.setDescriptionTask("Планерка по понедельникам");
        task_3.setPeriodicity(Periodicity.WEEKLY);
        task_3.setTypeTask("рабочая");
        task_3.getDatesList().add(CommonUtilites.convertStringToFullDate("09:15 28.11.2022"));
        TaskUtilites.addDatesToList(task_3.getDatesList(), task_3.getPeriodicity());
        TaskUtilites.taskMap.put(task_3.getID_number(), task_3);

        Task task_4 = new Task();
        task_4.setHeader("Спектакль Снегурочка");
        task_4.setDescriptionTask("Пойти с семьей на спектакль");
        task_4.setPeriodicity(Periodicity.ONCE);

        task_4.setTypeTask("личная");
        task_4.getDatesList().add(CommonUtilites.convertStringToFullDate("14:15 03.12.2022"));
        TaskUtilites.addDatesToList(task_4.getDatesList(), task_4.getPeriodicity());
        TaskUtilites.taskMap.put(task_4.getID_number(), task_4);

        Task task_5 = new Task();
        task_5.setHeader("Принять лекарство");
        task_5.setDescriptionTask("Принять таблетки, выписанные онкологом");
        task_5.setPeriodicity(Periodicity.DAILY);
        task_5.setTypeTask("личная");
        task_5.getDatesList().add(CommonUtilites.convertStringToFullDate("09:00 26.11.2022"));
        TaskUtilites.addDatesToList(task_5.getDatesList(), task_5.getPeriodicity());
        TaskUtilites.taskMap.put(task_5.getID_number(), task_5);

        Menu.showMenu();
    }


}