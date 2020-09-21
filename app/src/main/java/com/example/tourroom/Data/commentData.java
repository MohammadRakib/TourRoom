package com.example.tourroom.Data;

public class commentData {
    String commentId, userName, commentText;

    public commentData() {
    }

    public commentData(String commentId, String userName, String commentText) {
        this.commentId = commentId;
        this.userName = userName;
        this.commentText = commentText;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
