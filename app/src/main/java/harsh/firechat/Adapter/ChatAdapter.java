package harsh.firechat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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
    List<ChatModel> chatModelsList;
     ArrayList<ChatModel> chatModels;
    private Context context;
    private long totalUsers = 0;

    public ChatAdapter(List<ChatModel> chatModelsList) {
        this.chatModelsList=chatModelsList;
    }


    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_users_recycler_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, final int position) {
        //Data Binding
        final ChatModel model = chatModelsList.get(position);
        holder.textViewName.setText(model.getName());
        Glide.with(holder.userImage.getContext())
                .load(model.getUrl()).into(holder.userImage);




    }



    @Override
    public int getItemCount() {
        return chatModelsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView userImage;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.tv_name);
            userImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}
