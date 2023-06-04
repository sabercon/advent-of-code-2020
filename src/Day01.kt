fun main() {
    fun findSumOfTwo(nums: Set<Int>, sum: Int): Pair<Int, Int>? {
        return nums.find { (sum - it) in nums }
            ?.let { it to (sum - it) }
    }

    fun findSumOfThree(nums: Set<Int>, sum: Int): Triple<Int, Int, Int>? {
        return nums.firstNotNullOfOrNull {
            findSumOfTwo(nums, sum - it)
                ?.let { (a, b) -> Triple(a, b, it) }
        }
    }

    val input = readInput("Day01").map { it.toInt() }.toSet()

    findSumOfTwo(input, 2020)!!
        .let { (a, b) -> a * b }.println()

    findSumOfThree(input, 2020)!!
        .let { (a, b, c) -> a * b * c }.println()
}
