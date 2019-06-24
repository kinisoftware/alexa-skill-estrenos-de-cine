package com.kinisoftware.upcomingMovies.interceptor

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor
import com.google.gson.Gson

class LogRequestInterceptor(private val gson: Gson) : RequestInterceptor {
    override fun process(input: HandlerInput) {
        println("Request: ${gson.toJson(input)}")
    }
}