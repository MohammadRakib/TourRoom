package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

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

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class group_fragment extends Fragment  implements  VRecyclerViewClickInterface {

    private NavController navController;
    RecyclerView verticalparent_recyclerView;
    group_vertical_parent_recycle_view_adapter group_vertical_parent_recycle_view_adapterVariable;

    private String currentUserID;

    //use for tracking which group user opened
    static int yourGroupIntoPosition = -1;
    static String yourGroupIntoId = null;

    //listener and query for newMessageTracker
    Query newMessageQuery;
    ChildEventListener newMessageListener;

    boolean breaks = false; //for breaking the loop in the listener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.group_fragment, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fade fade = new Fade();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fade.excludeTarget(requireActivity().findViewById(R.id.main_toolbar_layout_id), true);
            fade.excludeTarget(requireActivity().findViewById(R.id.bottom_home_nav),true);
            fade.excludeTarget(requireActivity().findViewById(R.id.bottom_nav_host_fragment),true);
            fade.excludeTarget(requireActivity().findViewById(R.id.id_home_fragment),true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            requireActivity().getWindow().setEnterTransition(fade);
            requireActivity().getWindow().setExitTransition(fade);
        }

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


        //track new message dynamically
        newMessageTracker();


    }



    private void newMessageTracker(){

        newMessageQuery = getINSTANCE().getRootRef().child("Users").child(currentUserID).child("joinedGroups");
        newMessageListener = newMessageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (int i=0; i<getYourGroupListInstance().getYourGroupList().size(); i++){
                    if(getYourGroupListInstance().getYourGroupList().get(i).getGroupId().equals(dataSnapshot.getKey())){

                        //updating last message
                        getYourGroupListInstance().getYourGroupList().get(i).setLastmsgUserName(Objects.requireNonNull(dataSnapshot.child("lastmsgUserName").getValue()).toString());
                        getYourGroupListInstance().getYourGroupList().get(i).setLastMessage(Objects.requireNonNull(dataSnapshot.child("lastMessage").getValue()).toString());
                        getYourGroupListInstance().getYourGroupList().get(i).setLastmsgTime(Objects.requireNonNull(dataSnapshot.child("lastmsgTime").getValue()).toString());

                        // updating user message count
                        /*long tempMessageCountUser =  Long.parseLong(getYourGroupListInstance().getYourGroupList().get(i).getMsgCountUser()) + 1;
                        String messageCountUser = String.valueOf(tempMessageCountUser);*/

                        final int finalI = i;

                        getINSTANCE().getRootRef().child("GROUP").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                getYourGroupListInstance().getYourGroupList().get(finalI).setMsgCount(Objects.requireNonNull(dataSnapshot.child("msgCount").getValue()).toString());
                                yourGroupData temp =  getYourGroupListInstance().getYourGroupList().remove(finalI);
                                getYourGroupListInstance().getYourGroupList().add(0,temp);

                                group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();
                                breaks = true;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    if(breaks){
                        breaks = false;
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onItemClickV(int position, CircleImageView group_img, TextView group_name, int newMessage) {

        yourGroupIntoPosition = position;
        yourGroupIntoId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(group_img,"gimg"+position);
        pairs[1] = new Pair<View,String>(group_name,"gnm"+position);
        Intent intent = new Intent(getActivity(),group_host_activity.class);
        intent.putExtra("position",position);
        intent.putExtra("newMessage",newMessage);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),pairs);
        startActivity(intent,activityOptionsCompat.toBundle());
    }

    @Override
    public void creategrouponclick() {
        navController.navigate(R.id.create_group_fragment);
    }

    @Override
    public void groupinfoonclick(final group_data group_data) {
       // navController.navigate(R.id.group_info_fragment);

        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        alert.setTitle("join the group");
        alert.setMessage("Do you want join this group?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                join_group(group_data);
            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();


    }

    private void join_group(final group_data group_data) {

        final String groupId = group_data.getGroupId();
        Map<String, Object> update = new HashMap<>();

        update.put("GROUP/"+groupId+"/members/"+currentUserID,true);
        update.put("Users/"+currentUserID+"/joinedGroups/"+groupId+"/msgCountUser",group_data.getMsgCount());

        getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                getINSTANCE().getRootRef().child("GROUP").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        group_data group_data = snapshot.getValue(group_data.class);

                        assert group_data != null;
                        yourGroupData yourGroupData = new yourGroupData(groupId,group_data.getGroupName(),group_data.getGroupImage());
                        getYourGroupListInstance().getYourGroupList().add(0,yourGroupData);
                        group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(requireActivity(), "joined the group", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void onStop() {
        super.onStop();
        newMessageQuery.removeEventListener(newMessageListener);
    }
}
