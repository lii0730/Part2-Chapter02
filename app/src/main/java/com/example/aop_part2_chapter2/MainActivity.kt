package com.example.aop_part2_chapter2

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val btn_add: Button by lazy {
        findViewById(R.id.btn_add)
    }
    private val btn_reset: Button by lazy {
        findViewById(R.id.btn_reset)
    }
    private val btn_random: Button by lazy {
        findViewById(R.id.btn_random)
    }

    private val number_picker: NumberPicker by lazy {
        findViewById(R.id.number_picker)
    }

    private val textViewList: List<TextView> by lazy {
        listOf(
                findViewById(R.id.num1),
                findViewById(R.id.num2),
                findViewById(R.id.num3),
                findViewById(R.id.num4),
                findViewById(R.id.num5),
                findViewById(R.id.num6),
        )
    }

    private var num_list = mutableListOf<Int>() // 6개 숫자를 추가하기 위한 Collection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPickerNumberValue(1, 45)

        btn_add.setOnClickListener {

            num_list = InitAddButton().toMutableList()
            num_list.forEachIndexed { index, i ->
                val textView : TextView = textViewList.get(index)
                textView.text = i.toString()
                textView.isVisible = true
                setTextViewBackground(i, textView)
            }
        }

        btn_reset.setOnClickListener {

            InitResetButton()
        }

        btn_random.setOnClickListener {
            val list = InitRandomButton()

            list.forEachIndexed { index, number ->
                val textView = textViewList.get(index)
                setTextViewBackground(number, textView)
                textView.text = list[index].toString()
                textView.isVisible = true
            }
        }
    }

    private fun setPickerNumberValue(min: Int, max: Int) {
        number_picker.minValue = min
        number_picker.maxValue = max
    }

    private fun setTextViewBackground(number: Int, textview: TextView) {
        when (number) {
            in 1..10 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }

    private fun InitAddButton() : List<Int>{
        if(num_list.size < 6) {
            if(!num_list.contains(number_picker.value)){
                num_list.add(number_picker.value)
            }
        }
        return num_list.sorted()
    }

    private fun InitResetButton() {
        textViewList.forEach {
            it.text = ""
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
        num_list.clear()

        number_picker.value = 1
    }

    private fun InitRandomButton(): List<Int> {
        val list = mutableListOf<Int>()
        list.apply {
            for (i in 1..45) {
                if (num_list.contains(i)) {
                    continue
                }
                this.add(i)
            }
        }
        list.shuffle()

        val newList = num_list + list.subList(0, 6 - num_list.size)
        return newList.sorted()
    }
}