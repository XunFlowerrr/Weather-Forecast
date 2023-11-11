package com.example.weatherapp.Domains

class Hourly {
    private var hour: String? = null
    private var temp: Int? = null
    private var picPath: String? = null

    constructor(hour: String?, temp: Int?, picPath: String?) {
        this.hour = hour
        this.temp = temp
        this.picPath = picPath
    }

    fun getHour(): String? {
        return hour
    }

    fun setHour(hour: String?) {
        this.hour = hour
    }

    fun getTemp(): Int? {
        return temp
    }

    fun setTemp(temp: Int?) {
        this.temp = temp
    }

    fun getPicPath(): String? {
        return picPath
    }

    fun setPicPath(picPath: String?) {
        this.picPath = picPath
    }




}