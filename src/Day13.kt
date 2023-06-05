fun main() {
    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

    val input = readLines("Day13")
    val time = input[0].toLong()
    val buses = input[1].split(",").map { if (it == "x") 0 else it.toLong() }

    buses.filter { it != 0L }
        .map { it to (it - time % it) % it }
        .minBy { it.second }
        .run { println(first * second) }

    buses.withIndex().filter { it.value != 0L }
        .fold(0L to 1L) { (time, step), (index, bus) ->
            val nextTime = generateSequence(time) { it + step }.first { (it + index) % bus == 0L }
            val nextStep = lcm(step, bus)
            nextTime to nextStep
        }.first.println()
}
