package co.com.cesardiaz.misiontic.mytask.view.adapter;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.com.cesardiaz.misiontic.mytask.R;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskItem;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskState;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<TaskItem> data;

    // Atributo al adaptador que es el listener cuando escuche click
    private OnItemClickListener listener;
    private OnItemClickListener longListener;

    public TaskAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<TaskItem> data) {
        Log.i(TaskAdapter.class.getSimpleName(),"Set Data");
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(TaskItem item) {
        Log.i(TaskAdapter.class.getSimpleName(),"Add new item");
        data.add(item);
        notifyItemInserted(data.size() - 1);

    }

    public void setClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setLongClickListener(OnItemClickListener listener) {
        this.longListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskItem item = data.get(position);


        if(listener != null) {
            // A la vista de cada item cuando hagan click se le dice al listener que le hicieron click en un elemento
            holder.itemView.setOnClickListener(v -> listener.onClick(item));
        }

        if(longListener != null) {
            // A la vista de cada item cuando hagan click se le dice al listener que le hicieron click en un elemento
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onClick(item);
                    return false;
                }
            });
        }

        holder.tvDescription.setText(item.getDescription());
        holder.tvDate.setText(item.getDate());

        // Color de acuerdo al estado
        int color = item.getState() == TaskState.PENDING ? R.color.task_pending : R.color.task_done;

        // Cambio de color
        holder.ivIcon.setColorFilter(
                ContextCompat.getColor(holder.itemView.getContext(), color),
                android.graphics.PorterDuff.Mode.MULTIPLY);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void updateTask(TaskItem task) {
        // Segundo metodo de busqueda por hashCode
        int i = data.indexOf(task);
        TaskItem item= data.get(i);
        item.setState(task.getState());
        notifyItemChanged(i);


        // Recorrer la lista Primer método
        //for (int i=0; i < data.size(); i++ ) {
        //    TaskItem item= data.get(i);
        //    if (item.getDescription().equals(task.getDescription()) && item.getDate().equals(task.getDate())) {
        //        item.setState(task.getState());
        //        notifyItemChanged(i);
        //    }
        //}
    }

    public void removeTask(TaskItem task) {
        // Segundo metodo de busqueda por hashCode
        int i = data.indexOf(task);
        data.remove(i);
        notifyItemRemoved(i);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvDescription;
        private final TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

    public interface OnItemClickListener {
        // Agregar eventos a una lista
        // Cuando presione el boton
        void onClick(TaskItem item);



    }
}
