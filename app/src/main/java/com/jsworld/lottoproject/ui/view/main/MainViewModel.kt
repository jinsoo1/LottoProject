package com.jsworld.lottoproject.ui.view.main

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.jsworld.lottoproject.base.App.Companion.toast
import com.jsworld.lottoproject.base.BaseViewModel
import com.jsworld.lottoproject.data.common.model.LottoNum
import com.jsworld.lottoproject.data.remote.model.response.LottoNumResponse
import com.jsworld.lottoproject.data.repository.LottoRepository
import com.jsworld.lottoproject.data.room.entity.LottoNumEntity
import com.jsworld.lottoproject.data.room.repository.LottoNumRepository
import com.jsworld.lottoproject.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createRandomNumber: CreateRandomNumber,
    private val repository: LottoRepository,
    private val lottoNumRepo : LottoNumRepository
) : BaseViewModel() {

    private val _action = MutableEventFlow<LottoAction>()
    val action = _action.asEventFlow()

    private val _lottoNumber : MutableStateFlow<List<Int>> = MutableStateFlow(listOf())
    val lottoNumber = _lottoNumber.asStateFlow()

    private val _weeksLottoData : MutableStateFlow<LottoNum> = MutableStateFlow(LottoNum(0, "", 0, false, 0, 0,0,0,0,0,0,0,0,0))
    val weeksLottoData = _weeksLottoData.asStateFlow()

    private val _searchWeeksNum : MutableEventFlow<String> = MutableEventFlow()
    val searchWeeksNum = _searchWeeksNum.asEventFlow()

    private val _searchLottoData : MutableStateFlow<LottoNum> = MutableStateFlow(LottoNum(0, "", 0, false, 0, 0,0,0,0,0,0,0,0,0))
    val searchLottoData = _searchLottoData.asStateFlow()

    private val _diffWeeks : MutableStateFlow<Long> = MutableStateFlow(0)
    val diffWeeks = _diffWeeks.asStateFlow()

    init {
        getWeeks()

    }


    private fun getWeeks(){
        val getToday = Calendar.getInstance()
        getToday.time = Date() //금일 날짜

        val s_date = "2002-12-07"
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(s_date)
        val cmpDate: Calendar = Calendar.getInstance()
        cmpDate.time = date!! //특정 일자
        val diffSec = (getToday.timeInMillis - cmpDate.timeInMillis) / 1000
        val diffDays: Long = diffSec / (24 * 60 * 60) //일자수 차이
        _diffWeeks.value = (diffDays / 7) + 1
    }

    fun getRecentWinningNumber(diffWeeks : Long){
        viewModelScope.launch {
            val data = repository.getRecentWinningNumber(diffWeeks)

            when(data.isSuccessful){
                true -> {
                    if(data.body()!!.returnValue != "fail"){
                        _weeksLottoData.value = LottoNum(
                            data.body()!!.drwNo,
                            data.body()!!.drwNoDate,
                            data.body()!!.totSellamnt,
                            data.body()!!.returnValue == "success",
                            data.body()!!.firstWinamnt,
                            data.body()!!.firstPrzwnerCo,
                            data.body()!!.firstAccumamnt,
                            data.body()!!.drwtNo1,
                            data.body()!!.drwtNo2,
                            data.body()!!.drwtNo3,
                            data.body()!!.drwtNo4,
                            data.body()!!.drwtNo5,
                            data.body()!!.drwtNo6,
                            data.body()!!.bnusNo
                        )
                    }
                }
                else ->{
                    Timber.d(data.body().toString())
                }
            }



        }
    }

    fun getNumber(){
        _lottoNumber.value = createRandomNumber.createRandomNumber()
    }

    fun setSearchWeeks(string : String){
        viewModelScope.launch {
            if(string.toLong() > diffWeeks.value){
                toast("가장 최신주차보다 이전 회차를 입력하세요.")
                _searchLottoData.value = LottoNum(0, "", 0, false, 0, 0,0,0,0,0,0,0,0,0)
            }else{
                _searchWeeksNum.emit(string)
            }
        }
    }

    fun hideSearchLottoNum(){
        _searchLottoData.value = LottoNum(0, "", 0, false, 0, 0,0,0,0,0,0,0,0,0)
    }

    fun getSearchWeeksNum(searchWeeks : Long){
        viewModelScope.launch {
            val data = repository.getRecentWinningNumber(searchWeeks)

            when(data.isSuccessful){
                true -> {
                    if(data.body()!!.returnValue != "fail"){
                        _weeksLottoData.value = LottoNum(
                            data.body()!!.drwNo,
                            data.body()!!.drwNoDate,
                            data.body()!!.totSellamnt,
                            data.body()!!.returnValue == "success",
                            data.body()!!.firstWinamnt,
                            data.body()!!.firstPrzwnerCo,
                            data.body()!!.firstAccumamnt,
                            data.body()!!.drwtNo1,
                            data.body()!!.drwtNo2,
                            data.body()!!.drwtNo3,
                            data.body()!!.drwtNo4,
                            data.body()!!.drwtNo5,
                            data.body()!!.drwtNo6,
                            data.body()!!.bnusNo
                        )
                    }
                }
                else -> {
                    Timber.d(data.body().toString())
                }
            }
        }

    }

    fun startQRCode(){
        viewModelScope.launch {
            _action.emit(LottoAction.QRCODE)
        }
    }

    fun saveLottoNum(){
        viewModelScope.launch {
            _action.emit(LottoAction.SAVE)
        }
    }

    fun emptyLottoNum(){
        _lottoNumber.value = listOf()
    }

    fun saveLottoNumRepo(){
        viewModelScope.launch {
            lottoNumRepo.insertLottoNum(
                LottoNumEntity(
                    0,
                    _weeksLottoData.value.drwNo+1,
                    _lottoNumber.value[0],
                    _lottoNumber.value[1],
                    _lottoNumber.value[2],
                    _lottoNumber.value[3],
                    _lottoNumber.value[4],
                    _lottoNumber.value[5],
                    _lottoNumber.value[5],
                )
            )
        }
    }

    fun getLottoNumRepo(){
        viewModelScope.launch {
            Log.d("getAllLotto", lottoNumRepo.getAllLottoNum(1043).toString())
        }
    }

    enum class LottoAction {
        QRCODE, SAVE
    }


    companion object {
        @JvmStatic
        @BindingAdapter("bindSaveNumber")
        fun bindSaveNumber(rv:RecyclerView, list: List<String>){
            val adapter = rv.adapter as SaveNumberAdapter
            adapter.let {
                it.updateItems(list)
            }
        }
    }

}

