package com.mess.messcartoon.model

sealed class HomeListItem() {
    data class BannerItem(val banners: List<Banner>): HomeListItem()
    data class RecommendComicItem(val comics: RecComics): HomeListItem()
    data class TopicsComicItem(val comics: TopicList): HomeListItem()
    data class HotComicItem(val comics: List<HotComic>): HomeListItem()
    data class NewComicItem(val comics: List<NewComic>): HomeListItem()
    data class FinishedComicItem(val comics: FinishComics): HomeListItem()
    data class RankDayComicItem(val comics: RankComics): HomeListItem()
    data class RankWeekComicItem(val comics: RankComics): HomeListItem()
    data class RankMonthComicItem(val comics: RankComics): HomeListItem()
}
