package com.closeratio.aoc2022.day13

import com.closeratio.aoc.common.ResourceLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class PacketPairParser(
    private val resourceLoader: ResourceLoader
) {

    fun parsePacket(line: String): Packet {
        val lineQueue = LinkedList(line.map(Char::toString))
        val stack = LinkedList<InnerListItem>()

        val root = InnerListItem()
        var curr = root
        // Pop the first one because we know it'll be an opening bracket
        lineQueue.pop()
        // Take each character from the queue while there are still items to take
        while (lineQueue.isNotEmpty()) {
            var currString = lineQueue.pop()

            if (currString.toIntOrNull() != null) {
                while (lineQueue.peek().toIntOrNull() != null) {
                    currString += lineQueue.pop()
                }
                curr.items.add(IntegerItem(currString.toInt()))
            } else if (currString == "[") {
                stack.push(curr)

                val inner = InnerListItem()
                curr.items.add(inner)
                curr = inner
            } else if (currString == "]" && stack.isNotEmpty()) {
                curr = stack.pop()
            }
        }

        return Packet(root)
    }

    fun parsePair(
        firstLine: String,
        secondLine: String
    ): PacketPair = PacketPair(
        parsePacket(firstLine),
        parsePacket(secondLine)
    )

    private fun parse(path: String): List<PacketPair> = resourceLoader
        .loadResourceLines(path)
        .chunked(3)
        .map { (first, second) -> parsePair(first, second) }

    fun sumCorrectOrderPairs(path: String): Int = parse(path)
        .mapIndexedNotNull { index, pair ->
            if (pair.inOrder()) index + 1 else null
        }
        .sum()

    fun computeDecoderKey(path: String): Int {
        val firstDividerPacket = parsePacket("[[2]]")
        val secondDividerPacket = parsePacket("[[6]]")

        return resourceLoader
            .loadResourceLines(path)
            .filter(String::isNotEmpty)
            .map(::parsePacket)
            .let {
                it + listOf(firstDividerPacket, secondDividerPacket)
            }
            .sorted()
            .mapIndexedNotNull { index, packet ->
                if (packet == firstDividerPacket || packet == secondDividerPacket) {
                    index + 1
                } else {
                    null
                }
            }
            .reduce(Int::times)

    }

}
