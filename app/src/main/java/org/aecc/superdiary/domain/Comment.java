package org.aecc.superdiary.domain;

public class Comment {
    private final int commentId;
    private String datePosted;
    private String content;
    private boolean discarded;
    private boolean reminder;
    private String timesElapsed;

    public Comment(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
        return this.commentId;
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

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public String getTimesElapsed() {
        return timesElapsed;
    }

    public void setTimesElapsed(String timesElapsed) {
        this.timesElapsed = timesElapsed;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Comment Details *****\n");
        stringBuilder.append("id=" + this.getCommentId() + "\n");
        stringBuilder.append("datePosted=" + this.getDatePosted() + "\n");
        stringBuilder.append("content=" + this.getContent() + "\n");
        stringBuilder.append("discarded=" + this.isDiscarded() + "\n");
        stringBuilder.append("reminder=" + this.isReminder() + "\n");
        stringBuilder.append("timesElapsed=" + this.getTimesElapsed() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
