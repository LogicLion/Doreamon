package com.example.doreamon.ui.simple

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2024/2/19
 */
class CustomBottomEditTextDialog(context: Context) : AboveInputMethodDialog(context) {
    var etInput: EditText? = null
    override fun getContextViewResource() = R.layout.dialog_custom_bottom_edittext

    override fun getEditText(): EditText? {
        return etInput
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        etInput = findViewById<EditText>(R.id.et_input)
    }
}