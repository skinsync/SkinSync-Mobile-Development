package com.example.skinsync.data.articleadmin

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skinsync.data.setup.ApiService

class ArticlePagingSource(
    private val apiService: ApiService,
    private val searchQuery: String
) : PagingSource<Int, ArticleData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getArticles(
                page = position,
                limit = params.loadSize,
                order = "ASC",
                search = searchQuery,
                sortBy = "id"
            )
            val articles = response.data
            Log.i("APS Article Data: ", articles.toString())

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}