package com.example.doreamon.entity

/**
 * @author wzh
 * @date 2023/3/16
 */
data class ChatMessage(
    var isExpand: Boolean = false,
    var type: Int = 0,
    var quoteMsg: String =
        "留言信息内容留言信息内容留言信息内容留言信息内容" +
                "留言信息内容留言信息内容留言信息内容留言信息" +
                "内容留言信息内容留言信息内容留言信息内容留" +
                "言信息内容留言信息内容留言信息内容留言信息内容留言信息内容留言信" +
                "息内容留言信息内容留言信息内容留言信息内容留言信息内" +
                "容留言信息内容留言信息内容留言信息内容",
    var receiveMsg: String =
        "回复信息内容回复信息内容回复信息内容回复信息" +
                "内容回复信息内容回复信息" +
                "内容回复信息内容回复信息内容回复" +
                "信息内容回复信息内容回复信息内容回复信息内容"
)