package com.zc

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/*
ADA8600 ~ ADA8699
ADH3500 ~ ADH3599
AD59900 ~ AD59999
 */
data class CarIdRange(val from: Int, val to: Int, val size: Int)


/*
59999
59995
59959
...
59933
 */

fun hasSameCharNum(str: String):
        Map<Char,Int> {
    val map = HashMap<Char, Int>(str.length)
    str.forEach { victim ->
        val previous = map.getOrDefault(victim, 0)
        map.put(victim, previous + 1)
    }
    return map
}

class MostIdenticalComparable(val size:Int,
                              val strategy:(o1:Int,o2:Int,repeatNumber1:Int,repeatGroup1:Int,
                                            repeatNumber2:Int,repeatGroup2:Int)->Int)
    : Comparator<Int> {
    override fun compare(o1: Int?, o2: Int?): Int {
        if (o1 == o2) {
            return 0
        } else {
            val o1Str = String.format("%${size}s",o1.toString())
            val o2Str = String.format("%${size}s",o2.toString())

            val r1 = hasSameCharNum(o1Str)
            val r2 = hasSameCharNum(o2Str)

            val repeatNumber1 = r1.values.max()!!
            val repeatGroup1 = r1.size


            val repeatNumber2 = r2.values.max()!!
            val repeatGroup2 = r2.size

            return  strategy(o1!!,o2!!,repeatNumber1, repeatGroup1, repeatNumber2, repeatGroup2)


            return 0
        }
    }
}

val strategyOneRepeatNumberFirst:(Int,Int,Int,Int,Int,Int)->Int ={o1,o2, repeatNumber1:Int, repeatGroup1:Int, repeatNumber2:Int, repeatGroup2:Int->
    if(repeatNumber1 != repeatNumber2){
        (repeatNumber1 - repeatNumber2)  //相同数字越多越好
    }else{
        if(repeatGroup1!=repeatGroup2){
            repeatGroup2 - repeatGroup1   //group越少越好
        }else{
            //如果 重复号码相同 重复组号码相同，那么我们要对比这个数字的大小即可
            o1 - o2
        }
    }
}

val strategyOneRepeatGroupFirst:(Int,Int,Int,Int,Int,Int)->Int ={o1,o2, repeatNumber1:Int, repeatGroup1:Int, repeatNumber2:Int, repeatGroup2:Int->
    if(repeatGroup1!= repeatGroup2){
        (repeatGroup2 - repeatGroup1)
    }else{
        if(repeatNumber1!=repeatNumber2){
            repeatNumber1-repeatNumber2
        }else{
            //如果 重复号码相同 重复组号码相同，那么我们要对比这个数字的大小即可
            o1 - o2
        }
    }
}

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val list = ArrayList<Int>()
            for(i in 59900..59999){
                list.add(i)
            }
            Collections.sort(list,MostIdenticalComparable(5, strategyOneRepeatGroupFirst))
            list.reverse()
            list.forEach{
                println("$it")
            }
        }
    }
}