package com.mess.messcartoon.database.dao;

import androidx.autofill.HintConstants;
import androidx.room.InvalidationTracker;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

/* compiled from: AppDatabase_Impl.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0016J\"\u0010\r\u001a\u001c\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u00100\u000eH\u0014J\u0016\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u000f0\u0012H\u0016J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00102\u001a\u0010\u0016\u001a\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u000f\u0012\u0004\u0012\u00020\u00130\u000eH\u0016J\b\u0010\u0017\u001a\u00020\u0006H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/mess/messcartoon/database/dao/AppDatabase_Impl;", "Lcom/mess/messcartoon/database/dao/AppDatabase;", "<init>", "()V", "_comicReaderDao", "Lkotlin/Lazy;", "Lcom/mess/messcartoon/database/dao/ComicReaderDao;", "createOpenDelegate", "Landroidx/room/RoomOpenDelegate;", "createInvalidationTracker", "Landroidx/room/InvalidationTracker;", "clearAllTables", "", "getRequiredTypeConverterClasses", "", "Lkotlin/reflect/KClass;", "", "getRequiredAutoMigrationSpecClasses", "", "Landroidx/room/migration/AutoMigrationSpec;", "createAutoMigrations", "Landroidx/room/migration/Migration;", "autoMigrationSpecs", "comicReaderDao", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class AppDatabase_Impl extends AppDatabase {
    public static final int $stable = 8;
    private final Lazy<ComicReaderDao> _comicReaderDao = LazyKt.lazy(new Function0() { // from class: com.mess.messcartoon.database.dao.AppDatabase_Impl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ComicReaderDao_Impl _comicReaderDao$lambda$0;
            _comicReaderDao$lambda$0 = AppDatabase_Impl._comicReaderDao$lambda$0(AppDatabase_Impl.this);
            return _comicReaderDao$lambda$0;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final ComicReaderDao_Impl _comicReaderDao$lambda$0(AppDatabase_Impl this$0) {
        return new ComicReaderDao_Impl(this$0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.room.RoomDatabase
    public RoomOpenDelegate createOpenDelegate() {
        RoomOpenDelegate _openDelegate = new RoomOpenDelegate() { // from class: com.mess.messcartoon.database.dao.AppDatabase_Impl$createOpenDelegate$_openDelegate$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1, "2b1626fdeee9e62f5bd328f95bfd3e42", "6b842f3425f532114232e512f8233f38");
            }

            @Override // androidx.room.RoomOpenDelegate
            public void createAllTables(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
                SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `comic_reader` (`pathWord` TEXT NOT NULL, `name` TEXT NOT NULL, `cover` TEXT NOT NULL, `lastReadChapter` TEXT NOT NULL, `lastReadChapterTitle` TEXT NOT NULL, `updateState` INTEGER NOT NULL, `lastReadPage` INTEGER NOT NULL, `isInShelf` INTEGER NOT NULL, `popular` INTEGER NOT NULL, `themes` TEXT NOT NULL, `authors` TEXT NOT NULL, `datetimeUpdated` TEXT NOT NULL, `lastBrowserTime` INTEGER NOT NULL, `lastReadTime` INTEGER NOT NULL, `addToShelfTime` INTEGER NOT NULL, PRIMARY KEY(`pathWord`))");
                SQLite.execSQL(connection, RoomMasterTable.CREATE_QUERY);
                SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2b1626fdeee9e62f5bd328f95bfd3e42')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public void dropAllTables(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
                SQLite.execSQL(connection, "DROP TABLE IF EXISTS `comic_reader`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onCreate(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onOpen(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
                AppDatabase_Impl.this.internalInitInvalidationTracker(connection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onPreMigrate(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
                DBUtil.dropFtsSyncTriggers(connection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onPostMigrate(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
            }

            @Override // androidx.room.RoomOpenDelegate
            public RoomOpenDelegate.ValidationResult onValidateSchema(SQLiteConnection connection) {
                Intrinsics.checkNotNullParameter(connection, "connection");
                Map _columnsComicReader = new LinkedHashMap();
                _columnsComicReader.put("pathWord", new TableInfo.Column("pathWord", "TEXT", true, 1, null, 1));
                _columnsComicReader.put(HintConstants.AUTOFILL_HINT_NAME, new TableInfo.Column(HintConstants.AUTOFILL_HINT_NAME, "TEXT", true, 0, null, 1));
                _columnsComicReader.put("cover", new TableInfo.Column("cover", "TEXT", true, 0, null, 1));
                _columnsComicReader.put("lastReadChapter", new TableInfo.Column("lastReadChapter", "TEXT", true, 0, null, 1));
                _columnsComicReader.put("lastReadChapterTitle", new TableInfo.Column("lastReadChapterTitle", "TEXT", true, 0, null, 1));
                _columnsComicReader.put("updateState", new TableInfo.Column("updateState", "INTEGER", true, 0, null, 1));
                _columnsComicReader.put("lastReadPage", new TableInfo.Column("lastReadPage", "INTEGER", true, 0, null, 1));
                _columnsComicReader.put("isInShelf", new TableInfo.Column("isInShelf", "INTEGER", true, 0, null, 1));
                _columnsComicReader.put("popular", new TableInfo.Column("popular", "INTEGER", true, 0, null, 1));
                _columnsComicReader.put("themes", new TableInfo.Column("themes", "TEXT", true, 0, null, 1));
                _columnsComicReader.put("authors", new TableInfo.Column("authors", "TEXT", true, 0, null, 1));
                _columnsComicReader.put("datetimeUpdated", new TableInfo.Column("datetimeUpdated", "TEXT", true, 0, null, 1));
                _columnsComicReader.put("lastBrowserTime", new TableInfo.Column("lastBrowserTime", "INTEGER", true, 0, null, 1));
                _columnsComicReader.put("lastReadTime", new TableInfo.Column("lastReadTime", "INTEGER", true, 0, null, 1));
                _columnsComicReader.put("addToShelfTime", new TableInfo.Column("addToShelfTime", "INTEGER", true, 0, null, 1));
                Set _foreignKeysComicReader = new LinkedHashSet();
                Set _indicesComicReader = new LinkedHashSet();
                TableInfo _infoComicReader = new TableInfo("comic_reader", _columnsComicReader, _foreignKeysComicReader, _indicesComicReader);
                TableInfo _existingComicReader = TableInfo.Companion.read(connection, "comic_reader");
                if (!_infoComicReader.equals(_existingComicReader)) {
                    return new RoomOpenDelegate.ValidationResult(false, "comic_reader(com.mess.messcartoon.model.ComicReader).\n Expected:\n" + _infoComicReader + "\n Found:\n" + _existingComicReader);
                }
                return new RoomOpenDelegate.ValidationResult(true, null);
            }
        };
        return _openDelegate;
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        Map _shadowTablesMap = new LinkedHashMap();
        Map _viewTables = new LinkedHashMap();
        return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "comic_reader");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.performClear(false, "comic_reader");
    }

    @Override // androidx.room.RoomDatabase
    protected Map<KClass<?>, List<KClass<?>>> getRequiredTypeConverterClasses() {
        Map _typeConvertersMap = new LinkedHashMap();
        _typeConvertersMap.put(Reflection.getOrCreateKotlinClass(ComicReaderDao.class), ComicReaderDao_Impl.Companion.getRequiredConverters());
        return _typeConvertersMap;
    }

    @Override // androidx.room.RoomDatabase
    public Set<KClass<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecClasses() {
        Set _autoMigrationSpecsSet = new LinkedHashSet();
        return _autoMigrationSpecsSet;
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> createAutoMigrations(Map<KClass<? extends AutoMigrationSpec>, ? extends AutoMigrationSpec> autoMigrationSpecs) {
        Intrinsics.checkNotNullParameter(autoMigrationSpecs, "autoMigrationSpecs");
        List _autoMigrations = new ArrayList();
        return _autoMigrations;
    }

    @Override // com.mess.messcartoon.database.dao.AppDatabase
    public ComicReaderDao comicReaderDao() {
        return this._comicReaderDao.getValue();
    }
}