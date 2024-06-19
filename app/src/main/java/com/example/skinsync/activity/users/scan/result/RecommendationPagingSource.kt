package com.example.skinsync.activity.users.scan.result

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skinsync.data.setup.ApiService

class RecommendationPagingSource(
    private val apiService: ApiService,
    private val skintype: String,
    private val productType: String,
    private val notableEffects: List<String>
) : PagingSource<Int, RecommendationDataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendationDataItem> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getRecommendedSkincare(
                skintype, productType, notableEffects
            )
            val products = response.data
            LoadResult.Page(
                data = products,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (products.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecommendationDataItem>): Int? {
        return state.anchorPosition
    }
}