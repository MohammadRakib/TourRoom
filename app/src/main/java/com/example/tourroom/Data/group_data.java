package com.example.tourroom.Data;

import java.io.Serializable;

public class group_data implements Serializable {
    private String groupId, groupName, groupImage , groupDescription, groupAdmin, msgCount;

    public group_data() {
    }

    public group_data(String groupId, String groupName, String groupDescription, String groupAdmin, String msgCount) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupAdmin = groupAdmin;
        this.msgCount = msgCount;
    }

    public group_data(String groupId, String groupName, String groupImage, String groupDescription, String groupAdmin, String msgCount) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupImage = groupImage;
        this.groupDescription = groupDescription;
        this.groupAdmin = groupAdmin;
        this.msgCount = msgCount;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }

    public String getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(String groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
