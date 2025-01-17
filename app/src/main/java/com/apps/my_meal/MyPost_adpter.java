package com.apps.my_meal;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Handler;

public class MyPost_adpter extends RecyclerView.Adapter<MyPost_adpter.mypost> {




    private  Context context;
    private List<UploadImage> uploadImages;
    private String meal_ID;

    private UploadImage editpost;


    public MyPost_adpter(Context context, List<UploadImage> uploadImages) {
        this.context = context;
        this.uploadImages = uploadImages;
    }



    @NonNull
    @Override
    public MyPost_adpter.mypost onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mypost_rows,viewGroup,false);


        return new MyPost_adpter.mypost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPost_adpter.mypost holder, int position) {







        Picasso.get().load(uploadImages.get(position).getImgUrl()).into(holder.imageView);
        holder.name.setText(uploadImages.get(position).getMeal_name());
        holder.des.setText(uploadImages.get(position).getMeal_des());
        holder.calories.setText(""+uploadImages.get(position).getMeal_calories());
        holder.cocking_time.setText(""+uploadImages.get(position).getCocking_time());
        // holder.rating.setRating(Float.valueOf(uploadImages.get(position).getRating()));
        holder.editpost.setImageResource(holder.edit);

        meal_ID=uploadImages.get(position).getMeal_ID();
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


        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.edit!=R.drawable.ic_editpost){
                    holder.name.setEnabled(false);
                    holder.des.setEnabled(false);
                    holder.cocking_time.setEnabled(false);
                    holder.calories.setEnabled(false);
                    holder.editpost.setImageResource(R.drawable.ic_editpost);
                    holder.edit=R.drawable.ic_editpost;
                    holder.save.setImageResource(R.drawable.ic_save);

                    editpost=new UploadImage(MainActivity.USER_ID.trim(),meal_ID.trim(),uploadImages.get(position).getImgUrl().trim(), holder.name.getText().toString().trim() , holder.des.getText().toString().trim(),uploadImages.get(position).getMeal_type(),Integer.parseInt(holder.cocking_time.getText().toString().trim()),Integer.parseInt(holder.calories.getText().toString().trim()), uploadImages.get(position).getRating(),uploadImages.get(position).getLikes());
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("uploads");
                    databaseReference.child(meal_ID).setValue(editpost);




                }
            }
        });
       holder.deletpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference query=FirebaseDatabase.getInstance().getReference("uploads").child(meal_ID);
                query.removeValue();
                Log.d("                       ", "onClick: "+meal_ID);


                if(uploadImages.size()!=0){
                    uploadImages.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,uploadImages.size());
                }





            }
        });





    }

    @Override
    public int getItemCount() {
        return  uploadImages.size();
    }

    public class mypost extends RecyclerView.ViewHolder {


        ImageView imageView,love,editpost,deletpost,save;
        TextView publisher,Likes,des_lable,time_leabel,Calories_lable;
        EditText name,des,calories,cocking_time;

        RatingBar rating;
        CardView cardView;
        Users users;
        Query query;
        boolean flag=true;
        private boolean mHasDoubleClicked = false;
        int edit;


        private static final long DOUBLE_PRESS_INTERVAL = 250; // in millis
        private long lastPressTime;



        public mypost(View view) {
            super(view);
            imageView=itemView.findViewById(R.id.itemimage);
            love=itemView.findViewById(R.id.loveit);
            editpost=view.findViewById(R.id.EditPost);
            deletpost=itemView.findViewById(R.id.DeletePost);


            name=itemView.findViewById(R.id.tvtitel);
            des=itemView.findViewById(R.id.tvdes);
            calories=itemView.findViewById(R.id.meal_calo);
            rating=itemView.findViewById(R.id.rating);
            publisher=itemView.findViewById(R.id.publisher);
            cocking_time=itemView.findViewById(R.id.cocking_time);
            Likes=itemView.findViewById(R.id.likes);
            save=itemView.findViewById(R.id.save);

            des_lable=itemView.findViewById(R.id.des_label);
             time_leabel=itemView.findViewById(R.id.time_label);
            Calories_lable=itemView.findViewById(R.id.Calories_label);

            cardView=itemView.findViewById(R.id.mycardview);

            edit=R.drawable.ic_editpost;




            love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag==true){

                        love.setImageResource(R.drawable.ic_loveit);
                        flag=false;

                    }else {
                        love.setImageResource(R.drawable.ic_love);
                        flag=true;
                    }
                }
            });


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long pressTime = System.currentTimeMillis();


                    // If double click...
                    if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {

                        mHasDoubleClicked = true;
                        if (flag==true){

                            love.setImageResource(R.drawable.ic_loveit);
                            flag=false;

                        }else {
                            love.setImageResource(R.drawable.ic_love);
                            flag=true;
                        }



                    }
                    else {     // If not double click....
                        mHasDoubleClicked = false;





                    }
                    // record the last time the menu button was pressed.
                    lastPressTime = pressTime;
                }
            });

            editpost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(edit==R.drawable.ic_editpost){
                        editpost.setImageResource(R.drawable.ic_disedit);
                        edit=R.drawable.ic_disedit;
                        save.setImageResource(R.drawable.ic_saveit);

                        name.setEnabled(true);
                        des.setEnabled(true);
                        cocking_time.setEnabled(true);
                        calories.setEnabled(true);



                    }else {
                        editpost.setImageResource(R.drawable.ic_editpost);
                        edit=R.drawable.ic_editpost;
                        save.setImageResource(R.drawable.ic_save);

                        name.setEnabled(false);
                        des.setEnabled(false);
                        cocking_time.setEnabled(false);
                        calories.setEnabled(false);




                    }







                }
            });
            
        }



    }


}
