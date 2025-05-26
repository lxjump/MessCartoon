package com.mess.messcartoon.database.dao;

import androidx.autofill.HintConstants;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.mess.messcartoon.database.ListTypeConverters;
import com.mess.messcartoon.model.ComicReader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* compiled from: ComicReaderDao_Impl.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u0000 %2\u00020\u0001:\u0001%B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0096@¢\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0096@¢\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@¢\u0006\u0002\u0010\u0013J\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0096@¢\u0006\u0002\u0010\u000fJ\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0096@¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@¢\u0006\u0002\u0010\u0013J\u001e\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0096@¢\u0006\u0002\u0010\u001aJ.\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0096@¢\u0006\u0002\u0010\u001fJ&\u0010 \u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0019H\u0096@¢\u0006\u0002\u0010$R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/mess/messcartoon/database/dao/ComicReaderDao_Impl;", "Lcom/mess/messcartoon/database/dao/ComicReaderDao;", "__db", "Landroidx/room/RoomDatabase;", "<init>", "(Landroidx/room/RoomDatabase;)V", "__insertAdapterOfComicReader", "Landroidx/room/EntityInsertAdapter;", "Lcom/mess/messcartoon/model/ComicReader;", "insert", "", "book", "(Lcom/mess/messcartoon/model/ComicReader;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBook", "pathWord", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getShelfBooks", "getReadingHistory", "delete", "update", "lastReadTime", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgress", "chapter", "page", "", "(Ljava/lang/String;Ljava/lang/String;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateShelfStatus", "isInShelf", "", "addToShelfTime", "(Ljava/lang/String;ZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class ComicReaderDao_Impl implements ComicReaderDao {
    private final RoomDatabase __db;
    private final EntityInsertAdapter<ComicReader> __insertAdapterOfComicReader;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public ComicReaderDao_Impl(RoomDatabase __db) {
        Intrinsics.checkNotNullParameter(__db, "__db");
        this.__db = __db;
        this.__insertAdapterOfComicReader = new EntityInsertAdapter<ComicReader>() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl.1
            @Override // androidx.room.EntityInsertAdapter
            protected String createQuery() {
                return "INSERT OR REPLACE INTO `comic_reader` (`pathWord`,`name`,`cover`,`lastReadChapter`,`lastReadChapterTitle`,`updateState`,`lastReadPage`,`isInShelf`,`popular`,`themes`,`authors`,`datetimeUpdated`,`lastBrowserTime`,`lastReadTime`,`addToShelfTime`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertAdapter
            public void bind(SQLiteStatement statement, ComicReader entity) {
                Intrinsics.checkNotNullParameter(statement, "statement");
                Intrinsics.checkNotNullParameter(entity, "entity");
                statement.mo7662bindText(1, entity.getPathWord());
                statement.mo7662bindText(2, entity.getName());
                statement.mo7662bindText(3, entity.getCover());
                statement.mo7662bindText(4, entity.getLastReadChapter());
                statement.mo7662bindText(5, entity.getLastReadChapterTitle());
                statement.mo7660bindLong(6, entity.getUpdateState());
                statement.mo7660bindLong(7, entity.getLastReadPage());
                statement.mo7660bindLong(8, entity.isInShelf() ? 1L : 0L);
                statement.mo7660bindLong(9, entity.getPopular());
                String _tmp_1 = ListTypeConverters.Companion.fromThemeList(entity.getThemes());
                statement.mo7662bindText(10, _tmp_1);
                String _tmp_2 = ListTypeConverters.Companion.fromAuthorList(entity.getAuthors());
                statement.mo7662bindText(11, _tmp_2);
                statement.mo7662bindText(12, entity.getDatetimeUpdated());
                statement.mo7660bindLong(13, entity.getLastBrowserTime());
                statement.mo7660bindLong(14, entity.getLastReadTime());
                statement.mo7660bindLong(15, entity.getAddToShelfTime());
            }
        };
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object insert(final ComicReader book, Continuation<? super Unit> continuation) {
        Object performSuspending = DBUtil.performSuspending(this.__db, false, true, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit insert$lambda$0;
                insert$lambda$0 = ComicReaderDao_Impl.insert$lambda$0(ComicReaderDao_Impl.this, book, (SQLiteConnection) obj);
                return insert$lambda$0;
            }
        }, continuation);
        return performSuspending == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? performSuspending : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit insert$lambda$0(ComicReaderDao_Impl this$0, ComicReader $book, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        this$0.__insertAdapterOfComicReader.insert(_connection, (SQLiteConnection) $book);
        return Unit.INSTANCE;
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object getAll(Continuation<? super List<ComicReader>> continuation) {
        return DBUtil.performSuspending(this.__db, true, false, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                List all$lambda$1;
                all$lambda$1 = ComicReaderDao_Impl.getAll$lambda$1(r1, (SQLiteConnection) obj);
                return all$lambda$1;
            }
        }, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List getAll$lambda$1(String $_sql, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            int _columnIndexOfPathWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pathWord");
            int _tmpUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, HintConstants.AUTOFILL_HINT_NAME);
            int _tmpLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cover");
            int _tmpPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapter");
            int _columnIndexOfLastReadChapterTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapterTitle");
            int _columnIndexOfUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updateState");
            int _columnIndexOfLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadPage");
            int _columnIndexOfIsInShelf = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isInShelf");
            int _columnIndexOfPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "popular");
            int _columnIndexOfThemes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "themes");
            int _columnIndexOfAuthors = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "authors");
            int _columnIndexOfDatetimeUpdated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datetimeUpdated");
            int _columnIndexOfLastBrowserTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastBrowserTime");
            int _columnIndexOfLastReadTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadTime");
            int _columnIndexOfAddToShelfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "addToShelfTime");
            List _result = new ArrayList();
            while (_stmt.step()) {
                String _tmpPathWord = _stmt.getText(_columnIndexOfPathWord);
                String _tmpName = _stmt.getText(_tmpUpdateState);
                String _tmpCover = _stmt.getText(_tmpLastReadPage);
                String _tmpLastReadChapter = _stmt.getText(_tmpPopular);
                String _tmpLastReadChapterTitle = _stmt.getText(_columnIndexOfLastReadChapterTitle);
                int _columnIndexOfName = _tmpUpdateState;
                int _columnIndexOfCover = _tmpLastReadPage;
                int _tmpUpdateState2 = (int) _stmt.getLong(_columnIndexOfUpdateState);
                int _columnIndexOfLastReadChapter = _tmpPopular;
                int _tmpLastReadPage2 = (int) _stmt.getLong(_columnIndexOfLastReadPage);
                int _columnIndexOfLastReadChapterTitle2 = _columnIndexOfLastReadChapterTitle;
                int _tmp = (int) _stmt.getLong(_columnIndexOfIsInShelf);
                boolean _tmpIsInShelf = _tmp != 0;
                int _tmpPopular2 = (int) _stmt.getLong(_columnIndexOfPopular);
                String _tmp_1 = _stmt.getText(_columnIndexOfThemes);
                int _columnIndexOfPathWord2 = _columnIndexOfPathWord;
                List _tmpThemes = ListTypeConverters.Companion.toThemeList(_tmp_1);
                String _tmp_2 = _stmt.getText(_columnIndexOfAuthors);
                List _tmpAuthors = ListTypeConverters.Companion.toAuthorList(_tmp_2);
                String _tmpDatetimeUpdated = _stmt.getText(_columnIndexOfDatetimeUpdated);
                long _tmpLastBrowserTime = _stmt.getLong(_columnIndexOfLastBrowserTime);
                long _tmpLastReadTime = _stmt.getLong(_columnIndexOfLastReadTime);
                long _tmpAddToShelfTime = _stmt.getLong(_columnIndexOfAddToShelfTime);
                ComicReader _item = new ComicReader(_tmpPathWord, _tmpName, _tmpCover, _tmpLastReadChapter, _tmpLastReadChapterTitle, _tmpUpdateState2, _tmpLastReadPage2, _tmpIsInShelf, _tmpPopular2, _tmpThemes, _tmpAuthors, _tmpDatetimeUpdated, _tmpLastBrowserTime, _tmpLastReadTime, _tmpAddToShelfTime);
                int _columnIndexOfLastReadTime2 = _columnIndexOfLastReadTime;
                List _result2 = _result;
                _result2.add(_item);
                _result = _result2;
                _columnIndexOfLastReadTime = _columnIndexOfLastReadTime2;
                _tmpPopular = _columnIndexOfLastReadChapter;
                _tmpUpdateState = _columnIndexOfName;
                _tmpLastReadPage = _columnIndexOfCover;
                _columnIndexOfLastReadChapterTitle = _columnIndexOfLastReadChapterTitle2;
                _columnIndexOfPathWord = _columnIndexOfPathWord2;
            }
            return _result;
        } finally {
            _stmt.close();
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object getBook(final String pathWord, Continuation<? super ComicReader> continuation) {
        return DBUtil.performSuspending(this.__db, true, false, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ComicReader book$lambda$2;
                book$lambda$2 = ComicReaderDao_Impl.getBook$lambda$2(r1, pathWord, (SQLiteConnection) obj);
                return book$lambda$2;
            }
        }, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ComicReader getBook$lambda$2(String $_sql, String $pathWord, SQLiteConnection _connection) {
        ComicReader _result;
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            _stmt.mo7662bindText(1, $pathWord);
            int _columnIndexOfPathWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pathWord");
            int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, HintConstants.AUTOFILL_HINT_NAME);
            int _columnIndexOfCover = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cover");
            int _columnIndexOfLastReadChapter = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapter");
            int _columnIndexOfLastReadChapterTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapterTitle");
            int _columnIndexOfUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updateState");
            int _columnIndexOfLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadPage");
            int _columnIndexOfIsInShelf = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isInShelf");
            int _columnIndexOfPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "popular");
            int _columnIndexOfThemes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "themes");
            int _columnIndexOfAuthors = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "authors");
            int _columnIndexOfDatetimeUpdated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datetimeUpdated");
            int _columnIndexOfLastBrowserTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastBrowserTime");
            int _columnIndexOfLastReadTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadTime");
            int _columnIndexOfAddToShelfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "addToShelfTime");
            if (_stmt.step()) {
                String _tmpPathWord = _stmt.getText(_columnIndexOfPathWord);
                String _tmpName = _stmt.getText(_columnIndexOfName);
                String _tmpCover = _stmt.getText(_columnIndexOfCover);
                String _tmpLastReadChapter = _stmt.getText(_columnIndexOfLastReadChapter);
                String _tmpLastReadChapterTitle = _stmt.getText(_columnIndexOfLastReadChapterTitle);
                int _tmpUpdateState = (int) _stmt.getLong(_columnIndexOfUpdateState);
                int _tmpLastReadPage = (int) _stmt.getLong(_columnIndexOfLastReadPage);
                int _tmp = (int) _stmt.getLong(_columnIndexOfIsInShelf);
                boolean _tmpIsInShelf = _tmp != 0;
                int _tmpPopular = (int) _stmt.getLong(_columnIndexOfPopular);
                String _tmp_1 = _stmt.getText(_columnIndexOfThemes);
                List _tmpThemes = ListTypeConverters.Companion.toThemeList(_tmp_1);
                String _tmp_2 = _stmt.getText(_columnIndexOfAuthors);
                List _tmpAuthors = ListTypeConverters.Companion.toAuthorList(_tmp_2);
                String _tmpDatetimeUpdated = _stmt.getText(_columnIndexOfDatetimeUpdated);
                long _tmpLastBrowserTime = _stmt.getLong(_columnIndexOfLastBrowserTime);
                long _tmpLastReadTime = _stmt.getLong(_columnIndexOfLastReadTime);
                long _tmpAddToShelfTime = _stmt.getLong(_columnIndexOfAddToShelfTime);
                _result = new ComicReader(_tmpPathWord, _tmpName, _tmpCover, _tmpLastReadChapter, _tmpLastReadChapterTitle, _tmpUpdateState, _tmpLastReadPage, _tmpIsInShelf, _tmpPopular, _tmpThemes, _tmpAuthors, _tmpDatetimeUpdated, _tmpLastBrowserTime, _tmpLastReadTime, _tmpAddToShelfTime);
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _stmt.close();
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object getShelfBooks(Continuation<? super List<ComicReader>> continuation) {
        return DBUtil.performSuspending(this.__db, true, false, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                List shelfBooks$lambda$3;
                shelfBooks$lambda$3 = ComicReaderDao_Impl.getShelfBooks$lambda$3(r1, (SQLiteConnection) obj);
                return shelfBooks$lambda$3;
            }
        }, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List getShelfBooks$lambda$3(String $_sql, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            int _columnIndexOfPathWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pathWord");
            int _tmpUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, HintConstants.AUTOFILL_HINT_NAME);
            int _tmpLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cover");
            int _tmpPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapter");
            int _columnIndexOfLastReadChapterTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapterTitle");
            int _columnIndexOfUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updateState");
            int _columnIndexOfLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadPage");
            int _columnIndexOfIsInShelf = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isInShelf");
            int _columnIndexOfPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "popular");
            int _columnIndexOfThemes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "themes");
            int _columnIndexOfAuthors = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "authors");
            int _columnIndexOfDatetimeUpdated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datetimeUpdated");
            int _columnIndexOfLastBrowserTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastBrowserTime");
            int _columnIndexOfLastReadTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadTime");
            int _columnIndexOfAddToShelfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "addToShelfTime");
            List _result = new ArrayList();
            while (_stmt.step()) {
                String _tmpPathWord = _stmt.getText(_columnIndexOfPathWord);
                String _tmpName = _stmt.getText(_tmpUpdateState);
                String _tmpCover = _stmt.getText(_tmpLastReadPage);
                String _tmpLastReadChapter = _stmt.getText(_tmpPopular);
                String _tmpLastReadChapterTitle = _stmt.getText(_columnIndexOfLastReadChapterTitle);
                int _columnIndexOfName = _tmpUpdateState;
                int _columnIndexOfCover = _tmpLastReadPage;
                int _tmpUpdateState2 = (int) _stmt.getLong(_columnIndexOfUpdateState);
                int _columnIndexOfLastReadChapter = _tmpPopular;
                int _tmpLastReadPage2 = (int) _stmt.getLong(_columnIndexOfLastReadPage);
                int _columnIndexOfLastReadChapterTitle2 = _columnIndexOfLastReadChapterTitle;
                int _tmp = (int) _stmt.getLong(_columnIndexOfIsInShelf);
                boolean _tmpIsInShelf = _tmp != 0;
                int _tmpPopular2 = (int) _stmt.getLong(_columnIndexOfPopular);
                String _tmp_1 = _stmt.getText(_columnIndexOfThemes);
                int _columnIndexOfPathWord2 = _columnIndexOfPathWord;
                List _tmpThemes = ListTypeConverters.Companion.toThemeList(_tmp_1);
                String _tmp_2 = _stmt.getText(_columnIndexOfAuthors);
                List _tmpAuthors = ListTypeConverters.Companion.toAuthorList(_tmp_2);
                String _tmpDatetimeUpdated = _stmt.getText(_columnIndexOfDatetimeUpdated);
                long _tmpLastBrowserTime = _stmt.getLong(_columnIndexOfLastBrowserTime);
                long _tmpLastReadTime = _stmt.getLong(_columnIndexOfLastReadTime);
                long _tmpAddToShelfTime = _stmt.getLong(_columnIndexOfAddToShelfTime);
                ComicReader _item = new ComicReader(_tmpPathWord, _tmpName, _tmpCover, _tmpLastReadChapter, _tmpLastReadChapterTitle, _tmpUpdateState2, _tmpLastReadPage2, _tmpIsInShelf, _tmpPopular2, _tmpThemes, _tmpAuthors, _tmpDatetimeUpdated, _tmpLastBrowserTime, _tmpLastReadTime, _tmpAddToShelfTime);
                int _columnIndexOfLastReadTime2 = _columnIndexOfLastReadTime;
                List _result2 = _result;
                _result2.add(_item);
                _result = _result2;
                _columnIndexOfLastReadTime = _columnIndexOfLastReadTime2;
                _tmpPopular = _columnIndexOfLastReadChapter;
                _tmpUpdateState = _columnIndexOfName;
                _tmpLastReadPage = _columnIndexOfCover;
                _columnIndexOfLastReadChapterTitle = _columnIndexOfLastReadChapterTitle2;
                _columnIndexOfPathWord = _columnIndexOfPathWord2;
            }
            return _result;
        } finally {
            _stmt.close();
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object getReadingHistory(Continuation<? super List<ComicReader>> continuation) {
        return DBUtil.performSuspending(this.__db, true, false, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                List readingHistory$lambda$4;
                readingHistory$lambda$4 = ComicReaderDao_Impl.getReadingHistory$lambda$4(r1, (SQLiteConnection) obj);
                return readingHistory$lambda$4;
            }
        }, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List getReadingHistory$lambda$4(String $_sql, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            int _columnIndexOfPathWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pathWord");
            int _tmpUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, HintConstants.AUTOFILL_HINT_NAME);
            int _tmpLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "cover");
            int _tmpPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapter");
            int _columnIndexOfLastReadChapterTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadChapterTitle");
            int _columnIndexOfUpdateState = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updateState");
            int _columnIndexOfLastReadPage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadPage");
            int _columnIndexOfIsInShelf = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isInShelf");
            int _columnIndexOfPopular = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "popular");
            int _columnIndexOfThemes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "themes");
            int _columnIndexOfAuthors = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "authors");
            int _columnIndexOfDatetimeUpdated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datetimeUpdated");
            int _columnIndexOfLastBrowserTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastBrowserTime");
            int _columnIndexOfLastReadTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReadTime");
            int _columnIndexOfAddToShelfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "addToShelfTime");
            List _result = new ArrayList();
            while (_stmt.step()) {
                String _tmpPathWord = _stmt.getText(_columnIndexOfPathWord);
                String _tmpName = _stmt.getText(_tmpUpdateState);
                String _tmpCover = _stmt.getText(_tmpLastReadPage);
                String _tmpLastReadChapter = _stmt.getText(_tmpPopular);
                String _tmpLastReadChapterTitle = _stmt.getText(_columnIndexOfLastReadChapterTitle);
                int _columnIndexOfName = _tmpUpdateState;
                int _columnIndexOfCover = _tmpLastReadPage;
                int _tmpUpdateState2 = (int) _stmt.getLong(_columnIndexOfUpdateState);
                int _columnIndexOfLastReadChapter = _tmpPopular;
                int _tmpLastReadPage2 = (int) _stmt.getLong(_columnIndexOfLastReadPage);
                int _columnIndexOfLastReadChapterTitle2 = _columnIndexOfLastReadChapterTitle;
                int _tmp = (int) _stmt.getLong(_columnIndexOfIsInShelf);
                boolean _tmpIsInShelf = _tmp != 0;
                int _tmpPopular2 = (int) _stmt.getLong(_columnIndexOfPopular);
                String _tmp_1 = _stmt.getText(_columnIndexOfThemes);
                int _columnIndexOfPathWord2 = _columnIndexOfPathWord;
                List _tmpThemes = ListTypeConverters.Companion.toThemeList(_tmp_1);
                String _tmp_2 = _stmt.getText(_columnIndexOfAuthors);
                List _tmpAuthors = ListTypeConverters.Companion.toAuthorList(_tmp_2);
                String _tmpDatetimeUpdated = _stmt.getText(_columnIndexOfDatetimeUpdated);
                long _tmpLastBrowserTime = _stmt.getLong(_columnIndexOfLastBrowserTime);
                long _tmpLastReadTime = _stmt.getLong(_columnIndexOfLastReadTime);
                long _tmpAddToShelfTime = _stmt.getLong(_columnIndexOfAddToShelfTime);
                ComicReader _item = new ComicReader(_tmpPathWord, _tmpName, _tmpCover, _tmpLastReadChapter, _tmpLastReadChapterTitle, _tmpUpdateState2, _tmpLastReadPage2, _tmpIsInShelf, _tmpPopular2, _tmpThemes, _tmpAuthors, _tmpDatetimeUpdated, _tmpLastBrowserTime, _tmpLastReadTime, _tmpAddToShelfTime);
                int _columnIndexOfLastReadTime2 = _columnIndexOfLastReadTime;
                List _result2 = _result;
                _result2.add(_item);
                _result = _result2;
                _columnIndexOfLastReadTime = _columnIndexOfLastReadTime2;
                _tmpPopular = _columnIndexOfLastReadChapter;
                _tmpUpdateState = _columnIndexOfName;
                _tmpLastReadPage = _columnIndexOfCover;
                _columnIndexOfLastReadChapterTitle = _columnIndexOfLastReadChapterTitle2;
                _columnIndexOfPathWord = _columnIndexOfPathWord2;
            }
            return _result;
        } finally {
            _stmt.close();
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object delete(final String pathWord, Continuation<? super Unit> continuation) {
        Object performSuspending = DBUtil.performSuspending(this.__db, false, true, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit delete$lambda$5;
                delete$lambda$5 = ComicReaderDao_Impl.delete$lambda$5(r1, pathWord, (SQLiteConnection) obj);
                return delete$lambda$5;
            }
        }, continuation);
        return performSuspending == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? performSuspending : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit delete$lambda$5(String $_sql, String $pathWord, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            _stmt.mo7662bindText(1, $pathWord);
            _stmt.step();
            _stmt.close();
            return Unit.INSTANCE;
        } catch (Throwable th) {
            _stmt.close();
            throw th;
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object update(final String pathWord, final long lastReadTime, Continuation<? super Unit> continuation) {
        Object performSuspending = DBUtil.performSuspending(this.__db, false, true, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit update$lambda$6;
                update$lambda$6 = ComicReaderDao_Impl.update$lambda$6(r1, lastReadTime, pathWord, (SQLiteConnection) obj);
                return update$lambda$6;
            }
        }, continuation);
        return performSuspending == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? performSuspending : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit update$lambda$6(String $_sql, long $lastReadTime, String $pathWord, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            _stmt.mo7660bindLong(1, $lastReadTime);
            _stmt.mo7662bindText(2, $pathWord);
            _stmt.step();
            _stmt.close();
            return Unit.INSTANCE;
        } catch (Throwable th) {
            _stmt.close();
            throw th;
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object updateProgress(final String pathWord, final String chapter, final int page, final long lastReadTime, Continuation<? super Unit> continuation) {
        Object performSuspending = DBUtil.performSuspending(this.__db, false, true, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit updateProgress$lambda$7;
                updateProgress$lambda$7 = ComicReaderDao_Impl.updateProgress$lambda$7(r1, chapter, page, lastReadTime, pathWord, (SQLiteConnection) obj);
                return updateProgress$lambda$7;
            }
        }, continuation);
        return performSuspending == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? performSuspending : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit updateProgress$lambda$7(String $_sql, String $chapter, int $page, long $lastReadTime, String $pathWord, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        try {
            _stmt.mo7662bindText(1, $chapter);
            _stmt.mo7660bindLong(2, $page);
            _stmt.mo7660bindLong(3, $lastReadTime);
            _stmt.mo7662bindText(4, $pathWord);
            _stmt.step();
            _stmt.close();
            return Unit.INSTANCE;
        } catch (Throwable th) {
            _stmt.close();
            throw th;
        }
    }

    @Override // com.mess.messcartoon.database.dao.ComicReaderDao
    public Object updateShelfStatus(final String pathWord, final boolean isInShelf, final long addToShelfTime, Continuation<? super Unit> continuation) {
        Object performSuspending = DBUtil.performSuspending(this.__db, false, true, new Function1() { // from class: com.mess.messcartoon.database.dao.ComicReaderDao_Impl$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit updateShelfStatus$lambda$8;
                updateShelfStatus$lambda$8 = ComicReaderDao_Impl.updateShelfStatus$lambda$8(r1, isInShelf, addToShelfTime, pathWord, (SQLiteConnection) obj);
                return updateShelfStatus$lambda$8;
            }
        }, continuation);
        return performSuspending == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? performSuspending : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit updateShelfStatus$lambda$8(String $_sql, boolean $isInShelf, long $addToShelfTime, String $pathWord, SQLiteConnection _connection) {
        Intrinsics.checkNotNullParameter(_connection, "_connection");
        SQLiteStatement _stmt = _connection.prepare($_sql);
        int _tmp = $isInShelf ? 1 : 0;
        try {
            _stmt.mo7660bindLong(1, _tmp);
            _stmt.mo7660bindLong(2, $addToShelfTime);
            _stmt.mo7662bindText(3, $pathWord);
            _stmt.step();
            _stmt.close();
            return Unit.INSTANCE;
        } catch (Throwable th) {
            _stmt.close();
            throw th;
        }
    }

    /* compiled from: ComicReaderDao_Impl.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¨\u0006\u0007"}, d2 = {"Lcom/mess/messcartoon/database/dao/ComicReaderDao_Impl$Companion;", "", "<init>", "()V", "getRequiredConverters", "", "Lkotlin/reflect/KClass;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* loaded from: classes4.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<KClass<?>> getRequiredConverters() {
            return CollectionsKt.emptyList();
        }
    }
}