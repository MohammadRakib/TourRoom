package com.example.tourroom.ui.chat;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.Data.chat_message_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_host_activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static java.util.Objects.requireNonNull;


public class Chat_fragment extends Fragment {

    private RecyclerView chat_recycle_view;
    private AppCompatImageButton upload, send;
    private EditText messageBox;
    private TextView seeNewMessage;

    private String chat_message, currentUser, currentUserName, currentUserImage;
    private String groupId;
    private  String groupMessageCount;
    private int  checkTopItem;

    private group_host_activity group_host_activity;

    private chat_adapter chat_adapter;
    private List<chat_message_data> chatList, newchatList;
    private LinearLayoutManager layoutManager;
    private boolean isScrolling, flag, firsLoad = true;
    private String lastMessegeOnScrolled;

    Query queryLoad;
    ChildEventListener quryloadChildListener;

    int position, newMessageNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chat_recycle_view = view.findViewById(R.id.show_chat_recycle_view);
        layoutManager = new LinearLayoutManager(requireActivity());
        chat_recycle_view.setLayoutManager(layoutManager);
        upload = view.findViewById(R.id.upload_image);
        send = view.findViewById(R.id.send_message);
        messageBox = view.findViewById(R.id.Chat_edit_text);
        seeNewMessage = view.findViewById(R.id.seeNewMessages);

        currentUser = requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        //getting data from activity
        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
        newMessageNumber = group_host_activity.getNewMessageNumber();

        chatList = new ArrayList<>();
        newchatList = new ArrayList<>();

        chat_adapter = new chat_adapter(chatList, currentUser, requireActivity());
        chat_recycle_view.setAdapter(chat_adapter);



        getCurrentUserData();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "upload", Toast.LENGTH_SHORT).show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_message = messageBox.getText().toString().trim();
                sendMessage(chat_message);
            }
        });

        seeNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_recycle_view.smoothScrollToPosition(requireNonNull(chat_recycle_view.getAdapter()).getItemCount() - newMessageNumber);
                seeNewMessage.setVisibility(View.INVISIBLE);
            }
        });


        getCurrentMessegeCounter();
        loadMessage();
        loadMessageOnScroll();

    }

    private void getCurrentMessegeCounter() { //grab the message counter for that group

        getINSTANCE().getRootRef().child("GROUP").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                groupMessageCount = requireNonNull(dataSnapshot.child("msgCount").getValue()).toString(); //grab previous message count

                //updating last message count when user going offline for that user
                getINSTANCE().getRootRef().child("Users").child(currentUser).child("joinedGroups").child(groupId).child("msgCountUser").setValue(groupMessageCount);
                getYourGroupListInstance().getYourGroupList().get(position).setMsgCountUser(groupMessageCount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    private void getCurrentUserData() {

        getINSTANCE().getRootRef().child("Users").child(currentUser).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currentUserName = requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    if(dataSnapshot.hasChild("image")){
                        currentUserImage = requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadMessage() {

        if(newMessageNumber > 0){
            seeNewMessage.setVisibility(View.VISIBLE);
            String concate = newMessageNumber+" New Messages";
            seeNewMessage.setText(concate);
        }else {
            seeNewMessage.setVisibility(View.INVISIBLE);
        }

        queryLoad = getINSTANCE().getRootRef().child("groupMessage").child(groupId)
                .limitToLast(12);

        quryloadChildListener=  queryLoad.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){

                    chat_message_data chat_message_data = dataSnapshot.getValue(chat_message_data.class);
                    chatList.add(chat_message_data);
                    chat_adapter.notifyDataSetChanged();
                    chat_recycle_view.smoothScrollToPosition(requireNonNull(chat_recycle_view.getAdapter()).getItemCount());
                    assert chat_message_data != null;
                    if(!flag && firsLoad){
                        lastMessegeOnScrolled = chat_message_data.getMessageId();
                        flag = true; //tracking item pagination in other word tracking first loading message and putting its id on the lastMessageOnScrolled
                        // hello  <== first loading messege, this is also currently visible top message
                        // ..
                        //  ..
                        //  how

                        firsLoad = false;// for tracking firs time load, it necessary so that, this if block don't act again later
                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    private void loadMessageOnScroll() {

        chat_recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //currentItem = layoutManager.getChildCount();
                //TotalItem = layoutManager.getItemCount();
                checkTopItem = layoutManager.findFirstCompletelyVisibleItemPosition();

                if(isScrolling && checkTopItem == 0){

                    flag = false;
                    newchatList.clear();
                    Query query =  getINSTANCE().getRootRef().child("groupMessage").child(groupId).orderByChild("messageId").endAt(lastMessegeOnScrolled).limitToLast(13);
                    // Toast.makeText(group_host_activity, lastMessegeOnScrolled, Toast.LENGTH_LONG).show();
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                                chat_message_data chat_message_data = snapshot.getValue(chat_message_data.class);
                                newchatList.add(chat_message_data);
                                // Toast.makeText(group_host_activity, String.valueOf(newchatList.size()), Toast.LENGTH_SHORT).show();
                                if(!flag){
                                    assert chat_message_data != null;
                                    lastMessegeOnScrolled = chat_message_data.getMessageId();
                                    flag = true;
                                }
                            }

                            if(newchatList.size() != 0){
                                newchatList.remove(newchatList.size()-1);
                                if(!newchatList.isEmpty()){
                                    chatList.addAll(0,newchatList);
                                    chat_adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // Toast.makeText(group_host_activity, String.valueOf(newchatList.size()), Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    private void sendMessage(final String message) {
        if(!message.isEmpty()){
            //get the message
            String messageKey = getINSTANCE().getRootRef().child("groupMessage").push().getKey();

            //message send time
            final String date_time = String.valueOf(System.currentTimeMillis()/1000);

            //create object for new message
            chat_message_data chat_message_data = new chat_message_data(messageKey, currentUser, currentUserName, currentUserImage, message, date_time, false);
            final Map<String, Object> update = new HashMap<>();

            //put the new message
            update.put("groupMessage/"+groupId+"/"+messageKey,chat_message_data);

            //updating group message count for that group
            Long TempCount = Long.parseLong(groupMessageCount) + 1;
            groupMessageCount = String.valueOf(TempCount);

            update.put("GROUP/"+groupId+"/msgCount",groupMessageCount);//update the new one
            update.put("Users/"+currentUser+"/joinedGroups/"+groupId+"/msgCountUser",groupMessageCount);//update the user message count
            getYourGroupListInstance().getYourGroupList().get(position).setMsgCountUser(groupMessageCount);
            getYourGroupListInstance().getYourGroupList().get(position).setMsgCount(groupMessageCount);

                /*//create object for last message
                final yourGroupData assignLastMessage = new yourGroupData(groupMessageCount,currentUserName,message,date_time);
*/

            //find group member
            getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //grab the group members and updating their last message
                    for (DataSnapshot data : dataSnapshot.getChildren()){

                        update.put("Users/"+data.getKey()+"/joinedGroups/"+groupId+"/lastmsgUserName",currentUserName);
                        update.put("Users/"+data.getKey()+"/joinedGroups/"+groupId+"/lastMessage",message);
                        update.put("Users/"+data.getKey()+"/joinedGroups/"+groupId+"/lastmsgTime",date_time);
                    }

                    // update the all child at once
                    getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            messageBox.setText("");//clear message box after sending message
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(group_host_activity, "Error"+e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //message loading listener remove
        queryLoad.removeEventListener(quryloadChildListener);

    }

}
