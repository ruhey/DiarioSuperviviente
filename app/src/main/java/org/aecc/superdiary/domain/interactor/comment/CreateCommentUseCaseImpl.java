package org.aecc.superdiary.domain.interactor.comment;


import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.CommentRepository;

import javax.inject.Inject;

public class CreateCommentUseCaseImpl implements CreateCommentUseCase {

    private final CommentRepository commentRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Comment comment = null;
    private CreateCommentUseCase.Callback callback;

    @Inject
    public CreateCommentUseCaseImpl(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.commentRepository = commentRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override public void execute(Comment comment, Callback callback) {
        if (comment == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.comment = comment;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.commentRepository.createComment(this.comment, this.repositoryCallback);
    }

    private final CommentRepository.CommentCreationCallback repositoryCallback =
            new CommentRepository.CommentCreationCallback() {
                @Override public void onCommentCreated(Comment comment) {
                    notifyCreateCommentSuccessfully(comment);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyCreateCommentSuccessfully(final Comment comment) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCommentDataCreated(comment);
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
