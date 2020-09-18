package com.example.tourroom.ui.place;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.Data.place_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.MapsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.tourroom.After_login_Activity.dummyImage;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class add_new_place_fragment extends Fragment {

    private Add_New_Place_Fragment_ViewModel mViewModel;
    private TextInputEditText nameEditText_for_add_place_fragment,addressEditText_for_add_place_fragment
            ,descriptionEditText_for_add_place_fragment;
    private Button addButton_for_add_place_fragment;
    private ImageView mapiconImageButton_for_add_place_fragment;
    private String AddressTemp;

    Uri tour_place_image_uri;
    private NavController navController;
    private String currentUser, localPlaceId, PlaceImage;
    private ProgressDialog loadingBar;

    public static add_new_place_fragment newInstance() {
        return new add_new_place_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_place_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Add_New_Place_Fragment_ViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        nameEditText_for_add_place_fragment=view.findViewById(R.id.enter_place_name_editText_for_add_place_fragment);
        addressEditText_for_add_place_fragment=view.findViewById(R.id.enter_place_address_edittext_for_add_place_fragment);
        descriptionEditText_for_add_place_fragment=view.findViewById
                (R.id.enter_place_description_edittext_for_add_place_fragment);
        addButton_for_add_place_fragment=view.findViewById(R.id.add_place_information_to_database);
        mapiconImageButton_for_add_place_fragment=view.findViewById(R.id.location_map_icon_imageview);
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);
        loadingBar = new ProgressDialog(getContext());



        addButton_for_add_place_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNewTourPlace();

            }
        });

        mapiconImageButton_for_add_place_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireActivity(), MapsActivity.class);
                startActivityForResult(intent,1);
            }
        });


        nameEditText_for_add_place_fragment.addTextChangedListener(textWatcher);
        addressEditText_for_add_place_fragment.addTextChangedListener(textWatcher);
        descriptionEditText_for_add_place_fragment.addTextChangedListener(textWatcher);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createNewTourPlace() {
        final String place_name,place_description, place_address,place_id;
        loadingBar.setTitle("Add New Place");
        loadingBar.setMessage("Please wait, your place is adding to database...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        place_name = Objects.requireNonNull(nameEditText_for_add_place_fragment.getText()).toString().trim();
        place_address = Objects.requireNonNull(addressEditText_for_add_place_fragment.getText()).toString().trim();
        place_description = Objects.requireNonNull(descriptionEditText_for_add_place_fragment.getText()).toString().trim();
        place_id = getINSTANCE().getRootRef().child("Places").push().getKey();

        assert place_id != null;

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("Places")
                .orderByChild("placeAddress")
                .equalTo(AddressTemp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    //Place address found
                    Toast.makeText(getActivity(),"Place address already added by other users",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    nameEditText_for_add_place_fragment.setText("");
                    addressEditText_for_add_place_fragment.setText("");
                    descriptionEditText_for_add_place_fragment.setText("");
                }else{
                    place_data placeData = new place_data(place_id,place_name,place_address,place_description,dummyImage);

                    getINSTANCE().getRootRef().child("Places").child(place_id).setValue(placeData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    getINSTANCE().getRootRef().child("VisitedPlace").child(place_id).setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            loadingBar.dismiss();
                                            Toast.makeText(getContext(), "New tour place created Successfully", Toast.LENGTH_SHORT).show();

                                            navController.popBackStack();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Error "+e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Error "+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Database connection problem occured", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(data!=null)
            {
                String getaddressfrommapactivity=data.getStringExtra("key");
                addressEditText_for_add_place_fragment.setText(getaddressfrommapactivity);
                AddressTemp=getaddressfrommapactivity;
            }
        }

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String place_name = Objects.requireNonNull( nameEditText_for_add_place_fragment.getText()).toString().trim();
            String place_address = Objects.requireNonNull(addressEditText_for_add_place_fragment.getText()).toString().trim();
            String place_description = Objects.requireNonNull( descriptionEditText_for_add_place_fragment.getText()).toString().trim();
            addButton_for_add_place_fragment.setEnabled(!place_name.isEmpty() && !place_address.isEmpty() && !place_description.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
