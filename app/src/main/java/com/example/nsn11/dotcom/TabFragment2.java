package com.example.nsn11.dotcom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TabFragment2 extends Fragment {

    View view=null;
    Context context=null;
    private ArrayList<RepairShopReputeProfile> profile = new ArrayList<RepairShopReputeProfile>();
    private ArrayList<ArrayListStoreReputeData> arrayListStoreReputeData = new ArrayList<ArrayListStoreReputeData>();
    ListView listView;
    Button btnUID;
    String shopID;
    String estimateNumber;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference Repute_List=null;
    DatabaseReference Estimate_getTitle=null;
    DatabaseReference Estimate_getUserName=null;
    //String title;
    String repute_Rating;
    String str_Repute;
    String userID;
    String userName;
    String str;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_2,container,false);
        context = container.getContext();

        Bundle args = getArguments();
        String shopID = args.getString("shopID","123");
        //Toast.makeText(context,shopID.toString()+" Tab2",Toast.LENGTH_SHORT).show();
        listView = (ListView)view.findViewById(R.id.tab_list);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Repute_List = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("shop_Repute");
        Repute_List.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    estimateNumber = dataSnapshot1.child("estimateNumber").getValue(String.class);
                    repute_Rating = dataSnapshot1.child("repute_Rating").getValue(String.class);
                    str_Repute = dataSnapshot1.child("str_Repute").getValue(String.class);
                    userID = dataSnapshot1.child("uID").getValue(String.class);
                    arrayListStoreReputeData.add(new ArrayListStoreReputeData(estimateNumber.toString(),repute_Rating.toString(),str_Repute.toString(),userID.toString()));
                }

                Estimate_getTitle = firebaseDatabase.getReference("user_Estimate");
                for(int i=0;i<arrayListStoreReputeData.size();i++){
                    int finalI = i;
                    Estimate_getTitle.child(arrayListStoreReputeData.get(finalI).estimateNumber).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String title = dataSnapshot.child("editTitle").getValue(String.class);
                            Estimate_getUserName = firebaseDatabase.getReference("users").child(arrayListStoreReputeData.get(finalI).userID.toString());
                            Estimate_getUserName.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    userName = dataSnapshot.child("userName").getValue(String.class);
                                    profile.add(new RepairShopReputeProfile(userName.toString(),title.toString(),arrayListStoreReputeData.get(finalI).str_Repute.toString(),arrayListStoreReputeData.get(finalI).repute_Rating.toString()));
                                    RepairShopReputeProfileListAdapter adapter = new RepairShopReputeProfileListAdapter(context,R.layout.repute_profile_view,profile);

                                    //Toast.makeText(getActivity(),title.toString(),Toast.LENGTH_SHORT).show();
                                    listView.setAdapter(adapter);
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}
