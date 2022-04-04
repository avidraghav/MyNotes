import leakcanary.LeakCanary

object ConfigureLeakCanary {
    fun enableLeakCanary(isEnable: Boolean = true) {
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = isEnable)
        LeakCanary.showLeakDisplayActivityLauncherIcon(isEnable)
    }
}