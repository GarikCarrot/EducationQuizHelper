package com.garikcarrot.education.fragments.choose

import android.os.Bundle
import android.support.v4.app.Fragment
import com.garikcarrot.education.ArgumentsConst.SUBJECT
import com.garikcarrot.education.ArgumentsConst.TOPIC
import com.garikcarrot.education.R
import com.garikcarrot.education.fragments.questions.QuestionsLearnFragment
import com.garikcarrot.education.fragments.questions.QuestionsQuizFragment
import java.util.*

class ChooseTypeFragment : ChooseFragment() {
    companion object {
        private const val LEARN = "Learn"
        private const val QUIZ = "Quiz"

        fun getInstance(subject: String, topic: String): Fragment {
            val arguments = Bundle()
            val fragment = ChooseTypeFragment()
            arguments.putString(SUBJECT, subject)
            arguments.putString(TOPIC, topic)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun getListener(): (String) -> Unit {
        return {
            val subject = arguments.getString(SUBJECT)
            val topic = arguments.getString(TOPIC)
            val fragment = when (it) {
                LEARN -> QuestionsLearnFragment.getInstance(subject, topic)
                QUIZ -> QuestionsQuizFragment.getInstance(subject, topic)
                else -> QuestionsQuizFragment.getInstance(subject, topic)
            }
            activity.supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun load() {
        setData(Arrays.asList(LEARN, QUIZ))
    }
}