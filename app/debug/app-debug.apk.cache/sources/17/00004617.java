package com.mess.messcartoon.repository;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ComicRepository.kt */
@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "com.mess.messcartoon.repository.ComicRepository", f = "ComicRepository.kt", i = {}, l = {227}, m = "fetchComicCategories-gIAlu-s", n = {}, s = {})
/* loaded from: classes5.dex */
public final class ComicRepository$fetchComicCategories$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ComicRepository this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComicRepository$fetchComicCategories$1(ComicRepository comicRepository, Continuation<? super ComicRepository$fetchComicCategories$1> continuation) {
        super(continuation);
        this.this$0 = comicRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object m9555fetchComicCategoriesgIAlus = this.this$0.m9555fetchComicCategoriesgIAlus(null, this);
        return m9555fetchComicCategoriesgIAlus == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? m9555fetchComicCategoriesgIAlus : Result.m9679boximpl(m9555fetchComicCategoriesgIAlus);
    }
}