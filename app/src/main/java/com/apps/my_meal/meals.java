package com.apps.my_meal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class meals extends AppCompatActivity {
    RecyclerView recyclerView;
    List <FoodData> foodData;
    FoodData nfoodData;

    List<UploadImage> uploadImages;
    UploadImage nUploadImage;

    TextView titleMeals;


    DatabaseReference databaseReference;
    FloatingActionButton add_meals ;
    //هنا تم خلق سترنك لستقبال نوع الطبق الرئيسي من واجهه انواع الاطباق لغرض جلب فقط الاطباق التي تنتمي لهذا النوع
    private String meal_type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_meals);
        recyclerView=findViewById(R.id.recyclerview);
        add_meals= findViewById(R.id.add_meals);
        titleMeals=findViewById(R.id.titleMeals);
        Bundle bundle = getIntent().getExtras();


        //تم خلق باندل لغرض سحب النوع ووضعه في السترنك الخاصه بنوع الطبق القادمة من واجهه انواع الاطباق
        meal_type=bundle.getString("meal_Type");

        titleMeals.setText(meal_type+" Meals");

        //تم خلق كويري لجلب الاطباق التابعه فقط للنوع المطلوب
        Query query=FirebaseDatabase.getInstance().getReference("uploads").orderByChild("meal_type").equalTo(meal_type);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(meals.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        uploadImages=new ArrayList<>();
        foodData=new ArrayList<>();



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploadImages.clear();
                for (DataSnapshot post:dataSnapshot.getChildren()){
                    nUploadImage=post.getValue(UploadImage.class);
                    uploadImages.add(nUploadImage);

                }
                AdpterItems adpterItems =new AdpterItems(meals.this,uploadImages);
                recyclerView.setAdapter(adpterItems);

                Log.d("meals type sending", "onDataChange: "+meal_type);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //هنا تم استقبال نوع الرئيسي من الماكولات على سبيل المثال دجاج او لحوم او خضار بستخدامgetExtras و setExtras
        Bundle extras = getIntent().getExtras();
        Log.d("MEALS____MEALS", "onClick: "+extras.getString("id"));




        //هنا تم تشغيل اضافة وصفه طعام جديده بستخدام FloatingActionButton
        add_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //    .setAction("Action", null).show();
                //هنا الانتقال الى واجهه اضافه وجبه جديدة

                Intent intent=new Intent(meals.this,ShowImages.class);
                startActivity(intent);


            }
        });

    }

   /* public void imagesgo(View view){
        String mealname=nameM.getText().toString().trim();
        String id =databaseReference.push().getKey();
        infomeals inm=new infomeals(id,mealname);
        databaseReference.child(id).setValue(inm);




    }*/
}
