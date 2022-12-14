package com.closeratio.aoc2022.day15

import com.closeratio.aoc.common.AocTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@AocTest
class SensorStateParserTest {

    @Autowired
    private lateinit var beaconStateParser: SensorStateParser

    @Test
    fun parseSensorState_invalidBeaconPositions_returnsExpectedResult() {
        val state = beaconStateParser.parseSensorState("/test_input.txt")
        val result = state.invalidBeaconPositions(10)

        assertThat(result).isEqualTo(26)
    }

    @Test
    fun parseSensorState_invalidBeaconPositionsRealData_returnsExpectedResult() {
        val state = beaconStateParser.parseSensorState("/2022_day_15_input.txt")
        val result = state.invalidBeaconPositions(2_000_000)

        assertThat(result).isEqualTo(5108096)
    }

    @Test
    fun parseSensorState_computeTuningFrequency_returnsExpectedResult() {
        val state = beaconStateParser.parseSensorState("/test_input.txt")
        val result = state.computeTuningFrequency(20)

        assertThat(result).isEqualTo(56_000_011)
    }

}
