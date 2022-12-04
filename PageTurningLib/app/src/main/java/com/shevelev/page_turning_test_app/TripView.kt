package com.shevelev.page_turning_test_app

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class TripView(activity: Activity) {

    var layoutTrip: View? = null
    var tvTitle: TextView? = null
    var tvDesc: TextView? = null
    var ivImg1: ImageView? = null
    var ivImg2: ImageView? = null
    var ivImg3: ImageView? = null
    var ivImg4: ImageView? = null
    var ivImg5: ImageView? = null
    var ivImg6: ImageView? = null
    var ivImg7: ImageView? = null
    var ivImg8: ImageView? = null

    init {
        layoutTrip = activity.layoutInflater.inflate(R.layout.layout_trip, null)
        tvTitle = layoutTrip?.findViewById(R.id.tv_title)
        tvDesc = layoutTrip?.findViewById(R.id.tv_desc)
        ivImg1 = layoutTrip?.findViewById(R.id.iv_image_1)
        ivImg2 = layoutTrip?.findViewById(R.id.iv_image_2)
        ivImg3 = layoutTrip?.findViewById(R.id.iv_image_3)
        ivImg4 = layoutTrip?.findViewById(R.id.iv_image_4)
        ivImg5 = layoutTrip?.findViewById(R.id.iv_image_5)
        ivImg6 = layoutTrip?.findViewById(R.id.iv_image_6)
        ivImg7 = layoutTrip?.findViewById(R.id.iv_image_7)
        ivImg8 = layoutTrip?.findViewById(R.id.iv_image_8)
//        addView(layoutTrip)
    }

    fun getBitmap() : Bitmap? {
        return layoutTrip?.let { getBitmap(it) }
    }

    fun setData() {
        tvTitle?.text = "title"
        tvDesc?.text = "desc"
        ivImg1?.setImageResource(R.drawable.img)
        ivImg2?.setImageResource(R.drawable.img)
        ivImg3?.setImageResource(R.drawable.img)
    }

}