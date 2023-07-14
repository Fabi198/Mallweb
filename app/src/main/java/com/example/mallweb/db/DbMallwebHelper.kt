package com.example.mallweb.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mallweb.db.ArrayBrands.arrayBrands
import com.example.mallweb.db.ArrayCategories.arrayCategories
import com.example.mallweb.db.ArrayPayments.arrayPayments
import com.example.mallweb.db.ArrayProducts.arrayProducts
import com.example.mallweb.db.ArrayProvinces.arrayProvinces
import com.example.mallweb.db.provinces.AllProvinces.arrayAllProvinces


open class DbMallwebHelper(var context: Context, DB_NAME: String = "MallwebDB5", DB_VERSION: Int = 3): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val createTableClients = "CREATE TABLE IF NOT EXISTS clients (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL, name TEXT NOT NULL, birthday TEXT, codarea TEXT, numCelular TEXT, dni TEXT NOT NULL, cuit TEXT NOT NULL, wantABill TEXT NOT NULL, ivaCondition TEXT)"
    private val createTableAddress = "CREATE TABLE IF NOT EXISTS address (idClient INTEGER PRIMARY KEY REFERENCES clients(id), street TEXT NOT NULL, number TEXT NOT NULL, floor TEXT NOT NULL, door TEXT NOT NULL, postalCode TEXT NOT NULL, province TEXT NOT NULL, locality TEXT NOT NULL)"
    private val createTableBillAddress = "CREATE TABLE IF NOT EXISTS billAddress (idClient INTEGER PRIMARY KEY REFERENCES clients(id), street TEXT NOT NULL, number TEXT NOT NULL, floor TEXT NOT NULL, door TEXT NOT NULL, postalCode TEXT NOT NULL, province TEXT NOT NULL, locality TEXT NOT NULL)"
    private val createTableOrders = "CREATE TABLE IF NOT EXISTS orders (idClient INTEGER REFERENCES clients(id), numberOrder INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT NOT NULL, total DOUBLE NOT NULL, state TEXT NOT NULL, shipping TEXT NOT NULL, numPayment INTEGER REFERENCES payMethod(numPayment))"
    private val createTableBill = "CREATE TABLE IF NOT EXISTS bill (numBill INTEGER PRIMARY KEY AUTOINCREMENT, idClient INTEGER REFERENCES clients(id), date TEXT NOT NULL, numPayment INTEGER REFERENCES payMethod(numPayment))"
    private val createTablePayMethod = "CREATE TABLE IF NOT EXISTS payMethod (numPayment INTEGER PRIMARY KEY, namePayment TEXT NOT NULL, details TEXT)"
    private val createTableDetails = "CREATE TABLE IF NOT EXISTS details (numDetails INTEGER PRIMARY KEY AUTOINCREMENT, numOrder INTEGER REFERENCES orders(numberOrder), idProduct INTEGER REFERENCES products(idProduct), nameProduct TEXT REFERENCES products(name), cant INTEGER NOT NULL, price DOUBLE NOT NULL)"
    private val createTableProducts = "CREATE TABLE IF NOT EXISTS products (idProduct INTEGER PRIMARY KEY, codFab TEXT NOT NULL, name TEXT NOT NULL, idCategory INTEGER REFERENCES category(idCategory), idBrand REFERENCES brands(idBrand), stock INTEGER NOT NULL, price DOUBLE NOT NULL, image INTEGER NOT NULL)"
    private val createTableCategory = "CREATE TABLE IF NOT EXISTS category (idCategory INTEGER PRIMARY KEY, name TEXT NOT NULL)"
    private val createTableBrands = "CREATE TABLE IF NOT EXISTS brands (idBrand INTEGER PRIMARY KEY, name TEXT NOT NULL, image INTEGER NOT NULL, imageLight INTEGER NOT NULL)"
    private val createTableShoppingCart = "CREATE TABLE IF NOT EXISTS shopcart (idClient INTEGER REFERENCES clients(id), idProduct INTEGER REFERENCES products(idProduct), quantity INTEGER NOT NULL)"
    private val createTableFavorites = "CREATE TABLE IF NOT EXISTS favorites (idFavorites INTEGER PRIMARY KEY AUTOINCREMENT, idClient INTEGER REFERENCES clients(id), idProduct INTEGER REFERENCES products(idProduct))"
    private val createTableProvinces = "CREATE TABLE IF NOT EXISTS province (id INTEGER PRIMARY KEY, province_name TEXT NOT NULL)"
    private val createTableCitys = "CREATE TABLE IF NOT EXISTS city (id INTEGER NOT NULL, city_name TEXT NOT NULL, cp INTEGER NOT NULL, id_province INTEGER REFERENCES province(id))"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableClients)
        db?.execSQL(createTableAddress)
        db?.execSQL(createTableBillAddress)
        db?.execSQL(createTableOrders)
        db?.execSQL(createTableBill)
        db?.execSQL(createTablePayMethod)
        db?.execSQL(createTableDetails)
        db?.execSQL(createTableProducts)
        db?.execSQL(createTableCategory)
        db?.execSQL(createTableBrands)
        db?.execSQL(createTableShoppingCart)
        db?.execSQL(createTableFavorites)
        db?.execSQL(createTableProvinces)
        db?.execSQL(createTableCitys)
        loadData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE clients")
        db?.execSQL("DROP TABLE address")
        db?.execSQL("DROP TABLE billAddress")
        db?.execSQL("DROP TABLE orders")
        db?.execSQL("DROP TABLE bill")
        db?.execSQL("DROP TABLE payMethod")
        db?.execSQL("DROP TABLE details")
        db?.execSQL("DROP TABLE products")
        db?.execSQL("DROP TABLE category")
        db?.execSQL("DROP TABLE brands")
        db?.execSQL("DROP TABLE shopcart")
        db?.execSQL("DROP TABLE favorites")
        db?.execSQL("DROP TABLE province")
        db?.execSQL("DROP TABLE city")
        onCreate(db)
    }

    private fun loadData(db: SQLiteDatabase?) {
        arrayCategories.forEach { db?.execSQL("INSERT INTO category VALUES('${it.id}', '${it.name}')") }
        arrayBrands.forEach { db?.execSQL("INSERT INTO brands VALUES('${it.id}', '${it.name}', '${it.image}', '${it.imageLight}')") }
        arrayProducts.forEach { db?.execSQL("INSERT INTO products VALUES('${it.id}', '${it.codFab}', '${it.name}', '${it.idCategory}', '${it.idBrand}', '${it.stock}', '${it.price}', '${it.image}')") }
        arrayPayments.forEach { db?.execSQL("INSERT INTO payMethod VALUES('${it.numPayment}', '${it.name}', '${it.detail}')") }
        arrayProvinces.forEach { db?.execSQL("INSERT INTO province VALUES('${it.id}', '${it.name}')") }
        arrayAllProvinces.forEach { province -> province.forEach { db?.execSQL("INSERT INTO city VALUES('${it.id}', '${it.name}', '${it.postalCode}', '${it.idProvince}')") } }
    }









}