package com.example.myetp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class DrawView(context: Context?, gameState: GameState) : View(context) {
    var yOffset: Int
    var paint: Paint
    var gameState: GameState

    init {
        paint = Paint()
        paint.color = Color.BLUE
        yOffset = 200
        this.gameState = gameState
    }

    private fun getBlockColorCode(color: Int): Int {
        return when (color) {
            1 -> Color.BLUE
            2 -> Color.YELLOW
            3 -> Color.RED
            4 -> Color.GREEN
            5 -> Color.CYAN
            6 -> Color.MAGENTA
            7 -> Color.DKGRAY
            else -> Color.TRANSPARENT
        }
    }

    private fun DrawMatrix(matrix: Array<Array<BasicBlock?>>, canvas: Canvas) {
        for (i in 0..23) {
            for (j in 0..19) {
                if (matrix[i][j]!!.state === BasicBlockState.ON_EMPTY) continue
                val color = getBlockColorCode(matrix[i][j]!!.colour)
                val p = Paint()
                p.color = color
                canvas.drawRect(
                    (42 + j * 50).toFloat(),
                    (yOffset + i * 50 + 2).toFloat(),
                    (88 + j * 50).toFloat(),
                    (yOffset + (i + 1) * 50 - 2).toFloat(),
                    p
                )
            }
        }
    }

    private fun Clear(matrix: Array<Array<BasicBlock?>>, canvas: Canvas) {
        val p = Paint()
        p.color = Color.WHITE
        for (i in 0..23) {
            for (j in 0..19) {
                canvas.drawRect(
                    (42 + j * 50).toFloat(),
                    (yOffset + i * 50 + 2).toFloat(),
                    (88 + j * 50).toFloat(),
                    (yOffset + (i + 1) * 50 - 2).toFloat(),
                    p
                )
            }
        }
    }

    private fun DrawTetramino(tetramino: Tetramino, canvas: Canvas) {
        for (block in tetramino.blocks) {
            val color = getBlockColorCode(block!!.colour)
            val p = Paint()
            p.color = color
            canvas.drawRect(
                (42 + block.coordinate.x * 50).toFloat(),
                (yOffset + block.coordinate.y * 50 + 2).toFloat(),
                (88 + block.coordinate.x * 50).toFloat(),
                (yOffset + (block.coordinate.y + 1) * 50 - 2).toFloat(),
                p
            )
        }
    }

    private fun Boundary(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
        canvas.drawLine(40f, yOffset.toFloat(), 40f, (yOffset + 1200).toFloat(), paint)
        canvas.drawLine(40f, yOffset.toFloat(), 1040f, yOffset.toFloat(), paint)
        canvas.drawLine(1040f, yOffset.toFloat(), 1040f, (yOffset + 1200).toFloat(), paint)
        canvas.drawLine(1040f, (yOffset + 1200).toFloat(), 40f, (yOffset + 1200).toFloat(), paint)
    }

    private fun grid(canvas: Canvas) {
        paint.strokeWidth = 2f
        var i = 90
        while (i < 1040) {
            canvas.drawLine(
                i.toFloat(),
                yOffset.toFloat(),
                i.toFloat(),
                (yOffset + 1200).toFloat(),
                paint
            )
            i = i + 50
        }
        var j = 50
        while (j < 1200) {
            canvas.drawLine(40f, (yOffset + j).toFloat(), 1040f, (yOffset + j).toFloat(), paint)
            j = j + 50
        }
    }

    private fun PrintScore(score: Int, canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.TRANSPARENT
        canvas.drawRect(0f, 100f, 200f, 200f, paint)
        paint.color = Color.BLACK
        paint.textSize = 100f
        canvas.drawText(Integer.toString(score), 80f, 170f, paint)
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
        Boundary(canvas)
        grid(canvas)
        if (gameState.status) {
            Clear(gameState.board, canvas)
            DrawMatrix(gameState.board, canvas)
            DrawTetramino(gameState.falling, canvas)
            PrintScore(gameState.score, canvas)
        } else {
            val paint = Paint()
            DrawMatrix(gameState.board, canvas)
            DrawTetramino(gameState.falling, canvas)
            paint.color = Color.BLACK
            paint.textSize = 200f
            canvas.drawText(resources.getString(R.string.game_over), 60f, 800f, paint)
            PrintScore(gameState.score, canvas)
        }
    }
}