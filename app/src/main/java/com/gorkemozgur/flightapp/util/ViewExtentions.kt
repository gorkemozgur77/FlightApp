package com.gorkemozgur.flightapp.util

import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.setErrorDisableListener(textInputLayout: TextInputLayout){
    this.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus)
            textInputLayout.isErrorEnabled = false
    }

    this.addTextChangedListener {
        textInputLayout.isErrorEnabled = false
    }
}




