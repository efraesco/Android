package co.com.cesardiaz.misiontic.mytask.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import co.com.cesardiaz.misiontic.mytask.R;
import co.com.cesardiaz.misiontic.mytask.mvp.MainMVP;
import co.com.cesardiaz.misiontic.mytask.presenter.MainPresenter;
import co.com.cesardiaz.misiontic.mytask.view.adapter.TaskAdapter;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskItem;

// Implementa el método de la vista
// va a representar a la vista de la MVP
// -- public class MainActivity extends AppCompatActivity {
public class MainActivity extends AppCompatActivity implements MainMVP.View {

    // Desde la vista solicito ejecutar las tareas en el presentador

    private TextInputLayout tilNewTask;
    private TextInputEditText etNewTask;
    private RecyclerView rvTasks;

    private TaskAdapter taskAdapter;

    // Crear una variable ManiMVP.Presenter
    private MainMVP.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Al inicializar la clase inicializo el presentador
        // Nombre de la clase.this
        presenter = new MainPresenter(MainActivity.this);

        initUI();

        // Despues de cargar la interfaz gráfica le indico al presentador que cargue las tareas
        presenter.loadTasks();
    }

    private void initUI() {
        tilNewTask = findViewById(R.id.til_new_task);

        // Adiciona el evento addNewTask() usando Lambda
        tilNewTask.setEndIconOnClickListener(v -> presenter.addNewTask());

        // --- Codigo anterior ---
        //{
        //    Toast.makeText(MainActivity.this, "Add new task to list", Toast.LENGTH_SHORT)
        //            .show();
        //});

        etNewTask = findViewById(R.id.et_new_task);

        taskAdapter = new TaskAdapter();

        // Se cambia setListener por setClickListener
        taskAdapter.setClickListener(item -> presenter.taskItemClicked(item));
        taskAdapter.setLongClickListener(item -> presenter.taskItemLongClicked(item));

        rvTasks = findViewById(R.id.rv_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvTasks.setAdapter(taskAdapter);
    }

    @Override
    public void showTaskList(List<TaskItem> items) {
        // LLamado al adaptor de la lista con los items
        taskAdapter.setData(items);


    }

    @Override
    public String getTaskDescription() {
        return etNewTask.getText().toString();
    }

    @Override
    public void addTaskToList(TaskItem task) {
        taskAdapter.addItem(task);
        etNewTask.getText().clear();


    }

    @Override
    public void updateTask(TaskItem item) {
        taskAdapter.updateTask(item);
    }

    @Override
    public void showConfirmDialog(String message, TaskItem task) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Tarea elegida")
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which)  -> presenter.updateTask(task))
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void showDeleteDialog(String message, TaskItem task) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Tarea elegida")
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which)  -> presenter.deleteTask(task))
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void deleteTask(TaskItem task) {
        taskAdapter.removeTask(task);

    }
}