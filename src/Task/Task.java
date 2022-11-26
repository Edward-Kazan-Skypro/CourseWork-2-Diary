package Task;

import Enums.Periodicity;
import Utilites.CommonUtilites;

import java.time.LocalDateTime;
import java.util.*;

public class Task {

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
}
