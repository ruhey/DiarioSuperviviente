package org.aecc.superdiary.domain.interactor.comment;


import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.CommentRepository;

import javax.inject.Inject;

public class GetCommentDetailsUseCaseImpl implements GetCommentDetailsUseCase {

    private final CommentRepository commentRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int commentId = -1;
    private GetCommentDetailsUseCase.Callback callback;
    private final CommentRepository.CommentDetailsCallback repositoryCallback =
            new CommentRepository.CommentDetailsCallback() {
                @Override
                public void onCommentLoaded(Comment comment) {
                    notifyGetCommentDetailsSuccessfully(comment);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetCommentDetailsUseCaseImpl(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        this.commentRepository = commentRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int commentId, Callback callback) {
        if (commentId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.commentId = commentId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.commentRepository.getCommentById(this.commentId, this.repositoryCallback);
    }

    private void notifyGetCommentDetailsSuccessfully(final Comment comment) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCommentDataLoaded(comment);
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
