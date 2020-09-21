package com.example.tourroom.ui.chat;

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.chat_message_data;
import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_adapter extends RecyclerView.Adapter {

    private static final int UserMessage = 535;
    private static final int OtherMessage = 956;
    private static final int UserMessageImage = 777;
    private static final int OtherMessageImage = 999;
    private List<chat_message_data> chatList;
    private String currentUser;
    private final Calendar c = Calendar.getInstance(Locale.getDefault());
    private Context context;
    private chatInterface chatInterface;

    chat_adapter(List<chat_message_data> chatList, String currentUser, Context context, chatInterface chatInterface) {
        this.chatList = chatList;
        this.currentUser = currentUser;
        this.context = context;
        this.chatInterface = chatInterface;
    }

    @Override
    public int getItemViewType(int position) {
        if( chatList.get(position).getUserId().equals(currentUser)){
            if(chatList.get(position).isImage()){
                return UserMessageImage;
            }else {
                return UserMessage;
            }
        }else {
            if(chatList.get(position).isImage()){
                return OtherMessageImage;
            }else {
                return OtherMessage;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == UserMessage){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_row_user,parent,false);
            return new chatViewHolder_user(v);
        }else if (viewType == UserMessageImage){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_image_row_user,parent,false);
            return new chatViewHolderForImage_user(v);
        }else if (viewType == OtherMessage){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_row_others,parent,false);
            return new chatViewHolder_other(v);
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_image_row_others,parent,false);
            return new chatViewHolderForImage_other(v);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final chat_message_data chat_message_data = chatList.get(position);

        if( chat_message_data.getUserId().equals(currentUser)){

            if(chat_message_data.isImage()){

                chatViewHolderForImage_user chatViewHolderForImage_user = (chat_adapter.chatViewHolderForImage_user) holder;

                Glide.with(context)
                        .load(chat_message_data.getMessageText())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyimage)
                        .into(chatViewHolderForImage_user.userChatImage);

                String time = chat_message_data.getTimeDate();
                Long tm = Long.valueOf(time);
                c.setTimeInMillis(tm*1000);
                String date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
                chatViewHolderForImage_user.user_message_time.setText(date_time);

                chatViewHolderForImage_user.userChatImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chatInterface.chatImageClick(chat_message_data.getMessageText());
                    }
                });

            }else {
                chatViewHolder_user chatViewHolder_user = (chat_adapter.chatViewHolder_user) holder;
                chatViewHolder_user.user_chat_message_textView.setText(chat_message_data.getMessageText());
                String time = chat_message_data.getTimeDate();
                Long tm = Long.valueOf(time);
                c.setTimeInMillis(tm*1000);
                String date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
                chatViewHolder_user.user_message_time.setText(date_time);
            }

        }else {

            if(chat_message_data.isImage()){

                chatViewHolderForImage_other chatViewHolderForImage_other = (chat_adapter.chatViewHolderForImage_other) holder;

                chatViewHolderForImage_other.Other_userName_textView.setText(chat_message_data.getUsername());

                Glide.with(context)
                        .load(chat_message_data.getMessageText())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyimage)
                        .into(chatViewHolderForImage_other.otherChatImage);

                String time = chat_message_data.getTimeDate();
                Long tm = Long.valueOf(time);
                c.setTimeInMillis(tm*1000);
                String date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
                chatViewHolderForImage_other.other_message_time.setText(date_time);

                String userImage = chat_message_data.getUserImage();
                if(userImage != null){
                    Glide.with(context)
                            .load(userImage)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .dontAnimate()
                            .placeholder(R.drawable.dummyavatar)
                            .into(chatViewHolderForImage_other.other_userImage);
                }

                chatViewHolderForImage_other.otherChatImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chatInterface.chatImageClick(chat_message_data.getMessageText());
                    }
                });

            }else {
                chatViewHolder_other chatViewHolder_other = (chat_adapter.chatViewHolder_other) holder;

                chatViewHolder_other.Other_userName_textView.setText(chat_message_data.getUsername());

                chatViewHolder_other.other_chat_message_textView.setText(chat_message_data.getMessageText());
                String time = chat_message_data.getTimeDate();
                Long tm = Long.valueOf(time);
                c.setTimeInMillis(tm*1000);
                String date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
                chatViewHolder_other.other_message_time.setText(date_time);

                String userImage = chat_message_data.getUserImage();
                if(userImage != null){
                    Glide.with(context)
                            .load(userImage)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .dontAnimate()
                            .placeholder(R.drawable.dummyavatar)
                            .into(chatViewHolder_other.other_userImage);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class chatViewHolder_user extends RecyclerView.ViewHolder{

        MaterialCardView user_chat_row_card;
        TextView user_chat_message_textView, user_message_time;

        chatViewHolder_user(@NonNull View itemView) {
            super(itemView);
            user_chat_message_textView = itemView.findViewById(R.id.chat_message_row_textMessege_user);
            user_chat_row_card = itemView.findViewById(R.id.chat_message_row_card_user);
            user_message_time = itemView.findViewById(R.id.chat_message_row_time_user);
        }
    }

    static class chatViewHolderForImage_user extends RecyclerView.ViewHolder{

        MaterialCardView user_chat_row_card;
        TextView user_message_time;
        ImageView userChatImage;

        chatViewHolderForImage_user(@NonNull View itemView) {
            super(itemView);
            userChatImage = itemView.findViewById(R.id.chat_image_row_ImageMessege_user);
            user_chat_row_card = itemView.findViewById(R.id.chat_image_row_card_user);
            user_message_time = itemView.findViewById(R.id.chat_image_row_time_user);
        }
    }

    static class chatViewHolder_other extends RecyclerView.ViewHolder{
        MaterialCardView other_chat_row_card;
        TextView other_chat_message_textView, Other_userName_textView, other_message_time;
        CircleImageView other_userImage;

        chatViewHolder_other(@NonNull View itemView) {
            super(itemView);
            other_chat_row_card = itemView.findViewById(R.id.chat_message_row_card_other);
            other_chat_message_textView = itemView.findViewById(R.id.chat_message_row_textMessege_other);
            Other_userName_textView = itemView.findViewById(R.id.chat_message_row_userName_other);
            other_userImage = itemView.findViewById(R.id.chat_message_row_userImage_other);
            other_message_time = itemView.findViewById(R.id.chat_message_row_time_other);
        }
    }

    static class chatViewHolderForImage_other extends RecyclerView.ViewHolder{
        MaterialCardView other_chat_row_card;
        TextView Other_userName_textView, other_message_time;
        CircleImageView other_userImage;
        ImageView otherChatImage;
        chatViewHolderForImage_other(@NonNull View itemView) {
            super(itemView);
            other_chat_row_card = itemView.findViewById(R.id.chat_image_row_card_other);
            otherChatImage = itemView.findViewById(R.id.chat_image_row_ImageMessege_other);
            Other_userName_textView = itemView.findViewById(R.id.chat_image_row_userName_other);
            other_userImage = itemView.findViewById(R.id.chat_image_row_userImage_other);
            other_message_time = itemView.findViewById(R.id.chat_image_row_time_other);
        }
    }

}
