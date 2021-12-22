package co.com.cesardiaz.misiontic.mytask.presenter;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

import co.com.cesardiaz.misiontic.mytask.model.MainInteractor;
import co.com.cesardiaz.misiontic.mytask.mvp.MainMVP;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskItem;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskState;

public class MainPresenter implements MainMVP.Presenter {

    private final MainMVP.Model model;
    private final MainMVP.View view;

    public MainPresenter(MainMVP.View view) {
        // Inicializar la vista
        this.view = view;

        // MainInteractor implementa el modelo
        model = new MainInteractor();

    }

    @Override
    public void loadTasks() {
        // Recibe una lista de tareas
        List<TaskItem> items = model.getTasks();

        // Indicar a la vista que debe cargar las tareas
        view.showTaskList(items);

    }

    @Override
    public void addNewTask() {
        Log.i(MainPresenter.class.getSimpleName(),"Add new Task");
        String description = view.getTaskDescription();

        // SimpleDateFormat utilidad de Java que es un formateador de fechas, que permite convertir fechas a cadena y viceversa.
        String date = SimpleDateFormat.getDateTimeInstance().format(new Date());
        TaskItem task= new TaskItem(description, date);
        model.saveTask(task);
        view.addTaskToList(task);

    }

    @Override
    public void taskItemClicked(TaskItem task) {
        String message = task.getState() == TaskState.PENDING ? "Desea marcar como terminada esta tarea?" : "Desea marcar como pendiente esta tarea?";
        view.showConfirmDialog(message,task);
    }

    @Override
    public void updateTask(TaskItem task) {
        task.setState(task.getState() == TaskState.PENDING ? TaskState.DONE : TaskState.PENDING );

        model.updateTask(task);
        view.updateTask(task);
    }

    @Override
    public void taskItemLongClicked(TaskItem task) {
        if (task.getState() == TaskState.DONE) {
            view.showDeleteDialog("Desea borrar esta tarea?", task);
        }
    }

    @Override
    public void deleteTask(TaskItem task) {
        model.deleteTask(task);
        view.deleteTask(task);

    }
}
