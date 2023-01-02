package com.example.Class4Demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Class4Demo.model.Student;

import java.util.List;

class StudentViewHolder extends RecyclerView.ViewHolder {
    TextView nameTv;
    TextView idTv;
    CheckBox cb;
    List<Student> data;

    public StudentViewHolder(@NonNull View itemView, StudentRecyclerAdapter.onItemClickListener listener, List<Student> data) {
        super(itemView);
        this.data = data;
        nameTv = itemView.findViewById(R.id.studentlistrow_name_tv);
        idTv = itemView.findViewById(R.id.studentlistrow_id_tv);
        cb = itemView.findViewById(R.id.studentlistrow_cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) cb.getTag();
                Student st = data.get(pos);
                st.setChecked(cb.isChecked());
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Student st, int pos) {
        nameTv.setText(st.getName());
        idTv.setText(st.getId());
        cb.setChecked(st.isChecked());
        cb.setTag(pos);
    }
}

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    onItemClickListener listener;

    public static interface onItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Student> data;

    public void setData(List<Student> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public StudentRecyclerAdapter(LayoutInflater inflater, List<Student> data) {
        this.inflater = inflater;
        this.data = data;
    }


    void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.student_list_row, parent, false);
        return new StudentViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student st = data.get(position);
        holder.bind(st, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
