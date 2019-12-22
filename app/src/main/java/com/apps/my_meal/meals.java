package com.apps.my_meal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class meals extends AppCompatActivity {
    RecyclerView recyclerView;
    List <FoodData> foodData;
    FoodData nfoodData;

    DatabaseReference databaseReference;
    FloatingActionButton add_meals ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_meals);
        recyclerView=findViewById(R.id.recyclerview);


        add_meals= findViewById(R.id.add_meals);
        databaseReference= FirebaseDatabase.getInstance().getReference("MealsInfo");
        GridLayoutManager gridLayoutManager=new GridLayoutManager(meals.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        foodData=new ArrayList<>();
        nfoodData=new FoodData("onq","aslkdjasdjaslkjdl",R.drawable.p1);
        foodData.add(nfoodData);
        nfoodData=new FoodData("onq","aslkdjasdjaslkjdl",R.drawable.p2);
        foodData.add(nfoodData);
        nfoodData=new FoodData("onq","aslkdjasdjaslkjdl",R.drawable.p3);
        foodData.add(nfoodData);
        nfoodData=new FoodData("onq","aslkdjasdjaslkjdl",R.drawable.p4);
        foodData.add(nfoodData);
        AdpterItems adpterItems =new AdpterItems(meals.this,foodData);
        recyclerView.setAdapter(adpterItems);


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
