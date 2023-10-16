package com.jump.test.view
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jump.test.R
import com.jump.test.data.API
import com.jump.test.data.HTTPClient
import com.jump.test.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    var btnLogin: Button? = null
    var textViewEmail: EditText? = null
    var textViewPassword: EditText? = null
    var progressBar: ProgressBar? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        textViewEmail = findViewById( R.id.textViewEmail )
        textViewPassword = findViewById( R.id.textViewPassword )
        progressBar = findViewById( R.id.progressBar )
        progressBar?.visibility = View.GONE

        btnLogin = findViewById(R.id.btnLogin)
        btnLogin?.setOnClickListener(View.OnClickListener {
            intent = Intent(this@MainActivity,ManagementActvity::class.java)
            val email = textViewEmail?.text.toString()
            val password = textViewPassword?.text.toString()
            if ( email.isEmpty() ){
                showFailure( R.string.enter_email )
            } else if ( password.isEmpty() ){
                showFailure( R.string.enter_password )
            } else {
                login(  textViewEmail?.text.toString(), textViewPassword?.text.toString() )
            }
        })

    }

    fun manager() {
        startActivity( android.content.Intent( this@MainActivity,ManagementActvity::class.java ) )
    }

    fun login(email: String, password: String){

        showProgress()
        HTTPClient.email = email
        HTTPClient.password = password

        HTTPClient.retrofit()
            .create(API::class.java)
            .login()
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if ( response.isSuccessful ){
                        hideProgress()
                        val data = response.body()?.data

                        val sharedPreference =  getSharedPreferences("jumptest", Context.MODE_PRIVATE)
                        var editor = sharedPreference.edit()
                        editor.putString("accessToken", data?.user?.accessToken)
                        editor.putInt("userId", data?.session?.userId ?: 0)
                        editor.putInt("sessionId", data?.session?.sessionId ?: 0)
                        editor.putInt("establishmentId", data?.session?.establishmentId ?: 0)
                        editor.commit()

                        startActivity( Intent(this@MainActivity, ManagementActvity::class.java) )
                    } else {
                        hideProgress()
                        showFailure( R.string.error_login )
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    hideProgress()
                    showFailure( R.string.internal_error )
                }
            })
    }

    fun showProgress(){
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgress(){
        progressBar?.visibility = View.GONE
    }

    fun showFailure(message: Int){
        hideProgress()
        Toast.makeText( this@MainActivity, message, Toast.LENGTH_LONG ).show()
    }

}

