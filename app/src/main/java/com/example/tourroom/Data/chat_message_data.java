package com.example.tourroom.Data;

public class chat_message_data {
   private String messageId, userId, username, userImage , messageText,timeDate;
   private boolean image;

    public chat_message_data() {
    }

    public chat_message_data(String messageId, String userId, String username, String userImage, String messageText, String timeDate, boolean image) {
        this.messageId = messageId;
        this.userId = userId;
        this.username = username;
        this.userImage = userImage;
        this.messageText = messageText;
        this.timeDate = timeDate;
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

}
