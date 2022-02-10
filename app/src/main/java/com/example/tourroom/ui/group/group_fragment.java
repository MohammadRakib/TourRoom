package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.After_login_Activity.intoGroup;
import static com.example.tourroom.After_login_Activity.yourGroupIntoId;
import static com.example.tourroom.After_login_Activity.yourGroupIntoPosition;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class group_fragment extends Fragment  implements  VRecyclerViewClickInterface {

    private NavController navController;
    RecyclerView verticalparent_recyclerView;
    @SuppressLint("StaticFieldLeak")
    static public group_vertical_parent_recycle_view_adapter group_vertical_parent_recycle_view_adapterVariable;

    private String currentUserID;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.group_fragment, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(!Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).isShowing()){
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        }

        verticalparent_recyclerView=view.findViewById(R.id.group_vertical_parent_Recycle_view);
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        //setting up adapter
        group_vertical_parent_recycle_view_adapterVariable = new group_vertical_parent_recycle_view_adapter(requireActivity(),this, getYourGroupListInstance().getYourGroupList());

        verticalparent_recyclerView.setAdapter(group_vertical_parent_recycle_view_adapterVariable);
        //sending to adapter
        group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();



    }

    @Override
    public void onStart() {
        super.onStart();

        intoGroup = false;

        //dynamic updating last message for which group user last entered and send a message
        if(yourGroupIntoPosition != -1 && yourGroupIntoId != null){

            getINSTANCE().getRootRef().child("Users").child(currentUserID).child("joinedGroups").child(yourGroupIntoId).addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("lastmsgUserName")){
                        getYourGroupListInstance().getYourGroupList().get(yourGroupIntoPosition).setLastmsgUserName(Objects.requireNonNull(dataSnapshot.child("lastmsgUserName").getValue()).toString());
                        getYourGroupListInstance().getYourGroupList().get(yourGroupIntoPosition).setLastMessage(Objects.requireNonNull(dataSnapshot.child("lastMessage").getValue()).toString());
                        getYourGroupListInstance().getYourGroupList().get(yourGroupIntoPosition).setLastmsgTime(Objects.requireNonNull(dataSnapshot.child("lastmsgTime").getValue()).toString());
                        group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();
                    }

                    yourGroupIntoPosition = -1;
                    yourGroupIntoId = null;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();

    }


    @Override
    public void onItemClickV(int position) {

        yourGroupIntoPosition = position;
        yourGroupIntoId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();

        Intent intent = new Intent(getActivity(),group_host_activity.class);
        intent.putExtra("position",position);

        intoGroup = true;
        startActivity(intent);
    }

    @Override
    public void creategrouponclick() {
        navController.navigate(R.id.create_group_fragment);
    }

    @Override
    public void groupinfoonclick(final group_data group_data) {
       // navController.navigate(R.id.group_info_fragment);

        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        alert.setTitle("Group join request");
        alert.setMessage("Do you want to send a join request to this group?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendJoinRequest(group_data);
            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();


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
                                     Toast.makeText(requireActivity(), "Join Request Sent", Toast.LENGTH_SHORT).show();
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(requireActivity(), "Could not sent Join Request, Try Again", Toast.LENGTH_SHORT).show();
                         }
                     });
                 }else {
                     Toast.makeText(requireActivity(), "Your are already joined in this group", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
