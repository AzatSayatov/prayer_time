package com.sayatcode.core.model

sealed class Region (val name: String){
    object ASHGABAT: Region("ashgabat")
    object AHAL: Region("ahal")
    object MARY: Region("mary")
    object DASHOGUZ: Region("dashoguz")
    object BALKAN: Region("balkan")
    object LEBAP: Region("lebap")
}