package com.apps.my_meal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Collections;
import java.util.List;

import butterknife.internal.Utils;

public class meals extends AppCompatActivity {
    //تم تعريف اوبجكت RecyclerView لغرض عمليه البحث يعمل بطريقه مشابهه للListView لكن بطريقه اجمل واحلى
    RecyclerView recyclerView;


    //تم خلق اري من الكلاس ابلود امج الذي يحفظ جميع المعلومات حول الطباق
    List<UploadImage> uploadImages;


    //هنا تم عمل اري ثانيه لغرض حفظ المعلومات بعد عمل البحث(الاطباق المفلترة)
    List<UploadImage> Meals_Search;

    //هنا تم عمل اري ثانيه لغرض حفظ المعلومات بعد عمل ترتيب(الاطباق المفلترة)
    List<UploadImage> Meals_Sort;

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

    //تم خلق شيرد رفرنس ليذكر التطبيق بحاله الترتيب
    SharedPreferences preferences;

    //تم خلق كويري لغرض وضع شرط جلب البيانات من قاعدة البيانات
    Query query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_meals);

        //هنا تم ربط جميع الكائنات بمصادرها
        recyclerView=findViewById(R.id.recyclerview);
        add_meals= findViewById(R.id.add_meals);
        titleMeals=findViewById(R.id.titleMeals);
        Bundle bundle = getIntent().getExtras();
        SearchMeals=findViewById(R.id.SearchMeals);




        //تم خلق باندل لغرض سحب النوع الرئيسي للاطباق ووضعه في السترنك الخاصه بنوع الطبق القادمة من واجهه انواع الاطباق
        meal_type=bundle.getString("meal_Type");
        titleMeals.setText(meal_type+" Meals");


        //تم خلق كويري لجلب الاطباق التابعه فقط للنوع المطلوب
        query=FirebaseDatabase.getInstance().getReference("uploads").orderByChild("meal_type").equalTo(meal_type);

        //تم خلق كرد منجر لوضع RecycleView بتنسيق ملائم وجميل
        GridLayoutManager gridLayoutManager=new GridLayoutManager(meals.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);


        uploadImages=new ArrayList<>();
        Meals_Search=new ArrayList<>();
        Meals_Sort=new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logos);

        preferences=this.getSharedPreferences("my_Pre",MODE_PRIVATE);



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

                Meals_Sort=uploadImages;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if(item.getItemId()==R.id.sorting){
            SortingDailog();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void SortingDailog() {


        //نشاء اري من الخيارات الترتيب لوضعها في الدايلوك
        String []OP={"Meals names ASC ...","Meals Names DES ...","Less Cocking Time ...","Less Calories ...","high Rating ..."};


        //تم تصميم دايلوك لاظهار خيارات الترتيب
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //تم وضع عنوان لهذا الدايلوك
        builder.setTitle("Sort By");
                //وضعه صوره مصغره تدل على الترتيب
        builder.setIcon(android.R.drawable.ic_menu_sort_by_size);

                //وضع اري في الدايلوك وانشاء انتر فيس
        builder.setItems(OP, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //هنا الwhich يعني اي اختيار ضغط اليوزر من بين قائمه الترتيب
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("Sort",which+"");
                            editor.apply();
                            Sorting();
                            AdpterItems adpterItems =new AdpterItems(meals.this,Meals_Sort);
                            recyclerView.setAdapter(adpterItems);





                    }
                });

            builder.create().show();


        Sorting();



    }

    public void Sorting(){
        String My_sort_setting=preferences.getString("Sort","1");
        Sorting  sort_model=new  Sorting();

        if (My_sort_setting.equals("1")){
            Collections.sort(Meals_Sort,sort_model.ASC_by_Title);

        }
        if (My_sort_setting.equals("2")){
            Collections.sort(Meals_Sort,sort_model.DEC_by_Title);
        }

        if (My_sort_setting.equals("3")){
            Collections.sort(Meals_Sort,sort_model.Less_Cocking_Time);
        }

        if (My_sort_setting.equals("4")){
            Collections.sort(Meals_Sort,sort_model.Less_Cocking_Time);
        }

        if (My_sort_setting.equals("5")){
            Collections.sort(Meals_Sort,sort_model.High_Rating);
        }
    }
}
