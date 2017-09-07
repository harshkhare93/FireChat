package harsh.firechat.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import harsh.firechat.UserDetails;

import static harsh.firechat.UserDetails.username;

/**
 * Created by ngamacmini17 on 01/09/17.
 */


public class ChatModel {
    String name;
    String phone;
    String photo;
    String key;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;


    public ChatModel() {
    }

    public ChatModel(DataSnapshot snapshot) {
//            mdatabase = FirebaseDatabase.getInstance();
//            mReference = mdatabase.getReference("users");
        this.key = snapshot.getKey();
        this.name = snapshot.child("Full Name").getValue(String.class);
        this.phone = snapshot.child("Mobile Number").getValue(String.class);
        this.photo = (String) snapshot.child("photoUrl").getValue();

    }

//    public ChatModel(String name, String phone, String photo) {
//        this.name = name;
//        this.phone = phone;
//        this.photo = photo;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

