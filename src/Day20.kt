fun main() {
    data class Tile(val id: Int, val data: List<String>) {
        val top = data.first()
        val bottom = data.last()
        val left = data.map { it.first() }.joinToString("")
        val right = data.map { it.last() }.joinToString("")

        fun flip() = copy(data = data.reversed())

        fun rotate() = copy(data = data.indices.map { i -> data.map { it[i] }.joinToString("") }).flip()

        fun possibleOriginalTiles(): List<Tile> {
            return generateSequence(listOf(this)) { it + it.last().rotate() }
                .elementAt(3)
                .flatMap { listOf(it, it.flip()) }
        }

        fun image(): List<String> = data.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }
    }

    fun arrangeTiles(tiles: List<Tile>): List<List<Tile>> {
        val allPossibleTiles = tiles.flatMap { it.possibleOriginalTiles() }
        val topEdgeTilesMap = allPossibleTiles.groupBy { it.top }
        val leftEdgeTilesMap = allPossibleTiles.groupBy { it.left }

        val topLeftCorner = topEdgeTilesMap.filterValues { it.size == 1 }.map { it.value[0] }
            .intersect(leftEdgeTilesMap.filterValues { it.size == 1 }.map { it.value[0] }.toSet())
            .first()
        val arrangement = mutableListOf(mutableListOf(topLeftCorner))
        while (arrangement.size * arrangement.last().size < tiles.size) {
            val lastRow = arrangement.last()
            val lastTile = lastRow.last()
            if (leftEdgeTilesMap[lastTile.right]!!.size == 1) {
                val nextTile = topEdgeTilesMap[lastRow.first().bottom]!!.single { it.id != lastRow.first().id }
                arrangement.add(mutableListOf(nextTile))
            } else {
                val nextTile = leftEdgeTilesMap[lastTile.right]!!.single { it.id != lastTile.id }
                lastRow.add(nextTile)
            }
        }

        return arrangement
    }

    fun mergeTiles(arrangement: List<List<Tile>>): List<String> {
        return arrangement.flatMap { row ->
            val images = row.map { it.image() }
            images[0].indices.map { i ->
                images.joinToString("") { it[i] }
            }
        }
    }

    val monster = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    )
    val monsterIndexes = monster.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c -> if (c == '#') x to y else null }
    }

    fun hasMonster(image: List<String>, x: Int, y: Int): Boolean {
        return monsterIndexes.all { (dx, dy) ->
            image.getOrNull(y + dy)?.getOrNull(x + dx) == '#'
        }
    }

    fun findMonsters(image: List<String>): Int {
        return image.indices.sumOf { y -> image[y].indices.count { x -> hasMonster(image, x, y) } }
    }

    val input = readText("Day20").split(LINE_SEPARATOR + LINE_SEPARATOR)
    val tiles = input.map {
        val lines = it.lines()
        val (id) = """Tile (\d+):""".toRegex().destructureGroups(lines[0])
        Tile(id.toInt(), lines.drop(1))
    }

    val arrangement = arrangeTiles(tiles)
    (1L * arrangement.first().first().id * arrangement.first().last().id *
            arrangement.last().first().id * arrangement.last().last().id).println()

    val image = mergeTiles(arrangement)
    val monsters = Tile(0, image)
        .possibleOriginalTiles()
        .map { findMonsters(it.data) }
        .single { it > 0 }
    (image.sumOf { row -> row.count { it == '#' } } - monsters * 15).println()
}
