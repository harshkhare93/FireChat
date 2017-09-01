package harsh.firechat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by ngamacmini17 on 23/08/17.
 */

public class Register extends AppCompatActivity {

    public static final String SAVED_INSTANCE_URI = "uri";
    private TextView tvloginagain;
    private EditText username, password ,phone , fullname;
    String user, pass , contact ,fname;
    TextView login;
    private Button registerButton;
    private ImageView ivImage;
    private ImageButton camera;
    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    private String userChoosenTask;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Uri filepath;
    private Uri downloadUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phone    = (EditText) findViewById(R.id.phone_number);
        fullname    = (EditText) findViewById(R.id.fullname);


        registerButton = (Button) findViewById(R.id.registerButton);
        ivImage = (ImageView) findViewById(R.id.ivimg);
        camera = (ImageButton) findViewById(R.id.selectImage);
        tvloginagain = (TextView) findViewById(R.id.loginback);
        tvloginagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginbackIntent = new Intent(Register.this, LoginActivity.class);
                startActivity(loginbackIntent);
            }
        });
        //selecting Images from device
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        if (savedInstanceState != null) {
            filepath = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));

        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                contact=phone.getText().toString();
                fname=fullname.getText().toString();
                if (user.equals("")) {
                    username.setError("can't be blank");
                }
                else if (pass.equals("")) {
                    password.setError("can't be blank");
                }
                else if (contact.equals("")){
                    phone.setError("can't be blank");
                }
                else if (!user.matches("[A-Za-z0-9]+")) {
                    username.setError("only alphabet or number allowed");
                }
                else if (user.length() < 5) {
                    username.setError("at least 5 characters long");
                }
                else if (contact.length() < 10){
                    phone.setError("at least 10 digits long");
                }
                else {
                    if (pass.length() < 5) {
                        password.setError("at least 5 characters long");
                    }
                    else {
                        final ProgressDialog pd = new ProgressDialog(Register.this);
                        pd.setMessage("Loading...");
                        pd.show();

                        String url = "https://fir-cloudmessaging-e7d84.firebaseio.com/users.json";

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String s) {
                                //   Firebase reference = new Firebase("https://fir-cloudmessaging-e7d84.firebaseio.com/users");
                                database = FirebaseDatabase.getInstance();
                                reference = database.getReference("users");
                                mStorageRef = FirebaseStorage.getInstance().getReference().child(contact);

                                ivImage.setDrawingCacheEnabled(true);
                                ivImage.buildDrawingCache();
                                Bitmap bitmap = ivImage.getDrawingCache();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                UploadTask uploadTask = mStorageRef.putFile(filepath);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //if the upload is not successfull
                                        //hiding the progress dialog
                                        pd.dismiss();
                                        //and displaying error message
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //if the upload is successfull
                                        //hiding the progress dialog
                                        pd.dismiss();
                                        //and displaying a success toast
                                      //  Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                                        downloadUrl = taskSnapshot.getDownloadUrl();

                                        if (s.equals("null")) {
                                            reference.child(user).child("password").setValue(pass);
                                            reference.child(user).child("Mobile Number").setValue(contact);
                                            reference.child(user).child("Full Name").setValue(fname);
                                            reference.child(user).child("photoUrl").setValue(downloadUrl.toString());//uploading photo database url in database
                                            mStorageRef.child(contact);
                                            selectImage();
                                            Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                        } else {
                                            try {
                                                JSONObject obj = new JSONObject(s);

                                                if (!obj.has(user)) {
                                                    reference.child(user).child("password").setValue(pass);
                                                    reference.child(user).child("Full Name").setValue(fname);
                                                    reference.child(user).child("Mobile Number").setValue(contact);
                                                    reference.child(user).child("photoUrl").setValue(downloadUrl.toString());//uploading photo database url in database
                                                    mStorageRef.child(contact);
                                                    //   selectImage();
                                                    Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(Register.this, "username already exists", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });



                                pd.dismiss();
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                System.out.println("" + volleyError);
                                pd.dismiss();
                            }
                        });

                        RequestQueue rQueue = Volley.newRequestQueue(Register.this);
                        rQueue.add(request);
                    }
                }
            }
        });

    }


    //-----------------------------------------------------------------------Getting Image from Camera And Gallery--------------------------------------------------------------------
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        filepath = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filepath);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        filepath = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filepath);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                ivImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CAMERA)
            onCaptureImageResult(data);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivImage.setImageBitmap(thumbnail);
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
    }
}
