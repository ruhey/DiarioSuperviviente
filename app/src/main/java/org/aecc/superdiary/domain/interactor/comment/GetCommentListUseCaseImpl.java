package org.aecc.superdiary.domain.interactor.comment;


import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.CommentRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetCommentListUseCaseImpl implements GetCommentListUseCase{

    private final CommentRepository commentRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    @Inject
    public GetCommentListUseCaseImpl(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.commentRepository = commentRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.commentRepository.getCommentList(this.repositoryCallback);
    }

    private final CommentRepository.CommentListCallback repositoryCallback =
            new CommentRepository.CommentListCallback() {
                @Override public void onCommentListLoaded(Collection<Comment> commentsCollection) {
                    notifyGetCommentListSuccessfully(commentsCollection);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetCommentListSuccessfully(final Collection<Comment> commentsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onCommentListLoaded(commentsCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
