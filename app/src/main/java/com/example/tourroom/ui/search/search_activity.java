package com.example.tourroom.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.place_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.place.PlaceInfoActivity;
import com.example.tourroom.ui.profile.other_profile_activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class search_activity extends AppCompatActivity implements RecyclerviewAdapterForSearch.search_data_get_interface,
        RecyclerviewAdapterForSearchGroup.search_data_get_group__interface,
        RecyclerviewAdapterForSearchUser.search_data_get_user__interface{
    String []searchOptions;
    TextInputEditText searchEditText;
    Spinner spinner;
    RecyclerView recyclerView;
    RecyclerviewAdapterForSearch recyclerviewAdapterForSearch;
    RecyclerviewAdapterForSearchGroup recyclerviewAdapterForSearchGroup;
    RecyclerviewAdapterForSearchUser recyclerviewAdapterForSearchUser;
    List<place_data> placeDataList;
    List<group_data> groupDataList;
    List<User_Data> userDataList;
    private String currentUserID;

    //private ProgressDialog loadingBar;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        spinner=findViewById(R.id.spinnerforsearch);
        searchEditText=findViewById(R.id.edittextforsearch);
        recyclerView=findViewById(R.id.recyclerviewForSearch);
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();


        //loadingBar = new ProgressDialog(this);


        //for place
        placeDataList=new ArrayList<>();
        recyclerviewAdapterForSearch=new RecyclerviewAdapterForSearch(placeDataList,this,this);
       // recyclerView.setAdapter(recyclerviewAdapterForSearch);





        //for group
        groupDataList=new ArrayList<>();
        recyclerviewAdapterForSearchGroup=new RecyclerviewAdapterForSearchGroup(groupDataList,this,this);
       // recyclerView.setAdapter(recyclerviewAdapterForSearchGroup);



        //for user
        userDataList=new ArrayList<>();
        recyclerviewAdapterForSearchUser=new RecyclerviewAdapterForSearchUser(userDataList,this,this);
        //recyclerView.setAdapter(recyclerviewAdapterForSearchUser);

        searchOptions=getResources().getStringArray(R.array.searchOptions);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textviewforsampleview,searchOptions);
        spinner.setAdapter(adapter);



        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placeDataList.clear();
                groupDataList.clear();
                userDataList.clear();
                loadData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void loadData() {
      /* loadingBar.setTitle("Search");
         loadingBar.setMessage("Please wait,searching your query");
         loadingBar.setCanceledOnTouchOutside(false);
         loadingBar.show();*/
        String editTextValue=searchEditText.getText().toString();
        String value=spinner.getSelectedItem().toString();
        //Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        if(value.equals("Place"))
        {
            recyclerView.setAdapter(recyclerviewAdapterForSearch);
            //Toast.makeText(this, editTextValue, Toast.LENGTH_SHORT).show();
            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Places");

            Query query=mDatabaseRef.orderByChild("placeName").startAt(editTextValue).endAt(editTextValue+"\uf8ff");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){
                        final place_data placeDatas = data.getValue(place_data.class);
                        placeDataList.add(placeDatas);
                        recyclerviewAdapterForSearch.notifyDataSetChanged();
                       // loadingBar.dismiss();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                   //loadingBar.dismiss();
                }
            });

        }
       else if(value.equals("Group"))
        {
            recyclerView.setAdapter(recyclerviewAdapterForSearchGroup);
            //Toast.makeText(this, editTextValue, Toast.LENGTH_SHORT).show();
            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("GROUP");

            Query query=mDatabaseRef.orderByChild("groupName").startAt(editTextValue).endAt(editTextValue+"\uf8ff");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){

                        final group_data groupDatas = data.getValue(group_data.class);
                        groupDataList.add(groupDatas);
                        recyclerviewAdapterForSearchGroup.notifyDataSetChanged();
                        //loadingBar.dismiss();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(search_activity.this, "Error occured", Toast.LENGTH_SHORT).show();
                    //loadingBar.dismiss();
                }
            });
        }

        else  if(value.equals("User"))
        {
            recyclerView.setAdapter(recyclerviewAdapterForSearchUser);
            //Toast.makeText(this, editTextValue, Toast.LENGTH_SHORT).show();
            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

            Query query=mDatabaseRef.orderByChild("name").startAt(editTextValue).endAt(editTextValue+"\uf8ff");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){

                        final User_Data userDatas = data.getValue(User_Data.class);
                        userDataList.add(userDatas);
                        recyclerviewAdapterForSearchUser.notifyDataSetChanged();
                        //loadingBar.dismiss();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(search_activity.this, "Error occured", Toast.LENGTH_SHORT).show();
                    //loadingBar.dismiss();
                }
            });
        }
         else
         {
             //loadingBar.dismiss();
             Toast.makeText(this, "Find nothing", Toast.LENGTH_SHORT).show();

         }
    }

    @Override
    public void on_Item_click(int position, place_data placeData) {
        //finish();
        Intent intent=new Intent(this, PlaceInfoActivity.class);
        intent.putExtra("id",placeData.getPlaceId());
        startActivity(intent);
    }

    @Override
    public void on_Item_click_group(int position, final group_data groupData) {
        AlertDialog.Builder alert = new AlertDialog.Builder(search_activity.this);
        alert.setTitle("join the group");
        alert.setMessage("Do you want join this group?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendJoinRequest(groupData);
            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();

    }

    @Override
    public void on_Item_click_user(int position, User_Data user_Data) {
        Intent intent = new Intent(this, other_profile_activity.class);
        intent.putExtra("position",position);
        intent.putExtra("memberId",user_Data.getUid());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        placeDataList.clear();
        groupDataList.clear();
        userDataList.clear();
    }

    private void sendJoinRequest(final group_data group_data) {


        getINSTANCE().getRootRef().child("GROUP").child(group_data.getGroupId()).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(currentUserID)){

                    getINSTANCE().getRootRef().child("groupMemberRequest").child(group_data.getGroupId()).child(currentUserID).setValue("true")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(search_activity.this, "Join Request Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(search_activity.this, "Could not sent Join Request, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(search_activity.this, "Your are already joined in this group", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
