fun main() {
    tailrec fun run(instruction: List<Pair<String, Int>>, acc: Int, i: Int, visited: Set<Int>): Pair<Boolean, Int> {
        if (i in visited) return false to acc
        if (i == instruction.size) return true to acc

        val (op, arg) = instruction[i]
        val (newAcc, j) = when (op) {
            "acc" -> acc + arg to i + 1
            "jmp" -> acc to i + arg
            "nop" -> acc to i + 1
            else -> error("Unknown instruction $op")
        }
        return run(instruction, newAcc, j, visited + i)
    }

    val input = readLines("Day08").map {
        val (op, arg) = it.split(" ")
        op to arg.toInt()
    }

    run(input, 0, 0, emptySet()).second.println()

    for (i in input.indices) {
        val (op, arg) = input[i]
        val newOp = when (op) {
            "jmp" -> "nop"
            "nop" -> "jmp"
            else -> continue
        }
        val newInput = input.withIndex()
            .map { (j, instruction) -> if (i == j) newOp to arg else instruction }

        val (terminated, acc) = run(newInput, 0, 0, emptySet())
        if (terminated) {
            acc.println()
            break
        }
    }
}
