package br.com.rafaelbiasi.gameoflife

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JPanel

class Java2DRender(
    private val width: Int,
    private val height: Int,
    private val pixelWidth: Int = 2,
    private val pixelHeight: Int = 2
) : JPanel(), Render {
    private val canvas = BufferedImage(width * pixelWidth, height * pixelHeight, BufferedImage.TYPE_INT_RGB)
    private val frame = JFrame("Java2D Render")
    private val pixelStates = Array(height) { IntArray(width) }

    private val fireColorsPalette = arrayOf(
        Color(0, 0, 0),
        Color(255, 255, 255)
    )

    @Volatile
    private var running = true

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(this)
        frame.pack()
        frame.isVisible = true
    }

    override fun setPixel(column: Int, row: Int, state: Int) {
        if (column < 0 || column >= width || row < 0 || row >= height) return

        if (pixelStates[row][column] != state) {
            pixelStates[row][column] = state
            val color = fireColorsPalette[state]
            val graphics = canvas.graphics
            graphics.color = color
            graphics.fillRect(column * pixelWidth, row * pixelHeight, pixelWidth, pixelHeight)
        }
    }


    override fun setPixel(pixelIndex: Int, state: Int) {
        val column = pixelIndex % width
        val row = pixelIndex / width
        setPixel(column, row, state)
    }

    override fun render() {
        repaint()
    }

    override fun isRunning(): Boolean {
        return running
    }

    override fun close() {
        frame.dispose()
        running = false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.drawImage(canvas, 0, 0, this)
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(width * pixelWidth, height * pixelHeight)
    }
}
