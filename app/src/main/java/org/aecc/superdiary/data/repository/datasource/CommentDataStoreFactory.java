package org.aecc.superdiary.data.repository.datasource;


import android.content.Context;

import org.aecc.superdiary.data.cache.CommentCache;
import org.aecc.superdiary.data.database.CommentDatabaseAPI;
import org.aecc.superdiary.data.database.CommentDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.CommentEntityDataMapper;

import javax.inject.Inject;

public class CommentDataStoreFactory {

    private final Context context;
    private final CommentCache commentCache;
    private final CommentEntityDataMapper commentEntityDataMapper;

    @Inject
    public CommentDataStoreFactory(Context context, CommentCache commentCache, CommentEntityDataMapper commentEntityDataMapper) {
        this.context = context;
        this.commentCache = commentCache;
        this.commentEntityDataMapper = commentEntityDataMapper;
    }

    public CommentDataStore create(int commentId) {
        CommentDataStore commentDataStore;

        if (!this.commentCache.isExpired() && this.commentCache.isCached(commentId)) {
            //TODO: vamos a suar Cache?
            //commentDataStore = new DiskCommentDataStore(this.commentCache);
            commentDataStore = createDatabaseDataStore();
        } else {
            commentDataStore = createDatabaseDataStore();
        }

        return commentDataStore;
    }

    public CommentDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        CommentDatabaseAPI dataBaseAPI = new CommentDatabaseAPIImpl(this.context);
        return new DatabaseCommentDataStore(this.commentCache, dataBaseAPI, commentEntityDataMapper);
    }
}
