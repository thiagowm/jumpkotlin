package com.jump.test.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jump.test.R
import com.jump.test.model.AppDatabase
import com.jump.test.model.Vehicle
import java.text.SimpleDateFormat
import java.util.Calendar

class VehicleEntranceActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    var textViewPlaca: TextView? = null
    var textViewModel: TextView? = null
    var textViewColor: TextView? = null
    var btnVehicleEnterParking: Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_entrance)

        db = AppDatabase(this)

        textViewPlaca = findViewById( R.id.textViewPlaca )
        textViewModel = findViewById( R.id.textViewModel )
        textViewColor = findViewById( R.id.textViewColor )
        btnVehicleEnterParking = findViewById( R.id.btnVehicleEnterParking )

        btnVehicleEnterParking?.setOnClickListener(View.OnClickListener {
            val plate = textViewPlaca?.text.toString()
            val vehicle = textViewModel?.text.toString()
            val model = textViewModel?.text.toString()
            val color = textViewColor?.text.toString()

            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("HH:mm")
            val current = formatter.format(time)

            val vehicles = Vehicle( 0, vehicle, plate, model, color, "0", 1, current, "0", "0")

            db.insertVehicles(vehicles)
            finish()
        })
    }
}