package com.example.nsn11.dotcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

import org.w3c.dom.Text;

public class Repute_Repair_Shop_Activity extends AppCompatActivity {
    TextView talk,repute;
    RatingBar ratingBar;
    EditText editRepute;
    Button btnSet;
    String UID;
    String userName;
    String shopName;
    String shopUID;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference get_Shop_UID=null;
    String float_rating;
    String estimateNumber;
    LayerDrawable stars;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repute_repair_shop);

        talk = (TextView)findViewById(R.id.talk);
        repute = (TextView)findViewById(R.id.repute);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        editRepute = (EditText)findViewById(R.id.editRepute);
        btnSet = (Button)findViewById(R.id.btnSet);
        stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);

        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        UID = firebaseAuth.getUid();

        Intent getIntent = getIntent();
        shopName = getIntent.getExtras().getString("repairShopName","");
        estimateNumber = getIntent.getExtras().getString("estimateNumber","");

        //Toast.makeText(getApplicationContext(),estimateNumber.toString(),Toast.LENGTH_SHORT).show();

        talk.setText("수리가 완료 되었습니다!\n"+shopName.toString()+"은 어떠셨나요?\n"+"수리점을 평가해주세요!\n"
                +"수리점 평가를 하시면 수리점 후기에 반영이 됩니다!");

        get_Shop_UID = firebaseDatabase.getReference("shop_Infomation");
        get_Shop_UID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("shopName").getValue(String.class).toString().equals(shopName.toString())){
                        shopUID = snapshot.getKey();
                        //Toast.makeText(getApplicationContext(),shopUID.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!editRepute.getText().toString().equals("") && !float_rating.equals("")){
                            Repair_Shop_Repute repair_shop_repute = new Repair_Shop_Repute();
                            repair_shop_repute.str_Repute = editRepute.getText().toString();
                            repair_shop_repute.repute_Rating = float_rating.toString();
                            repair_shop_repute.estimateNumber = estimateNumber.toString();
                            repair_shop_repute.uID = UID.toString();

                            repair_shop_repute.setRepute_Rating(float_rating.toString());
                            repair_shop_repute.setStr_Repute(editRepute.getText().toString());
                            repair_shop_repute.setEstimateNumber(estimateNumber.toString());
                            repair_shop_repute.setuID(UID.toString());

                            FirebaseDatabase.getInstance().getReference("shop_Infomation").child(shopUID.toString()).child("shop_Repute").push().setValue(repair_shop_repute).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Repute_Repair_Shop_Activity.this);
                                    builder.setTitle("수리점 평가 완료");
                                    builder.setMessage("수리점을 평가해 주셔서 감사합니다!\n"+"해주신 평가는 수리점의 평점에 반영됩니다");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FirebaseDatabase.getInstance().getReference().child("user_Estimate").child(estimateNumber.toString()).child("nowState").setValue("수리 평가 완료");
                                            Intent intent = new Intent(Repute_Repair_Shop_Activity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"빈칸이 있어요 ㅠㅠ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float_rating = String.valueOf(rating);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
