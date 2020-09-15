package com.example.tourroom.ui.chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.Data.chat_message_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_host_activity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static java.util.Objects.requireNonNull;


public class Chat_fragment extends Fragment implements chatInterface{

    private RecyclerView chat_recycle_view;
    private AppCompatImageButton upload, send;
    private EditText messageBox;
    private TextView seeNewMessage;
    private ImageView messageImageView;

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

    Uri group_image_uri;

    private StorageReference chatImageMessageReference;
    private ProgressDialog loadingBar;

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
        messageImageView = view.findViewById(R.id.messageImage);
        loadingBar = new ProgressDialog(requireActivity());

        currentUser = requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        chatImageMessageReference = FirebaseStorage.getInstance().getReference("chatImage");

        //getting data from activity
        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
        newMessageNumber = group_host_activity.getNewMessageNumber();

        chatList = new ArrayList<>();
        newchatList = new ArrayList<>();

        chat_adapter = new chat_adapter(chatList, currentUser, requireActivity(),this);
        chat_recycle_view.setAdapter(chat_adapter);


        getCurrentUserData();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = CropImage.activity()  //opening crop image activity for choosing image from gallery and cropping, this activity is from a custom api library
                        .getIntent(requireContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {  //requesting for pick image from gallery
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                group_image_uri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        messageImageView.setVisibility(View.VISIBLE);
        messageImageView.setImageURI(group_image_uri);
        messageBox.setText("");
        messageBox.setEnabled(false);
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


            //find group member
            getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //grab the group members and updating their last message for dynamic message count in the yourGroup List
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

        }else if(group_image_uri != null) {

            loadingBar.setTitle("Sending Image");
            loadingBar.setMessage("Please wait, sending your image...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            //get the message
            String messageKey = getINSTANCE().getRootRef().child("groupMessage").push().getKey();


            //message send time
            final String date_time = String.valueOf(System.currentTimeMillis()/1000);

            uploadChatImageToStorage(messageKey, date_time);


            messageBox.setEnabled(true);
            group_image_uri = null;
            messageImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void uploadChatImageToStorage(final String messageKey, final String date_time ) {

        final StorageReference filePath = chatImageMessageReference.child(groupId).child(messageKey+".jpg");
        UploadTask uploadTask =filePath.putFile(group_image_uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    final Uri downloadUri = task.getResult();
                    assert downloadUri != null;

                    loadingBar.dismiss();
                    //create object for new message
                    chat_message_data chat_message_data = new chat_message_data(messageKey, currentUser, currentUserName, currentUserImage, downloadUri.toString(), date_time, true);
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


                    //find group member
                    getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //grab the group members and updating their last message for dynamic message count in the yourGroup List
                            for (DataSnapshot data : dataSnapshot.getChildren()){

                                update.put("Users/"+data.getKey()+"/joinedGroups/"+groupId+"/lastmsgUserName",currentUserName);
                                update.put("Users/"+data.getKey()+"/joinedGroups/"+groupId+"/lastMessage",downloadUri.toString());
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



                } else {
                    Toast.makeText(requireActivity(), "could not send image, try again", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }
        });

    }

    @Override
    public void chatImageClick(String chatImageUrl) {
        Intent intent = new Intent(getActivity(),chatImageShowActivity.class);
        intent.putExtra("chatImageUrl",chatImageUrl);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //message loading listener remove
        queryLoad.removeEventListener(quryloadChildListener);

    }


}
