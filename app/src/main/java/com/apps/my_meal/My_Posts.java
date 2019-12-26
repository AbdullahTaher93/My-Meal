package com.apps.my_meal;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class My_Posts extends AppCompatActivity {
    //تم خلق ابجكت للزر فلوت لغرض الانتقال الى واجهه اضافه طبق جديد
    FloatingActionButton add_meals ;

    Query query_getPostsUser;


    //تم تعريف اوبجكت RecyclerView لغرض عمليه البحث يعمل بطريقه مشابهه للListView لكن بطريقه اجمل واحلى
    RecyclerView recyclerView;


    //تم خلق اري من الكلاس ابلود امج الذي يحفظ جميع المعلومات حول الطباق
    List<UploadImage> uploadImages;





    //تم خلق اوبجكت من الكلاس ابلود امج لغرض التعامل مع طبق طبق ووضعه في الاري اعلاه لغرض ارسالهم الى الادبتر التي تقوم بالعرض في الRecycleView
    UploadImage nUploadImage;








    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_posts);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logos);



        //هنا تم ربط جميع الكائنات بمصادرها
        recyclerView=findViewById(R.id.recyclerview);
        add_meals= findViewById(R.id.add_meals);
        Bundle bundle = getIntent().getExtras();

        uploadImages=new ArrayList<>();

        //تم خلق كرد منجر لوضع RecycleView بتنسيق ملائم وجميل
        GridLayoutManager gridLayoutManager=new GridLayoutManager(My_Posts.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);


        Query databaseReference= FirebaseDatabase.getInstance().getReference("uploads").orderByChild("user_ID").equalTo(MainActivity.USER_ID);

        //هنا تم تشغيل اضافة وصفه طعام جديده بستخدام FloatingActionButton
        add_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //    .setAction("Action", null).show();
                //هنا الانتقال الى واجهه اضافه وجبه جديدة

                Intent intent=new Intent(My_Posts.this,ShowImages.class);
                startActivity(intent);


            }
        });

   databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           uploadImages.clear();

           for (DataSnapshot post:dataSnapshot.getChildren()){
               nUploadImage=post.getValue(UploadImage.class);
               uploadImages.add(nUploadImage);
           }
           MyPost_adpter myPost_adpter=new MyPost_adpter(My_Posts.this,uploadImages);
           recyclerView.setAdapter(myPost_adpter);
       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });




    }
    public void delete_meal(String Meal_ID){


        Query databaseReference=FirebaseDatabase.getInstance().getReference("uploads").orderByChild("user_ID").equalTo(MainActivity.USER_ID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploadImages.clear();

                for (DataSnapshot post:dataSnapshot.getChildren()){
                    nUploadImage=post.getValue(UploadImage.class);
                    uploadImages.add(nUploadImage);
                }
                MyPost_adpter myPost_adpter=new MyPost_adpter(My_Posts.this,uploadImages);
                recyclerView.setAdapter(myPost_adpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
