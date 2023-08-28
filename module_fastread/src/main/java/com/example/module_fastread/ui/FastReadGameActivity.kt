package com.example.module_fastread.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.module_base.base.BaseActivity
import com.example.module_base.base.BaseViewModel
import com.example.module_fastread.R
import com.example.module_fastread.databinding.FastreadActivityGameBinding

/**
 * 快速阅读游戏界面
 * @author wzh
 * @date 2023/5/26
 */
class FastReadGameActivity : BaseActivity<BaseViewModel>() {

    lateinit var gameTitle: String
    lateinit var gameFragment: Fragment

    override fun setupLayoutId() = R.layout.fastread_activity_game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = getViewBinding<FastreadActivityGameBinding>()
        binding.ivBack.setOnClickListener { finish() }

        val gameConfig = intent.extras?.getSerializable("gameConfig") as? GameConfig

        if (gameConfig != null) {

            val fragment = setGameFragment(gameConfig)
            val bundle = Bundle()
            bundle.putSerializable("gameConfig", gameConfig)
            fragment.arguments = bundle

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fl_content, fragment)
                    .commit()
            }

            gameTitle = gameConfig.gameType.gameName
            binding.toolbarTitle.text = gameTitle
        }
    }


    private fun setGameFragment(gameConfig: GameConfig): Fragment {

        return when (gameConfig.gameType) {
            FastReadType.CIRCLE_CLOCKWISE -> {
                PathAnimatorFragment()
            }
            else -> PathAnimatorFragment()
        }
    }

    companion object {
        fun actionStart(
            packageContext: Context,
            gameConfig: GameConfig
        ) {
            val intent = Intent(packageContext, FastReadGameActivity::class.java)
            intent.putExtra("gameConfig", gameConfig)
            packageContext.startActivity(intent)
        }
    }


}