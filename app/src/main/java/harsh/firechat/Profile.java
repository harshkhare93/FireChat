package harsh.firechat;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import static harsh.firechat.R.id.username;

public class Profile extends AppCompatActivity {

    private ImageView imageview;
    private Task<Uri> pathReference;
    private String generatedFilePath;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageview = (ImageView) findViewById(R.id.iv_Profile_View);


      //  fetchImage();

    }

//    private void fetchImage() {
//        FirebaseStorage storage=FirebaseStorage.getInstance();
//        storageRef = storage.getReference(String.valueOf(username));
//        pathReference = storageRef.child("Mobile Number").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                //Got the url
//                StorageReference FileDownloadTask;
//                com.google.firebase.storage.FileDownloadTask.TaskSnapshot taskSnapshot = null;
//                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
//                generatedFilePath = downloadUri.toString();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Profile.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //Load Image
//        Glide.with(this)
//                .using(new FirebaseImageLoader())
//                .load()
//                .into(imageview);


   // }
}
