typealias Seats = List<List<Char>>
typealias Point = Pair<Int, Int>
typealias Direction = Pair<Int, Int>
typealias Rule = (Seats, Point, Direction) -> Boolean

fun main() {
    fun hasAdjacentOccupiedSeats(seats: Seats, point: Point, direction: Direction): Boolean {
        val (i, j) = point
        val (di, dj) = direction
        return seats.getOrNull(i + di)?.getOrNull(j + dj) == '#'
    }

    fun hasSeenOccupiedSeats(seats: Seats, point: Point, direction: Direction): Boolean {
        val (i, j) = point
        val (di, dj) = direction
        return generateSequence(i + di to j + dj) { (i, j) -> i + di to j + dj }
            .map { (i, j) -> seats.getOrNull(i)?.getOrNull(j) }
            .dropWhile { it == '.' }
            .first() == '#'
    }

    fun seenOccupiedSeats(seats: Seats, point: Point, occupiedRule: Rule): Int {
        return (-1..1).flatMap { i -> (-1..1).map { j -> i to j } }
            .filter { (i, j) -> i != 0 || j != 0 }
            .count { occupiedRule(seats, point, it) }
    }

    fun move(seats: Seats, occupiedLimit: Int, occupiedRule: Rule): Seats {
        return seats.mapIndexed { i, row ->
            row.mapIndexed { j, seat ->
                when {
                    seat == 'L' && seenOccupiedSeats(seats, i to j, occupiedRule) == 0 -> '#'
                    seat == '#' && seenOccupiedSeats(seats, i to j, occupiedRule) >= occupiedLimit -> 'L'
                    else -> seat
                }
            }
        }
    }

    tailrec fun finalOccupiedSeats(seats: Seats, occupiedLimit: Int, occupiedRule: Rule): Int {
        val nextSeats = move(seats, occupiedLimit, occupiedRule)
        return if (nextSeats == seats) {
            seats.sumOf { row -> row.count { it == '#' } }
        } else {
            finalOccupiedSeats(nextSeats, occupiedLimit, occupiedRule)
        }
    }

    val input = readLines("Day11").map { it.toList() }

    finalOccupiedSeats(input, 4, ::hasAdjacentOccupiedSeats).println()

    finalOccupiedSeats(input, 5, ::hasSeenOccupiedSeats).println()
}
