package com.example.tourroom.Data;

public class postdata {
    String postId,postImage,likeNumber,commentNumber, userId;

    public postdata() {
    }

    public postdata(String postId, String postImage, String likeNumber, String commentNumber, String userId) {
        this.postId = postId;
        this.postImage = postImage;
        this.likeNumber = likeNumber;
        this.commentNumber = commentNumber;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(String likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }
}
