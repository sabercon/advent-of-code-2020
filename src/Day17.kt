fun main() {
    abstract class Point(val dimension: Int) {
        abstract fun neighbors(): List<Point>

        protected fun deltas(): List<List<Int>> {
            val seq = generateSequence(listOf(emptyList<Int>())) { deltas ->
                deltas.flatMap { delta -> (-1..1).map { delta + it } }
            }
            return seq.elementAt(dimension).filterNot { it.all { d -> d == 0 } }
        }
    }

    data class Point3D(val x: Int = 0, val y: Int = 0, val z: Int = 0) : Point(3) {
        override fun neighbors(): List<Point> {
            return deltas().map { (dx, dy, dz) -> Point3D(x + dx, y + dy, z + dz) }
        }
    }

    data class Point4D(val x: Int = 0, val y: Int = 0, val z: Int = 0, val w: Int = 0) : Point(4) {
        override fun neighbors(): List<Point> {
            return deltas().map { (dx, dy, dz, dw) -> Point4D(x + dx, y + dy, z + dz, w + dw) }
        }
    }

    fun process(points: Set<Point>): Set<Point> {
        return (points + points.flatMap { it.neighbors() })
            .filter { point ->
                val activeNeighbors = point.neighbors().count { it in points }
                if (point in points) {
                    activeNeighbors == 2 || activeNeighbors == 3
                } else {
                    activeNeighbors == 3
                }
            }
            .toSet()
    }

    fun processSequence(points: Set<Point>): Sequence<Set<Point>> {
        return generateSequence(points) { process(it) }
    }

    val input = readLines("Day17")

    input.flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c == '#') Point3D(x, y) else null } }
        .let { processSequence(it.toSet()) }
        .elementAt(6)
        .size.println()

    input.flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c == '#') Point4D(x, y) else null } }
        .let { processSequence(it.toSet()) }
        .elementAt(6)
        .size.println()
}
