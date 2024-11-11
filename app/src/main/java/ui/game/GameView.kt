package com.example.tetrisgamegroup11.ui.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.tetrisgamegroup11.manager.GameManager
import com.example.tetrisgamegroup11.model.GamePiece

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private var cellSize = 60
    private lateinit var gameManager: GameManager

    // Phương thức thiết lập GameManager sau khi khởi tạo
    fun setGameManager(manager: GameManager) {
        this.gameManager = manager
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cellSize = minOf(w / gameManager.gameGrid.columns, h / gameManager.gameGrid.rows)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGrid(canvas)           // Vẽ lưới
        drawPlacedPieces(canvas)    // Vẽ các khối đã xếp trong lưới
        drawCurrentPiece(canvas)    // Vẽ khối hiện tại
    }

    private fun drawGrid(canvas: Canvas) {
        paint.color = Color.LTGRAY
        for (i in 0 until gameManager.gameGrid.columns) {
            for (j in 0 until gameManager.gameGrid.rows) {
                val left = i * cellSize.toFloat()
                val top = j * cellSize.toFloat()
                val right = left + cellSize
                val bottom = top + cellSize
                paint.style = Paint.Style.FILL
                canvas.drawRect(left, top, right, bottom, paint)

                paint.color = Color.DKGRAY
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 2f
                canvas.drawRect(left, top, right, bottom, paint)

                paint.color = Color.LTGRAY
            }
        }
    }

    private fun drawPlacedPieces(canvas: Canvas) {
        gameManager.gameGrid.colors.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, color ->
                if (color != Color.TRANSPARENT) {
                    paint.color = color
                    val left = colIndex * cellSize.toFloat()
                    val top = rowIndex * cellSize.toFloat()
                    val right = left + cellSize
                    val bottom = top + cellSize
                    paint.style = Paint.Style.FILL
                    canvas.drawRect(left, top, right, bottom, paint)

                    paint.color = Color.BLACK
                    paint.style = Paint.Style.STROKE
                    canvas.drawRect(left, top, right, bottom, paint)

                    paint.style = Paint.Style.FILL
                }
            }
        }
    }

    private fun drawCurrentPiece(canvas: Canvas) {
        drawPieceAtPosition(
            canvas,
            gameManager.currentPiece,
            gameManager.currentPiece.positionX.toFloat(),
            gameManager.currentPiece.positionY.toFloat()
        )
    }

    private fun drawPieceAtPosition(canvas: Canvas, piece: GamePiece, x: Float, y: Float) {
        piece.shape.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (cell == 1) {
                    val left = (x + colIndex) * cellSize
                    val top = (y + rowIndex) * cellSize
                    val right = left + cellSize
                    val bottom = top + cellSize
                    paint.color = piece.type.getColor()
                    paint.style = Paint.Style.FILL
                    canvas.drawRect(left, top, right, bottom, paint)

                    paint.color = Color.BLACK
                    paint.style = Paint.Style.STROKE
                    canvas.drawRect(left, top, right, bottom, paint)

                    paint.style = Paint.Style.FILL
                }
            }
        }
    }

    fun reset() {
        invalidate()
    }

    fun pauseGame() {
        // Thực hiện các thao tác khi tạm dừng
    }

    fun resumeGame() {
        // Thực hiện các thao tác khi tiếp tục trò chơi
    }
}
