package com.garikcarrot.education

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.garikcarrot.education.fragments.choose.ChooseSubjectFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().reference.keepSynced(true)
        supportFragmentManager.beginTransaction()
                .add(R.id.container, ChooseSubjectFragment())
                .commit()
    }
}
