package com.garikcarrot.education.fragments.choose

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.garikcarrot.education.ArgumentsConst.ALL
import com.garikcarrot.education.ArgumentsConst.SUBJECT
import com.garikcarrot.education.FirebaseRequests
import com.garikcarrot.education.R

class ChooseTopicFragment : ChooseFragment() {
    override fun load() {

        FirebaseRequests.getTopics(
                subject = arguments.getString(SUBJECT),
                onError = {
                    Toast.makeText(activity, "Topic not loaded", Toast.LENGTH_LONG).show()
                },
                onTopics = {
                    val result = ArrayList<String>()
                    result.addAll(it)
                    result.add(ALL)
                    setData(result)
                }
        )
    }

    override fun getListener(): (String) -> Unit {
        return {
            val fragment = ChooseTypeFragment.getInstance(arguments.getString(SUBJECT), it)
            activity.supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    companion object {
        fun getInstance(subject: String): Fragment {
            val arguments = Bundle()
            val fragment = ChooseTopicFragment()
            arguments.putString(SUBJECT, subject)
            fragment.arguments = arguments
            return fragment
        }
    }


}