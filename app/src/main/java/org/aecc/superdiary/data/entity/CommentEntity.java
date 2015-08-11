package org.aecc.superdiary.data.entity;

import com.google.gson.annotations.SerializedName;

public class CommentEntity {

    @SerializedName("id")
    private int commentId;

    @SerializedName("datePosted")
    private String datePosted;

    @SerializedName("content")
    private String content;

    @SerializedName("discarded")
    private String discarded;

    @SerializedName("reminder")
    private String reminder;

    @SerializedName("timesElapsed")
    private String timesElapsed;

    public CommentEntity(){
        //empty
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDiscarded() {
        return discarded;
    }

    public void setDiscarded(String discarded) {
        this.discarded = discarded;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getTimesElapsed() {
        return timesElapsed;
    }

    public void setTimesElapsed(String timesElapsed) {
        this.timesElapsed = timesElapsed;
    }

    @Override public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** User Entity Details *****\n");
        stringBuilder.append("id=" + this.getCommentId() + "\n");
        stringBuilder.append("datePosted=" + this.getDatePosted() + "\n");
        stringBuilder.append("content=" + this.getContent() + "\n");
        stringBuilder.append("discarded=" + this.getDiscarded() + "\n");
        stringBuilder.append("reminder=" + this.getReminder() + "\n");
        stringBuilder.append("timesElapsed=" + this.getTimesElapsed() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
