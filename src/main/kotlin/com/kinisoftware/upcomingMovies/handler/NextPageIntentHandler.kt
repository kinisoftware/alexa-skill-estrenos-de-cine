package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.alexa.presentation.apl.ExecuteCommandsDirective
import com.amazon.ask.model.interfaces.alexa.presentation.apl.Position
import com.amazon.ask.model.interfaces.alexa.presentation.apl.SetPageCommand
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.supportAPL
import java.util.Optional

class NextPageIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.supportAPL() && input.matches(Predicates.intentName("NextPageIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val directive = ExecuteCommandsDirective.builder()
                .withToken("newReleasesSkillAPLToken")
                .addCommandsItem(SetPageCommand.builder()
                        .withComponentId("moviesPager")
                        .withPosition(Position.RELATIVE)
                        .withValue(1)
                        .build()
                )
                .build()

        return input.responseBuilder.addDirective(directive).build()
    }
}
