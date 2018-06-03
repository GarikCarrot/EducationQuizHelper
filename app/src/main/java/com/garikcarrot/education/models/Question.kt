package com.garikcarrot.education.models

class Question(
        val question: String,
        val answer: List<String>
) {
    fun buildAnswer(): String {
        val builder = StringBuilder()
        answer.forEach { builder.append(it).append("\n") }
        return builder.toString()
    }
}