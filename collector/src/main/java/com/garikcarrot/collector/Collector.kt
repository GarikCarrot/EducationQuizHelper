package com.garikcarrot.collector

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.*
import java.net.URLEncoder


object Collector {
    @JvmStatic
    fun main(args: Array<String>) {
        val root = File("/home/garik-carrot/IdeaProjects/EducationQuizHelper/questions")
        val result = JsonArray()
        result.addAll(dirs(root) { collectSubject(it) })
        FileWriter(File(root, "questions.json")).use({ writer ->
            GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(result, writer)
        })
    }

    private fun collectSubject(folder: File): JsonObject {
        val subject = JsonObject()
        val topics = JsonArray()
        val name = folder.name
        topics.addAll(dirs(folder) { collectTopic(it) })

        val other = JsonObject()
        val questions = JsonArray()
        questions.addAll(files(folder))
        other.addProperty("name", "other")
        other.add("questions", questions)
        if (questions.size() != 0) {
            topics.add(other)
        }

        subject.addProperty("name", name)
        subject.add("topics", topics)
        return subject
    }

    private fun collectTopic(folder: File): JsonObject {
        val topic = JsonObject()
        val name = folder.name
        val questions = JsonArray()
        questions.addAll(files(folder))
        topic.addProperty("name", name)
        topic.add("questions", questions)
        return topic
    }

    private fun collectQuestions(file: File): JsonArray {
        val questions = JsonArray()
        BufferedReader(InputStreamReader(FileInputStream(file))).use { input ->

            var q = input.readLine()
            while (q != null) {
                val question = JsonObject()
                val answer = JsonArray()
                var a = input.readLine()
                while (a != null && a.trim().isNotEmpty()) {
                    answer.add(URLEncoder.encode(a, "UTF-8"))
                    a = input.readLine()
                }
                question.addProperty("question", q)
                question.add("answer", answer)
                questions.add(question)
                q = input.readLine()
            }
        }
        return questions
    }

    private fun dirs(folder: File, map: (File) -> JsonElement): JsonArray {
        val result = JsonArray()
        folder.listFiles()
                .filter { it.isDirectory }
                .filter { it.name != ".git" }
                .map(map)
                .forEach { result.add(it) }
        return result
    }

    private fun files(folder: File): JsonArray {
        val result = JsonArray()
        folder.listFiles()
                .filter { it.isFile }
                .filter { it.name.matches("q[0-9]+.txt".toRegex()) }
                .map { collectQuestions(it) }
                .forEach { result.addAll(it) }
        return result
    }
}
