package com.jsworld.lottoproject.util

import javax.inject.Inject

class CreateRandomNumber @Inject constructor() {

    fun createRandomNumber() : List<Int>{

        val lottoNumbers = mutableListOf<Int>()
        val lottoNumbersResult= mutableListOf<Int>()

        for (i in 1..45) {
            lottoNumbers.add(i)
        }

        lottoNumbers.shuffle()

        for (i in 0..5) {
            lottoNumbersResult.add(lottoNumbers[i])
        }

        lottoNumbersResult.sort()

        return lottoNumbersResult

    }
}