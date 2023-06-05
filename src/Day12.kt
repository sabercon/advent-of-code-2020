import kotlin.math.absoluteValue

fun main() {
    val directions = listOf('E', 'N', 'W', 'S')

    data class Position1(val east: Int = 0, val north: Int = 0, val direction: Char = 'E') {
        fun move(action: Char, value: Int): Position1 = when (action) {
            'E' -> copy(east = east + value)
            'N' -> copy(north = north + value)
            'W' -> copy(east = east - value)
            'S' -> copy(north = north - value)
            'L' -> copy(direction = directions[(directions.indexOf(direction) + value / 90).mod(4)])
            'R' -> copy(direction = directions[(directions.indexOf(direction) - value / 90).mod(4)])
            'F' -> move(direction, value)
            else -> throw IllegalArgumentException("Unknown Action: $action")
        }

        fun distance() = east.absoluteValue + north.absoluteValue
    }

    data class Position2(val east: Int = 0, val north: Int = 0, val wpEast: Int = 10, val wyNorth: Int = 1) {
        fun move(action: Char, value: Int): Position2 = when (action) {
            'E' -> copy(wpEast = wpEast + value)
            'N' -> copy(wyNorth = wyNorth + value)
            'W' -> copy(wpEast = wpEast - value)
            'S' -> copy(wyNorth = wyNorth - value)
            'L' -> {
                if (value == 0) this
                else copy(wpEast = -wyNorth, wyNorth = wpEast).move('L', value - 90)
            }

            'R' -> {
                if (value == 0) this
                else copy(wpEast = wyNorth, wyNorth = -wpEast).move('R', value - 90)
            }

            'F' -> copy(east = east + wpEast * value, north = north + wyNorth * value)
            else -> throw IllegalArgumentException("Unknown Action: $action")
        }

        fun distance() = east.absoluteValue + north.absoluteValue
    }

    val input = readLines("Day12").map { it[0] to it.drop(1).toInt() }

    input.fold(Position1()) { position, (action, value) -> position.move(action, value) }.distance().println()

    input.fold(Position2()) { position, (action, value) -> position.move(action, value) }.distance().println()
}
