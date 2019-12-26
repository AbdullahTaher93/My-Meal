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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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
// هنا تم تعريف سبنر من اجل جعل المستخدم يختار خيارات محددة لنوع الطبق الرئيسي
    private Spinner meal_type;
//تم تعريف مربع حوار لادخال اسم الطبق  الجديدة و اخر لوصفها وطريقة تحضيرها واخر لوقت المستغرق للطبخ واخر لعدد السعرات الحرارية فيها
    EditText meal_name,meal_des,cocking_time,meal_calories;
    // تم تعريف صورة لوضع صورة للطبق
    ImageView imagePreview;

    //تم تعريف رابط لسحب وحفظ الصورة في الداتا بيس
    Uri imgUrl;
    // شريط تحميل يعمل على اظهار مدة التحميل
    ProgressBar progressBar;
    private  int PICK_IMAGE_REQUEST=1;
    // هنا قمنا باخذ يوزر ايدي من اجل حفظه مع الطبق لمعرفه من قام بضافه هذا الطبق
    private String USER_ID;

//تم تعريف ستورج الخاص بالفايربيس لحفظ الصور
    private StorageReference storageReference;
    //تم تعريف داتة بيس خاصه بالفايبريس
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logos);

        btnUploadImage=findViewById(R.id.upload);


        USER_ID =MainActivity.USER_ID;

        Toast.makeText(this,USER_ID,Toast.LENGTH_LONG).show();


        meal_name=findViewById(R.id.meal_name);
        meal_des=findViewById(R.id.meals_desc);
        cocking_time=findViewById(R.id.cocking_time);
        meal_calories=findViewById(R.id.meals_calo);


        imagePreview=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressBar2);
        meal_type=findViewById(R.id.meal_type);


        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference=FirebaseDatabase.getInstance().getReference("uploads");

        imagePreview.setOnClickListener(new View.OnClickListener() {
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


        if(requestCode==1  && resultCode==RESULT_OK && data!=null){
            imgUrl=data.getData();
            Picasso.get().load(imgUrl).into(imagePreview);
        }else{
            Log.d("", "onActivityResult: ");
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
                            //UploadImage uploadImage=new UploadImage(meal_name.getText().toString().trim(),uri.toString());
                            String Meal_ID=databaseReference.push().getKey();
                            UploadImage uploadImage=new UploadImage(MainActivity.USER_ID.trim(),Meal_ID.trim(),uri.toString(),meal_name.getText().toString().trim(),meal_des.getText().toString().trim(),meal_type.getSelectedItem().toString().trim(),Integer.parseInt(cocking_time.getText().toString().trim()),Integer.parseInt(meal_calories.getText().toString().trim()),0,0);

                            databaseReference.child(Meal_ID).setValue(uploadImage);




                            //العودة الى قائمة الاطباق
                            Intent intent=new Intent(ShowImages.this,meals.class);
                            intent.putExtra("meal_Type",meal_type.getSelectedItem().toString());

                            meal_name.setText("");
                            meal_des.setText("");
                            cocking_time.setText("");
                            meal_calories.setText("");
                            imagePreview.setImageResource(R.drawable.preview);


                            startActivity(intent);

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
