package com.example.tourroom.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.R;

public class chatImageShowActivity extends AppCompatActivity {

    String chatImageUrl;
    ImageView chatImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_image_show);

        Bundle extras = getIntent().getExtras();
        chatImageView = findViewById(R.id.chatImageShow);
        if (extras != null) {
            chatImageUrl = extras.getString("chatImageUrl");
            Glide.with(this)
                    .load(chatImageUrl)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyimage)
                    .into(chatImageView);
        }
    }
}