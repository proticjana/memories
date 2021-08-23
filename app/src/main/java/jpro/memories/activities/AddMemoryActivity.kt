package jpro.memories.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jpro.memories.R
import jpro.memories.database.DatabaseHandler
import jpro.memories.databinding.ActivityAddMemoryBinding
import jpro.memories.models.MemoryModel
import java.text.SimpleDateFormat
import java.util.*

class AddMemoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddMemoryBinding

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbAddMemory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbAddMemory.setNavigationOnClickListener {
            onBackPressed()
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        updateDateInView()

        binding.etDate.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddMemoryActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            R.id.btn_save -> {
                when {
                    binding.etName.text.isNullOrEmpty() -> {
                        Toast.makeText(this, "Please enter memory name", Toast.LENGTH_SHORT)
                            .show()
                    }

                    binding.etDescription.text.isNullOrEmpty() -> {
                        Toast.makeText(this, "Please enter memory description", Toast.LENGTH_SHORT)
                            .show()
                    }

                    binding.etLocation.text.isNullOrEmpty() -> {
                        Toast.makeText(this, "Please enter memory location", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        val memoryModel = MemoryModel(
                            0,
                            binding.etName.text.toString(),
                            binding.etDescription.text.toString(),
                            binding.etDate.text.toString(),
                            binding.etLocation.text.toString(),
                            mLatitude,
                            mLongitude
                        )
                        val dbHandler = DatabaseHandler(this)
                        val addMemoryResult = dbHandler.addMemory(memoryModel)

                        if (addMemoryResult > 0) {
                            Toast.makeText(this, "New memory added", Toast.LENGTH_SHORT).show()
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun updateDateInView() {
        val dateFormat = "dd.MM.yyyy."
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())

        binding.etDate.setText(sdf.format(cal.time).toString())
    }
}