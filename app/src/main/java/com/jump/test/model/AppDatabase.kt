package com.jump.test.model

import android.content.ClipData.Item
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.math.sin


class AppDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "jumptest.db"
        private const val DATABASE_VERSION = 7

        //Table paymentmethods...
//        private const val TABLE_NAME = "paymentmethods"
        private const val TABLE_NAME_PAYMENT_METHODS = "paymentmethods"
        private const val COLUMN_ESTABLISHMENT_PAYMETHOD_ID = "establishment_payment_method_id"
        private const val COLUMN_PAYMENT_METHOD_NAME = "payment_method_name"
        private const val COLUMN_PRIMITIVE_PAYMENT_METHOD_ID = "primitive_payment_method_id"
        private const val COLUMN_RECEIVING_DAYS = "receiving_days"
        private const val COLUMN_RECEIVING_FEE = "receiving_fee"
        private const val COLUMN_ACCOUNT_ID = "account_id"

        //Table prices...
//        private const val TABLE_NAME_PRICES = "prices"
//        private const val COLUMN_ESTABLISHMENT_ID = "establishment_id"
//        private const val COLUMN_TYPE_PRICE = "type_price"
//        private const val COLUMN_COVENANT = "covenant"
//        private const val COLUMN_INVISIBLE = "invisible"
//        private const val COLUMN_MAJOR = "major"
//        private const val COLUMN_TOLERANCE = "tolerance"
//        private const val COLUMN_MAXIMUM_PERIOD = "maximum_period"
//        private const val COLUMN_MAXIMUM_VALUE = "maximum_value"
//        private const val COLUMN_ITEMS = "items"

        //Table vehicles...
        private const val TABLE_NAME_VEHICLES = "vehicles"
        private const val COLUMN_VEHICLE_ID = "vehicle_id"
        private const val COLUMN_VEHICLE = "vehicle"
        private const val COLUMN_PLATE = "plate"
        private const val COLUMN_MODEL = "model"
        private const val COLUMN_COLOR = "color"
        private const val COLUMN_TYPE_PAYMENT = "type_payment"
        private const val COLUMN_COURTYARD = "courtyard"
        private const val COLUMN_TIME_ENTER = "time_enter"
        private const val COLUMN_TIME_OUT = "time_out"
        private const val COLUMN_TOTAL = "total"

        //Table Items in Prices...
        private const val TABLE_NAME_ITEMS = "items"
        private const val COLUMN_ITEM_PRICE_ID = "id"
        private const val COLUMN_ITEM_ID = "item_id"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_PERIOD = "period"
        private const val COLUMN_SINCE = "since"
        private const val COLUMN_ESTABLISMENT_ID = "establishment_id"
        private const val COLUMN_TYPE_PRICE = "type_price"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        //Table paymentmethods...
        val createTableQuery1 = "CREATE TABLE $TABLE_NAME_PAYMENT_METHODS (" +
                "$COLUMN_ESTABLISHMENT_PAYMETHOD_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_PAYMENT_METHOD_NAME TEXT, " +
                "$COLUMN_PRIMITIVE_PAYMENT_METHOD_ID INTEGER, " +
                "$COLUMN_RECEIVING_DAYS INTEGER, " +
                "$COLUMN_RECEIVING_FEE TEXT, " +
                "$COLUMN_ACCOUNT_ID INTEGER)"
        db?.execSQL(createTableQuery1)

        //Table prices...
