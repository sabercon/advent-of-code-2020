fun main() {
    fun modification1(mask: String, address: String, value: String): Map<String, String> {
        return value.zip(mask) { v, m -> if (m == 'X') v else m }
            .joinToString("")
            .let { mapOf(address to it) }
    }

    fun modification2(mask: String, address: String, value: String): Map<String, String> {
        return address.zip(mask) { a, m -> if (m == '0') a else m }
            .fold(listOf("")) { acc, c ->
                when (c) {
                    'X' -> acc.flatMap { listOf(it + '0', it + '1') }
                    else -> acc.map { it + c }
                }
            }.associateWith { value }
    }

    fun run(
        mask: String,
        memory: Map<Long, Long>,
        instruction: String,
        modificationFn: (String, String, String) -> Map<String, String>,
    ): Pair<String, Map<Long, Long>> {
        if (instruction.startsWith("mask")) {
            val newMask = instruction.split(" = ")[1]
            return newMask to memory
        }
        val (addressStr, valueStr) = """mem\[(\d+)] = (\d+)""".toRegex().destructureGroups(instruction)
        val address = addressStr.toLong().toString(2).padStart(36, '0')
        val value = valueStr.toLong().toString(2).padStart(36, '0')
        val newMemory = memory + modificationFn(mask, address, value)
            .map { (k, v) -> k.toLong(2) to v.toLong(2) }
        return mask to newMemory
    }

    fun run(instructions: List<String>, modificationFn: (String, String, String) -> Map<String, String>): Long {
        return instructions
            .fold("" to emptyMap<Long, Long>()) { (mask, memory), instruction ->
                run(mask, memory, instruction, modificationFn)
            }.second.values.sum()
    }

    val input = readLines("Day14")

    run(input, ::modification1).println()

    run(input, ::modification2).println()
}
