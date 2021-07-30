package Logger

import java.io.PrintWriter

object Log {
    private val objects = ArrayList<String>()
    private val commits = ArrayList<String>()
    fun add(`object`: String, commit: String) {
        objects.add(`object`)
        commits.add(commit)
    }
    fun clear() {
        objects.clear()
        commits.clear()
    }
    fun print_log() {
        val printWriter = PrintWriter(System.out, true)
        for (i in objects.indices) {
            printWriter.println(commits[i])
            printWriter.println(objects[i])
        }
    }
    fun get_log() : ArrayList<String>{
        val answer = ArrayList<String>()
        for (i in 0 until objects.size){
            answer.add(objects[i])
            answer.add(commits[i])
        }
        return answer
    }
}