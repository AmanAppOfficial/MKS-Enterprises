package e.hr.mks.auth

interface AuthListener {
    fun success()
    fun failed(msg: String)
}