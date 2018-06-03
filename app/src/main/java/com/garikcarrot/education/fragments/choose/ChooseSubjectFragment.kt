package com.garikcarrot.education.fragments.choose

import android.view.View
import android.widget.Toast
import com.garikcarrot.education.FirebaseRequests
import com.garikcarrot.education.R

class ChooseSubjectFragment : ChooseFragment() {
    override fun load() {
        FirebaseRequests.getSubjects(
                onError = {
                    Toast.makeText(activity, "Subjects not loaded", Toast.LENGTH_LONG).show()
                },
                onSubject = {
                    setData(it)
                }
        )
    }

    override fun getListener(): (String) -> Unit {
        return {
            activity.supportFragmentManager.beginTransaction()
                    .add(R.id.container, ChooseTopicFragment.getInstance(it))
                    .addToBackStack(null)
                    .commit()
        }
    }
}