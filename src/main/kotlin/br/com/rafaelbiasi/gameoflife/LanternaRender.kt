package br.com.rafaelbiasi.gameoflife

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import java.awt.Window
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent


class LanternaRender(private val width: Int, private val height: Int) : Render {

    private val terminal = DefaultTerminalFactory()
        .setInitialTerminalSize(TerminalSize(width, height))
        .createTerminal()

    private val screen = TerminalScreen(terminal);

    private var running: Boolean = true

    private val fireColorsPalette = arrayOf(
        RGB(0, 0, 0),
        RGB(255, 255, 255)
    )

    init {
        screen.startScreen()

        when (val window = screen.terminal) {
            is Window -> window.addWindowListener(object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent) {
                    running = false
                }
            })
        }
    }

    override fun setPixel(column: Int, row: Int, state: Int) {
        val color = fireColorsPalette[state]

        screen.setCharacter(
            column,
            row,
            TextCharacter.fromCharacter(' ')[0].withBackgroundColor(TextColor.RGB(color.r, color.g, color.b))
        )
    }

    override fun setPixel(pixelIndex: Int, state: Int) {
        val column = pixelIndex % width
        val row = pixelIndex / width
        setPixel(column, row, state)
    }

    override fun render() {
        screen.refresh()
    }

    override fun close() {
        screen.close()
    }

    override fun isRunning(): Boolean {
        return running
    }
}
