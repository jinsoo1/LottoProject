package com.jsworld.lottoproject.ui.view.scanner

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureManager
import com.jsworld.lottoproject.R
import com.jsworld.lottoproject.base.BaseVmActivity
import com.jsworld.lottoproject.databinding.ActivityScanbarcodeBinding
import java.util.*

class ScanBarcodeActivity : BaseVmActivity<ActivityScanbarcodeBinding, ScanBarcodeViewModel>(){
    override val layoutResID: Int = R.layout.activity_scanbarcode
    override val viewModel: ScanBarcodeViewModel by viewModels()

    private lateinit var capture : CaptureManager

    override fun initActivity() {


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        capture = CaptureManager(this, binding.zxingBarcodeScanner)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(true)
        capture.decode()

        changeMaskColor()
        changeLaserVisibility()

    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }
    override fun onPause() {
        super.onPause()
        capture.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }


    private fun changeMaskColor() {
        val rnd = Random()
        val color: Int = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        binding.zxingBarcodeScanner.viewFinder.setMaskColor(color)
    }

    private fun changeLaserVisibility() {
        binding.zxingBarcodeScanner.viewFinder.setLaserVisibility(true)
    }


}