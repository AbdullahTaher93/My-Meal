package com.apps.my_meal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import butterknife.internal.Utils;

public class meals extends AppCompatActivity {
    //تم تعريف اوبجكت RecyclerView لغرض عمليه البحث يعمل بطريقه مشابهه للListView لكن بطريقه اجمل واحلى
    RecyclerView recyclerView;


    //تم خلق اري من الكلاس ابلود امج الذي يحفظ جميع المعلومات حول الطباق
    List<UploadImage> uploadImages;


    //هنا تم عمل اري ثانيه لغرض حفظ المعلومات بعد عمل البحث(الاطباق المفلترة)
    List<UploadImage> Meals_Search;

    //تم خلق اوبجكت من الكلاس ابلود امج لغرض التعامل مع طبق طبق ووضعه في الاري اعلاه لغرض ارسالهم الى الادبتر التي تقوم بالعرض في الRecycleView
    UploadImage nUploadImage;

    //تم خلق كائن من نوعTextView الخاص بالتايتل بغرض وضع اسم النوع الرئيسي لهذا الاطباق (لحم,دجادج ....الخ)
    TextView titleMeals;

    //تعريف اوبجكت من نوع EditText  الخاص بعميله البحث عن طريق اسم الطبق
    EditText SearchMeals;


   //تم خلق ابجكت للزر فلوت لغرض الانتقال الى واجهه اضافه طبق جديد
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
        SearchMeals=findViewById(R.id.SearchMeals);
        Query query;




        //تم خلق باندل لغرض سحب النوع ووضعه في السترنك الخاصه بنوع الطبق القادمة من واجهه انواع الاطباق
        meal_type=bundle.getString("meal_Type");

        titleMeals.setText(meal_type+" Meals");

        //تم خلق كويري لجلب الاطباق التابعه فقط للنوع المطلوب
        query=FirebaseDatabase.getInstance().getReference("uploads").orderByChild("meal_type").equalTo(meal_type);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(meals.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        uploadImages=new ArrayList<>();
        Meals_Search=new ArrayList<>();




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


        SearchMeals.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Meals_Search.addAll(uploadImages);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(TextUtils.isEmpty(SearchMeals.getText().toString())||SearchMeals.getText().length()==0){
                    Meals_Search.clear();
                    Meals_Search.addAll(uploadImages);
                }else {

                    Meals_Search.clear();
                    for (UploadImage meal:uploadImages) {

                        if (meal.getMeal_name().toLowerCase().contains(SearchMeals.getText().toString().toLowerCase())) {

                            Meals_Search.add(meal);


                        }
                    }



                }

                AdpterItems adpterItems =new AdpterItems(meals.this,Meals_Search);
                recyclerView.setAdapter(adpterItems);








            }

            @Override
            public void afterTextChanged(Editable s) {

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
