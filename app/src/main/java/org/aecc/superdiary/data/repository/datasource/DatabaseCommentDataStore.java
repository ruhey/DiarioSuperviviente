package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.CommentCache;
import org.aecc.superdiary.data.database.CommentDatabaseAPI;
import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.data.entity.mapper.CommentEntityDataMapper;
import org.aecc.superdiary.domain.Comment;

import java.util.Collection;

public class DatabaseCommentDataStore implements CommentDataStore {

    private final CommentCache commentCache;
    private final CommentDatabaseAPI databaseAPI;
    private final CommentEntityDataMapper commentEntityDataMapper;

    public DatabaseCommentDataStore(CommentCache commentCache, CommentDatabaseAPI databaseAPI, CommentEntityDataMapper commentEntityDataMapper) {
        this.commentCache = commentCache;
        this.databaseAPI = databaseAPI;
        this.commentEntityDataMapper = commentEntityDataMapper;

    }

    @Override
    public void getCommentsEntityList(final CommentListCallback commentListCallback) {
        this.databaseAPI.getCommentEntityList(new CommentDatabaseAPI.CommentListCallback() {

            @Override
            public void onCommentListLoaded(Collection<CommentEntity> commentsCollection) {
                commentListCallback.onCommentListLoaded(commentsCollection);
            }

            @Override
            public void onError(Exception exception) {
                commentListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getCommentEntityDetails(int id, final CommentDetailsCallback commentDetailsCallback) {
        this.databaseAPI.getCommentEntityById(id, new CommentDatabaseAPI.CommentDetailsCallback() {

            @Override
            public void onCommentEntityLoaded(CommentEntity commentEntity) {
                commentDetailsCallback.onCommentEntityLoaded(commentEntity);
                //CloudUserDataStore.this.putCommentEntityInCache(commentEntity);
            }

            @Override
            public void onError(Exception exception) {
                commentDetailsCallback.onError(exception);
            }
        });
    }

    @Override
    public void createCommentEntity(final Comment comment, final CommentCreationCallback commentCreationCallback) {
        this.databaseAPI.createCommentEntity(this.commentEntityDataMapper.untransform(comment), new CommentDatabaseAPI.CommentCreationCallback() {

            @Override
            public void onCommentEntityCreated(CommentEntity commentEntity) {
                commentCreationCallback.onCommentCreated(commentEntity);
            }

            @Override
            public void onError(Exception exception) {
                commentCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveCommentEntity(final Comment comment, final CommentSaveCallback commentSaveCallback) {
        this.databaseAPI.saveCommentEntity(this.commentEntityDataMapper.untransform(comment), new CommentDatabaseAPI.CommentSaveCallback() {
            @Override
            public void onCommentEntitySaved(CommentEntity commentEntity) {
                commentSaveCallback.onCommentSaved(commentEntity);
            }

            @Override
            public void onError(Exception exception) {
                commentSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteCommentEntity(int id, final CommentDetionCallback commentDeletionCallback) {
        this.databaseAPI.deleteCommentEntity(id, new CommentDatabaseAPI.CommentDeletionCallback() {
            @Override
            public void onCommentEntityDeleted(Collection<CommentEntity> commentsCollection) {
                commentDeletionCallback.onCommentDeleted(commentsCollection);
            }

            @Override
            public void onError(Exception exception) {
                commentDeletionCallback.onError(exception);
            }
        });
    }

}
