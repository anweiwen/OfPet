package com.cn.ql.frame.widget

import android.content.Context
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * @author axw_an on 2018/6/12.
 * 注：不给输入emoji
 */
class ContainsEmojiEditText : AppCompatEditText {
    /**
     * 输入表情前的光标位置
     */
    private var cursorPos: Int = 0
    /**
     * 输入表情前EditText中的文本
     */
    private var inputAfterText: String? = null
    /**
     * 是否重置了EditText的内容
     */
    private var resetText: Boolean = false

    constructor(context: Context) : super(context) {
        initEditText()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initEditText()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initEditText()
    }

    /**
     * 初始化edittext 控件
     */
    private fun initEditText() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // 这里用s.toString()而不直接用s是因为如果用s，
                // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                // inputAfterText也就改变了，那么表情过滤就失败了
                if (!resetText) {
                    cursorPos = selectionEnd
                    inputAfterText = s.toString()
                }

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!resetText) {
                    //表情符号的字符长度最小为2
                    if (count >= 2) {
                        val len = s.length
                        val temp = cursorPos + count
                        val input = s.subSequence(if (len > cursorPos) cursorPos else 0, if (len > temp) temp else len)
                        if (containsEmoji(input.toString())) {
                            resetText = true
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            setText(inputAfterText)
                            val text = text
                            if (text is Spannable) {
                                val spanText = text as Spannable?
                                Selection.setSelection(spanText, text.length)
                            }
                        }
                    }
                } else {
                    resetText = false
                }
            }

            override fun afterTextChanged(s: Editable) {

            }

        })
    }

    companion object {


        /**
         * 检测是否有emoji表情
         *
         * @param source
         * @return
         */
        fun containsEmoji(source: String): Boolean {
            val len = source.length
            for (i in 0 .. len) {
                if (source.length <= i){
                    return false
                }
                val codePoint = source[i]
                //如果不能匹配,则该字符是Emoji表情
                if (!isEmojiCharacter(codePoint)) {
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否是Emoji
         * @param codePoint 比较的单个字符
         * @return
         */
        private fun isEmojiCharacter(codePoint: Char): Boolean {
            return codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA ||
                    codePoint.toInt() == 0xD || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF ||
                    codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF
        }
    }

}
