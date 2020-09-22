package com.example.tourroom.ui.Poll;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tourroom.Data.Read_poll_data;
import com.example.tourroom.Data.poll_data;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class create_poll_activity extends AppCompatActivity {

    Button createnewpollbutton;
    String groupIdForPoll;

    TextInputEditText option1_edittext_forpoll,option2_edittext_forpoll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_poll_activity);


        option1_edittext_forpoll=findViewById(R.id.option_1_edit_text);
        option2_edittext_forpoll=findViewById(R.id.option_2_edit_text);
        createnewpollbutton=findViewById(R.id.create_newpollbutton);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            groupIdForPoll=bundle.getString("key100");
        }



        createnewpollbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //createNewPoll();
            }
        });
        option1_edittext_forpoll.addTextChangedListener(textWatcher);
        option2_edittext_forpoll.addTextChangedListener(textWatcher);

    }

  /*  private void createNewPoll() {


        String optionId1,optionId2,pollId,option1Text,option2Text;
        pollId=getINSTANCE().getRootRef().child("Poll").child(groupIdForPoll).push().getKey();
        optionId1=getINSTANCE().getRootRef().child("Poll").child(groupIdForPoll).child(pollId).child("Option1").push().getKey();
        optionId2=getINSTANCE().getRootRef().child("Poll").child(groupIdForPoll).child(pollId).child("Option2").push().getKey();
        option1Text=option1_edittext_forpoll.getText().toString();
        option2Text=option2_edittext_forpoll.getText().toString();

        poll_data pollData1=new poll_data(optionId1,option1Text,"0",pollId);
        poll_data pollData2=new poll_data(optionId2,option2Text,"0",pollId);

        getINSTANCE().getRootRef().child("Poll").child(groupIdForPoll).child(pollId).child("Option1").setValue(pollData1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(create_poll_activity.this, "Option1 created", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        getINSTANCE().getRootRef().child("Poll").child(groupIdForPoll).child(pollId).child("Option2").setValue(pollData2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(create_poll_activity.this, "Option2 created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String option1 = Objects.requireNonNull(option1_edittext_forpoll.getText()).toString().trim();
            String option2 = Objects.requireNonNull(option2_edittext_forpoll.getText()).toString().trim();

            createnewpollbutton.setEnabled(!option1.isEmpty() && !option2.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
