package com.apps.my_meal;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdpterHeaderCategories extends PagerAdapter {
    private Context context;
    private List<UploadImage> uploadImages;

    public AdpterHeaderCategories(Context context, List<UploadImage> uploadImages) {
        this.context = context;
        this.uploadImages = uploadImages;

    }




    @Override
    public int getCount() {
        return uploadImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_view_pager_header,viewGroup,false);


        ImageView mealThumb = view.findViewById(R.id.mealThumb);
        TextView mealName = view.findViewById(R.id.mealName);

        Picasso.get().load(uploadImages.get(position).getImgUrl()).into(mealThumb);
        mealName.setText(uploadImages.get(position).getMeal_name()+"saldaslkdaslkdjlaskjdlkj");







        viewGroup.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //container.removeView((View)object);
    }


}
