package com.mess.messcartoon.repository

import ChapterDefault
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Category
import com.mess.messcartoon.model.CategoryList
import com.mess.messcartoon.model.ChapterTankobon
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.ComicChapterDetail
import com.mess.messcartoon.model.ComicDetail
import com.mess.messcartoon.model.ComicDetailResponse
import com.mess.messcartoon.model.FinishComics
import com.mess.messcartoon.model.HomeData
import com.mess.messcartoon.model.HotComic
import com.mess.messcartoon.model.HotComics
import com.mess.messcartoon.model.NewComic
import com.mess.messcartoon.model.NewComics
import com.mess.messcartoon.model.RankComic
import com.mess.messcartoon.model.RankComics
import com.mess.messcartoon.model.RecComics
import com.mess.messcartoon.model.TopicDetailData
import com.mess.messcartoon.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getComics(): Result<List<Comic>> {
        return try {
            withContext(Dispatchers.IO) {
                val comics = apiService.getComics()
                Result.success(comics)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchHome(): Result<HomeData> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchHome()
                if (response.code == 200) {
                    LogUtils.d(response.results)
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }

            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchRecComicList(params: Map<String, @JvmSuppressWildcards Any>): Result<RecComics> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchRecComicList(params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchNewestComicList(params: Map<String, @JvmSuppressWildcards Any>): Result<NewComics> {
        return try {
            withContext(Dispatchers.IO) {
                LogUtils.d("1111111111111111111111")
                val response = apiService.fetchNewestComicList(params)
                LogUtils.d("222222222222222222")
                if (response.code == 200) {
                    LogUtils.d(response.results)
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }


    suspend fun fetchFinishedComicList(params: Map<String, @JvmSuppressWildcards Any>): Result<FinishComics> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchFinishedComicList(params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchHotComicList(params: Map<String, @JvmSuppressWildcards Any>): Result<HotComics> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchHotComicList(params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchRankComicList(params: Map<String, @JvmSuppressWildcards Any>): Result<RankComics> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchRankComicList(params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchComicDetail(path: String, params: Map<String, @JvmSuppressWildcards Any>): Result<ComicDetailResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicDetail(path, params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchComicTopicList(path: String, params: Map<String, @JvmSuppressWildcards Any>): Result<TopicDetailData> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicTopicList(path, params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchComicChapterDefault(path: String, params: Map<String, @JvmSuppressWildcards Any>): Result<ChapterDefault> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicChapterDefault(path, params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchComicChapterTankobon(path: String, params: Map<String, @JvmSuppressWildcards Any>): Result<ChapterTankobon> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicChapterTankobon(path, params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchComicChapterDetail(path: String, uuid: String, params: Map<String, @JvmSuppressWildcards Any>): Result<ComicChapterDetail> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicChapterDetail(path, uuid, params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }


    suspend fun fetchComicCategories(params: Map<String, @JvmSuppressWildcards Any>): Result<Category> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicCategories(params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun fetchComicCategoryList(params: Map<String, @JvmSuppressWildcards Any>): Result<CategoryList> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.fetchComicCategoryList(params)
                if (response.code == 200) {
                    Result.success(response.results)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e.toString())
            Result.failure(e)
        }
    }
}