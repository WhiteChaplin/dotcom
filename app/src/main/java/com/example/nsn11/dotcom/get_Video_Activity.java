package com.example.nsn11.dotcom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class get_Video_Activity extends AppCompatActivity {
    VideoView videoView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference user_estimateRef = null;
    DatabaseReference estimate_numRef=null;
    FirebaseStorage firebaseStorage=null;
    String UID=null;
    String Estimate_UID=null;
    private String userUID;
    private String pictureFilePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_video);

        videoView = (VideoView)findViewById(R.id.Videoview);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        estimate_numRef = firebaseDatabase.getReference("Estimate_Num");
        UID = firebaseUser.getUid();

        estimate_numRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Estimate_UID = snapshot.child("estimateNum").getValue(String.class);
                    //textEstimateNum.setText(Estimate_UID.toString());
                }
                user_estimateRef = firebaseDatabase.getReference("user_Video");

                //user_estimateRef.child(Estimate_UID).addValueEventListener(new ValueEventListener()
                user_estimateRef.child(Estimate_UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //textUserUID.setText(dataSnapshot.child("userUID").getValue(String.class));
                        pictureFilePath = dataSnapshot.child("pictureFilePath").getValue(String.class);
                        Toast.makeText(getApplicationContext(),pictureFilePath.toString(),Toast.LENGTH_SHORT).show();
                        //textExplainText.setText(dataSnapshot.child("explainEditText").getValue(String.class));
                        //textSeparateText.setText(dataSnapshot.child("separateText").getValue(String.class));
                        //textSelfRepairText.setText(dataSnapshot.child("selfRepairText").getValue(String.class));
                        //textLocationText.setText(dataSnapshot.child("locationText").getValue(String.class));
                        //textMoreOption.setText(dataSnapshot.child("moreOption").getValue(String.class));
                        //textDate.setText(dataSnapshot.child("date").getValue(String.class));
                        //storageReference = firebaseStorage.getReference().child("images").child("201906012318.png");
                        //storageReference = firebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/dotcom-f8c8a.appspot.com/o/images%2F201906012318.png?alt=media&token=9714e700-de16-49ac-a763-16826fb8f496");

                        //textTitle.setText(dataSnapshot.child("editTitle").getValue(String.class));
                        try{
                            videoView.setVideoPath(pictureFilePath);
                            MediaController controller = new MediaController(get_Video_Activity.this);
                            videoView.setMediaController(controller);
                            videoView.start();
                            videoView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    controller.show(1);
                                    videoView.pause();
                                    Log.e("스레드","스레드");
                                }
                            },1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
