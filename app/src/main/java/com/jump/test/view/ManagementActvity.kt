package com.jump.test.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.jump.test.R
import com.jump.test.data.API
import com.jump.test.data.HTTPClient
import com.jump.test.model.AppDatabase
import com.jump.test.model.ExitResponse
import com.jump.test.model.Items
import com.jump.test.model.ManualResponse
import com.jump.test.model.PaymentMethods
import com.jump.test.presentation.ManagementPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import kotlin.system.exitProcess


class ManagementActvity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var presenter: ManagementPresenter
    var drawer: DrawerLayout? = null
    var btnVehicleEnter: Button? = null
    private lateinit var db: AppDatabase
    var progressBarManagement: ProgressBar? = null
    var textViewTotalVehicleNumber: TextView? = null
    var textViewMoney: TextView? = null
    var textViewCredit: TextView? = null
    var textViewDebit: TextView? = null
    var textViewTicket: TextView? = null
    var textViewPix: TextView? = null
    var textViewTotalG: TextView? = null
    var moneyTotal: Double = 0.0
    var creditTotal: Double = 0.0
    var debitTotal: Double = 0.0
    var ticketTotal: Double = 0.0
    var pixTotal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_management_actvity)

        btnVehicleEnter = findViewById(R.id.btnVehicleEnter)
        drawer = findViewById<View>(R.id.drawer_layout_menu) as DrawerLayout
        val btnMenuManagement = findViewById<View>(R.id.btnMenuManagement) as ImageView
        val navigationView2 = findViewById<View>(R.id.nav_view2) as NavigationView
        navigationView2.setNavigationItemSelectedListener( this@ManagementActvity )

        textViewTotalVehicleNumber = findViewById( R.id.textViewTotalVehicleNumber )
        progressBarManagement = findViewById( R.id.progressBarManagement )
        progressBarManagement?.visibility = View.GONE
        textViewMoney = findViewById( R.id.textViewMoney )
        textViewCredit = findViewById( R.id.textViewCredit )
        textViewDebit = findViewById( R.id.textViewDebit )
        textViewTicket = findViewById( R.id.textViewTicket )
        textViewPix = findViewById( R.id.textViewPix )
        textViewTotalG = findViewById( R.id.textViewTotalG )

        db = AppDatabase(this)
        getManualAPI()
        getAllVehiclesInYard()
        setAllPaymentMethods()

        btnVehicleEnter?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@ManagementActvity,
                    VehicleEntranceActivity::class.java
                )
            )
        })

        btnMenuManagement.setOnClickListener {
            val window = findViewById<View>(R.id.btnMenuManagement)
            window.clearFocus()
            if (drawer!!.isDrawerOpen(GravityCompat.END)) {
                drawer!!.closeDrawer(GravityCompat.END)
            } else {
                drawer!!.openDrawer(GravityCompat.END)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getAllVehiclesInYard()
    }

    fun showProgress(){
        progressBarManagement?.visibility = View.VISIBLE
    }

    fun hideProgress(){
        progressBarManagement?.visibility = View.GONE
    }

    fun showFailure(message: Int){
        hideProgress()
        Toast.makeText( this@ManagementActvity, message, Toast.LENGTH_SHORT ).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menuListVehicles) {
            drawer!!.closeDrawer(GravityCompat.END)
            startActivity(Intent(this@ManagementActvity, VehicleList::class.java))
        } else if (id == R.id.menuExit) {
            val builder = AlertDialog.Builder(
                this@ManagementActvity
            )
            builder.setTitle(R.string.app_name)
            builder.setMessage(R.string.msg_exit_app)
            builder.setPositiveButton(
                android.R.string.ok
            ) { dialog, which ->
                drawer!!.closeDrawer(GravityCompat.END)
                dialog.dismiss()
                exitApp()
            }
            builder.setNegativeButton(
                android.R.string.cancel
            ) { dialog, which -> drawer!!.closeDrawer(GravityCompat.END) }
            val dlg = builder.create()
            dlg.setCancelable(false)
            dlg.setCanceledOnTouchOutside(false)
            dlg.show()
        }
        return true
    }

    fun getAllVehiclesInYard(){
        val allVehicles = db.getAllVehicles(1)
        textViewTotalVehicleNumber?.text = allVehicles.size.toString()
    }

    fun setAllPaymentMethods(){
        val allVehiclesOut = db.getAllVehicles(0)
        for (i in allVehiclesOut.indices){
            if ( allVehiclesOut[i].type_payment == "Dinheiro" ){
                moneyTotal += allVehiclesOut[i].total.toDouble()
            }
            if ( allVehiclesOut[i].type_payment == "Crédito" ){
                creditTotal += allVehiclesOut[i].total.toDouble()
            }
            if ( allVehiclesOut[i].type_payment == "Débito" ){
                debitTotal += allVehiclesOut[i].total.toDouble()
            }
            if ( allVehiclesOut[i].type_payment == "Boleto" ){
                ticketTotal += allVehiclesOut[i].total.toDouble()
            }
            if ( allVehiclesOut[i].type_payment == "Pix" ){
                pixTotal += allVehiclesOut[i].total.toDouble()
            }
        }

        textViewMoney?.text = "R$ " + currencyFormat( moneyTotal.toString() )
        textViewCredit?.text = "R$ " + currencyFormat( creditTotal.toString() )
        textViewDebit?.text = "R$ " + currencyFormat( debitTotal.toString() )
        textViewTicket?.text = "R$ " + currencyFormat( ticketTotal.toString() )
        textViewPix?.text = "R$ " + currencyFormat( pixTotal.toString() )

        val totalG = moneyTotal + creditTotal + debitTotal + ticketTotal + pixTotal
        textViewTotalG?.text = currencyFormat( totalG.toString() )
    }

    fun currencyFormat(amount: String): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }

    fun getManualAPI(){
        val sharedPreference =  getSharedPreferences("jumptest", Context.MODE_PRIVATE)
        val requestServer =  sharedPreference.getInt("request",0)
        if (requestServer == 0){
            getManual()
        }
    }

    fun getManual(){
        showProgress()
        val sharedPreference =  getSharedPreferences("jumptest", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putInt("request", 1)
        editor.commit()

        getSharedPreference()

        HTTPClient.retrofit()
            .create(API::class.java)
            .getManual()
            .enqueue(object : Callback<ManualResponse> {
                override fun onResponse(
                    call: Call<ManualResponse>,
                    response: Response<ManualResponse>
                ) {
                    if (response.isSuccessful){
                        hideProgress()
                        response.body()?.data?.let { insertPaymentMethods( it.paymentMethods ) }
                        response.body()?.data?.prices?.get(0)?.let { insertItemsPrices( it.items ) }
                    } else {
                        hideProgress()
                        showFailure( R.string.error_manual )
                    }
                }

                override fun onFailure(call: Call<ManualResponse>, t: Throwable) {
                    hideProgress()
                    showFailure( R.string.internal_error )
                }

            })
    }

    fun insertPaymentMethods(paymentMethods: Array<PaymentMethods>) {

        for (i in paymentMethods.indices){
            val establishmentPaymentMethodId = paymentMethods[i].establishmentPaymentMethodId
            val paymentMethodName = paymentMethods[i].paymentMethodName
            val primitivePaymentMethodId = paymentMethods[i].primitivePaymentMethodId
            val receivingDays = paymentMethods[i].receivingDays
            val receivingFee = paymentMethods[i].receivingFee
            val accountId = paymentMethods[i].accountId

            val payments = PaymentMethods(
                establishmentPaymentMethodId,
                paymentMethodName,
                primitivePaymentMethodId,
                receivingDays,
                receivingFee,
                accountId
            )

            db.insertPaymentMethods( payments )
        }
    }

    fun insertItemsPrices(item: Array<Items>){
        for (i in item.indices){
            val itemId = item[i].itemId
            val price = item[i].price
            val period = item[i].period
            val since = item[i].since
            val establishmentId = item[i].establishmentId
            val typePrice = item[i].typePrice

            val items = Items(
                itemId,
                price,
                period,
                since,
                establishmentId,
                typePrice
            )

            db.insertPricesItems( items )
        }
    }

    fun getSharedPreference(){
        val sharedPreference =  getSharedPreferences("jumptest", Context.MODE_PRIVATE)
        HTTPClient.token = sharedPreference.getString("accessToken", " ").toString()
        HTTPClient.userId =  sharedPreference.getInt("userId",0)
        HTTPClient.sessionId =  sharedPreference.getInt("sessionId",0)
        HTTPClient.establishmentId =  sharedPreference.getInt("establishmentId",0)
    }

    fun exitApp(){
        showProgress()
        getSharedPreference()

        HTTPClient.retrofit()
            .create(API::class.java)
            .exitApp()
            .enqueue(object : Callback<ExitResponse> {
                override fun onResponse(
                    call: Call<ExitResponse>,
                    response: Response<ExitResponse>
                ) {
                    if (response.isSuccessful){
                        this@ManagementActvity.deleteDatabase("jumptest.db")

                        val settings: SharedPreferences =
                            this@ManagementActvity.getSharedPreferences("jumptest", MODE_PRIVATE)
                        settings.edit().clear().commit()

                        hideProgress()
                        finish()
                        exitProcess(0)
                    } else {
                        hideProgress()
                        showFailure( R.string.error_out_app )
                    }
                }

                override fun onFailure(call: Call<ExitResponse>, t: Throwable) {
                    hideProgress()
                    showFailure( R.string.internal_error )
                }

            })
    }


}