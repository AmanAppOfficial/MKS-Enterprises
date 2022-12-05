package e.hr.mks.utils

object DateUtility {

    fun formatDate(date: String): String{
        val month = date.substring(0,2)
        val year = date.substring(2)

        if(month == "01" || month=="1")
            return "January $year"
        else if(month == "02" || month=="2")
            return "February $year"
        else if(month == "03" || month=="3")
            return "March $year"
        else if(month == "04" || month=="4")
            return "April $year"
        else if(month == "05" || month=="5")
            return "May $year"
        else if(month == "06" || month=="6")
            return "June $year"
        else if(month == "07" || month=="7")
            return "July $year"
        else if(month == "08" || month=="8")
            return "August $year"
        else if(month == "09" || month=="9")
            return "September $year"
        else if(month == "10")
            return "October $year"
        else if(month == "11")
            return "November $year"
        else
            return "December $year"
    }
}