package e.hr.mks.salary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.AndroidSupportInjection
import e.hr.mks.databinding.FragmentSalaryBinding
import e.hr.mks.salary.SalaryModel
import e.hr.mks.salary.SalaryViewModel
import e.hr.mks.utils.DateUtility
import javax.inject.Inject


class SalaryFragment : Fragment(){

    private lateinit var binding: FragmentSalaryBinding

    @Inject
    lateinit var salaryViewModel: SalaryViewModel

    private var selectedDate = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        binding = FragmentSalaryBinding.inflate(inflater, container, false)

        initViewModel()
        return binding.root
    }

    private fun initViewModel() {

        salaryViewModel.salaryLiveData.observe(viewLifecycleOwner){
            setSpinner(it)
        }

        salaryViewModel.getSalaryData(requireContext(),FirebaseAuth.getInstance().uid.toString())

        binding.downloadBtn.setOnClickListener{
            if(selectedDate!="")
                salaryViewModel.downloadSlip(requireContext(), FirebaseAuth.getInstance().uid.toString(), selectedDate)
        }
    }


    private fun setSpinner(it: ArrayList<SalaryModel>) {

        // if list size 0 then so no data layout
        if(it.size<=0){
            binding.noDataText.visibility = View.VISIBLE
        }
        else{
            binding.noDataText.visibility = View.GONE
        }

        // set spinner custom adapter
        val adapter = CustomDropDownAdapter(requireContext(), it.reversed())
        binding.spinnerDateLayout.adapter = adapter

        binding.spinnerDateLayout.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val salaryModel = parent!!.getItemAtPosition(position) as SalaryModel
                val data = salaryModel.getData()
                binding.nameText.text = data?.getName()
                binding.emailText.text = data?.getEmail()
                binding.joiningDateText.text = data?.getJoining()
                binding.adhaarText.text = data?.getAdhaar()
                binding.basicSalaryText.text = data?.getBasicSalary() + "/-"
                binding.incentiveText.text = data?.getIncentive() + "/-"

                selectedDate = DateUtility.formatDate(salaryModel.getDate().toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        // show/hide views
        if(it.size>0){
            binding.progressBar.visibility = View.GONE
            binding.mainSalaryLayout.visibility = View.VISIBLE
        }
    }



}