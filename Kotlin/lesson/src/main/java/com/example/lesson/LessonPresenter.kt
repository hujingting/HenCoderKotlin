package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient.get
import com.example.core.utils.toast
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken

class LessonPresenter(private var activity: LessonActivity?) {

    //编译器常量的定义 （java： private static final String LESSON_PATH = "lessons";）
    companion object {
        private const val LESSON_PATH = "lessons"
    }

    private var lessons: List<Lesson?> = ArrayList()

    private val type = object : TypeToken<List<Lesson?>?>() {}.type

    fun fetchData() {
        get(LESSON_PATH, type, object : EntityCallback<List<Lesson?>> {
            override fun onSuccess(lessons: List<Lesson?>) {
                //引用外部类
                this@LessonPresenter.lessons = lessons
                activity!!.runOnUiThread { activity!!.showResult(lessons) }
            }

            override fun onFailure(message: String?) {
                activity!!.runOnUiThread { toast(message!!) }
            }
        })
    }

    fun showPlayback() {
//        val playbackLessons: MutableList<Lesson?> = ArrayList()
//        for (lesson in lessons) {
//            if (lesson?.state === Lesson.State.PLAYBACK) {
//                playbackLessons.add(lesson)
//            }
//        }
        
        val filter = lessons.filter { it?.state === Lesson.State.PLAYBACK }
        activity!!.showResult(filter)
    }
}