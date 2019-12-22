package com.apps.my_meal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdpter extends RecyclerView.Adapter<ImageAdpter.ImageViewHolder> {
    private Context context;
    private List<UploadImage> mUploads;

    public ImageAdpter(Context context, List<UploadImage> mUploads) {
        this.context = context;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.items_rowtest,viewGroup,false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int position) {
     UploadImage uploadImage=mUploads.get(position);
        imageViewHolder.image_desc.setText(uploadImage.getImgName());
        Picasso.get().load(uploadImage.getImgUrl())
                .into(imageViewHolder.im_view);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView image_desc;
        public ImageView im_view;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image_desc=itemView.findViewById(R.id.desc);
            im_view=itemView.findViewById(R.id.MealImage);

        }

        @Override
        public void onClick(View v) {

        }
    }
    private void Myonclick(String id) {

        Intent intent = new Intent();

        context.startActivity(intent);
    }
}
