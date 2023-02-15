package com.jsworld.lottoproject.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jsworld.lottoproject.BR

abstract class BaseVmActivity<T : ViewDataBinding, R : BaseViewModel>() : AppCompatActivity() {

    lateinit var binding: T

    abstract val layoutResID : Int
    abstract val viewModel : R
    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResID)
        binding.lifecycleOwner = this@BaseVmActivity
        bindVariables()
        initActivity()
    }

    abstract fun initActivity()

    open fun bindVariables() {
        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
    }
//    override fun onBackPressed() {
//
//        if (System.currentTimeMillis() - waitTime >= 1500) {
//            waitTime = System.currentTimeMillis()
//            shortShowToast("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
//        } else finish()
//
//    }

//    protected fun shortShowToast(msg: String) =
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//
//    protected fun longShowToast(msg: String) =
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

}