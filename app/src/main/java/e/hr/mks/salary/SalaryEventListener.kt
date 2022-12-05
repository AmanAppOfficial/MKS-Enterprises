package e.hr.mks.salary

interface SalaryEventListener {
    fun success(list: ArrayList<SalaryModel>)
    fun failed(e: String)
}