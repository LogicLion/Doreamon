package com.example.doreamon.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.doreamon.R

/**
 * 通用确认弹窗
 * @author wzh
 * @date 2021/9/9
 */
class CommonConfirmDialog(context: Context) : Dialog(context) {
    private val tvMessage: TextView
    private val tvTitle: TextView
    private val tvOk: TextView
    private val tvCancel: TextView

    private var onPositiveClickListener: View.OnClickListener? = null
    private var onNegativeClickListener: View.OnClickListener? = null

    init {
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_common_confirm, null)
        tvTitle = view.findViewById(R.id.tv_dialog_title)
        tvMessage = view.findViewById(R.id.tv_content)
        tvOk = view.findViewById(R.id.tv_ok)
        tvOk.setOnClickListener {
            dismiss()
            onPositiveClickListener?.onClick(tvOk)
        }
        tvCancel = view.findViewById(R.id.tv_cancel)
        tvCancel.setOnClickListener {
            if (onNegativeClickListener != null) {
                dismiss()
                onNegativeClickListener?.onClick(tvCancel)
            } else {
                dismiss()
            }
        }
        setContentView(view)
        val dialogWindow: Window? = this.getWindow()
        dialogWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun setMessage(title: String?): CommonConfirmDialog {
        tvMessage.visibility = View.VISIBLE
        tvMessage.text = title
        return this
    }

    fun setTitle(message: String?): CommonConfirmDialog {
        tvTitle.text = message
        return this
    }

    fun setMessageTextFromHtml(message: String): CommonConfirmDialog {
        tvMessage.text = Html.fromHtml(message)
        return this
    }

    fun setPositiveButtonText(text: String?): CommonConfirmDialog {
        tvOk.text = text
        return this
    }

    fun setNegativeButtonText(text: String?): CommonConfirmDialog {
        tvCancel.visibility = View.VISIBLE
        tvCancel.text = text
        return this
    }

    fun setPositiveButton(text: String?, listener: View.OnClickListener?): CommonConfirmDialog {
        tvOk.text = text
        onPositiveClickListener = listener
        return this
    }

    fun setNegativeButton(text: String?, listener: View.OnClickListener): CommonConfirmDialog {
        tvCancel.visibility = View.VISIBLE
        tvCancel.text = text
        onNegativeClickListener = listener
        return this
    }

    fun setNegativeButtonGone(isGone: Boolean): CommonConfirmDialog {
        if (isGone) {
            tvCancel.visibility = View.GONE
        } else {
            tvCancel.visibility = View.VISIBLE
        }

        return this
    }
}