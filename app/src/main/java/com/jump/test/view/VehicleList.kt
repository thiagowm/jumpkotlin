package com.jump.test.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jump.test.R
import com.jump.test.model.AppDatabase
import com.jump.test.presentation.VehicleListPresenter

class VehicleList : AppCompatActivity() {

    private lateinit var vehicleListPresenter: VehicleListPresenter
    private lateinit var db: AppDatabase
    private lateinit var vehicleAdapter: VehiclesAdapter
    private var recyclerViewVehicleList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        db = AppDatabase(this)
        recyclerViewVehicleList = findViewById( R.id.recyclerViewVehicleList )

        vehicleAdapter = VehiclesAdapter( db.getAllVehicles(1), this )

        recyclerViewVehicleList?.layoutManager = LinearLayoutManager(this)
        recyclerViewVehicleList?.adapter = vehicleAdapter

//        val dataSource = VehiclesRemoteDataSource()
//        vehicleListPresenter = VehicleListPresenter(this, dataSource)
//        vehicleListPresenter = VehicleListPresenter(this)
//        vehicleListPresenter.findAllVehicles()
    }

    override fun onResume() {
        super.onResume()
        vehicleAdapter.refreshData( db.getAllVehicles(1) )
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
//        return inflater.inflate(R.layout.activity_vehicle_list, container, false)
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val recyclerViewVehicleList = view.findViewById<RecyclerView>(R.id.recyclerViewVehicleList)
//        recyclerViewVehicleList.layoutManager = LinearLayoutManager(applicationContext)
//
//        vehicleListPresenter.findAllVehicles()
//
//        val adapter = GroupieAdapter()
//        recyclerViewVehicleList.adapter = adapter
//
//        adapter.notifyDataSetChanged()
//    }
}