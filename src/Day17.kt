fun main() {
    abstract class Point {
        abstract fun neighbors(): List<Point>
    }

    data class Point3D(val x: Int = 0, val y: Int = 0, val z: Int = 0) : Point() {
        override fun neighbors(): List<Point> {
            return (-1..1).flatMap { dx -> (-1..1).map { dy -> dx to dy } }
                .flatMap { (dx, dy) -> (-1..1).map { dz -> Triple(dx, dy, dz) } }
                .filter { (dx, dy, dz) -> dx != 0 || dy != 0 || dz != 0 }
                .map { (dx, dy, dz) -> Point3D(x + dx, y + dy, z + dz) }
        }
    }

    data class Point4D(val x: Int = 0, val y: Int = 0, val z: Int = 0, val w: Int = 0) : Point() {
        override fun neighbors(): List<Point> {
            return (-1..1).flatMap { dx -> (-1..1).map { dy -> dx to dy } }
                .flatMap { (dx, dy) -> (-1..1).map { dz -> Triple(dx, dy, dz) } }
                .flatMap { (dx, dy, dz) -> (-1..1).map { dw -> listOf(dx, dy, dz, dw) } }
                .filter { (dx, dy, dz, dw) -> dx != 0 || dy != 0 || dz != 0 || dw != 0 }
                .map { (dx, dy, dz, dw) -> Point4D(x + dx, y + dy, z + dz, w + dw) }
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
