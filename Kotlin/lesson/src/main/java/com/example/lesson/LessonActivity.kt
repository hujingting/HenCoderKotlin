package com.example.lesson

import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import com.example.core.BaseView
import com.example.lesson.LessonPresenter
import com.example.lesson.LessonAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.os.Bundle
import android.view.MenuItem
import com.example.lesson.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.lesson.entity.Lesson

class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter>,
    Toolbar.OnMenuItemClickListener {

//    private val lessonPresenter = LessonPresenter(this)

//    override fun getPresenter() : LessonPresenter {
//        return lessonPresenter
//    }

    // by lazy : 访问的时候才会创建对象且只会创建一次
    override val p: LessonPresenter by lazy {
        return@lazy LessonPresenter(this)
    }

    private val lessonAdapter = LessonAdapter()
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lesson)
        toolbar.setOnMenuItemClickListener(this)

        findViewById<RecyclerView>(R.id.list).run {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = lessonAdapter
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayout.VERTICAL))
        }

        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).run {
            refreshLayout = this
            setOnRefreshListener(OnRefreshListener { p?.fetchData() })
            isRefreshing = true
        }

//        val recyclerView = findViewById<RecyclerView>(R.id.list)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = lessonAdapter
//        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
//
//        refreshLayout = findViewById(R.id.swipe_refresh_layout)
//        refreshLayout.setOnRefreshListener(OnRefreshListener { getPresenter().fetchData() })
//        refreshLayout.isRefreshing = true


        p.fetchData()
    }

    fun showResult(lessons: List<Lesson?>) {
        lessonAdapter.updateAndNotify(lessons)
        refreshLayout.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        p.showPlayback()
        return false
    }
}