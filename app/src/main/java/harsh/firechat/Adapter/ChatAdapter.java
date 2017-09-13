package harsh.firechat.Adapter;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


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


    private Context context;
    private LayoutInflater layoutInflater;


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
    public void onBindViewHolder(final ChatAdapter.ViewHolder holder, final int position) {

        final ChatModel model = chatlist.get(position);
        holder.tvname.setText(model.getName());
        holder.tvphone.setText(model.getPhone());

        Glide.with(holder.imageView.getContext())
                .load(model.getPhoto())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails.chatWith = model.getKey();
                context = view.getContext();
                Intent intent = new Intent(context, Chat.class);
                context.startActivity(intent);
            }
        });

        //Show profile image in Dialogbox
      /*  holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatAdapter.this.context);
                final Dialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.activity_image_layout, null);
                dialog.setContentView(dialoglayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        ImageView profilepic = (ImageView) dialog.findViewById(R.id.iv_image_View);
                        Glide.with(context)
                                .load(model.getPhoto())
                                .dontAnimate()
                                .into(profilepic);


                    }
                });

            }
        });*/

        //Delete users on long press on itemview
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(ChatAdapter.this.context)
                        .canceledOnTouchOutside(false)
                        .content("You want to delete this Name ?")
                        .positiveText("Yes")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FirebaseDatabase.getInstance().getReference("users").child(model.getKey()).removeValue();

                                Toast.makeText(context, "One user is deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvname, tvphone;
        public ImageView imageView, profilepicview;


        public ViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tv_name);
            tvphone = (TextView) itemView.findViewById(R.id.tv_phone);
            imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            //   profilepicview = (ImageView) itemView.findViewById(R.id.iv_image_View);


        }
    }
}