//        val createTableQuery2 = "CREATE TABLE $TABLE_NAME_PRICES (" +
//                "$COLUMN_ESTABLISHMENT_ID INTEGER PRIMARY KEY, " +
//                "$COLUMN_TYPE_PRICE TEXT, " +
//                "$COLUMN_COVENANT INTEGER, " +
//                "$COLUMN_INVISIBLE INTEGER, " +
//                "$COLUMN_MAJOR INTEGER, " +
//                "$COLUMN_TOLERANCE TEXT, " +
//                "$COLUMN_MAXIMUM_PERIOD INTEGER, " +
//                "$COLUMN_MAXIMUM_VALUE TEXT, " +
//                "$COLUMN_ITEMS BLOB)"
//        db?.execSQL(createTableQuery2)

        //Table vehicles...
        val createTableQuery3 = "CREATE TABLE $TABLE_NAME_VEHICLES (" +
                "$COLUMN_VEHICLE_ID INTEGER PRIMARY KEY , " +
                "$COLUMN_VEHICLE TEXT, " +
                "$COLUMN_PLATE INTEGER, " +
                "$COLUMN_MODEL INTEGER, " +
                "$COLUMN_COLOR TEXT, " +
                "$COLUMN_TYPE_PAYMENT TEXT, " +
                "$COLUMN_COURTYARD INTEGER, " +
                "$COLUMN_TIME_ENTER TEXT," +
                "$COLUMN_TIME_OUT TEXT," +
                "$COLUMN_TOTAL TEXT)"
        db?.execSQL(createTableQuery3)

        //Table items in Prices...
        val createTableQuery4 = "CREATE TABLE $TABLE_NAME_ITEMS (" +
                "$COLUMN_ITEM_PRICE_ID INTEGER PRIMARY KEY , " +
                "$COLUMN_ITEM_ID INTEGER, " +
                "$COLUMN_PRICE TEXT, " +
                "$COLUMN_PERIOD INTEGER, " +
                "$COLUMN_SINCE INTEGER, " +
                "$COLUMN_ESTABLISMENT_ID INTEGER, " +
                "$COLUMN_TYPE_PRICE TEXT)"
        db?.execSQL(createTableQuery4)

//        COLUMN_ITEM_ID = "item_id"
//        private const val COLUMN_PRICE = "price"
//        private const val COLUMN_PERIOD = "period"
//        private const val COLUMN_SINCE = "since"
//        private const val COLUMN_ESTABLISMENT_ID = "establishment_id"
//        private const val COLUMN_TYPE_PRICE = "type_price"


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Table paymentmethods...
        val dropTableQuery1 = "DROP TABLE IF EXISTS $TABLE_NAME_PAYMENT_METHODS"
        db?.execSQL(dropTableQuery1)
//        onCreate(db)

        //Table prices...
//        val dropTableQuery2 = "DROP TABLE IF EXISTS $TABLE_NAME_PRICES"
//        db?.execSQL(dropTableQuery2)
//        onCreate(db)

        //Table vehicles...
        val dropTableQuery3 = "DROP TABLE IF EXISTS $TABLE_NAME_VEHICLES"
        db?.execSQL(dropTableQuery3)
//        onCreate(db)

        //Table items in prices...
        val dropTableQuery4 = "DROP TABLE IF EXISTS $TABLE_NAME_ITEMS"
        db?.execSQL(dropTableQuery4)
        onCreate(db)
    }

    fun insertPaymentMethods(paymentMethods: PaymentMethods){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_ESTABLISHMENT_PAYMETHOD_ID, paymentMethods.establishmentPaymentMethodId)
            put(COLUMN_PAYMENT_METHOD_NAME, paymentMethods.paymentMethodName)
            put(COLUMN_PRIMITIVE_PAYMENT_METHOD_ID, paymentMethods.primitivePaymentMethodId)
            put(COLUMN_RECEIVING_DAYS, paymentMethods.receivingDays)
            put(COLUMN_RECEIVING_FEE, paymentMethods.receivingFee)
            put(COLUMN_ACCOUNT_ID, paymentMethods.accountId)
        }

        db.insert(TABLE_NAME_PAYMENT_METHODS, null, values)
        db.close()
    }

    fun insertPricesItems(items: Items){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_ITEM_ID, items.itemId)
            put(COLUMN_PRICE, items.price)
            put(COLUMN_PERIOD, items.period)
            put(COLUMN_SINCE, items.since)
            put(COLUMN_ESTABLISMENT_ID, items.establishmentId)
            put(COLUMN_TYPE_PRICE, items.typePrice)
        }

        db.insert(TABLE_NAME_ITEMS, null, values)
        db.close()
    }

