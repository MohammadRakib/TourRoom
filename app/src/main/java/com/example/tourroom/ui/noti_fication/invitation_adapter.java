package com.example.tourroom.ui.noti_fication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static com.example.tourroom.ui.group.group_fragment.group_vertical_parent_recycle_view_adapterVariable;

public class invitation_adapter extends RecyclerView.Adapter<invitation_adapter.invitation_view_holder>{

    private final String currentUserID;
    List<group_data> invitationList;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public invitation_adapter(List<group_data> invitationList, Context context) {
        this.invitationList = invitationList;
        this.context = context;
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
    }

    @NonNull
    @Override
    public invitation_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.invitation_row,parent,false);
        return new invitation_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull invitation_view_holder holder, int position) {

        final group_data group_data = invitationList.get(position);
        String temp = "You got a invitation to join the "+group_data.getGroupName()+" group";
        holder.invitation_notification_text_view.setText(temp);
        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                join_group(group_data);

            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getINSTANCE().getRootRef().child("Invitation").child(currentUserID).child(group_data.getGroupId()).setValue(null)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Invitation rejected", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Could not Invitation", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return invitationList.size();
    }

    static class invitation_view_holder extends RecyclerView.ViewHolder{

        TextView invitation_notification_text_view;
        Button reject,join;

        public invitation_view_holder(@NonNull View itemView) {
            super(itemView);
            invitation_notification_text_view = itemView.findViewById(R.id.invitation_notification_textv_id);
            reject = itemView.findViewById(R.id.reject);
            join = itemView.findViewById(R.id.join);
        }
    }

      private void join_group(final group_data group_data) {

        final String groupId = group_data.getGroupId();
        Map<String, Object> update = new HashMap<>();

        update.put("GROUP/"+groupId+"/members/"+currentUserID,true);
        update.put("Users/"+currentUserID+"/joinedGroups/"+groupId+"/msgCountUser",group_data.getMsgCount());
        update.put("Invitation/"+currentUserID+"/"+groupId,null);

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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(context, "joined the group", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }

        });

    }


}
