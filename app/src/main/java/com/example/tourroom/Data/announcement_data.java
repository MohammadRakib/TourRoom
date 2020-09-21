package com.example.tourroom.Data;

public class announcement_data {
    private String userId,announcementId,announcementText,date_time;

    public announcement_data() {
    }


    public announcement_data(String announcementId, String announcementText,String userId,String date_time) {
        this.announcementId = announcementId;
        this.announcementText = announcementText;
        this.userId = userId;
        this.date_time=date_time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
