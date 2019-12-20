package com.apps.my_meal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class hini extends AppCompatActivity {
    RecyclerView recyclerView;
    List <FoodData> foodData;
    FoodData nfoodData;
    Button add_bt;
    EditText nameM;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_meals);
        recyclerView=findViewById(R.id.recyclerview);
        add_bt=findViewById(R.id.addnewmeal);
        nameM=findViewById(R.id.nameM);
        databaseReference= FirebaseDatabase.getInstance().getReference("MealsInfo");


        GridLayoutManager gridLayoutManager=new GridLayoutManager(hini.this,1);
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
         AdpterItems adpterItems =new AdpterItems(hini.this,foodData);
        recyclerView.setAdapter(adpterItems);

        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newmeal();

            }
        });

    }
    public void newmeal(){
        String mealname=nameM.getText().toString().trim();
        String id =databaseReference.push().getKey();
        infomeals inm=new infomeals(id,mealname);
        databaseReference.child(id).setValue(inm);

    }

    public void imagesgo(View view){
        Intent intent=new Intent(hini.this,ShowImages.class);
        startActivity(intent);

    }
}
