package harsh.firechat.Model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by ngamacmini17 on 01/09/17.
 */

public class ChatModel {
    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)

    public ChatModel(DataSnapshot snapshot) {
    }

    public ChatModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
