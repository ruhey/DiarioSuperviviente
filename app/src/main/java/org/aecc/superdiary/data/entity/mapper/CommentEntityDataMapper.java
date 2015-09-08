package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.domain.Comment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommentEntityDataMapper {
    @Inject
    public CommentEntityDataMapper() {
    }

    public Comment transform(CommentEntity commentEntity) {
        Comment comment = null;

        if (commentEntity != null) {
            comment = new Comment(commentEntity.getCommentId());
            comment.setDatePosted(commentEntity.getDatePosted());
            comment.setContent(commentEntity.getContent());
            comment.setDiscarded(Boolean.parseBoolean(commentEntity.getDiscarded()));
            //TODO:ver como hacer lo de la imagen
            comment.setReminder(Boolean.parseBoolean(commentEntity.getReminder()));
            comment.setTimesElapsed(commentEntity.getTimesElapsed());
        }

        return comment;
    }

    public CommentEntity untransform(Comment comment) {
        CommentEntity commentEntity = new CommentEntity();

        if (comment != null) {
            commentEntity.setCommentId(comment.getCommentId());
            commentEntity.setDatePosted(comment.getDatePosted());
            commentEntity.setContent(comment.getContent());
            commentEntity.setDiscarded(String.valueOf(comment.isDiscarded()));
            //TODO:ver como hacer lo de la imagen
            commentEntity.setReminder(String.valueOf(comment.isReminder()));
            commentEntity.setTimesElapsed(comment.getTimesElapsed());
            //TODO: ver como va lo de los enumerados

        }
        return commentEntity;
    }

    public Collection<Comment> transform(Collection<CommentEntity> commentEntityCollection) {
        List<Comment> commentList = new ArrayList<Comment>();
        Comment comment;
        for (CommentEntity commentEntity : commentEntityCollection) {
            comment = transform(commentEntity);
            if (comment != null) {
                commentList.add(comment);
            }
        }
        return commentList;
    }

    public Collection<CommentEntity> untransform(Collection<Comment> commentCollection) {
        List<CommentEntity> commentEntityList = new ArrayList<CommentEntity>();
        CommentEntity commentEntity;
        for (Comment comment : commentCollection) {
            commentEntity = untransform(comment);
            if (commentEntity != null) {
                commentEntityList.add(commentEntity);
            }
        }
        return commentEntityList;
    }
}
