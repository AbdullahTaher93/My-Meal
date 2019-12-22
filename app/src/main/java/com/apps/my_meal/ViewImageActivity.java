package com.apps.my_meal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdpter imageAdpter;

    private DatabaseReference databaseReference;
     private List<UploadImage> mupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mupload=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        Query query=FirebaseDatabase.getInstance().getReference("uploads").orderByChild("imgName").equalTo("ali");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post:dataSnapshot.getChildren() ){
                    UploadImage uploadImage1=post.getValue(UploadImage.class);

                    mupload.add(uploadImage1);


                }
                imageAdpter=new ImageAdpter(ViewImageActivity.this,mupload);
                recyclerView.setAdapter(imageAdpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
