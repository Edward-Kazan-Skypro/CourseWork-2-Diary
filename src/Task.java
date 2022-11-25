import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LinkedList<LocalDateTime> datesList = new LinkedList<>();

    //Тип задачи, от него зависят признаки - личная/публичная
    private String typeTask;
    private boolean activeTask = true;
    private int ID_number;

    //ID задачи - создается автоматически, новая задача - новый ID
    static int idTask = 1;

    public Task(String header, String descriptionTask, Periodicity periodicity, String typeTask, LinkedList<LocalDateTime> datesList) {
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

    public LinkedList<LocalDateTime> getDatesList() {
        return datesList;
    }

    public void setDatesList(LinkedList<LocalDateTime> datesList) {
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
        LocalDateTime dateTime = CommonUtilites.inputTimeAndDate();
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
            LinkedList<LocalDateTime> dList = new LinkedList<>();
            //Создали список с актуальными датами
            for (int i = 0; i < task.getDatesList().size(); i++) {
                if (task.getDatesList().get(i).isAfter(LocalDateTime.now())) {
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
                LocalDateTime oldDate = task.getDatesList().get(0);
                task.getDatesList().clear();
                task.getDatesList().add(oldDate);
                //теперь обновим список дат задачи с учетом изменившейся периодичности
                TasksUtilites.addDatesToList(task.getDatesList(), task.getPeriodicity());
            }

            //Получим первую дату, на которую была назначена задача
            String date = CommonUtilites.convertFullDateToString(task.getDatesList().get(0));
            System.out.println("Время и дата, на которые запланирована задача: " + date);
            System.out.println("Редактируем?");
            if (CommonUtilites.selectChoice()) {
                //вводим новую дату
                LocalDateTime newDate = CommonUtilites.inputTimeAndDate();
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
        LocalDateTime date = getDatesList().get(0);
        String str = CommonUtilites.convertFullDateToString(date);
        String string = str + " - " + header + "\n" +
                "описание задачи: " + descriptionTask + "\n" +
                "периодичность: " + periodicity +
                ", тип задачи: " + typeTask +
                ", ID задачи: " + ID_number;
        LinkedList<String> listDatesForConsole = new LinkedList<>();
        if (isActiveTask()) {
            for (LocalDateTime ld : datesList) {
                listDatesForConsole.add(CommonUtilites.convertFullDateToString(ld));
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

        public static String createTaskListByDate(LocalDateTime inputFullDate) {
            //Из полной даты получаем только дату
            LocalDate inputDate = inputFullDate.toLocalDate();
            //Конвертируем дату в текст
            String onlyDateAsString = CommonUtilites.convertDateToString(inputDate);
            String view = "Список задач на " + onlyDateAsString + ":\n";
            String bufferView = "";
            //Нужен отсортированный список задач, отвечающих условию - введенной дате
            TreeMap<LocalDateTime, Task> sortedDates = new TreeMap<>();
            //Заполним этот список
            for (Map.Entry<Integer, Task> t : taskMap.entrySet()) {
                Task bufferTask = t.getValue();
                for (int i = 0; i < bufferTask.datesList.size(); i++) {
                    //Здесь тоже надо из каждой задачи получить только дату этой задачи
                    //Это надо для фильтрации только по дате, без учета времени
                    LocalDateTime fullDateTimeTask = bufferTask.datesList.get(i);
                    //Из полной даты получаем только дату
                    LocalDate onlyDateTask = fullDateTimeTask.toLocalDate();
                    //Теперь пропускаем задачи через условия
                    if (onlyDateTask.isEqual(inputDate) && bufferTask.isActiveTask()) {
                        //И добавляем отфильтрованную задачу в список
                        sortedDates.put(bufferTask.datesList.get(i), bufferTask);
                    }
                }
            }
            //Мы получили список задач, отвечающих условию - введенной дате.
            //Сформируем из этого списка текст для просмотра.
            for (Map.Entry<LocalDateTime, Task> e : sortedDates.entrySet()) {
                //Получим время и дату задачи и конвертируем в строку
                String onlyTimeBuffer = CommonUtilites.convertFullDateToString(e.getKey());
                //Отбросим дату, оставим только время задачи
                String onlyTime = onlyTimeBuffer.substring(0, 5);
                //Добавим в текст полученные строки
                bufferView = bufferView + onlyTime + " - " + e.getValue().getHeader() + "\n";
            }
            if (bufferView.isEmpty()) {
                view = view + "На эту дату нет запланированных задач.";
            } else {
                view = view + bufferView;
            }
            return view;
        }

        public static void viewCurrentDayTask() {
            LocalDateTime bufferDate = LocalDateTime.now();
            if (TasksUtilites.taskMap.size() < 1) {
                System.out.println("Список запланированных задач пуст. Добавьте задачу.");
            } else {
                System.out.println(TasksUtilites.createTaskListByDate(bufferDate));
            }
        }

        public static void viewTasksByDate() {
            LocalDateTime bufferDate;
            String dateAsString;
            String fullDateAsString;
            if (TasksUtilites.taskMap.size() < 1) {
                System.out.println("Список запланированных задач пуст. Добавьте задачу.");
            } else {
                dateAsString = CommonUtilites.inputString();
                //Выше получили только дату, но без времени.
                //Вводить удобнее только дату, поэтому время сами добавим к тексту.
                //А текст конвертируем в полную дату и время.
                fullDateAsString = "00:00" + " " + dateAsString;
                bufferDate = CommonUtilites.convertStringToFullDate(fullDateAsString);
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

        public static LinkedList<LocalDateTime> addDatesToList(LinkedList<LocalDateTime> datesList, Periodicity periodicity) {
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
