package br.com.rafaelbiasi.gameoflife

interface Render {

    fun setPixel(column: Int, row: Int, state: Int)
    fun setPixel(pixelIndex: Int, state: Int)
    fun render()
    fun isRunning(): Boolean
    fun close()
}
