package com.kinisoftware.upcomingMovies.interceptor

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor
import com.google.gson.Gson

class LogRequestInterceptor : RequestInterceptor {
    override fun process(input: HandlerInput) {
        println("Request: ${Gson().toJson(input)}")
    }
}