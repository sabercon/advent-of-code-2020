fun main() {
    fun evaluate1(expression: List<String>): Long {
        val ops = ArrayDeque<String>()
        val nums = ArrayDeque<Long>()
        for (token in expression) {
            when (token) {
                "+", "*", "(" -> {
                    ops.add(token)
                    continue
                }

                ")" -> ops.removeLast()
                else -> nums.add(token.toLong())
            }
            if (ops.lastOrNull() in listOf("+", "*")) {
                when (ops.removeLast()) {
                    "+" -> nums.add(nums.removeLast() + nums.removeLast())
                    "*" -> nums.add(nums.removeLast() * nums.removeLast())
                }
            }
        }
        return nums.single()
    }

    fun evaluate2(expression: List<String>): Long {
        val ops = ArrayDeque<String>()
        val nums = ArrayDeque<Long>()
        for (token in expression) {
            when (token) {
                "+", "*", "(" -> {
                    ops.add(token)
                    continue
                }

                ")" -> while (ops.removeLast() != "(") nums.add(nums.removeLast() * nums.removeLast())
                else -> nums.add(token.toLong())
            }
            if (ops.lastOrNull() == "+") {
                ops.removeLast()
                nums.add(nums.removeLast() + nums.removeLast())
            }
        }
        return nums.single()
    }

    val input = readLines("Day18")
        .map { "($it)".replace("(", "( ").replace(")", " )").split(" ") }

    input.sumOf { evaluate1(it) }.println()

    input.sumOf { evaluate2(it) }.println()
}
