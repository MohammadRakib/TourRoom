package com.example.tourroom.ui.place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class PlaceInfoActivity extends AppCompatActivity {
    ImageView imageView;
    TextView texPlaceName,texDescription,texaddress;
    Button visitedbtn,editplaceInfoButton;
    String dataPassAddress;
    String placeId;
    AppCompatImageButton mapbtn;
    String coverPhotoUrl;
    String currentUser,passValueDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        imageView=findViewById(R.id.placeinfo_imageview);
        texPlaceName=findViewById(R.id.coxbazar_textview);
        texDescription=findViewById(R.id.discription_extand_for_groupinfo_textview);
        texaddress=findViewById(R.id.location_extand_for_groupinfo_textview);
        mapbtn=findViewById(R.id.editlocation_for_editgroupinfo_imageview);
        visitedbtn=findViewById(R.id.VisitedButton);
        editplaceInfoButton=findViewById(R.id.placeinfoedit_button_);

        currentUser=getINSTANCE().getMAuth().getCurrentUser().getUid();

        visitedbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getINSTANCE().getRootRef().child("VisitedPlace").child(placeId).child(currentUser).setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(PlaceInfoActivity.this, "place added in your visited list in database", Toast.LENGTH_SHORT).show();
                                visitedbtn.setVisibility(View.GONE);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlaceInfoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeId=extras.getString("id");
        }
        if(placeId!=null)
        {
            getINSTANCE().getRootRef().child("VisitedPlace").child(placeId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.hasChild(currentUser))
                    {
                        visitedbtn.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        visitedbtn.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        editplaceInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passValueDescription!=null)
                {
                    Intent intent=new Intent(PlaceInfoActivity.this,EditPlaceInfoActivity.class);
                    intent.putExtra("messagefromPlaceInfoActivity", passValueDescription);
                    intent.putExtra("id",placeId);
                    startActivity(intent);
                }

            }
        });

        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dataPassAddress!=null)
                {
                    Intent intent=new Intent(PlaceInfoActivity.this,MapsActivityForPlaceInfo.class);
                    intent.putExtra("addresspass",dataPassAddress);
                    startActivity(intent);
                }

            }
        });



        getINSTANCE().getRootRef().child("Places").child(placeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("placeName"))
                {
                    texPlaceName.setText(snapshot.child("placeName").getValue().toString());
                }
                if(snapshot.hasChild("placeAddress"))
                {
                    texaddress.setText(snapshot.child("placeAddress").getValue().toString());
                    dataPassAddress=texaddress.getText().toString();
                }

                if(snapshot.hasChild("placeDescription"))
                {
                    texDescription.setText(snapshot.child("placeDescription").getValue().toString());
                    passValueDescription=texDescription.getText().toString();
                }
                if(snapshot.hasChild("placeImage"))
                {
                    Bundle   extras1 = getIntent().getExtras();
                    if(extras1!=null)
                    {
                        coverPhotoUrl=snapshot.child("placeImage").getValue().toString();
                        Glide.with(getApplicationContext())
                                .load(coverPhotoUrl)
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .dontAnimate()
                                .placeholder(R.drawable.dummyimage)
                                .into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}