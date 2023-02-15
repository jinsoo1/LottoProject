package com.jsworld.lottoproject.ui.view.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.jsworld.lottoproject.R
import com.jsworld.lottoproject.base.App.Companion.toast
import com.jsworld.lottoproject.base.BaseRecyclerAdapter
import com.jsworld.lottoproject.base.BaseVmActivity
import com.jsworld.lottoproject.databinding.ActivityMainBinding
import com.jsworld.lottoproject.databinding.ItemSavcNumberBinding
import com.jsworld.lottoproject.ui.view.scanner.ScanBarcodeActivity
import com.jsworld.lottoproject.ui.view.scanner.ScanBarcodeDialog
import com.jsworld.lottoproject.util.MoveClickListener
import com.jsworld.lottoproject.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseVmActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResID: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()


    private lateinit var launcher : ActivityResultLauncher<Intent>

    override fun initActivity() {

        binding.apply {
            etSearchWeeks.apply {

                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().isEmpty()) {
                            //tilMain.error = "공백은 허용하지 않습니다."
                        } else {
                            s.toString().toIntOrNull()?.let {
                                if (s != null) {
                                    if (s.length > 4) {
                                        tilMain.error = "4글자가 초과되었습니다."
                                    } else {
                                        tilMain.error = null
                                    }
                                }
                            } ?: run {
                                tilMain.error = "숫자를 입력하세요."
                            }
                        }
                    }

                })


                setOnEditorActionListener { v, actionId, event ->
                    return@setOnEditorActionListener when(actionId){
                        EditorInfo.IME_ACTION_SEARCH ->{
                            viewModel.setSearchWeeks(this.text.toString())
                            hideKeyboard()
                            this.text = null
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        viewModel.setObserves()

        launcher = registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult ->
            val data = result.data

            val intentResult: IntentResult? = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if(intentResult != null){
                //QRCode Scan 성공
                if(intentResult.contents != null){
                    //QRCode Scan result 있는경우
                    Log.d("lotto2", intentResult.contents.substring(0,24))
                    try {
                        if(intentResult.contents.substring(0,24) == "http://m.dhlottery.co.kr"){
                            ScanBarcodeDialog(this, intentResult.contents.substring(28,32), object : MoveClickListener{
                                override fun moveClick() {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentResult.contents))
                                    startActivity(intent)
                                }

                                override fun cancelClick() {

                                }

                            }).show()
                        }else{
                            toast("로또 QR코드가 아닙니다.")
                        }
                    }catch (e : StringIndexOutOfBoundsException){
                        toast("QR스캔에 실패했습니다.")
                    }catch (e : Exception){
                        toast("QR스캔에 실패했습니다.")
                    }

                }else{
                    //QRCode Scan result 없는경우
                    toast("인식된 QR-data가 없습니다.")
                }
            }else{
                //QRCode Scan 실패
                toast("QR스캔에 실패했습니다.")
            }

        }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun MainViewModel.setObserves(){
        repeatOnStarted{
            lottoNumber.collect{ it ->
                if(it.isNotEmpty()){
                    if(it[0] in 1..10){
                        binding.tvFirstNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                    } else if(it[0] in 11..20){
                        binding.tvFirstNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                    } else if(it[0] in 21..30){
                        binding.tvFirstNumber.setBackgroundResource(R.drawable.bg_circle_red)
                    } else if(it[0] in 31..40){
                        binding.tvFirstNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                    } else if (it[0] in 41..45){
                        binding.tvFirstNumber.setBackgroundResource(R.drawable.bg_circle_green)
                    }

                    if(it[1] in 1..10){
                        binding.tvSecondNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                    } else if(it[1] in 11..20){
                        binding.tvSecondNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                    } else if(it[1] in 21..30){
                        binding.tvSecondNumber.setBackgroundResource(R.drawable.bg_circle_red)
                    } else if(it[1] in 31..40){
                        binding.tvSecondNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                    } else if (it[1] in 41..45){
                        binding.tvSecondNumber.setBackgroundResource(R.drawable.bg_circle_green)
                    }

                    if(it[2] in 1..10){
                        binding.tvThirdNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                    } else if(it[2] in 11..20){
                        binding.tvThirdNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                    } else if(it[2] in 21..30){
                        binding.tvThirdNumber.setBackgroundResource(R.drawable.bg_circle_red)
                    } else if(it[2] in 31..40){
                        binding.tvThirdNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                    } else if (it[2] in 41..45){
                        binding.tvThirdNumber.setBackgroundResource(R.drawable.bg_circle_green)
                    }

                    if(it[3] in 1..10){
                        binding.tvFourthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                    } else if(it[3] in 11..20){
                        binding.tvFourthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                    } else if(it[3] in 21..30){
                        binding.tvFourthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                    } else if(it[3] in 31..40){
                        binding.tvFourthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                    } else if (it[3] in 41..45){
                        binding.tvFourthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                    }

                    if(it[4] in 1..10){
                        binding.tvFifthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                    } else if(it[4] in 11..20){
                        binding.tvFifthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                    } else if(it[4] in 21..30){
                        binding.tvFifthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                    } else if(it[4] in 31..40){
                        binding.tvFifthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                    } else if (it[4] in 41..45){
                        binding.tvFifthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                    }

                    if(it[5] in 1..10){
                        binding.tvSixthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                    } else if(it[5] in 11..20){
                        binding.tvSixthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                    } else if(it[5] in 21..30){
                        binding.tvSixthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                    } else if(it[5] in 31..40){
                        binding.tvSixthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                    } else if (it[5] in 41..45){
                        binding.tvSixthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                    }
                }

            }

        }

        repeatOnStarted{
            weeksLottoData.collect{
                if(it.returnValue){
                    when (it.drwtNo1) {
                        in 1..10 -> {
                            binding.tvWeeksFirstNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksFirstNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksFirstNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksFirstNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksFirstNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo2) {
                        in 1..10 -> {
                            binding.tvWeeksSecondNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksSecondNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksSecondNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksSecondNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksSecondNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo3) {
                        in 1..10 -> {
                            binding.tvWeeksThirdNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksThirdNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksThirdNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksThirdNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksThirdNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo4) {
                        in 1..10 -> {
                            binding.tvWeeksFourthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksFourthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksFourthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksFourthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksFourthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo5) {
                        in 1..10 -> {
                            binding.tvWeeksFifthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksFifthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksFifthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksFifthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksFifthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo6) {
                        in 1..10 -> {
                            binding.tvWeeksSixthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksSixthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksSixthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksSixthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksSixthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.bnusNo) {
                        in 1..10 -> {
                            binding.tvWeeksBnusNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvWeeksBnusNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvWeeksBnusNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvWeeksBnusNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvWeeksBnusNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }
                }
            }
        }

        repeatOnStarted {
            diffWeeks.collect{ it : Long ->
                if(it != 0.toLong()){
                    getRecentWinningNumber(it)
                }

            }
        }

        repeatOnStarted {
            searchWeeksNum.collect{
                getSearchWeeksNum(it.toLong())
            }
        }


        repeatOnStarted{
            searchLottoData.collect{
                if(it.returnValue){
                    when (it.drwtNo1) {
                        in 1..10 -> {
                            binding.tvSearchFirstNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchFirstNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchFirstNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchFirstNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchFirstNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo2) {
                        in 1..10 -> {
                            binding.tvSearchSecondNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchSecondNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchSecondNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchSecondNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchSecondNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo3) {
                        in 1..10 -> {
                            binding.tvSearchThirdNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchThirdNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchThirdNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchThirdNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchThirdNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo4) {
                        in 1..10 -> {
                            binding.tvSearchFourthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchFourthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchFourthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchFourthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchFourthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo5) {
                        in 1..10 -> {
                            binding.tvSearchFifthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchFifthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchFifthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchFifthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchFifthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.drwtNo6) {
                        in 1..10 -> {
                            binding.tvSearchSixthNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchSixthNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchSixthNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchSixthNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchSixthNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }

                    when (it.bnusNo) {
                        in 1..10 -> {
                            binding.tvSearchBnusNumber.setBackgroundResource(R.drawable.bg_circle_yellow)
                        }
                        in 11..20 -> {
                            binding.tvSearchBnusNumber.setBackgroundResource(R.drawable.bg_circle_blue)
                        }
                        in 21..30 -> {
                            binding.tvSearchBnusNumber.setBackgroundResource(R.drawable.bg_circle_red)
                        }
                        in 31..40 -> {
                            binding.tvSearchBnusNumber.setBackgroundResource(R.drawable.bg_circle_grey)
                        }
                        in 41..45 -> {
                            binding.tvSearchBnusNumber.setBackgroundResource(R.drawable.bg_circle_green)
                        }
                    }
                }
            }
        }

        repeatOnStarted{
            action.collect{
                when(it){
                    MainViewModel.LottoAction.QRCODE ->{
                        val intent = Intent(this@MainActivity, ScanBarcodeActivity::class.java)
                        launcher.launch(intent)
                    }

                    MainViewModel.LottoAction.SAVE ->{
                        if(lottoNumber.value.isNotEmpty()){
                            toast("저장")
                            saveLottoNumRepo()
                            //emptyLottoNum()
                        }else{
                            toast("번호가 없습니다.")
                        }
                    }
                }
            }
        }

    }

    fun hideKeyboard() {
        val inputManager: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로가기 클릭 시 실행시킬 코드 입력
                toast("뒤로가기")

        }

    }
}

class SaveNumberAdapter(vm : MainViewModel) : BaseRecyclerAdapter<String, ItemSavcNumberBinding>(
    R.layout.item_savc_number, vm
)