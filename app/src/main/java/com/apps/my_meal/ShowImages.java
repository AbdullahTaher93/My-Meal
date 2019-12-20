package com.apps.my_meal;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ShowImages extends AppCompatActivity {
    Button chooseImage,btnUploadImage,viewGarlley;
    EditText imgDescrption;
    ImageView imagePreview;
    Uri imgUrl;
    ProgressBar progressBar;
    private  int PICK_IMAGE_REQUEST=1;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimage);
        chooseImage=findViewById(R.id.brows);
        btnUploadImage=findViewById(R.id.upload);
        viewGarlley=findViewById(R.id.show);
        imgDescrption=findViewById(R.id.NAME);
        imagePreview=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressBar2);

        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference=FirebaseDatabase.getInstance().getReference("uploads");








        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();

            }


        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 uploadfile();

            }
        });

        viewGarlley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowImages.this,ViewImageActivity.class);
                startActivity(intent);

            }
        });


    }
    private void OpenFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && data.getData()!=null && resultCode==RESULT_OK&& data!=null){
            imgUrl=data.getData();
            Log.d("IMAGE", "onActivityResult: "+imgUrl);
            Picasso.get().load(imgUrl).into(imagePreview);



        }
    }
    private String getfileEx(Uri uri){
        ContentResolver CR=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(CR.getType(uri));
    }
    public void uploadfile(){

        if (imgUrl!=null){
            StorageReference SR=storageReference.child(System.currentTimeMillis()+"."+getfileEx(imgUrl));
            SR.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        progressBar.setProgress(0);
                        }
                    },500);
                    SR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UploadImage uploadImage=new UploadImage(imgDescrption.getText().toString().trim(),uri.toString());
                            String ID=databaseReference.push().getKey();
                            databaseReference.child(ID).setValue(uploadImage);

                        }
                    });





                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);

                }
            });

        }else Toast.makeText(this,"No file selection",Toast.LENGTH_LONG).show();

    }
}
