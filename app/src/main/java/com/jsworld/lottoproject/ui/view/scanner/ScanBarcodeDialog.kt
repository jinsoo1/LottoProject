package com.jsworld.lottoproject.ui.view.scanner

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.jsworld.lottoproject.R
import com.jsworld.lottoproject.databinding.DialogMoveUrlBinding
import com.jsworld.lottoproject.util.MoveClickListener

class ScanBarcodeDialog(context: Context, private var title : String, val listener: MoveClickListener) : Dialog(context) {

    private lateinit var binding : DialogMoveUrlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_move_url,
            null,
            false
        )

        this.window?.setBackgroundDrawableResource(android.R.color.transparent)

        setContentView(binding.root)

        val window = this.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        if (title.substring(0,1) == "0"){
            title = title.substring(1,4)
        }
        binding.tvRound.text = " $title "

        binding.tvCancel.setOnClickListener {
            listener.cancelClick()
            dismiss()
        }

        binding.tvMove.setOnClickListener {
            listener.moveClick()
            dismiss()
        }

    }

}