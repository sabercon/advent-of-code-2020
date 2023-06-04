fun main() {
    fun findTwoWithSum(nums: Set<Int>, sum: Int): Pair<Int, Int>? {
        return nums.find { (sum - it) in nums }
            ?.let { it to (sum - it) }
    }

    fun findThreeWithSum(nums: Set<Int>, sum: Int): Triple<Int, Int, Int>? {
        return nums.firstNotNullOfOrNull {
            findTwoWithSum(nums, sum - it)
                ?.let { (a, b) -> Triple(a, b, it) }
        }
    }

    val input = readLines("Day01").map { it.toInt() }.toSet()

    findTwoWithSum(input, 2020)!!
        .let { (a, b) -> a * b }.println()

    findThreeWithSum(input, 2020)!!
        .let { (a, b, c) -> a * b * c }.println()
}
