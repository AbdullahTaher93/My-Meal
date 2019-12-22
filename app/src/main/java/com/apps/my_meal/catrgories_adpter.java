package com.apps.my_meal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class catrgories_adpter extends RecyclerView.Adapter<catrgories_adpter.catrgoriesView>  {
    private Context context;
    private List<categories_Type> categories_types;
   // private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public catrgories_adpter(Context context, List<categories_Type> categories_types) {
        this.context = context;
        this.categories_types = categories_types;
    }

    @NonNull
    @Override
    public catrgoriesView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_category,viewGroup,false);
       // view.setOnClickListener(mOnClickListener);
        return new catrgoriesView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull catrgoriesView viewHolder, int position) {
        String strCategoryThum = categories_types.get(position).getURL();
        Picasso.get().load(strCategoryThum).placeholder(R.drawable.ic_circle).into(viewHolder.categoryThumb);

        String strCategoryName = categories_types.get(position).getCat_name();
        viewHolder.categoryName.setText(strCategoryName);

    }

    @Override
    public int getItemCount() {
        return categories_types.size();
    }

    public class catrgoriesView extends RecyclerView.ViewHolder implements View.OnClickListener{
        //@BindView(R.id.categoryThumb)
        ImageView categoryThumb;
       // @BindView(R.id.categoryName)
        TextView categoryName;

        public catrgoriesView(@NonNull View itemView) {

            super(itemView);
            //ButterKnife.bind(this, itemView);
            categoryThumb=itemView.findViewById(R.id.categoryThumb);
            categoryName=itemView.findViewById(R.id.categoryName);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


            passData(categoryName.getText().toString());


        }



    }


    private void passData(String id) {

        Intent intent = new Intent(context,meals.class);
        intent.putExtra("id", ""+id);
        context.startActivity(intent);
    }
}
