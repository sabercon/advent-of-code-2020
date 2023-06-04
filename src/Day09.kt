fun main() {
    fun isValid(nums: List<Long>, i: Int): Boolean {
        if (i < 25) return true
        val preamble = nums.subList(i - 25, i).toSet()
        return preamble.any { it * 2 != nums[i] && preamble.contains(nums[i] - it) }
    }

    tailrec fun findRange(nums: List<Long>, target: Long, start: Int, end: Int, sum: Long): List<Long> {
        return when {
            end == nums.size -> error("No range found")
            start + 1 == end || sum < target -> findRange(nums, target, start, end + 1, sum + nums[end])
            sum > target -> findRange(nums, target, start + 1, end, sum - nums[start])
            else -> nums.subList(start, end)
        }
    }

    val input = readLines("Day09").map { it.toLong() }

    val num = input.withIndex().first { (i, _) -> !isValid(input, i) }.value
    num.println()

    val range = findRange(input, num, 0, 2, input[0] + input[1])
    println(range.min() + range.max())
}
