package com.example.doreamon.entity

import android.view.View
import java.io.Serializable

/**
 * 弹窗信息
 * @author wzh
 * @date 2021/12/31
 */
data class AlertDialogModel(
    val title: String? = "",
    val content: String? = "",
    val actionText: String? = "",
    val cancelText: String? = "",
    val cancelBtnGone: Boolean = false,
    val onAction: View.OnClickListener? = null,
):Serializable