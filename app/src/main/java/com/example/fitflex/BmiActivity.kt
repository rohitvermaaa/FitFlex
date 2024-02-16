package com.example.fitflex

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitflex.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = KG_AND_CM

    companion object {
        const val KG_AND_CM = "KG_AND_CM"
        const val LB_AND_FT = "LB_AND_FT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            calculateUnits()
        }

        makeVisibleMetricUnitsView()
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etWeight?.text?.toString()?.isEmpty() == true) {
            isValid = false
            Toast.makeText(this, "Enter Weight", Toast.LENGTH_SHORT).show()
        }
        if (binding?.etMetricHeight?.text?.toString()?.isEmpty() == true) {
            isValid = false
            Toast.makeText(this, "Enter Height", Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(18.5f) < 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Lower weight than healthy, potential nutrient deficiencies."
            binding?.tvBMIType?.setTextColor(Color.parseColor("#00a1ff"))
        } else if (bmi.compareTo(18.5f) >= 0 && bmi.compareTo(25f) < 0) {
            bmiLabel = "Normal Weight"
            bmiDescription = " Healthy weight range, lower risk of chronic diseases."
            binding?.tvBMIType?.setTextColor(Color.parseColor("#02A437"))
        } else if (bmi.compareTo(25f) >= 0 && bmi.compareTo(30f) < 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Higher weight than healthy, increased risk of chronic diseases."
            binding?.tvBMIType?.setTextColor(Color.parseColor("#F5B945"))
        } else {
            bmiLabel = "Obese"
            bmiDescription = "Significantly high weight, increased risk of serious health problems."
            binding?.tvBMIType?.setTextColor(Color.parseColor("#f53c4e"))
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(1, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = KG_AND_CM
        binding?.etWeight?.visibility = View.VISIBLE
        binding?.tlForetMetricHeight?.visibility = View.VISIBLE
        binding?.etMetricHeight?.visibility = View.VISIBLE
        binding?.llForUSUnits?.visibility = View.GONE
        binding?.etHeightInUSFeet?.visibility = View.GONE
        binding?.etHeightInUSFInches?.visibility = View.GONE

        binding?.etMetricHeight?.text?.clear()
        binding?.etWeight?.text?.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = LB_AND_FT
        binding?.etWeight?.visibility = View.VISIBLE
        binding?.tlForetMetricHeight?.visibility = View.GONE
        binding?.etMetricHeight?.visibility = View.GONE
        binding?.llForUSUnits?.visibility = View.VISIBLE
        binding?.etHeightInUSFeet?.visibility = View.VISIBLE
        binding?.etHeightInUSFInches?.visibility = View.VISIBLE

        binding?.etHeightInUSFInches?.text?.clear()
        binding?.etHeightInUSFeet?.text?.clear()
        binding?.etWeight?.text?.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun validateUsUnits(): Boolean {
        var isValid: Boolean = true
        when {
            binding?.etHeightInUSFeet?.text?.toString()?.isEmpty() == true -> {
                isValid = false
                Toast.makeText(this, "Enter Height", Toast.LENGTH_SHORT).show()
            }
            binding?.etHeightInUSFInches?.text?.toString()?.isEmpty() == true -> {
                isValid = false
                Toast.makeText(this, "Enter Height", Toast.LENGTH_SHORT).show()
            }
            binding?.etWeight?.text?.toString()?.isEmpty() == true -> {
                isValid = false
                Toast.makeText(this, "Enter Weight", Toast.LENGTH_SHORT).show()
            }
        }
        return isValid
    }

    private fun calculateUnits() {
        if (currentVisibleView == KG_AND_CM) {
            if (validateMetricUnits()) {
                val metricHeightValueCm: Float? =
                    binding?.etMetricHeight?.text?.toString()?.toFloat()?.div(100)
                val metricWeightValueKg: Float? = binding?.etWeight?.text?.toString()?.toFloat()
                val bmi =
                    metricWeightValueKg?.div((metricHeightValueCm?.times(metricHeightValueCm)!!))
                displayBmiResult(bmi!!)
            }
        }
        if (currentVisibleView == LB_AND_FT) {
            if (validateUsUnits()) {
                val usHeightValueFeet: Float? =
                    binding?.etHeightInUSFeet?.text?.toString()?.toFloat()
                val usHeightValueInches: Float? =
                    binding?.etHeightInUSFInches?.text?.toString()?.toFloat()
                val usWeightValuePound: Float? = binding?.etWeight?.text?.toString()?.toFloat()
                val heightValueFeetAndInches =
                    usHeightValueInches?.plus((usHeightValueFeet?.times(12)!!))
                val bmi =
                    703 * (usWeightValuePound!! / (heightValueFeetAndInches?.times(heightValueFeetAndInches)!!))
                displayBmiResult(bmi)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}