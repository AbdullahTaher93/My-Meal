package com.apps.my_meal;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdpterItems extends RecyclerView.Adapter<FoodViewHeader> {
private  Context context;
private List<UploadImage> uploadImages;

    public AdpterItems(Context context, List<UploadImage> uploadImages) {
        this.context = context;
        this.uploadImages = uploadImages;
    }

    @NonNull
    @Override
    public FoodViewHeader onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_rows,viewGroup,false);

        return new FoodViewHeader(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHeader holder, int position) {

        Picasso.get().load(uploadImages.get(position).getImgUrl()).into(holder.imageView);
        holder.name.setText(uploadImages.get(position).getMeal_name());
        holder.des.setText("Recipe: "+uploadImages.get(position).getMeal_des());
       holder.calories.setText("Calories: "+uploadImages.get(position).getMeal_calories());
       holder.cocking_time.setText("Cocking time: "+uploadImages.get(position).getCocking_time());
       // holder.rating.setRating(Float.valueOf(uploadImages.get(position).getRating()));
        holder.query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("user_ID").equalTo(uploadImages.get(position).getUSER_ID());
        holder.query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post:dataSnapshot.getChildren())
                holder.publisher.setText("Publisher: "+post.getValue(Users.class).getUser_Nickname());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("d", position+"");

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return uploadImages.size();
    }
}


class FoodViewHeader extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView name, des,publisher, calories,cocking_time;
    RatingBar rating;
    CardView cardView;
    Users users;
    Query query;


    public FoodViewHeader(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.itemimage);
        name=itemView.findViewById(R.id.tvtitel);
        des=itemView.findViewById(R.id.tvdes);
        calories=itemView.findViewById(R.id.meal_calo);
        rating=itemView.findViewById(R.id.rating);
        publisher=itemView.findViewById(R.id.publisher);
        cocking_time=itemView.findViewById(R.id.cocking_time);

        cardView=itemView.findViewById(R.id.mycardview);
    }
}
