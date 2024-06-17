package com.example.skinsync.activity.users.listproduct

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skinsync.data.setup.ApiService

class ListProductPagingSource(private val apiService: ApiService) : PagingSource<Int, ProductDataItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDataItem> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getProducts(
                page = position,
                limit = params.loadSize,
                order = "ASC",
                search = "",
                sortBy = "id"
            )
            val products = response.data
            Log.d("ListProductPagingSource", "Loaded products: ${products.size}, Page: $position")

            LoadResult.Page(
                data = products,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (products.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            Log.e("ListProductPagingSource", "Error loading products", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}