package e.hr.mks.auth

class UserModel{
    private var uid =  ""
    private var name =  ""
    private var email = ""
    private var baseSalary = ""
    private var joiningDate = ""
    private var incentive = ""
    private var adhaarNumber = ""

    fun setUid(uid: String){
        this.uid = uid
    }
    fun setName(name: String){
        this.name = name
    }
    fun setEmail(email: String){
        this.email = email
    }
    fun setBasicSalary(baseSalary: String){
        this.baseSalary = baseSalary
    }
    fun setJoining(joiningDate: String){
        this.joiningDate = joiningDate
    }
    fun setIncentive(incentive: String){
        this.incentive = incentive
    }
    fun setAdhaar(adhaarNumber: String){
        this.adhaarNumber = adhaarNumber
    }

    fun getUid(): String{
        return uid
    }
    fun getName(): String{
        return name
    }
    fun getEmail(): String{
        return email
    }
    fun getBasicSalary(): String{
        return baseSalary
    }
    fun getJoining(): String{
        return joiningDate
    }
    fun getIncentive(): String{
        return incentive
    }
    fun getAdhaar(): String{
        return adhaarNumber
    }


}