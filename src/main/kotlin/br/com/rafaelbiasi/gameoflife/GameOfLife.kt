package br.com.rafaelbiasi.gameoflife;

import kotlin.random.Random

class GameOfLife(private val width: Int = 50, private val height: Int = 50) {

    private val cells = Array(height) { BooleanArray(width) }
    private val offsets = arrayOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    fun start() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                cells[y][x] = Random.nextInt(5) == 1
            }
        }
    }

    fun renderGame(render: Render) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                render.setPixel(x, y, if (cells[y][x]) 1 else 0)
            }
        }
        render.render()
    }

    fun newGeneration() {
        val nextGen = Array(height) { BooleanArray(width) }
        for (y in 0 until height) {
            for (x in 0 until width) {
                val aliveNeighbors = countAliveNeighbors(x, y)
                nextGen[y][x] = cells[y][x] && aliveNeighbors in 2..3 || !cells[y][x] && aliveNeighbors == 3
            }
        }
        for (y in 0 until height) {
            System.arraycopy(nextGen[y], 0, cells[y], 0, width)
        }
    }

    private fun countAliveNeighbors(x: Int, y: Int): Int = offsets.count { (dx, dy) ->
        val nx = x + dx
        val ny = y + dy
        nx in 0 until width && ny in 0 until height && cells[ny][nx]
    }
}
