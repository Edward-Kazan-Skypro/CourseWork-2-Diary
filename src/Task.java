import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Task {

    enum Periodicity {
        //В условии не указан горизонт планирования (на год, пять или 10 лет)
        //Исходя из того, что есть ежегодные задачи, то 1 год точно должен быть.
        //Установим горизонт планирования в три года (как срок исковой давности :))
        ONCE(1),
        DAILY(1095),
        WEEKLY(156),
        MONTHLY(36),
        ANNUALLY(3);

        //Будем учитывать горизонт планирования в разах (сколько дат надо будет добавить в список к задаче)
        //Для однократной задачи - 0 раз, т.е. будет только 1 дата, по истечении которой задача уходит в архив
        //Для ежеденевной - 365 дней х 3 года = 1095 раз, т.е. с запланированной даты каждый последующий день
        //Для еженедельной - 52 недели в году х 3 года = 156 раз
        //Для ежемесячной - 12 х 3 = 36 раз
        //Для ежегодной - 1 х 3 = 3 раза

        private final int horizontPlanning;

        public int getHorizontPlanning() {
            return horizontPlanning;
        }

        Periodicity(int horizontPlanning) {
            this.horizontPlanning = horizontPlanning;
        }
    }

    //Заголовок задачи
    private String header;
    //Описание задачи
    private String descriptionTask;

    //Повторяемость (м.б. - однократная, ежедневная, еженедельная, ежемесячная, ежегодная)
    private Periodicity periodicity;

    //Список для хранения дат, на которые назначена задача
    private LinkedList<LocalDate> datesList = new LinkedList<>();

    //Тип задачи, от него зависят признаки - личная/публичная
    private String typeTask;
    private boolean activeTask = true;
    private int ID_number;

    //ID задачи - создается автоматически, новая задача - новый ID
    static int idTask = 1;

    public Task(String header, String descriptionTask, Periodicity periodicity, String typeTask, LinkedList<LocalDate> datesList) {
        this.header = header;
        this.descriptionTask = descriptionTask;
        this.periodicity = periodicity;
        this.typeTask = typeTask;
        this.datesList.addAll(datesList);
        ID_number = idTask;
        idTask++;
    }

    public Task() {
        ID_number = idTask;
        idTask++;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public LinkedList<LocalDate> getDatesList() {
        return datesList;
    }

    public void setDatesList(LinkedList<LocalDate> datesList) {
        this.datesList = datesList;
    }

    public String getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(String typeTask) {
        this.typeTask = typeTask;
    }

    public int getID_number() {
        return ID_number;
    }

    public void setID_number(int ID_number) {
        this.ID_number = ID_number;
    }

    public boolean isActiveTask() {
        return activeTask;
    }

    public void setActiveTask(boolean activeTask) {
        this.activeTask = activeTask;
    }

    public static void addTask() {
        System.out.println("Создадим задачу и добавим ее в список задач.");
        Task task = new Task();

        //Блок - ввод заголовка и описания задачи
        System.out.print("Введите заголовок задачи ---> ");
        task.setHeader(CommonUtilites.inputString());
        System.out.print("Введите описание задачи ---> ");
        task.setDescriptionTask(CommonUtilites.inputString());

        //Блок - ввод периодичности выполнения задачи
        task.setPeriodicity(CommonUtilites.selectPeriodicity());

        //Блок - ввод даты и времени (и сразу же проверка)
        LocalDate dateTime = CommonUtilites.inputTimeAndDate();
        //Теперь, зная периодичность выполнения задачи
        //И можем сформировать список дат, когда эта задача должна выполняться
        //Список дат зависит от выбранной периодичности
        //Список дат заполним в специальном методе
        //Сразу добавим в список введенную ранее дату
        task.getDatesList().add(dateTime);
        //Теперь надо сформировать список запланированных дат, исходя из введенной
        Task.TasksUtilites.addDatesToList(task.getDatesList(), task.getPeriodicity());
        //Блок - отметка личная/рабочая задача
        task.setTypeTask(CommonUtilites.selectTypeTask());

        //Все поля задачи заполнены
        //Поэтому добавляем созданный объект в общий список
        TasksUtilites.taskMap.put(task.getID_number(), task);

        //добавляем задачу в общий список задач
        System.out.println("Задача '" + task.getHeader() + "' создана и добавлена в список.");
    }

    public static void deleteTaskByID(int id) {
        if (TasksUtilites.taskMap.containsKey(id)) {
            TasksUtilites.taskMap.get(id).setActiveTask(false);
            System.out.println("Задача с ID " + id + " удалена из списка (отмечена как архивная).");
        } else {
            System.out.println("Задачи с таким ID не существует.");
        }
    }

    public static void modifyTask(int id) {

        if (TasksUtilites.taskMap.containsKey(id)) {
            Task task = TasksUtilites.taskMap.get(id);
            //Перед редактированием задачи хорошо бы обновить список дат, на которые назначена задача
            //Ситуация - задачу создали год назад и она еще длится. Так зачем хранить все старые даты?
            LinkedList<LocalDate> dList = new LinkedList<>();
            //Создали список с актуальными датами
            for (int i = 0; i < task.getDatesList().size(); i++) {
                if (task.getDatesList().get(i).isAfter(LocalDate.now())) {
                    dList.add(task.getDatesList().get(i));
                }
            }
            //Список с актуальными датами добавляем к задаче(старый список сотрем)
            task.getDatesList().clear();
            task.getDatesList().addAll(dList);

            System.out.println("Сейчас такой заголовок задачи: " + task.getHeader());
            System.out.println("Редактируем заголовок задачи?");
            if (CommonUtilites.selectChoice()) {
                task.setHeader(CommonUtilites.inputString());
            }
            System.out.println("Сейчас такое описание задачи: " + task.getDescriptionTask());
            System.out.println("Редактируем описание задачи?");
            if (CommonUtilites.selectChoice()) {
                task.setDescriptionTask(CommonUtilites.inputString());
            }
            System.out.println("Сейчас такой тип задачи: " + task.getTypeTask());
            System.out.println("Редактируем тип задачи (личная/рабочая)?");
            if (CommonUtilites.selectChoice()) {
                task.setTypeTask(CommonUtilites.selectTypeTask());
            }
            System.out.println("Сейчас такая периодичность задачи: " + task.getPeriodicity());
            System.out.println("Редактируем периодичность задачи?");
            if (CommonUtilites.selectChoice()) {
                task.setPeriodicity(CommonUtilites.selectPeriodicity());
                //сохраним первую дату
                LocalDate oldDate = task.getDatesList().get(0);
                task.getDatesList().clear();
                task.getDatesList().add(oldDate);
                //теперь обновим список дат задачи с учетом изменившейся периодичности
                TasksUtilites.addDatesToList(task.getDatesList(), task.getPeriodicity());
            }

            //Получим первую дату, на которую была назначена задача
            String date = CommonUtilites.convertDateToString(task.getDatesList().get(0));
            System.out.println("Дата, на которую запланирована задача: " + date);
            System.out.println("Редактируем дату задачи?");
            if (CommonUtilites.selectChoice()) {
                //вводим новую дату
                LocalDate newDate = CommonUtilites.inputTimeAndDate();
                //очищаем список от старых дат
                task.getDatesList().clear();
                //добавляем в список новую дату (первую)
                task.getDatesList().add(newDate);
                //заполняем список последующими датами
                TasksUtilites.addDatesToList(task.getDatesList(), task.getPeriodicity());
            }
            //если мы поменяли периодичность, но не меняли дату, то пересчет дат не происходит?
            //если меняем периодичность, то даты тоже надо обновить

            System.out.println("Сейчас задача активная");
            System.out.println("Хотите отметить задачу как архивная (неактивная, в списке задач отображаться не будет)?");
            if (CommonUtilites.selectChoice()) {
                task.setActiveTask(false);
            }

            //Поля обновлены, ID не меняли
            //задачу в списке с этим ID обновляем (удаляем старую с этим ключом и добавляем новый объект)
            TasksUtilites.taskMap.remove(id);
            TasksUtilites.taskMap.put(id, task);
            System.out.println("Редактирование закончено. Задача добавлена в список.");
        } else {
            System.out.println("Задачи с таким ID не существует.");
        }
    }

    @Override
    public String toString() {
        String string = "Задача - " + header +
                ", описание задачи: " + descriptionTask + "\n" +
                "периодичность: " + periodicity +
                ", тип задачи: " + typeTask +
                ", ID задачи: " + ID_number;
        LinkedList<String> listDatesForConsole = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (isActiveTask()) {
            for (LocalDate ld : datesList) {
                listDatesForConsole.add(ld.format(formatter));
            }
        }
        return string + "\nзадача запланирована на:\n" + listDatesForConsole;
    }

    //Внутренний класс для работы со списком задач и списком дат задачи
    public static class TasksUtilites {
        public static Map<Integer, Task> taskMap = new HashMap<>();

        public static void viewFullTaskList() {
            if (taskMap.size() < 1) {
                System.out.println("Список запланированных задач пуст. Добавьте задачу.");
            } else {
                for (Map.Entry<Integer, Task> t : taskMap.entrySet()) {
                    if (t.getValue().isActiveTask()) {
                        System.out.println(t.getValue());
                    }
                }
            }
        }

        public static void viewTasksWithID() {
            if (taskMap.size() < 1) {
                System.out.println("Список запланированных задач пуст. Добавьте задачу.");
            } else {
                for (Map.Entry<Integer, Task> t : taskMap.entrySet()) {
                    if (t.getValue().isActiveTask()) {
                        System.out.println(t.getValue().ID_number + " - " + t.getValue().header);
                    }
                }
            }
        }

        public static String createTaskListByDate(LocalDate bufferDate) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String dateAsString = bufferDate.format(dateFormat);
            String view = "Список задач на " + dateAsString + ":\n";
            String bufferView = "";
            for (Map.Entry<Integer, Task> t : taskMap.entrySet()) {
                Task bufferTask = t.getValue();
                int size = bufferTask.datesList.size();
                for (int i = 0; i < size; i++) {
                    if (bufferTask.datesList.get(i).isEqual(bufferDate) && bufferTask.isActiveTask()) {
                        bufferView = bufferView + bufferTask.header + "\n";
                    }
                }
            }
            if (bufferView.isEmpty()) {
                view = view + "На эту дату нет запланированных задач.";
            } else {
                view = view + bufferView;
            }
            return view;
        }

        public static void viewCurrentDayTask() {
            LocalDate bufferDate = LocalDate.now();
            String view = "";
            if (TasksUtilites.taskMap.size() < 1) {
                System.out.println("Список запланированных задач пуст. Добавьте задачу.");
            } else {
                System.out.println(TasksUtilites.createTaskListByDate(bufferDate));
            }
        }

        public static void viewTasksByDate() {
            LocalDate bufferDate;
            String dateAsString;
            String view = "";
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            if (TasksUtilites.taskMap.size() < 1) {
                System.out.println("Список запланированных задач пуст. Добавьте задачу.");
            } else {
                dateAsString = CommonUtilites.inputString();
                bufferDate = LocalDate.parse(dateAsString, dateFormat);
                System.out.println(TasksUtilites.createTaskListByDate(bufferDate));
            }
        }

        public static void viewDeletedTasks() {
            String view = "";
            if (TasksUtilites.taskMap.size() < 1) {
                System.out.println("Список удаленных задач пустой.");
            } else {
                System.out.println("Список удаленных задач:");
                for (Map.Entry<Integer, Task> t : TasksUtilites.taskMap.entrySet()) {
                    if (!t.getValue().isActiveTask()) {
                        view = view + t.getValue() + "\n";
                    }
                }
            }
            if (!view.isEmpty()) {
                System.out.println(view);
            } else System.out.println("- пустой ");
        }

        public static LinkedList<LocalDate> addDatesToList(LinkedList<LocalDate> datesList, Periodicity periodicity) {
            int sizeDateList = periodicity.getHorizontPlanning();
            //Цикл начинаем с 1, т.к. первый элемент уже добавили ранее.
            //Получается, что к первому элементу списка добавляем следующий в зависимости от периодичности

            //Добавим ежегодные даты
            if (sizeDateList == 3) {
                for (int i = 1; i < sizeDateList; i++) {
                    datesList.add(datesList.get(0).plusYears(i));
                }
            }
            //Добавим ежемесячные даты
            if (sizeDateList == 36) {
                for (int i = 1; i < sizeDateList; i++) {
                    datesList.add(datesList.get(0).plusMonths(i));
                }
            }
            //Добавим еженедельной даты
            if (sizeDateList == 156) {
                for (int i = 1; i < sizeDateList; i++) {
                    datesList.add(datesList.get(0).plusWeeks(i));
                }
            }
            //Добавим ежедневные даты
            if (sizeDateList == 1095) {
                for (int i = 1; i < sizeDateList; i++) {
                    datesList.add(datesList.get(0).plusDays(i));
                }
            }
            //Если выбрана периодичность РАЗОВО, то дата уже добавлена выше
            //Поэтому в список ничего не добавляем - в списке будет только одна дата
            return datesList;
        }
    }
}
