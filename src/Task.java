import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Task {

    //Поля, обязательные к заполнению
    //Если эти поля незаполнены или заполнены некорректно, то выброс исключения
    //Заголовок задачи
    String header;
    //Описание задачи
    StringBuilder descriptionTask = new StringBuilder();

    //Повторяемость (м.б. - однократная, ежедневная, еженедельная, ежемесячная, ежегодная)
    String repeatability;
    //Тип задачи, от него зависят признаки - личная/публичная
    String typeTask;


    //Признак - публичная задача
    boolean typeTaskIsPublic = true;
    //Признак - личная задача
    boolean typeTaskIsPrivate = false;

    //Поля, необязательные к заполнению
    //м.б. эти поля можно заполнять/редактировать потом?
    //Время создания задачи
    LocalDateTime timeCreateTask;
    //Дата создания задачи
    LocalDateTime dateCreateTask;

    int ID_number;

    //ID задачи - создается автоматически, новая задача -новый ID
    static int idTask = 1;

    public Task(String header, StringBuilder descriptionTask, String repeatability, String typeTask) {
        this.header = header;
        this.descriptionTask = descriptionTask;
        this.repeatability = repeatability;
        this.typeTask = typeTask;
        ID_number = idTask;
        idTask++;
    }

    //Метод для получения даты и времени следующего выполнения задачи

    public static class TasksUtilites{

        Map<Integer, Task> taskMap = new HashMap<>();

    }

}
