package com.xmartlabs.fountain.common.extensions

import android.arch.paging.PagedList

fun <T> PagedList<T>.getList(): List<T?> {
  return (0..(size - 1)).toList()
      .map { index ->
        loadAround(index)
        get(index)
      }
}