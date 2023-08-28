package com.example.module_fastread.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.module_fastread.R
import com.example.module_fastread.databinding.FastreadFragmentFastReadListBinding

/**
 * @author wzh
 * @date 2023/5/26
 */
class FastReadListFragment : BaseFragment<BaseViewModel>() {

    private val typeList = listOf(
        FastReadType.CIRCLE_CLOCKWISE,
        FastReadType.CIRCLE_ANTICLOCKWISE,
        FastReadType.STAR_PATH,
        FastReadType.HORIZONTAL_8_PATH,
        FastReadType.VERTICAL_8_PATH,
        FastReadType.HORIZONTAL_ZHI_ZI_PATH,
        FastReadType.VERTICAL_ZHI_ZI_PATH,
        FastReadType.DIAGONAL_PATH,
        FastReadType.DIAGONAL_REVERSE_PATH,
        FastReadType.ENLARGE_CIRCLE,
        FastReadType.ENLARGE_RECT,
        FastReadType.ARROW_SPREAD,
        FastReadType.DIAGONAL_FLASH,
        FastReadType.FOCAL_POINT,
        FastReadType.CIRCLE_SPREAD,
        FastReadType.RECT_SPREAD,
        FastReadType.RECT_FLASH_MOVE
    )

    override fun setupLayoutId() = R.layout.fastread_fragment_fast_read_list

    override fun initView() {

        val binding = getViewBinding<FastreadFragmentFastReadListBinding>()
        val adapter = FastReadListAdapter()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireActivity())

        adapter.setList(typeList)

        adapter.setOnItemClickListener { adapter, view, position ->

            var duration = 3
            var repeat = 1

            val timeStr = binding.etDuration.text.toString().trim()
            if (timeStr.isNotEmpty()) {
                duration = timeStr.toInt()
            }

            val repeatStr = binding.etRepeat.text.toString().trim()
            if (repeatStr.isNotEmpty()) {
                repeat = repeatStr.toInt()
            }

            val config = GameConfig(
                typeList[position], duration, repeat
            )

            if (typeList[position] == FastReadType.RECT_FLASH_MOVE) {
                config.extra = RectFlashMoveData(5)
            }
            FastReadGameActivity.actionStart(requireActivity(), config)
        }


    }
}