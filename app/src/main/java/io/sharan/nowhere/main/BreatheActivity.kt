package io.sharan.nowhere.main

import android.animation.Animator
import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import com.sharan.nowhere.R
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.nowhere.data.Config
import io.sharan.nowhere.data.analytics.EventLogger
import io.sharan.nowhere.data.analytics.EventsCreator
import kotlinx.android.synthetic.main.activity_breathe.*
import javax.inject.Inject

@AndroidEntryPoint
@Keep
class BreatheActivity : AppCompatActivity(), BreatheContract.View {

    private lateinit var presenter: BreatheContract.Presenter
    private var breathing: Boolean = false
    private var config: Config = Config()
    private lateinit var vibrator: Vibrator
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private lateinit var timer: CountDownTimer

    @Inject
    lateinit var eventLogger: EventLogger

    @Inject
    lateinit var eventsCreator: EventsCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breathe)

        // Prevent phone from going to sleep.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        /*     btn_switch.setOnClickListener {
                 when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                     Configuration.UI_MODE_NIGHT_YES ->
                         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                     Configuration.UI_MODE_NIGHT_NO ->
                         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                 }
             }
     */
        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.e("Animation:", "start")

                if (config.sound) {
                    performSound()
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.e("Animation:", "end")
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.e("Animation:", "repeat")
                if (config.sound) {
                    performSound()
                }
            }
        })

        // On circle click start breathing.
        animationView.setOnClickListener {
            eventLogger.logCustomEvent(
                eventsCreator.getCustomEvent(
                    EventsCreator.HALE_CLICK_EVENT,
                    EventsCreator.Screen.HOME_SCREEN
                )
            )
            // Verify if breathing has not started.
            if (!breathing) {
//                performNotification()
                // Set time of breathing.
                when (spinner_minutes.selectedItemPosition) {
                    0 -> config.minutes = 9.5
                    1 -> config.minutes = 19.0
                    2 -> config.minutes = 38.0
                }
                presenter.startBreathing(config)
            }
        }

        // Create vibrator.
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Create sound player.
        soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        soundId = soundPool.load(this, R.raw.ding, 1)

        // Create the presenter.
        BreatheViewModel(this)
        presenter.start()

    }

    override fun onPause() {
        super.onPause()
        if (breathing) {
            presenter.stopBreathing()
        }
    }

    override fun onBackPressed() {
        if (breathing) {
            presenter.stopBreathing()
        } else {
            super.onBackPressed()
        }
    }

    override fun setPresenter(presenter: BreatheContract.Presenter) {
        this.presenter = presenter
    }

    override fun loadConfiguration(list: ArrayList<String>) {
        val adapter = ArrayAdapter(this, R.layout.spinner_item, list)
        spinner_minutes.adapter = adapter
    }

    override fun showConfiguration() {
        spinner_minutes.visibility = View.VISIBLE
    }

    override fun hideConfiguration() {
        spinner_minutes.visibility = View.INVISIBLE
    }

    override fun startAnimation() {
        // Breathing has started.
        breathing = true
        animationView.playAnimation()
    }

    override fun startTimer() {
        timer = object : CountDownTimer((config.minutes * 60 * 1000).toLong(), 1000) {
            override fun onFinish() {
                presenter.stopBreathing()
                eventLogger.logCustomEvent(
                    eventsCreator.getCustomEvent(
                        EventsCreator.HALE_FINISH_EVENT,
                        EventsCreator.Screen.HOME_SCREEN
                    )
                )


            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }

    override fun stopTimer() {
        timer.cancel()
    }

    override fun stopAnimation() {
        // Stop breathing.
        breathing = false
        // Show configuration.
        showConfiguration()
        // End lottie
        animationView.pauseAnimation()

    }

    private fun performSound() {
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

}
