package com.example.tourroom.Data;

import java.io.Serializable;

public class yourGroupData implements Serializable {
    private String groupId, groupName, groupImage, msgCountUser,msgCount ,lastmsgUserName, lastMessage, lastmsgTime;

    public yourGroupData() {
    }

    public yourGroupData(String groupId, String groupName, String msgCountUser, String msgCount) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.msgCountUser = msgCountUser;
        this.msgCount = msgCount;
    }

    public yourGroupData(String groupId, String groupName, String groupImage) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupImage = groupImage;
    }

    public String getMsgCountUser() {
        return msgCountUser;
    }

    public void setMsgCountUser(String msgCountUser) {
        this.msgCountUser = msgCountUser;
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

    public String getLastmsgUserName() {
        return lastmsgUserName;
    }

    public void setLastmsgUserName(String lastmsgUserName) {
        this.lastmsgUserName = lastmsgUserName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastmsgTime() {
        return lastmsgTime;
    }

    public void setLastmsgTime(String lastmsgTime) {
        this.lastmsgTime = lastmsgTime;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }
}
