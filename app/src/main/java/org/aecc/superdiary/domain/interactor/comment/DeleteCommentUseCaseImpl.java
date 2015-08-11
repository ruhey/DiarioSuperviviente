package org.aecc.superdiary.domain.interactor.comment;


import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.CommentRepository;

import java.util.Collection;

import javax.inject.Inject;

public class DeleteCommentUseCaseImpl implements DeleteCommentUseCase {

    private final CommentRepository commentRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int commentId = -1;
    private DeleteCommentUseCase.Callback callback;

    @Inject
    public DeleteCommentUseCaseImpl(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.commentRepository = commentRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override public void execute(int commentId, Callback callback) {
        if (commentId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.commentId = commentId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.commentRepository.deleteComment(this.commentId, this.repositoryCallback);
    }

    private final CommentRepository.CommentDetionCallback repositoryCallback =
            new CommentRepository.CommentDetionCallback() {
                @Override public void onCommentDeleted(Collection<Comment> commentsCollection) {
                    notifyDeleteCommentSuccessfully(commentsCollection);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyDeleteCommentSuccessfully(final Collection<Comment> commentsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onCommentDataDeleted(commentsCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
