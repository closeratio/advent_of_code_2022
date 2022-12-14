package com.closeratio.aoc2022.day17

import com.closeratio.aoc.common.ResourceLoader
import com.closeratio.aoc2022.day17.Direction.LEFT
import com.closeratio.aoc2022.day17.Direction.RIGHT
import org.springframework.stereotype.Component

@Component
class GameStateSimulationParser(
    private val resourceLoader: ResourceLoader
) {

    fun parse(path: String): GameStateSimulation = resourceLoader
        .loadResourceLines(path)
        .first()
        .map { char ->
            when (char) {
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw IllegalArgumentException("Unhandled character: $char")
            }
        }
        .let { directions ->
            GameState(
                emptySet(),
                0,
                directions,
                0,
                0
            )
        }
        .let(::GameStateSimulation)

}

