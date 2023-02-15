package com.jsworld.lottoproject.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
//    val compositeDisposable = CompositeDisposable()
    val isProcessing by lazy { MutableLiveData<Boolean>(false) }

//    override fun onCleared() {
//        compositeDisposable.clear()
//        super.onCleared()
//    }
//
//    fun addDisposable(disposable: Disposable){
//        compositeDisposable.add(disposable)
//    }

    open fun clear() {
//        onCleared()
    }

    fun beginProcess() {
        isProcessing.postValue(true)
    }

    fun endProcess() {
        isProcessing.postValue(false)
    }

    fun toastError(throwable: Throwable) {
        endProcess()
    }


}