package com.mess.messcartoon.viewmodel;

import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.LogUtils;
import com.mess.messcartoon.model.Category;
import com.mess.messcartoon.model.CategoryComic;
import com.mess.messcartoon.model.CategoryList;
import com.mess.messcartoon.model.CategoryOrdering;
import com.mess.messcartoon.model.CategoryTheme;
import com.mess.messcartoon.model.CategoryTop;
import com.mess.messcartoon.repository.ComicRepository;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: CategoryViewModel.kt */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001:\u0001MB\u0011\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010<\u001a\u00020=J\u000e\u0010>\u001a\u00020=H\u0082@¢\u0006\u0002\u0010?JB\u0010@\u001a\u00020=2\u0006\u0010A\u001a\u00020+2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020C2\u0006\u0010E\u001a\u00020C2\b\b\u0002\u0010F\u001a\u00020 2\b\b\u0002\u0010G\u001a\u00020 H\u0082@¢\u0006\u0002\u0010HJ\u000e\u0010I\u001a\u00020=2\u0006\u0010J\u001a\u00020+J\u000e\u0010K\u001a\u00020=2\u0006\u0010J\u001a\u00020+J\u000e\u0010L\u001a\u00020=2\u0006\u0010J\u001a\u00020+J\u0006\u0010F\u001a\u00020=J\u0006\u0010G\u001a\u00020=R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010!\u001a\b\u0012\u0004\u0012\u00020 0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\f\"\u0004\b\"\u0010#R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010%\u001a\b\u0012\u0004\u0012\u00020 0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\f\"\u0004\b&\u0010#R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020 0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010(\u001a\b\u0012\u0004\u0012\u00020 0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\f\"\u0004\b)\u0010#R\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010,\u001a\b\u0012\u0004\u0012\u00020+0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\f\"\u0004\b.\u0010#R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020+0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R \u00100\u001a\b\u0012\u0004\u0012\u00020+0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\f\"\u0004\b2\u0010#R\u0014\u00103\u001a\b\u0012\u0004\u0012\u00020+0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R \u00104\u001a\b\u0012\u0004\u0012\u00020+0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\f\"\u0004\b6\u0010#R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Lcom/mess/messcartoon/viewmodel/CategoryViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/mess/messcartoon/repository/ComicRepository;", "<init>", "(Lcom/mess/messcartoon/repository/ComicRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "_categoryTheme", "Landroidx/compose/runtime/snapshots/SnapshotStateList;", "Lcom/mess/messcartoon/model/CategoryTheme;", "categoryTheme", "getCategoryTheme", "()Landroidx/compose/runtime/snapshots/SnapshotStateList;", "_categoryTop", "Lcom/mess/messcartoon/model/CategoryTop;", "categoryTop", "getCategoryTop", "_categoryOrder", "Lcom/mess/messcartoon/model/CategoryOrdering;", "categoryOrder", "getCategoryOrder", "_categoryList", "Lcom/mess/messcartoon/model/CategoryComic;", "categoryList", "getCategoryList", "_isEndReached", "", "isEndReached", "setEndReached", "(Lkotlinx/coroutines/flow/StateFlow;)V", "_isLoading", "isLoading", "setLoading", "_isLoadMoreError", "isLoadMoreError", "setLoadMoreError", "_orderIndex", "", "orderIndex", "getOrderIndex", "setOrderIndex", "_topIndex", "topIndex", "getTopIndex", "setTopIndex", "_themeIndex", "themeIndex", "getThemeIndex", "setThemeIndex", "category", "Lcom/mess/messcartoon/model/Category;", "allTheme", "allTop", "currentPage", "loadData", "", "fetchComicCategories", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchComicCategoryList", TypedValues.CycleType.S_WAVE_OFFSET, "ordering", "", "theme", "top", "loadMore", "reload", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changeTheme", "index", "changeTop", "changeOrder", "UiState", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class CategoryViewModel extends ViewModel {
    public static final int $stable = 8;
    private final SnapshotStateList<CategoryComic> _categoryList;
    private final SnapshotStateList<CategoryOrdering> _categoryOrder;
    private final SnapshotStateList<CategoryTheme> _categoryTheme;
    private final SnapshotStateList<CategoryTop> _categoryTop;
    private MutableStateFlow<Boolean> _isEndReached;
    private MutableStateFlow<Boolean> _isLoadMoreError;
    private MutableStateFlow<Boolean> _isLoading;
    private MutableStateFlow<Integer> _orderIndex;
    private MutableStateFlow<Integer> _themeIndex;
    private MutableStateFlow<Integer> _topIndex;
    private final MutableStateFlow<UiState> _uiState;
    private final CategoryTheme allTheme;
    private final CategoryTop allTop;
    private Category category;
    private final SnapshotStateList<CategoryComic> categoryList;
    private final SnapshotStateList<CategoryOrdering> categoryOrder;
    private final SnapshotStateList<CategoryTheme> categoryTheme;
    private final SnapshotStateList<CategoryTop> categoryTop;
    private int currentPage;
    private StateFlow<Boolean> isEndReached;
    private StateFlow<Boolean> isLoadMoreError;
    private StateFlow<Boolean> isLoading;
    private StateFlow<Integer> orderIndex;
    private final ComicRepository repository;
    private StateFlow<Integer> themeIndex;
    private StateFlow<Integer> topIndex;
    private final StateFlow<UiState> uiState;

    @Inject
    public CategoryViewModel(ComicRepository repository) {
        Intrinsics.checkNotNullParameter(repository, "repository");
        this.repository = repository;
        this._uiState = StateFlowKt.MutableStateFlow(UiState.Loading.INSTANCE);
        this.uiState = FlowKt.asStateFlow(this._uiState);
        this._categoryTheme = SnapshotStateKt.mutableStateListOf();
        this.categoryTheme = this._categoryTheme;
        this._categoryTop = SnapshotStateKt.mutableStateListOf();
        this.categoryTop = this._categoryTop;
        this._categoryOrder = SnapshotStateKt.mutableStateListOf();
        this.categoryOrder = this._categoryOrder;
        this._categoryList = SnapshotStateKt.mutableStateListOf();
        this.categoryList = this._categoryList;
        this._isEndReached = StateFlowKt.MutableStateFlow(false);
        this.isEndReached = FlowKt.asStateFlow(this._isEndReached);
        this._isLoading = StateFlowKt.MutableStateFlow(false);
        this.isLoading = FlowKt.asStateFlow(this._isLoading);
        this._isLoadMoreError = StateFlowKt.MutableStateFlow(false);
        this.isLoadMoreError = FlowKt.asStateFlow(this._isLoadMoreError);
        this._orderIndex = StateFlowKt.MutableStateFlow(0);
        this.orderIndex = FlowKt.asStateFlow(this._orderIndex);
        this._topIndex = StateFlowKt.MutableStateFlow(0);
        this.topIndex = FlowKt.asStateFlow(this._topIndex);
        this._themeIndex = StateFlowKt.MutableStateFlow(0);
        this.themeIndex = FlowKt.asStateFlow(this._themeIndex);
        this.allTheme = new CategoryTheme("", 0, 0, "", "全部", "");
        this.allTop = new CategoryTop("全部", "");
    }

    /* compiled from: CategoryViewModel.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0003\u0004\u0005\u0006B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState;", "", "<init>", "()V", "Loading", "Success", "Error", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState$Error;", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState$Loading;", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState$Success;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* loaded from: classes9.dex */
    public static abstract class UiState {
        public static final int $stable = 0;

        public /* synthetic */ UiState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* compiled from: CategoryViewModel.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÖ\u0003J\t\u0010\b\u001a\u00020\tHÖ\u0001J\t\u0010\n\u001a\u00020\u000bHÖ\u0001¨\u0006\f"}, d2 = {"Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState$Loading;", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState;", "<init>", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
        /* loaded from: classes9.dex */
        public static final class Loading extends UiState {
            public static final int $stable = 0;
            public static final Loading INSTANCE = new Loading();

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj instanceof Loading) {
                    Loading loading = (Loading) obj;
                    return true;
                }
                return false;
            }

            public int hashCode() {
                return 1969130648;
            }

            public String toString() {
                return "Loading";
            }

            private Loading() {
                super(null);
            }
        }

        private UiState() {
        }

        /* compiled from: CategoryViewModel.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState$Success;", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState;", "data", "Lcom/mess/messcartoon/model/CategoryList;", "<init>", "(Lcom/mess/messcartoon/model/CategoryList;)V", "getData", "()Lcom/mess/messcartoon/model/CategoryList;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
        /* loaded from: classes9.dex */
        public static final class Success extends UiState {
            public static final int $stable = 8;
            private final CategoryList data;

            public static /* synthetic */ Success copy$default(Success success, CategoryList categoryList, int i, Object obj) {
                if ((i & 1) != 0) {
                    categoryList = success.data;
                }
                return success.copy(categoryList);
            }

            public final CategoryList component1() {
                return this.data;
            }

            public final Success copy(CategoryList data) {
                Intrinsics.checkNotNullParameter(data, "data");
                return new Success(data);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Success) && Intrinsics.areEqual(this.data, ((Success) obj).data);
            }

            public int hashCode() {
                return this.data.hashCode();
            }

            public String toString() {
                return "Success(data=" + this.data + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Success(CategoryList data) {
                super(null);
                Intrinsics.checkNotNullParameter(data, "data");
                this.data = data;
            }

            public final CategoryList getData() {
                return this.data;
            }
        }

        /* compiled from: CategoryViewModel.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState$Error;", "Lcom/mess/messcartoon/viewmodel/CategoryViewModel$UiState;", "message", "", "<init>", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
        /* loaded from: classes9.dex */
        public static final class Error extends UiState {
            public static final int $stable = 0;
            private final String message;

            public static /* synthetic */ Error copy$default(Error error, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = error.message;
                }
                return error.copy(str);
            }

            public final String component1() {
                return this.message;
            }

            public final Error copy(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                return new Error(message);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Error) && Intrinsics.areEqual(this.message, ((Error) obj).message);
            }

            public int hashCode() {
                return this.message.hashCode();
            }

            public String toString() {
                return "Error(message=" + this.message + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Error(String message) {
                super(null);
                Intrinsics.checkNotNullParameter(message, "message");
                this.message = message;
            }

            public final String getMessage() {
                return this.message;
            }
        }
    }

    public final StateFlow<UiState> getUiState() {
        return this.uiState;
    }

    public final SnapshotStateList<CategoryTheme> getCategoryTheme() {
        return this.categoryTheme;
    }

    public final SnapshotStateList<CategoryTop> getCategoryTop() {
        return this.categoryTop;
    }

    public final SnapshotStateList<CategoryOrdering> getCategoryOrder() {
        return this.categoryOrder;
    }

    public final SnapshotStateList<CategoryComic> getCategoryList() {
        return this.categoryList;
    }

    public final StateFlow<Boolean> isEndReached() {
        return this.isEndReached;
    }

    public final void setEndReached(StateFlow<Boolean> stateFlow) {
        Intrinsics.checkNotNullParameter(stateFlow, "<set-?>");
        this.isEndReached = stateFlow;
    }

    public final StateFlow<Boolean> isLoading() {
        return this.isLoading;
    }

    public final void setLoading(StateFlow<Boolean> stateFlow) {
        Intrinsics.checkNotNullParameter(stateFlow, "<set-?>");
        this.isLoading = stateFlow;
    }

    public final StateFlow<Boolean> isLoadMoreError() {
        return this.isLoadMoreError;
    }

    public final void setLoadMoreError(StateFlow<Boolean> stateFlow) {
        Intrinsics.checkNotNullParameter(stateFlow, "<set-?>");
        this.isLoadMoreError = stateFlow;
    }

    public final StateFlow<Integer> getOrderIndex() {
        return this.orderIndex;
    }

    public final void setOrderIndex(StateFlow<Integer> stateFlow) {
        Intrinsics.checkNotNullParameter(stateFlow, "<set-?>");
        this.orderIndex = stateFlow;
    }

    public final StateFlow<Integer> getTopIndex() {
        return this.topIndex;
    }

    public final void setTopIndex(StateFlow<Integer> stateFlow) {
        Intrinsics.checkNotNullParameter(stateFlow, "<set-?>");
        this.topIndex = stateFlow;
    }

    public final StateFlow<Integer> getThemeIndex() {
        return this.themeIndex;
    }

    public final void setThemeIndex(StateFlow<Integer> stateFlow) {
        Intrinsics.checkNotNullParameter(stateFlow, "<set-?>");
        this.themeIndex = stateFlow;
    }

    public final void loadData() {
        if (!this._categoryTheme.isEmpty() || !this._categoryTop.isEmpty() || !this._categoryOrder.isEmpty()) {
            return;
        }
        if (this._categoryTheme.isEmpty()) {
            this._categoryTheme.add(this.allTheme);
        }
        if (this._categoryTop.isEmpty()) {
            this._categoryTop.add(this.allTop);
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$loadData$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0121  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object fetchComicCategories(kotlin.coroutines.Continuation<? super kotlin.Unit> r20) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mess.messcartoon.viewmodel.CategoryViewModel.fetchComicCategories(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object fetchComicCategoryList(int r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, boolean r13, boolean r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mess.messcartoon.viewmodel.CategoryViewModel.fetchComicCategoryList(int, java.lang.String, java.lang.String, java.lang.String, boolean, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object fetchComicCategoryList$default(CategoryViewModel categoryViewModel, int i, String str, String str2, String str3, boolean z, boolean z2, Continuation continuation, int i2, Object obj) {
        return categoryViewModel.fetchComicCategoryList(i, str, str2, str3, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? false : z2, continuation);
    }

    public final void changeTheme(int index) {
        LogUtils.d("changeTheme " + index);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$changeTheme$1(this, index, null), 3, null);
    }

    public final void changeTop(int index) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$changeTop$1(this, index, null), 3, null);
    }

    public final void changeOrder(int index) {
        Integer value = this._orderIndex.getValue();
        Integer value2 = this._topIndex.getValue();
        LogUtils.d("changeOrder _orderIndex: " + value + ", topIndex " + value2 + ", themeIndex " + this._themeIndex.getValue());
        LogUtils.d("_categoryOrder : " + this._categoryOrder.toList());
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$changeOrder$1(this, index, null), 3, null);
    }

    public final void loadMore() {
        this._isLoadMoreError.setValue(false);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$loadMore$1(this, null), 3, null);
    }

    public final void reload() {
        Integer value = this._orderIndex.getValue();
        Integer value2 = this._topIndex.getValue();
        LogUtils.d("reload _orderIndex: " + value + ", topIndex " + value2 + ", themeIndex " + this._themeIndex.getValue());
        if (!this._categoryOrder.isEmpty() && !this._categoryTheme.isEmpty() && !this._categoryTop.isEmpty()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$reload$2(this, null), 3, null);
            return;
        }
        if (this._categoryTheme.isEmpty()) {
            this._categoryTheme.add(this.allTheme);
        }
        if (this._categoryTop.isEmpty()) {
            this._categoryTop.add(this.allTop);
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CategoryViewModel$reload$1(this, null), 3, null);
    }
}