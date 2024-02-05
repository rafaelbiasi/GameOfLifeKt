package br.com.rafaelbiasi.gameoflife

fun main() {
    val pixelWidth = 2
    val pixelHeight = 2
    val widthRatio = 16
    val heightRatio = 9
    val sizeMultiplication = 100

    val fireWidth: Int = (widthRatio * sizeMultiplication / pixelWidth).toInt()
    val fireHeight: Int = (heightRatio * sizeMultiplication / pixelHeight).toInt()
    val render = Java2DRender(fireWidth, fireHeight, pixelWidth, pixelHeight)
    val doomFire = GameOfLife(fireWidth, fireHeight)

    GameLoop(doomFire::start, doomFire::newGeneration, { doomFire.renderGame(render) }, render::isRunning).start()
}
