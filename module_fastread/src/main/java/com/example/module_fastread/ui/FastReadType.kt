package com.example.module_fastread.ui

/**
 * 快速阅读题型
 * @author wzh
 * @date 2023/5/29
 */
enum class FastReadType(val type: Int, val gameName: String) : java.io.Serializable {
    /**
     * 圆圈顺向移动
     */
    CIRCLE_CLOCKWISE(1, "圆圈顺向移动"),

    /**
     * 圆圈逆向移动
     */
    CIRCLE_ANTICLOCKWISE(2, "圆圈逆向移动"),

    /**
     * 五角星移动
     */
    STAR_PATH(3, "五角星移动"),

    /**
     * 横向8字移动
     */
    HORIZONTAL_8_PATH(4, "横向8字移动"),

    /**
     * 纵向8字移动
     */
    VERTICAL_8_PATH(5, "纵向8字移动"),


    /**
     * 横向之字移动
     */
    HORIZONTAL_ZHI_ZI_PATH(6, "横向之字移动"),

    /**
     * 纵向之字移动
     */
    VERTICAL_ZHI_ZI_PATH(7, "纵向之字移动"),

    /**
     * 对角线顺向移动
     */
    DIAGONAL_PATH(8, "对角线顺向移动"),

    /**
     * 对角线逆向移动
     */
    DIAGONAL_REVERSE_PATH(9, "对角线逆向移动"),

    /**
     * 圆周扩大
     */
    ENLARGE_CIRCLE(10, "圆周扩大"),

    /**
     * 矩形扩大
     */
    ENLARGE_RECT(11, "方框扩大"),

    /**
     * 箭头扩散
     */
    ARROW_SPREAD(12, "箭头扩散"),

    /**
     * 对角线数字移动
     */
    DIAGONAL_FLASH(13, "对角线数字移动"),

    /**
     * 凝视点训练
     */
    FOCAL_POINT(14, "凝视点训练"),

    /**
     * 圆环扩散
     */
    CIRCLE_SPREAD(15, "圆环扩散"),

    /**
     * 方框扩散
     */
    RECT_SPREAD(16, "方框扩散"),

    /**
     * 视点止移
     */
    RECT_FLASH_MOVE(17,"视点止移")

}