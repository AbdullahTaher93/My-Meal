package com.apps.my_meal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdpterItems extends RecyclerView.Adapter<FoodViewHeader> {
private  Context context;
private List<FoodData> myfood;

    public AdpterItems(Context context, List<FoodData> myfood) {
        this.context = context;
        this.myfood = myfood;
    }

    @NonNull
    @Override
    public FoodViewHeader onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_rows,viewGroup,false);

        return new FoodViewHeader(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHeader holder, int position) {

        holder.imageView.setImageResource(myfood.get(position).getItemimage());
        holder.name.setText(myfood.get(position).getItemname());
        holder.des.setText(myfood.get(position).getItemdes());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("d", position+"");

            }
        });

    }

    @Override
    public int getItemCount() {
        return myfood.size();
    }
}


class FoodViewHeader extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView name, des;
    CardView cardView;


    public FoodViewHeader(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.itemimage);
        name=itemView.findViewById(R.id.tvtitel);
        des=itemView.findViewById(R.id.tvdes);
        cardView=itemView.findViewById(R.id.mycardview);
    }
}
