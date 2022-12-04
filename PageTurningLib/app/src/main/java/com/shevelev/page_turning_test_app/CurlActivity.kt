package com.shevelev.page_turning_test_app

import android.app.Activity
import android.content.Intent
import android.graphics.Outline
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.page_turning_lib.page_curling.CurlView
import com.shevelev.page_turning_lib.page_curling.CurlViewEventsHandler
import com.shevelev.page_turning_lib.page_curling.textures_manager.PageLoadingEventsHandler
import com.shevelev.page_turning_lib.user_actions_managing.Area
import com.shevelev.page_turning_lib.user_actions_managing.Point

class CurlActivity : AppCompatActivity() {
    private var curlView: CurlView? = null

    private var currentPageIndex: Int? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_curl)

        currentPageIndex = savedInstanceState?.getInt(CURRENT_PAGE) ?: intent.getIntExtra(START_PAGE, 0)

        val rawResourcesBitmapProvider = RawResourcesBitmapProvider(this)

//        val tripView = layoutInflater.inflate(R.layout.layout_trip, null)
//        getBitmap(tripView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}
        val tripView = TripView(this)
//        tripView.setData()
        tripView.getBitmap()?.let {
            rawResourcesBitmapProvider.bitmaps.add(it)
        }

        val humanView = layoutInflater.inflate(R.layout.layout_human, null)
        getBitmap(humanView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}

        val foodView = layoutInflater.inflate(R.layout.layout_food, null)
        getBitmap(foodView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}

        val petView = layoutInflater.inflate(R.layout.layout_pet, null)
        getBitmap(petView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}

        val sceneView = layoutInflater.inflate(R.layout.layout_scene, null)
        getBitmap(sceneView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}

        val plantsView = layoutInflater.inflate(R.layout.layout_plants, null)
        getBitmap(plantsView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}

        val babyView = layoutInflater.inflate(R.layout.layout_baby, null)
        getBitmap(babyView)?.let { rawResourcesBitmapProvider.bitmaps.add(it)}

        curlView = (findViewById<View>(R.id.curl) as? CurlView)?.also {
//            it.setRoundRectShape(20f)
            it.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    val rect = Rect()
                    view?.getGlobalVisibleRect(rect)
                    val rectSelf = Rect(0, 0, rect.right - rect.left, rect.bottom - rect.left)
                    outline?.setRoundRect(rectSelf, 20f)
                }

            }
            it.clipToOutline = true
            it.setBitmapProvider(rawResourcesBitmapProvider)
            it.initCurrentPageIndex(currentPageIndex!!)
//            it.setBackgroundColor(Color.BLUE)
            it.setHotAreas(listOf(Area(0, Point(0, 0), Size(100, 100))))

            it.setExternalEventsHandler(object: CurlViewEventsHandler {
                override fun onPageChanged(newPageIndex: Int) {
                    currentPageIndex = newPageIndex
                }

                override fun onHotAreaPressed(areaId: Int) {
                    it.setCurrentPageIndex(2)
                    Toast.makeText(this@CurlActivity, "We've moved to the page with index 2", Toast.LENGTH_SHORT).show()
                }
            })

            it.setOnPageLoadingListener(object: PageLoadingEventsHandler {
                override fun onLoadingStarted() {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                }

                override fun onLoadingCompleted() {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                }

                override fun onLoadingError() {
                    Toast.makeText(this@CurlActivity, R.string.generalError, Toast.LENGTH_SHORT).show()
                }
            })
        }

        val params  = curlView?.layoutParams
        params?.width = dpTopx(320, this).toInt()
        params?.height = dpTopx(618, this).toInt()
        curlView?.layoutParams = params


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
    }

    public override fun onPause() {
        super.onPause()
        curlView!!.onPause()
    }

    public override fun onResume() {
        super.onResume()
        curlView!!.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_PAGE, currentPageIndex ?: 0)
    }

    companion object {
        private const val START_PAGE = "START_PAGE"
        private const val CURRENT_PAGE = "CURRENT_PAGE"

        fun start(parentActivity: Activity, startPage: Int) {
            val intent = Intent(parentActivity, CurlActivity::class.java).putExtra(START_PAGE, startPage)
            parentActivity.startActivity(intent)
        }
    }
}
