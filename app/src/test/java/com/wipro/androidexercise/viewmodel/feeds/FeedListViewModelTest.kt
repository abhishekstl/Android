package com.wipro.androidexercise.viewmodel.feeds


import com.wipro.androidexercise.modelbuilder.services.FeedAPIInterface
import org.junit.Before
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.wipro.androidexercise.modelbuilder.dataaccess.FeedRepository
import com.wipro.androidexercise.modelbuilder.model.FeedListData
import com.wipro.androidexercise.ui.feeds.FeedResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import org.junit.Assert
import okhttp3.ResponseBody
import org.mockito.Mock


public class FeedListViewModelTest {

    @Mock
    private lateinit var repository: FeedRepository

    @Mock
    private lateinit var apiInterface: FeedAPIInterface

    @Mock
    private lateinit var responseFeed:Response<FeedListData>

    @Mock
    private lateinit var callFeed:Callback<FeedListData>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockedCall: Call<FeedListData>

    private var fResponse = mock(FeedResponse::class.java)

    private lateinit var flVM: FeedListViewModel

    private lateinit var feedListData: FeedListData

    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var responseBody: ResponseBody

    @Captor
    var argumentCapture: ArgumentCaptor<Callback<FeedListData>>? = null


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = FeedRepository(apiInterface)
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/feeds.json")
        val source = Okio.buffer(Okio.source(inputStream))

        val jsonStr = source.readString(Charsets.UTF_8)

        val jsonObj = Gson()
        feedListData = jsonObj.fromJson(jsonStr, FeedListData::class.java)
    }



    @Test
    fun feedViewModel_shouldGetFeeds(){

        Mockito.`when`(apiInterface.feeds).thenReturn(mockedCall)

        var res: Response<FeedListData> = Response.success( feedListData );

        flVM = FeedListViewModel(repository)

        Mockito.verify(mockedCall).enqueue(argumentCapture?.capture());

        argumentCapture?.value?.onResponse(mockedCall,res)

        Assert.assertEquals(flVM.mToolBarTitle.value, "About Canada")
        Assert.assertEquals(flVM.showLoading.value, false)
        Assert.assertEquals(flVM.showError.value, false)
        Assert.assertEquals(flVM.feedsList.value, feedListData.rows)

    }

    @Test
    fun feedViewModel_shouldSetError_whenBadRequest(){

        Mockito.`when`(apiInterface.feeds).thenReturn(mockedCall)

        var res: Response<FeedListData> = Response.error( 500,responseBody );

        flVM = FeedListViewModel(repository)

        Mockito.verify(mockedCall).enqueue(argumentCapture?.capture());

        argumentCapture?.value?.onResponse(mockedCall,res)


        Assert.assertEquals(flVM.showLoading.value, false)
        Assert.assertEquals(flVM.showError.value, true)
    }

    @Test
    fun feedViewModel_shouldSetError_whenFailedRequest(){

        Mockito.`when`(apiInterface.feeds).thenReturn(mockedCall)

        val throwable = Throwable(RuntimeException())

        flVM = FeedListViewModel(repository)

        Mockito.verify(mockedCall).enqueue(argumentCapture?.capture());

        argumentCapture?.value?.onFailure(mockedCall,throwable)


        Assert.assertEquals(flVM.showLoading.value, false)
        Assert.assertEquals(flVM.showError.value, true)

    }








}