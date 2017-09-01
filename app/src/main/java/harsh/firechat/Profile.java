package harsh.firechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {

    private ImageView imageview;

    private String photoUrl ;
    private TextView tvname;
    private TextView tvphone;
    private String name;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageview = (ImageView) findViewById(R.id.iv_Profile_View);
        tvname = (TextView) findViewById(R.id.tv_name);
        tvphone = (TextView) findViewById(R.id.tv_phone);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference().child("users").child(UserDetails.username);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                photoUrl = (String) dataSnapshot.child("photoUrl").getValue();
                name = dataSnapshot.child("Full Name").getValue(String.class);
                phone =dataSnapshot.child("Mobile Number").getValue(String.class);


                fetchImage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchImage() {
        Glide.with(Profile.this).load(photoUrl).placeholder(R.drawable.user3).dontAnimate().into(imageview);
    }
}

