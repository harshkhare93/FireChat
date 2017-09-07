package harsh.firechat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;


import harsh.firechat.Chat;
import harsh.firechat.Model.ChatModel;
import harsh.firechat.R;
import harsh.firechat.UserDetails;
import harsh.firechat.Users;

/**
 * Created by ngamacmini17 on 01/09/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatModel> chatlist;


    public ChatAdapter(List<ChatModel> chatModels) {
        this.chatlist = chatModels;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_users_recycler_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, final int position) {
        final ChatModel model = chatlist.get(position);
        holder.tvname.setText(model.getName());
        holder.tvphone.setText(model.getPhone());
        Glide.with(holder.imageView.getContext())
                .load(model.getPhoto())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               UserDetails.chatWith= model.getKey();
                Context context = view.getContext();
                Intent intent = new Intent(context, Chat.class);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvname, tvphone;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tv_name);
            tvphone = (TextView) itemView.findViewById(R.id.tv_phone);
            imageView = (ImageView) itemView.findViewById(R.id.ivImage);


        }
    }
}
