package com.hencoder.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hencoder.animation.practice.ProvinceView
import com.hencoder.animation.view.ProvinceEvaluator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

//    对字符串做属性动画
//    val provinceAnimator = ObjectAnimator.ofObject(view, "province", ProvinceView.ProvinceEvaluator(), "西藏自治区")
//    provinceAnimator.startDelay = 1000
//    provinceAnimator.duration = 6000
//    provinceAnimator.start()

    val bottomClipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 60f)
    bottomClipAnimator.startDelay = 1000
    bottomClipAnimator.duration = 2000

    val flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f)
    flipRotationAnimator.startDelay = 1000
    flipRotationAnimator.duration = 2000

    val topClipAnimator = ObjectAnimator.ofFloat(view, "topFlip", -60f)
    topClipAnimator.startDelay = 1000
    topClipAnimator.duration = 2000

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(bottomClipAnimator, flipRotationAnimator, topClipAnimator)
    animatorSet.start()

    /*view.animate() // radius
      .translationX(200.dp) // setTranslationX(10) setTranslationX(20) setTranslationX(40)
      .translationY(100.dp)
      .alpha(0.5f)
      .scaleX(2f)
      .scaleY(2f)
      .rotation(90f)
      .setStartDelay(1000)*/

//      val  animator = ObjectAnimator.ofFloat(view, "radius", 50.dp, 150.dp)
//      animator.startDelay = 1000
//      animator.duration = 2000
//      animator.start()

//    val bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 60f)
//    bottomFlipAnimator.startDelay = 1000
//    bottomFlipAnimator.duration = 1000
//    bottomFlipAnimator.start()

//    val flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f)
//    flipRotationAnimator.startDelay = 200
//    flipRotationAnimator.duration = 1000
//
//    val topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", - 60f)
//    topFlipAnimator.startDelay = 200
//    topFlipAnimator.duration = 1000
//
//    val animatorSet = AnimatorSet()
//    animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator)
//    animatorSet.start()

    /*val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
    val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
    val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", - 60f)
    val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlipHolder,  flipRotationHolder,  topFlipHolder)
    holderAnimator.startDelay = 1000
    holderAnimator.duration = 2000
    holderAnimator.start()*/

    /*val length = 200.dp
    val keyframe1 = Keyframe.ofFloat(0f, 0f)
    val keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length)
    val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
    val keyframe4 = Keyframe.ofFloat(1f, 1f * length)
    val keyframeHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4)
    val animator = ObjectAnimator.ofPropertyValuesHolder(view, keyframeHolder)
    animator.startDelay = 1000
    animator.duration = 2000
    animator.start()*/

//    val animator = ObjectAnimator.ofObject(view, "point", PointFEvaluator(),
//      PointF(50.dp, 50.dp),
//      PointF(200.dp, 300.dp))
//    animator.startDelay = 3000
//    animator.duration = 2000
//    animator.start()

//    val animator = ObjectAnimator.ofObject(view, "province", ProvinceEvaluator(), "澳门特别行政区")
//    animator.startDelay = 1000
//    animator.duration = 10000
//    animator.start()
//
//    view.animate()
//      .translationY(200.dp)
//      .withLayer()
  }

//  class PointFEvaluator : TypeEvaluator<PointF> {
//    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
//      val startX = startValue.x
//      val endX = endValue.x
//      val currentX = startX + (endX - startX) * fraction
//      val startY = startValue.y
//      val endY = endValue.y
//      val currentY = startY + (endY - startY) * fraction
//      return PointF(currentX, currentY)
//    }
//  }

  class PointFEvaluator : TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
      val startX = startValue.x
      val endX = endValue.x
      val currentX = startX + (endX - startX) * fraction
      val startY = startValue.y
      val endY = endValue.y
      val currentY = startY + (endY - startY) * fraction
      return PointF(currentX, currentY)
    }

  }
}