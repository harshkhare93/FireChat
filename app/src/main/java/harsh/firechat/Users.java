package harsh.firechat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import harsh.firechat.Adapter.ChatAdapter;
import harsh.firechat.Model.ChatModel;
import io.github.tonnyl.light.Light;

/**
 * Created by ngamacmini17 on 23/08/17.
 */

public class Users extends AppCompatActivity {

//   private ListView usersList;

    private TextView noUsersText;
    private ProgressDialog pd;
    //   ArrayList<String> al = new ArrayList<>();
    private long totalUsers = 0;
    private FirebaseAuth mAuth;

    //---------------------------------------Recycler view Code
    private RecyclerView recyclerView;
    private ArrayList<ChatModel> chatlist;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChatAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        //   usersList = (ListView) findViewById(R.id.usersList);

        noUsersText = (TextView) findViewById(R.id.noUsersText);
        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

//        //------------------------------------------------------------------showing data from firebase using List view-----------------------------------------------------------
//       String url = "https://fir-cloudmessaging-e7d84.firebaseio.com/users.json";
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                doOnSuccess(s);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(" " + error);
//            }
//        });
//        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
//        rQueue.add(request);
//        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                UserDetails.chatWith = al.get(position);
//                startActivity(new Intent(Users.this, Chat.class));
//            }
//        });
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        //Creating a Blank list in Memory
        chatlist = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new ChatAdapter(chatlist);
        recyclerView.setAdapter(adapter);


        //Setup listener
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatlist.clear();
                int position = 0;

                 if (dataSnapshot.hasChildren()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = (String) snapshot.getKey();
                        if(!name.equals(UserDetails.username)){
                            chatlist.add(new ChatModel(snapshot));
                            adapter.notifyDataSetChanged();
                            adapter.notifyItemInserted(position);
                            position++;
                        }
                    }
                    //  Toast.makeText(Users.this, "Data Loaded Succesfully", Toast.LENGTH_SHORT).show();
                    ChatAdapter adapter = new ChatAdapter(chatlist);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(Users.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//---------list view -------------------------------------------------------------------------------------------------------------------------------------------------------------
//    public void doOnSuccess(String s) {
//        try {
//            JSONObject obj = new JSONObject(s);
//            Iterator i = obj.keys();
//            String key = "";
//            while (i.hasNext()) {
//                key = i.next().toString();
//                if (!key.equals(UserDetails.username)) {
//                    al.add(key);
//                }
//                totalUsers++;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (totalUsers <= 1) {
//            noUsersText.setVisibility(View.VISIBLE);
//            usersList.setVisibility(View.GONE);
//        } else {
//            noUsersText.setVisibility(View.GONE);
//            usersList.setVisibility(View.VISIBLE);
//            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
//
//        }
//        pd.dismiss();
//    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout) {
            // TODO: 29/08/17 performing logout operation
            mAuth.signOut();
            Intent lgtIntent = new Intent(Users.this, LoginActivity.class);
            startActivity(lgtIntent);
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else if (id == R.id.menu_view_profile) {
            Intent viewprofile = new Intent(Users.this, Profile.class);
            startActivity(viewprofile);
        }


        return super.onOptionsItemSelected(item);
    }
}
