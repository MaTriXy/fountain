package com.xmartlabs.fountain.pagefetcher.totalentities

import com.xmartlabs.fountain.ListResponse
import com.xmartlabs.fountain.adapter.NetworkDataSourceAdapter
import com.xmartlabs.fountain.common.IntMockedListingCreator

class RefreshCacheModeNetworkDataSourceWithTotalEntityCountAdapterUnitTest
  : NetworkDataSourceWithTotalEntityCountAdapterUnitTest() {
  override fun createListing(mockedNetworkDataSourceAdapter: NetworkDataSourceAdapter<out ListResponse<Int>>) =
      IntMockedListingCreator.createNetworkWithCacheSupportListing(mockedNetworkDataSourceAdapter)
}