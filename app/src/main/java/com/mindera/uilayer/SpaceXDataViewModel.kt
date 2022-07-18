package com.mindera.uilayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.datalayer.SpaceXDataRepositoryInterface
import com.mindera.rocketscience.model.AllLaunchesRespItem
import com.mindera.rocketscience.model.CompanyInfoResp
import com.mindera.rocketscience.model.USDCurrencyAmount
import com.mindera.rocketscience.utility.Constants.pageLimit
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class SpaceXDataViewModel
@Inject constructor(
    private val spaceXDataRepository: SpaceXDataRepositoryInterface,
) :
    ViewModel() {
    private var _allLaunchesRespItemFirstPage = MutableLiveData<List<AllLaunchesRespItem>?>()
    val allLaunchesRespItemFirstPage: LiveData<List<AllLaunchesRespItem>?>

        get() = _allLaunchesRespItemFirstPage

    private var _allLaunchesRespItemNextPage = MutableLiveData<List<AllLaunchesRespItem>?>()
    val allLaunchesRespItemNextPage: LiveData<List<AllLaunchesRespItem>?>
        get() = _allLaunchesRespItemNextPage

    private var _companyInfoResp = MutableLiveData<CompanyInfoResp>()
    val companyInfoResp: LiveData<CompanyInfoResp>
        get() = _companyInfoResp

    private var _currencyValue = MutableLiveData<Long>()
    val currencyValue: LiveData<Long>
        get() = _currencyValue

    var error = MutableLiveData<String>()

    private var _errorStatus = MutableLiveData<Boolean>(false)
    val errorStatus: LiveData<Boolean>
        get() = _errorStatus

    private var _loadig = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean>
        get() = _loadig


    fun callAPI() {
        viewModelScope.launch {
            val call1 = async { spaceXDataRepository.getCompanyInfo() }
            val call3 = async { spaceXDataRepository.getAllLaunches(pageLimit, 0) }
            _loadig.value = true
            try {
                val call1Data = call1.await()
                if (call1Data.isSuccessful) {
                    val companyInfoResp: CompanyInfoResp = call1Data.body() as CompanyInfoResp
                    _companyInfoResp.value = companyInfoResp
                    val call2 =
                        async { spaceXDataRepository.getLatestUSDValue("https://api.apilayer.com/exchangerates_data/convert?to=USD&from=INR&amount=${_companyInfoResp.value?.valuation}") }
                    val call2Data = call2.await()
                    collectCurrencyAPIData(call2Data)
                } else {
                    error.value = call1Data.errorBody().toString()
                }

            } catch (ex: Exception) {
                error.value = ex.message.toString()

            }
            val callData3 = call3.await()
            collectAllLaunchesApiData(callData3, true)

        }
    }

    private fun collectCurrencyAPIData(call2Data: Response<USDCurrencyAmount>) {
        try {
            if (call2Data.isSuccessful) {
                val usdCurrencyAmount = call2Data.body()
                _currencyValue.value = usdCurrencyAmount?.result?.toLong()
            } else {
                error.value = call2Data.errorBody().toString()
            }
        } catch (ex: Exception) {
            error.value = ex.message
            _errorStatus.value = true
        }
    }

    fun callNextPageAllLaunchesApi(limit: Int, offset: Int) {
        viewModelScope.launch {
            val call = async { spaceXDataRepository.getAllLaunches(limit, offset) }
            collectAllLaunchesApiData(callData = call.await(), false)
        }
    }

    private fun collectAllLaunchesApiData(
        callData: Response<List<AllLaunchesRespItem>>,
        boolean: Boolean
    ) {
        try {
            if (callData.isSuccessful) {
                val launchesRespItem = callData.body() as List<AllLaunchesRespItem>
                if (boolean) _allLaunchesRespItemFirstPage.value =
                    launchesRespItem else _allLaunchesRespItemNextPage.value = launchesRespItem
                _loadig.value = false
            } else {
                error.value = callData.errorBody().toString()
            }
        } catch (ex: Exception) {
            error.value = ex.message
            _errorStatus.value = true
        }
    }


}