package com.garikcarrot.education

import com.garikcarrot.education.models.Question
import com.google.firebase.database.*

object FirebaseRequests {
    private const val NAME = "name"
    private const val TOPICS = "topics"
    private const val QUESTIONS = "questions"
    private const val ANSWER = "answer"
    private const val QUESTION = "question"

    private fun getReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    fun getSubjects(onError: (String) -> Unit, onSubject: (List<String>) -> Unit) {
        getReference().addListenerForSingleValueEvent(object : Listener(onError) {
            override fun onDataChange(snapshot: DataSnapshot) {
                onSubject(snapshot.children.map { it.child(NAME).value.toString() })
            }
        })
    }

    fun getTopics(subject: String, onError: (String) -> Unit, onTopics: (List<String>) -> Unit) {
        getReference()
                .orderByChild("name").equalTo(subject)
                .addListenerForSingleValueEvent(object : Listener(onError) {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        onTopics(
                                snapshot.singleChild().child(TOPICS)
                                        .children.map { it.child(NAME).value.toString() }
                        )
                    }
                })
    }

    fun getAllQuestions(subject: String, onError: (String) -> Unit, onQuestions: (List<Question>) -> Unit) {
        getReference()
                .orderByChild(NAME).equalTo(subject)
                .addListenerForSingleValueEvent(object : Listener(onError) {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        onQuestions(snapshot.singleChild().child(TOPICS).children.map {
                            it.child(QUESTIONS).children.map { createQuestion(it) }
                        }.flatten())
                    }
                })
    }

    fun getQuestions(subject: String, topic: String, onError: (String) -> Unit, onQuestions: (List<Question>) -> Unit) {
        //Firebase can't multiply order
        getReference()
                .orderByChild(NAME).equalTo(subject)
                .addListenerForSingleValueEvent(object : Listener(onError) {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.singleChild().child(TOPICS).ref
                                .orderByChild(NAME).equalTo(topic)
                                .addListenerForSingleValueEvent(object : Listener(onError) {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        onQuestions(snapshot.singleChild().child(QUESTIONS)
                                                .children.map { createQuestion(it) })
                                    }
                                })
                    }
                })
    }

    private fun createQuestion(snapshot: DataSnapshot): Question {
        return Question(
                question = snapshot.child(QUESTION).value.toString(),
                answer = snapshot.child(ANSWER).children.map { it.value.toString() }
        )
    }
}

fun DataSnapshot.singleChild(): DataSnapshot {
    var result: DataSnapshot? = null
    children.forEach { result = it }
    return result!!
}

abstract class Listener(private val onError: (String) -> Unit) : ValueEventListener {
    override fun onCancelled(error: DatabaseError) {
        onError(error.message)
    }
}