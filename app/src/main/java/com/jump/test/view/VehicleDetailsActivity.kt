package com.jump.test.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jump.test.R
import com.jump.test.model.AppDatabase
import com.jump.test.model.Vehicle
import java.text.SimpleDateFormat
import java.util.Calendar

class VehicleDetailsActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var vehicleId: Int = -1
    private var textViewPlateDetails: TextView? = null
    private var textViewModelDetails: TextView? = null
    private var textViewColorDetails: TextView? = null
    private var btnVehicleEnter: Button? = null
    var typePayment : Int = 0
    var type_payment: String = ""
    var timeEnter: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)

        db = AppDatabase(this)
        textViewPlateDetails = findViewById( R.id.textViewPlateDetails )
        textViewModelDetails = findViewById( R.id.textViewModelDetails )
        textViewColorDetails = findViewById( R.id.textViewColorDetails )
        btnVehicleEnter = findViewById( R.id.btnVehicleEnter )

        vehicleId = intent.getIntExtra("vehicle_id", -1)
        if(vehicleId == -1){
            finish()
            return
        }

        val vehicle = db.getVehicleGetById( vehicleId )
        textViewPlateDetails?.text = "Placa: " + vehicle.plate.lowercase()
        textViewModelDetails?.text = "Modelo: " + vehicle.model
        textViewColorDetails?.text = "Cor: " + vehicle.color
        timeEnter = vehicle.time_enter

        btnVehicleEnter?.setOnClickListener {

            val builder = AlertDialog.Builder(
                this@VehicleDetailsActivity
            )
            builder.setTitle(R.string.out_vehicles)
            builder.setMessage(R.string.msg_exit_vehicle)
            builder.setPositiveButton(
                android.R.string.ok
            ) { dialog, which ->

                val time = Calendar.getInstance().time
                val formatter = SimpleDateFormat("HH:mm")
                val currentTime = formatter.format(time)

                type_payment = when ( typePayment ) {
                    1 -> "Dinheiro"
                    2 -> "Crédito"
                    3 -> "Débito"
                    4 -> "Boleto"
                    else -> {
                        "Pix"
                    }
                }

                val diferentTime = intervalValid( timeEnter, currentTime)
                val allItemsPrice = db.getAllItemsPrice()
                var total = 0.0

                for ( i in allItemsPrice.indices ){
                    if ( diferentTime.toInt() <= allItemsPrice[i].period ){
                        total = allItemsPrice[i].price.toDouble()
                        break
                    } else if ( diferentTime.toInt() > 60 ){
                        total = allItemsPrice[i].price.toDouble() + 1
                        break
                    }
                }

                val updateVehicle = Vehicle(vehicleId, "", "", "", "", type_payment, 0, timeEnter, currentTime, total.toString())
                db.updateVehicle( updateVehicle )
                dialog.dismiss()
                finish()
                Toast.makeText(this@VehicleDetailsActivity, R.string.msg_confirm_out_vehicle, Toast.LENGTH_LONG).show()

            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                dialog.dismiss()
            }
            val dlg = builder.create()
            dlg.setCancelable(false)
            dlg.setCanceledOnTouchOutside(false)
            dlg.show()

        }

    }

    fun intervalValid(timeBegin: String, timeFinish: String): Long {
        val sdf = SimpleDateFormat("HH:mm")
        val calBegin = Calendar.getInstance()
        val calFinish = Calendar.getInstance()
        calBegin.time = sdf.parse( timeBegin )
        calFinish.time = sdf.parse( timeFinish )
        val minutes = ( calFinish.timeInMillis - calBegin.timeInMillis ) / 60000
        return minutes
    }

    fun onRadioButtonDetails(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.btnRadioMoney ->
                    if (checked) {
                        typePayment = 1
                    }
                R.id.btnRadioCredit ->
                    if (checked) {
                        typePayment = 2
                    }
                R.id.btnRadioDebit ->
                    if (checked) {
                        typePayment = 3
                    }
                R.id.btnRadioTicket ->
                    if (checked) {
                        typePayment = 4
                    }
                R.id.btnRadioPix ->
                    if (checked) {
                        typePayment = 5
                    }
            }
        }
    }
}