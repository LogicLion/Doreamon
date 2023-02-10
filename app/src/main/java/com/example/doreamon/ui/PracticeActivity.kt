package com.example.doreamon.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.doreamon.R
import com.example.doreamon.StudyTopic
import com.doreamon.treasure.base.BaseActivity
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.ActivityPracticeBinding

/**
 * @author wzh
 * @date 2022/5/7
 */
class PracticeActivity : BaseActivity<BaseViewModel>() {
    var fragmentTag: String = ""
    private val fragmentTagKey = "FRAGMENT_TAG_KEY"
    override fun setupLayoutId() = R.layout.activity_practice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = getViewBinding<ActivityPracticeBinding>()
        val targetTopic = intent?.extras?.getSerializable("targetTopic") as? StudyTopic

        binding.tvTitle.text = targetTopic?.title
        if (savedInstanceState != null) {
        //异常时让Activity自动重建fragment就行，此时若有需要可以通过findFragmentByTag找到
        //supportFragmentManager.findFragmentByTag(TopicListFragment::class.java.simpleName)
        } else {
            targetTopic?.let {
                fragmentTag = it.fragmentTag
                val fragment = Class.forName(fragmentTag).newInstance() as Fragment
                supportFragmentManager.beginTransaction()
                    .add(R.id.fl_content, fragment, fragmentTag).commit()
            }
        }


//        lifecycle.addObserver()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(fragmentTagKey, fragmentTag)
    }

    companion object {
        fun actionStart(context: Context, studyTopic: StudyTopic) {
            val intent = Intent(context, PracticeActivity::class.java)
            intent.putExtra("targetTopic", studyTopic)
            context.startActivity(intent)
        }
    }
}