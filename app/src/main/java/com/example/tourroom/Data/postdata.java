package com.example.tourroom.Data;

public class postdata {
    String postId,postImage,likeNumber,CommentNumber;

    public postdata() {
    }

    public postdata(String postId, String postImage, String likeNumber, String commentNumber) {
        this.postId = postId;
        this.postImage = postImage;
        this.likeNumber = likeNumber;
        CommentNumber = commentNumber;
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
        return CommentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        CommentNumber = commentNumber;
    }
}
