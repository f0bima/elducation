package com.example.elducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class forumpostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button pilihimg, upload;
    EditText judul, pertanyaan;
    ImageView imageView;
    ProgressBar progressBar;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri imguri;
    private String userid;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumpost);

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getUid();

        pilihimg = findViewById(R.id.pilih_img);
        upload = findViewById(R.id.upload);

        judul = findViewById(R.id.judul);
        pertanyaan = findViewById(R.id.pertanyaan);
        imageView = findViewById(R.id.img);
        progressBar = findViewById(R.id.progress_bar);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("forum");

        pilihimg.setOnClickListener(v -> clickpilihimg());
        upload.setOnClickListener(v -> clickupload());

    }

    private void clickpilihimg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imguri = data.getData();
            Glide.with(this).load(imguri).into(imageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void clickupload(){
        if (judul.getText().toString().equals("") || pertanyaan.getText().toString().equals("")){
            Toast.makeText(this, "Judul dan Pertanyaan tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            long timestamp = System.currentTimeMillis();
            if (imguri != null) {
                String namefile = timestamp + "." + getFileExtension(imguri);
                StorageReference filereference = storageReference.child(namefile);
                filereference.putFile(imguri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(0);
                                    }
                                }, 5000);
//                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
//                            while(!uri.isComplete());
//                            Uri url = uri.getResult();
//                            Uri url = uri.getResult();
                                Toast.makeText(forumpostActivity.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                                String url = "https://firebasestorage.googleapis.com/v0/b/elducation-d878a.appspot.com/o/uploads%2F" + namefile + "?alt=media";

//                            Upload upload = new Upload(editText.getText().toString().trim(), url);
//                            Upload upload = new Upload(editText.getText().toString().trim(), url.toString().substring(0,uri.toString().indexOf("&token")));
//                            String uploadID = databaseReference.push().getKey();


                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("imgpub", mAuth.getCurrentUser().getPhotoUrl().toString());
                                hashMap.put("img", url);
                                hashMap.put("status", false);
                                hashMap.put("nama", mAuth.getCurrentUser().getDisplayName());
                                hashMap.put("judul", judul.getText().toString().trim());
                                hashMap.put("tgl", timestamp);
                                hashMap.put("pertanyaan", pertanyaan.getText().toString().trim());
                                hashMap.put("publisher", userid);

                                databaseReference.push().setValue(hashMap);
//                            databaseReference.push().setValue(upload);

                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(forumpostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressBar.setProgress((int) progress);
                            }
                        });
            } else {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                String url = "https://firebasestorage.googleapis.com/v0/b/elducation-d878a.appspot.com/o/uploads%2Fno-photo.png?alt=media";
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("imgpub", mAuth.getCurrentUser().getPhotoUrl().toString());
                hashMap.put("img", url);
                hashMap.put("status", false);
                hashMap.put("nama", mAuth.getCurrentUser().getDisplayName());
                hashMap.put("judul", judul.getText().toString().trim());
                hashMap.put("tgl", timestamp);
                hashMap.put("pertanyaan", pertanyaan.getText().toString().trim());
                hashMap.put("publisher", userid);

                databaseReference.push().setValue(hashMap);
                finish();

            }
        }
    }
}
