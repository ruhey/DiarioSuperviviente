package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.data.entity.mapper.CommentEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateCommentException;
import org.aecc.superdiary.data.exception.CantSaveCommentException;
import org.aecc.superdiary.data.exception.CommentNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.CommentDataStore;
import org.aecc.superdiary.data.repository.datasource.CommentDataStoreFactory;
import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.repository.CommentRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommentDataRepository implements CommentRepository {

    private final CommentDataStoreFactory commentDataStoreFactory;
    private final CommentEntityDataMapper commentEntityDataMapper;

    @Inject
    public CommentDataRepository(CommentDataStoreFactory commentDataStoreFactory, CommentEntityDataMapper commentEntityDataMapper){
        this.commentDataStoreFactory = commentDataStoreFactory;
        this.commentEntityDataMapper = commentEntityDataMapper;
    }

    @Override
    public void getCommentList(final CommentListCallback commentListCallback) {
        final CommentDataStore commentDataStore = this.commentDataStoreFactory.createDatabaseDataStore();
        commentDataStore.getCommentsEntityList(new CommentDataStore.CommentListCallback() {
            @Override
            public void onCommentListLoaded(Collection<CommentEntity> commentsCollection) {
                Collection<Comment> comments =
                        CommentDataRepository.this.commentEntityDataMapper.transform(commentsCollection);
                commentListCallback.onCommentListLoaded(comments);
            }

            @Override
            public void onError(Exception exception) {
                commentListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getCommentById(final int commentId, final CommentDetailsCallback commentCallback) {
        CommentDataStore commentDataStore = this.commentDataStoreFactory.create(commentId);
        commentDataStore.getCommentEntityDetails(commentId, new CommentDataStore.CommentDetailsCallback() {
            @Override
            public void onCommentEntityLoaded(CommentEntity commentEntity) {
                Comment comment = CommentDataRepository.this.commentEntityDataMapper.transform(commentEntity);
                if (comment != null) {
                    commentCallback.onCommentLoaded(comment);
                } else {
                    commentCallback.onError(new RepositoryErrorBundle(new CommentNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                commentCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createComment(final Comment comment, final CommentCreationCallback commentCreateCallback) {
        final CommentDataStore commentDataStore = this.commentDataStoreFactory.createDatabaseDataStore();
        commentDataStore.createCommentEntity(comment, new CommentDataStore.CommentCreationCallback() {

            @Override
            public void onCommentCreated(CommentEntity commentEntity) {
                Comment comment = CommentDataRepository.this.commentEntityDataMapper.transform(commentEntity);
                if (comment != null) {
                    commentCreateCallback.onCommentCreated(comment);
                } else {
                    commentCreateCallback.onError(new RepositoryErrorBundle(new CantCreateCommentException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                commentCreateCallback.onError(new RepositoryErrorBundle(new CantCreateCommentException()));
            }
        });
    }

    @Override
    public void saveComment(final Comment comment, final CommentSaveCallback commentSaveCallback) {
        final CommentDataStore commentDataStore = this.commentDataStoreFactory.createDatabaseDataStore();
        commentDataStore.saveCommentEntity(comment, new CommentDataStore.CommentSaveCallback() {

            @Override
            public void onCommentSaved(CommentEntity commentEntity) {
                Comment comment = CommentDataRepository.this.commentEntityDataMapper.transform(commentEntity);
                if (comment != null) {
                    commentSaveCallback.onCommentSaved(comment);
                } else {
                    commentSaveCallback.onError(new RepositoryErrorBundle(new CantSaveCommentException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                commentSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteComment(final int commentId, final CommentDetionCallback commentDeletionCallback) {
        final CommentDataStore commentDataStore = this.commentDataStoreFactory.createDatabaseDataStore();
        commentDataStore.deleteCommentEntity(commentId, new CommentDataStore.CommentDetionCallback() {
            @Override
            public void onCommentDeleted(Collection<CommentEntity> commentsCollection) {
                Collection<Comment> comments =
                        CommentDataRepository.this.commentEntityDataMapper.transform(commentsCollection);
                commentDeletionCallback.onCommentDeleted(comments);
            }

            @Override
            public void onError(Exception exception) {
                commentDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
