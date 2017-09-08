package harsh.firechat.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.Date;

/**
 * Created by ngamacmini17 on 01/09/17.
 */


public class ChatModel {
    String name;
    String phone;
    String photo;
    String key;


    private long messageTime;


    public ChatModel() {
    }


    public ChatModel(DataSnapshot snapshot) {
        this.key = snapshot.getKey();
        this.name = snapshot.child("Full Name").getValue(String.class);
        this.phone = snapshot.child("Mobile Number").getValue(String.class);

        this.photo = (String) snapshot.child("photoUrl").getValue();
        // Initialize to current time

    }


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

