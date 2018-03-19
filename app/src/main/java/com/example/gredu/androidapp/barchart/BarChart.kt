package com.example.gredu.androidapp.barchart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint.Align
import android.graphics.Paint
import android.view.View


class GraphView(context: Context, values: FloatArray?, title: String?, horlabels: Array<String>, verlabels: Array<String>, private val type: Boolean) : View(context) {

    private val paint: Paint
    private var values: FloatArray? = null
    private var horlabels: Array<String>
    private var verlabels: Array<String>
    private var title: String? = null

    private val max: Float
        get() {
            var largest = Integer.MIN_VALUE.toFloat()
            for (i in values!!.indices)
                if (values!![i] > largest)
                    largest = values!![i]
            return largest
        }

    private val min: Float
        get() {
            var smallest = Integer.MAX_VALUE.toFloat()
            for (i in values!!.indices)
                if (values!![i] < smallest)
                    smallest = values!![i]
            return smallest
        }

    init {
        var values = values
        var title = title
        if (values == null)
            values = FloatArray(0)
        else
            this.values = values
        if (title == null)
            title = ""
        else
            this.title = title
        if (horlabels == null)
            this.horlabels = arrayOf("")
        else
            this.horlabels = horlabels
        if (verlabels == null)
            this.verlabels = arrayOf("")
        else
            this.verlabels = verlabels
        paint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        val border = 20f
        val horstart = border * 2
        val height = getHeight().toFloat()
        val width = (getWidth() - 1).toFloat()
        val max = max
        val min = min
        val diff = max - min
        val graphheight = height - 2 * border
        val graphwidth = width - 2 * border

        paint.setTextAlign(Align.LEFT)
        val vers = verlabels!!.size - 1
        for (i in verlabels!!.indices) {
            paint.setColor(Color.DKGRAY)
            val y = graphheight / vers * i + border
            canvas.drawLine(horstart, y, width, y, paint)
            paint.setColor(Color.WHITE)
            canvas.drawText(verlabels!![i], 0F, y, paint)
        }
        val hors = horlabels!!.size - 1
        for (i in horlabels!!.indices) {
            paint.setColor(Color.DKGRAY)
            val x = graphwidth / hors * i + horstart
            canvas.drawLine(x, height - border, x, border, paint)
            paint.setTextAlign(Align.CENTER)
            if (i == horlabels!!.size - 1)
                paint.setTextAlign(Align.RIGHT)
            if (i == 0)
                paint.setTextAlign(Align.LEFT)
            paint.setColor(Color.WHITE)
            canvas.drawText(horlabels!![i], x, height - 4, paint)
        }

        paint.setTextAlign(Align.CENTER)
        canvas.drawText(title, graphwidth / 2 + horstart, border - 4, paint)

        if (max != min) {
            paint.setColor(Color.LTGRAY)
            if (type == BAR) {
                val datalength = values!!.size.toFloat()
                val colwidth = (width - 2 * border) / datalength
                for (i in values!!.indices) {
                    val `val` = values!![i] - min
                    val rat = `val` / diff
                    val h = graphheight * rat
                    canvas.drawRect(i * colwidth + horstart, border - h + graphheight, i * colwidth + horstart + (colwidth - 1), height - (border - 1), paint)
                }
            } else {
                val datalength = values!!.size.toFloat()
                val colwidth = (width - 2 * border) / datalength
                val halfcol = colwidth / 2
                var lasth = 0f
                for (i in values!!.indices) {
                    val `val` = values!![i] - min
                    val rat = `val` / diff
                    val h = graphheight * rat
                    if (i > 0)
                        canvas.drawLine((i - 1) * colwidth + (horstart + 1) + halfcol, border - lasth + graphheight, i * colwidth + (horstart + 1) + halfcol, border - h + graphheight, paint)
                    lasth = h
                }
            }
        }
    }

    companion object {

        var BAR = true
        var LINE = false
    }

}