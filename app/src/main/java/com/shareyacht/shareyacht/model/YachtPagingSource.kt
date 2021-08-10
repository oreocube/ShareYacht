package com.shareyacht.shareyacht.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shareyacht.shareyacht.retrofit.RetrofitService
import retrofit2.HttpException
import java.io.IOException

private const val YACHT_STARTING_PAGE_INDEX = 1

class YachtPagingSource(
    private val retrofitApi: RetrofitService
) : PagingSource<Int, Yacht>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Yacht> {
        val position = params.key ?: YACHT_STARTING_PAGE_INDEX

        return try {
            val response = retrofitApi.requestYachtList(position)
            val yachts = response.data.results

            LoadResult.Page(
                data = yachts,
                prevKey = if (position == YACHT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (yachts.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Yacht>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}