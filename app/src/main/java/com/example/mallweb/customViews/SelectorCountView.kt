package com.example.mallweb.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mallweb.R

class SelectorCountView @JvmOverloads constructor(context: Context, private val attrs: AttributeSet?= null, private var defStyleAttr: Int = 0): LinearLayout(context, attrs, defStyleAttr){

    lateinit var imgAgregar: ImageButton
    lateinit var imgRemover: ImageButton
    private lateinit var txtNumber: TextView
    var text = ""
    private var cantMax = CantTops.CANTIDAD_MAXIMA
    private var cantMin = CantTops.CANTIDAD_MINIMA


    init {
        View.inflate(context, R.layout.customview_selectorcount, this)
        initializeAttrs()
        setupUI()
        addOnClicked()
        remOnClicked()
        orientation = HORIZONTAL
    }

    private fun initializeAttrs() {
        attrs?.let {
            val typedArray =
                context.theme.obtainStyledAttributes(
                    it,
                    R.styleable.SelectorCountView,
                    defStyleAttr,
                    0
                )
            cantMax = typedArray.getInt(R.styleable.SelectorCountView_cantidadMaxima,
                CantTops.CANTIDAD_MAXIMA
            )
            cantMin = typedArray.getInt(R.styleable.SelectorCountView_cantidadMinima,
                CantTops.CANTIDAD_MINIMA
            )
        }
    }

    private fun addOnClicked() {
        imgAgregar.setOnClickListener{plus()}
    }

    private fun remOnClicked() {
        imgRemover.setOnClickListener {minus()}
    }

    @SuppressLint("SetTextI18n")
    fun plus() {
        txtNumber.text = (txtNumber.text.toString().toInt()+1).toString()
        text = (txtNumber.text.toString().toInt()).toString()
        checkQuantity()
    }

    @SuppressLint("SetTextI18n")
    fun minus() {
        txtNumber.text = (txtNumber.text.toString().toInt()-1).toString()
        text = (txtNumber.text.toString().toInt()).toString()
        checkQuantity()
    }

    private fun checkQuantity(){
        when(txtNumber.text.toString().toInt()) {
            cantMin -> imgRemover.isEnabled = false
            cantMax -> imgAgregar.isEnabled = false
            else -> {
                imgRemover.isEnabled = true
                imgAgregar.isEnabled = true
            }
        }
    }

    private fun setupUI() {
        imgAgregar = findViewById(R.id.imgAgregar)
        imgRemover = findViewById(R.id.imgRemover)
        if (text == "0" && txtNumber.text == "0") { imgRemover.isEnabled = false }
        txtNumber = findViewById(R.id.txtNumber)
    }

    fun getText(): Int {
        return Integer.parseInt(txtNumber.text.toString())
    }

    fun setText(number: Int) {
        text = number.toString()
        txtNumber.text = number.toString()
    }


}