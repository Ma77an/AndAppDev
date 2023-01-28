package com.example.Class4Demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;
import com.example.Class4Demo.model.Student;
import com.squareup.picasso.Picasso;

import java.util.List;

class PostViewHolder extends RecyclerView.ViewHolder {

    Student st;
    ImageView userImage;
    TextView userName;

    Boolean isImage;
    ImageView postImage;
    TextView postTitle;
    TextView postDesc;

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.onItemClickListener listener, List<Post> data) {
        super(itemView);

        userImage = itemView.findViewById(R.id.student_image);
        userName = itemView.findViewById(R.id.student_name_tv);

        postImage = itemView.findViewById(R.id.post_image);
        postTitle = itemView.findViewById(R.id.post_title_tv);
        postDesc = itemView.findViewById(R.id.post_description_tv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Post pst, int pos) {
        Model.instance().getStudentById(pst.getUid(), stu -> {
            if (stu != null) {
                userName.setText(stu.getName());
                if (!stu.getAvatar().equals("")) {
                    Picasso.get().load(stu.getAvatar()).placeholder(R.drawable.avatar).into(userImage);
                } else {
                    userImage.setImageResource(R.drawable.avatar);
                }
            }
            if (!pst.getPhotoUrl().equals("")) {
                Picasso.get().load(pst.getPhotoUrl()).placeholder(android.R.color.transparent).into(postImage);
            } else {
                postImage.setImageResource(0);
            }
            postTitle.setText(pst.getTitle());
            postDesc.setText(pst.getDesc());
        });

    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    onItemClickListener listener;

    public static interface onItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
    }


    void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_row, parent, false);
        return new PostViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post pst = data.get(position);
        holder.bind(pst, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}