package com.analyzer.core.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.R

class HeaderLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var view: View
    private lateinit var ivBack: ImageView
    private lateinit var ivClose: ImageView
    private lateinit var textviewtitle: TextView

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.header_layout, this, true)
        textviewtitle = findViewById(R.id.tvTitle)
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
        textviewtitle.text = title
    }
}