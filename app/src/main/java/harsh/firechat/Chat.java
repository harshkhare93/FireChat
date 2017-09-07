package harsh.firechat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

import harsh.firechat.Adapter.ChatAdapter;
import harsh.firechat.Model.ChatModel;


public class Chat extends AppCompatActivity {

    private LinearLayout layout;
    private RelativeLayout layout_2;
    private ImageView sendButton;
    private EditText messageArea;
    private ScrollView scrollView;
    private FirebaseDatabase database;
    private DatabaseReference reference1, reference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.backarrow);
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference1 = database.getReference("users" + UserDetails.username + "_" + UserDetails.chatWith);
//        DatabaseReference reference2 = database.getReference("users" + UserDetails.chatWith + "_" + UserDetails.username);
        reference1 = database.getReference().child("users" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = database.getReference().child("users" + UserDetails.chatWith + "_" + UserDetails.username);
        Log.e("####################", "onCreate: "+UserDetails.username);
        Log.e("####################", "onCreate: "+UserDetails.chatWith);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("message", messageText);
                    map.put("users", UserDetails.username);
                    reference1.push().setValue(map);
                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put("message", messageText);
                    map2.put("users", UserDetails.chatWith);
                    reference2.push().setValue(map2);
                    messageArea.setText("");
                }
            }
        });
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //  Map map = dataSnapshot.getValue(Map.class);
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);

                String message = map.get("message");
                String userName = map.get("users");

                if (userName.equals(UserDetails.username)) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        } else {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.layout.activity_users:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}