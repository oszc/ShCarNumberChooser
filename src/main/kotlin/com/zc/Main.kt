package com.zc

data class CarIdRange(val from:Int,val to:Int,val size:Int)
class Main {

    companion object{

        @JvmStatic
        fun main(args:Array<String>){
            println("Hello World")
            val carIdRange = CarIdRange(1,10000,5)
        }
    }
}