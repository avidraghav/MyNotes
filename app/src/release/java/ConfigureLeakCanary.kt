import android.util.Log
import com.raghav.mynotes.R

object ConfigureLeakCanary {
    fun enableLeakCanary(isEnable: Boolean = true) {
        Log.i(R.string.app_name.toString(), "LeakCanary Disabled - $isEnable")
    }
}