//    fun insertPrices(prices: Prices){
//        val db = writableDatabase
//
//        val values = ContentValues().apply {
//            put(COLUMN_ESTABLISHMENT_ID, prices.establishmentId)
//            put(COLUMN_TYPE_PRICE, prices.typePrice)
//            put(COLUMN_COVENANT, prices.covenant)
//            put(COLUMN_INVISIBLE, prices.invisible)
//            put(COLUMN_MAJOR, prices.major)
//            put(COLUMN_TOLERANCE, prices.tolerance)
//            put(COLUMN_MAXIMUM_PERIOD, prices.maximumPeriod)
//            put(COLUMN_MAXIMUM_VALUE, prices.maximumValue)
//            put(COLUMN_ITEMS, prices.items)
//        }
//
//        db.insert(TABLE_NAME_PRICES, null, values)
//        db.close()
//    }

    fun insertVehicles(vehicle: Vehicle){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_VEHICLE, vehicle.vehicle)
            put(COLUMN_PLATE, vehicle.plate)
            put(COLUMN_MODEL, vehicle.model)
            put(COLUMN_COLOR, vehicle.color)
            put(COLUMN_TYPE_PAYMENT, vehicle.type_payment)
            //value 1 is true and 0 is false
            put(COLUMN_COURTYARD, vehicle.courtyard)
            put(COLUMN_TIME_ENTER, vehicle.time_enter)
            put(COLUMN_TIME_OUT, vehicle.time_out)
            put(COLUMN_TOTAL, vehicle.total)
        }

        db.insert(TABLE_NAME_VEHICLES, null, values)
        db.close()
    }

    fun getAllVehicles(courtyard: Int) : List<Vehicle> {
        val vehiclesList = mutableListOf<Vehicle>()
        val db = readableDatabase
        var query: String? = null
        if (courtyard == 1){
            query = "SELECT * FROM $TABLE_NAME_VEHICLES WHERE $COLUMN_COURTYARD = 1"
        } else {
            query = "SELECT * FROM $TABLE_NAME_VEHICLES WHERE $COLUMN_COURTYARD = 0"
        }
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_ID))
            val vehicle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE))
            val plate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLATE))
            val model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR))
            val type_payment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_PAYMENT))
            val courtyard = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURTYARD))
            val time_enter = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_ENTER))
            val time_out = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_OUT))
            val total = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL))

            val vehicles = Vehicle(id, vehicle, plate, model, color, type_payment, courtyard, time_enter, time_out, total)
            vehiclesList.add( vehicles )
        }
        cursor.close()
        db.close()

        return vehiclesList
    }

    fun updateVehicle(vehicle: Vehicle){
        val db = writableDatabase
        //value courtyard if 1 is true and 0 is false
        val values = ContentValues().apply {
            put( COLUMN_TYPE_PAYMENT, vehicle.type_payment )
            put( COLUMN_COURTYARD, vehicle.courtyard )
            put( COLUMN_TIME_OUT, vehicle.time_out )
            put( COLUMN_TOTAL, vehicle.total )
        }
        val whereClause = "$COLUMN_VEHICLE_ID = ?"
        val whereArgs = arrayOf(vehicle.vehicle_id.toString())
        db.update(TABLE_NAME_VEHICLES, values, whereClause, whereArgs)
        db.close()
    }

    fun getVehicleGetById(vehicleId: Int): Vehicle{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_VEHICLES WHERE $COLUMN_VEHICLE_ID = $vehicleId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_ID))
        val vehicle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE))
        val plate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLATE))
        val model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL))
        val color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR))
        val courtyard = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURTYARD))
        val time_enter = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_ENTER))
        val time_out = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_OUT))
        val total = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL))

        cursor.close()
        db.close()
        return Vehicle(id, vehicle, plate, model, color, "", courtyard, time_enter, time_out, total)
    }

    fun getAllItemsPrice() : List<Items> {
        val itemsPriceList = mutableListOf<Items>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_ITEMS"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val itemId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
            val period = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERIOD))
            val since = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SINCE))
            val establishmentId = cursor.getInt(cursor.getColumnIndexOrThrow(
                COLUMN_ESTABLISMENT_ID))
            val typePrice = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_PRICE))

            val itemsPrices = Items(itemId, price, period, since, establishmentId, typePrice)
            itemsPriceList.add( itemsPrices )
        }
        cursor.close()
        db.close()

        return itemsPriceList
    }

}