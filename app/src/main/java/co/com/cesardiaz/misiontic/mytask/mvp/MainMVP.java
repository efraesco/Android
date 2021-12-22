package co.com.cesardiaz.misiontic.mytask.mvp;

import java.util.List;

import co.com.cesardiaz.misiontic.mytask.view.dto.TaskItem;

public interface MainMVP {

    // Las interfaces son solamente contratos

    interface Model {
        // Que es lo que tiene que hacer el modelo de la pantalla Main
        List<TaskItem> getTasks();


        void saveTask(TaskItem task);

        void updateTask(TaskItem item);

        void deleteTask(TaskItem task);
    }

    interface Presenter {
        // Que es lo que tiene que hacer el presentador (funciones) para la pantalla Main
        // Primer evento : cargar las tareas
        void loadTasks();

        // Adicionar el evento addNewTask
        void addNewTask();

        void taskItemClicked(TaskItem item);

        void updateTask(TaskItem task);

        void taskItemLongClicked(TaskItem task);

        void deleteTask(TaskItem task);
    }

    interface View {
        // Que funciones / métodos va a tener la vista para la pantalla Main
        // La vista es la MainActivity

        void showTaskList(List<TaskItem> items);

        String getTaskDescription();

        void addTaskToList(TaskItem task);

        void updateTask(TaskItem item);

        void showConfirmDialog(String message, TaskItem task);

        void showDeleteDialog(String message, TaskItem task);

        void deleteTask(TaskItem task);
    }


}
