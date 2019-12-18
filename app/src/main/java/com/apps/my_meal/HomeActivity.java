
package com.apps.my_meal;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO 31 implement the HomeView interface at the end
public class HomeActivity extends AppCompatActivity implements HomeView {

    /*
     * TODO 32 Add @BindView Annotation (1)
     *
     * 1. viewPagerHeader
     * 2. recyclerCategory
     *
     */

    /*
     *  TODO 33 Create variable for presenter;
     */
    HomePresenter presenter;
@BindView(R.id.viewpagerheader) ViewPager  viewPagermeal;
@BindView(R.id.recylecatogires)
RecyclerView recyclerViewCategry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
       presenter=new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();
    }

    @Override
    public void showLoding() {
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoding() {
        findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);

    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
        ViewPagerHeaderAdapter headerAdapter=new ViewPagerHeaderAdapter(meal,this);
        viewPagermeal.setAdapter(headerAdapter);
        viewPagermeal.setPadding(20,0,150,0);
        headerAdapter.notifyDataSetChanged();


    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter recyclerViewHomeAdapter=new RecyclerViewHomeAdapter(category,this);
//        recyclerViewCategry.setAdapter(recyclerViewHomeAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        recyclerViewCategry.setLayoutManager(gridLayoutManager);
        recyclerViewCategry.setNestedScrollingEnabled(true);
        recyclerViewHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorLoding(String massage) {
        Utils.showDialogMessage(this,"Error",massage);

    }

    // TODO 36 Overriding the interface

}
