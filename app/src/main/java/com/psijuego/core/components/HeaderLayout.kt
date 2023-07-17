package com.psijuego.core.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.psijuego.R

class HeaderLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var view: View
    private lateinit var ivBack: ImageButton
    private lateinit var ivClose: ImageButton
    private lateinit var tvtitle: TextView

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.header_layout, this, true)
        tvtitle = findViewById(R.id.tvTitle)
        ivBack = findViewById(R.id.ivBack)
        ivClose = findViewById(R.id.ivClose)
        setUpComponents(attrs)
    }

    private fun setUpComponents(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderLayout, 0, 0)
        val title = a.getString(R.styleable.HeaderLayout_textHeader)
        val isClose = a.getBoolean(R.styleable.HeaderLayout_close, false)
        val isBack = a.getBoolean(R.styleable.HeaderLayout_back, true)

        ivBack.visibility = if (isBack) View.VISIBLE else View.GONE
        ivClose.visibility = if (isClose) View.VISIBLE else View.GONE
        tvtitle.text = title
    }

    fun setBackGroundColor(color: Int){
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint)
        constraintLayout.setBackgroundResource(color)
    }

    fun setBackDrawable(drawable: Drawable){
        ivBack.setImageDrawable(drawable)
    }

    fun setCloseDrawable(drawable: Drawable){
        ivClose.setImageDrawable(drawable)
    }

    fun setTitle(title: String) {
        tvtitle.text = title
    }

    fun showBack(show: Boolean) {
        ivBack.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showClose(show: Boolean) {
        ivClose.visibility = if (show) View.VISIBLE else View.GONE
    }
}