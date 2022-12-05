package e.hr.mks.salary

import e.hr.mks.auth.UserModel

class SalaryModel {
    private var date:String? = null
    private var data: UserModel? = null

    constructor(date: String?, data: UserModel?){
        this.date = date
        this.data = data
    }

    fun getDate(): String?{
        return this.date
    }

    fun getData(): UserModel?{
        return this.data
    }
}