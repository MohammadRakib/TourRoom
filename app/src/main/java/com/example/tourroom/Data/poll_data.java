package com.example.tourroom.Data;

public class poll_data {

   String optionId,optionText,vote,pollId;

    public poll_data(String optionId, String optionText, String vote, String pollId) {
        this.optionId = optionId;
        this.optionText = optionText;
        this.vote = vote;
        this.pollId = pollId;
    }

    public poll_data() {
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }
}
