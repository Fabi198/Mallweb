package com.example.mallweb.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mallweb.entities.dbEntities.*
import java.sql.SQLException

class DbMallweb(context: Context): DbMallwebHelper(context) {
    private fun setDatabase(): SQLiteDatabase {
        val db = DbMallwebHelper(context)
        return db.writableDatabase
    }

    // Favorites methods
    fun queryForFavorite(idClient: Int, idProduct: Int): Boolean {
        setDatabase()
        var result = false
        val cursor: Cursor = setDatabase().rawQuery("SELECT idFavorites FROM favorites WHERE idClient = '${idClient}' AND idProduct = '${idProduct}'", null)
        if (cursor.moveToFirst()) {
            if (cursor.getInt(0) > 0) {
                result = true
            }
        }
        setDatabase().close()
        cursor.close()
        return result
    }

    fun queryForFavorites(idClient: Int): ArrayList<Product> {
        val listProduct = ArrayList<Product>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT idProduct FROM favorites WHERE idClient = '$idClient'", null)
        if (cursor.moveToFirst()) {
            do {
                val cursorProduct: Cursor = setDatabase().rawQuery("SELECT * FROM products WHERE idProduct = '${cursor.getInt(0)}'", null)
                if (cursorProduct.moveToFirst()) {
                    val product = Product()
                    product.id = cursorProduct.getInt(0)
                    product.codFab = cursorProduct.getString(1)
                    product.name = cursorProduct.getString(2)
                    product.idCategory = cursorProduct.getInt(3)
                    product.idBrand = cursorProduct.getInt(4)
                    product.stock = cursorProduct.getInt(5)
                    product.price = cursorProduct.getDouble(6)
                    product.image = cursorProduct.getInt(7)
                    listProduct.add(product)
                }
                cursorProduct.close()
            } while (cursor.moveToNext())
        }
        cursor.close()
        setDatabase().close()
        return listProduct
    }

    fun deleteAllFavorites(idClient: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM favorites WHERE idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }

    fun deleteFavorites(idClient: Int, idProduct: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM favorites WHERE idClient = '$idClient' AND idProduct = '$idProduct'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }

    fun createFavorite(idClient: Int, idProduct: Int): Long {
        var id: Long = 0
        setDatabase()

        try {
            val v = ContentValues()
            v.put("idClient", idClient)
            v.put("idProduct", idProduct)
            id = setDatabase().insert("favorites", null, v)
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            setDatabase().close()
        }

        return id
    }

    // Payment methods
    fun queryForPayment(numPayment: Int): Payment {
        val payment = Payment()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM payMethod WHERE numPayment = '$numPayment'", null)
        if (cursor.moveToFirst()) {
            payment.numPayment = cursor.getInt(0)
            payment.name = cursor.getString(1)
            payment.detail = cursor.getString(2)
        }
        cursor.close()
        setDatabase().close()
        return payment
    }

    // Details methods
    fun deleteAllDetails(idClient: Int): Boolean {
        val listOrders = ArrayList<Int>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT numberOrder FROM orders WHERE idClient = '$idClient'", null)
        if (cursor.moveToFirst()) {
            do {
                listOrders.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return try {
            listOrders.forEach { setDatabase().execSQL("DELETE FROM details WHERE numOrder = '$it'") }
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun queryForDetails(numOrder: Int): ArrayList<Detail> {
        val listDetails = ArrayList<Detail>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM details WHERE numOrder = '$numOrder'", null)
        if (cursor.moveToFirst()) {
            do {
                val detail = Detail()
                detail.numOrder = cursor.getInt(1)
                detail.idProduct = cursor.getInt(2)
                detail.nameProduct = cursor.getString(3)
                detail.cant = cursor.getInt(4)
                detail.price = cursor.getDouble(5)
                listDetails.add(detail)
            } while (cursor.moveToNext())
        }
        cursor.close()
        setDatabase().close()
        return listDetails
    }
    fun putDetailsBackToCart(numOrder: Int, idClient: Int): Boolean {
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT numOrder, idProduct, cant FROM details WHERE numOrder = '$numOrder'", null)
        if (cursor.moveToFirst()) {
            do {
                val detail = Detail()
                detail.numOrder = cursor.getInt(0)
                detail.idProduct = cursor.getInt(1)
                detail.cant = cursor.getInt(2)
                addProductToShoppingCart(idClient, detail.idProduct, detail.cant)
            } while (cursor.moveToNext())
        }
        setDatabase().close()
        cursor.close()
        return true
    }

    // Order methods
    fun deleteAllOrders(idClient: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM orders WHERE idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun editTotalOrder(numOrder: Int, total: Double): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("UPDATE orders SET total = '$total' WHERE numberOrder = '$numOrder'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun editPayMethodOrder(numOrder: Int, numPayment: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("UPDATE orders SET numPayment = '$numPayment' WHERE numberOrder = '$numOrder'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun editStateOrder(numOrder: Int, state: String): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("UPDATE orders SET state = '$state' WHERE numberOrder = '$numOrder'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun queryForOrders(idClient: Int, state: String): ArrayList<Order> {
        val listOrders = ArrayList<Order>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM orders WHERE idClient = '$idClient' AND state = '$state'", null)
        if (cursor.moveToFirst()) {
            do {
                val order = Order()
                order.idClient = cursor.getInt(0)
                order.numOrder = cursor.getInt(1)
                order.date = cursor.getString(2)
                order.total = cursor.getDouble(3)
                order.state = cursor.getString(4)
                order.shipping = cursor.getString(5)
                order.payMethod = cursor.getInt(6)
                listOrders.add(order)
            } while (cursor.moveToNext())
        }
        cursor.close()
        setDatabase().close()
        return listOrders
    }
    fun queryForLastOrder(idClient: Int): Order {
        val order = Order()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM orders WHERE idClient = '$idClient' ORDER BY numberOrder DESC", null)
        if (cursor.moveToFirst()) {
            order.idClient = cursor.getInt(0)
            order.numOrder = cursor.getInt(1)
            order.date = cursor.getString(2)
            order.total = cursor.getDouble(3)
            order.state = cursor.getString(4)
            order.shipping = cursor.getString(5)
            order.payMethod = cursor.getInt(6)
        }
        cursor.close()
        setDatabase().close()
        return order
    }
    fun queryForOrder(numOrder: Int): Order {
        val order = Order()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM orders WHERE numberOrder = '$numOrder'", null)
        if (cursor.moveToFirst()) {
            order.idClient = cursor.getInt(0)
            order.numOrder = cursor.getInt(1)
            order.date = cursor.getString(2)
            order.total = cursor.getDouble(3)
            order.state = cursor.getString(4)
            order.shipping = cursor.getString(5)
            order.payMethod = cursor.getInt(6)
        }
        cursor.close()
        setDatabase().close()
        return order
    }
    fun createOrder(idClient: Int, date: String, total: Double, state: String, shipping: String, listProducts: ArrayList<ShopCartItem>): Long {
        var id: Long = 0
        setDatabase()
        try {
            val v = ContentValues()
            v.put("idClient", idClient)
            v.put("date", date)
            v.put("total", total)
            v.put("state", state)
            v.put("shipping", shipping)
            id = setDatabase().insert("orders", null, v)

            run breaking@ {
                listProducts.forEach {
                    try {
                        val vS = ContentValues()
                        vS.put("numOrder", queryForLastOrder(idClient).numOrder)
                        vS.put("idProduct", it.idProduct)
                        vS.put("nameProduct", queryForProduct(it.idProduct).name)
                        vS.put("cant", it.quantity)
                        vS.put("price", queryForProduct(it.idProduct).price * it.quantity)
                        setDatabase().insert("details", null, vS)
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        id = 0
                        return@breaking
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            setDatabase().close()
        }
        return id
    }

    // Province/City methods
    fun queryForProvinceForSpinner(cp: String): ArrayList<String> {
        val listProvinces = ArrayList<String>()
        val listPreProvince = ArrayList<Int>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT id_province FROM city WHERE cp = '$cp' ORDER BY id_province", null)
        if (cursor.moveToFirst()) { do { val idProvince = cursor.getInt(0); listPreProvince.add(idProvince) } while (cursor.moveToNext()) }
        var i = 0
        val listFinal = ArrayList<Int>()
        listPreProvince.forEach { if (it > i) { i = it; listFinal.add(it) } }
        listFinal.forEach { val cursorP: Cursor = setDatabase().rawQuery("SELECT province_name FROM province WHERE id = '$it'", null); if (cursorP.moveToFirst()) { listProvinces.add(cursorP.getString(0)) }; cursorP.close() }
        cursor.close()
        setDatabase().close()
        return listProvinces
    }
    fun queryForCitysForSpinner(postalCode: String, province_name: String): ArrayList<String> {
        setDatabase()
        val idProvince = queryForProvinceByName(province_name)
        val list = ArrayList<String>()
        val cursor: Cursor = setDatabase().rawQuery("SELECT city_name FROM city WHERE cp = '${Integer.parseInt(postalCode)}' AND id_province = '${idProvince.id}'", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        setDatabase().close()
        return list
    }
    private fun queryForProvinceByName(province_name: String): Province {
        setDatabase()
        val province = Province()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM province WHERE province_name = '$province_name'", null)
        if (cursor.moveToFirst()) {
            province.id = cursor.getInt(0)
            province.name = cursor.getString(1)
        }
        setDatabase().close()
        cursor.close()
        return province
    }
    fun queryForAllProvinces(): ArrayList<Province> {
        val listProvince = ArrayList<Province>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM province", null)
        if (cursor.moveToFirst()) {
            do {
                val province = Province()
                province.id = cursor.getInt(0)
                province.name = cursor.getString(1)
                listProvince.add(province)
            }while (cursor.moveToNext())
        }
        cursor.close()
        setDatabase().close()
        return listProvince
    }

    // Address methods

    fun deleteShippingAddress(idClient: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM address WHERE idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }

    fun deleteBillAddress(idClient: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM billAddress WHERE idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }


    fun createShippingAddress(idClient: Int, s: String, n: String, f: String, d: String, pC: String, p: String, l: String): Long {
        var id: Long = 0
        setDatabase()

        try {
            val v = ContentValues()
            v.put("idClient", idClient)
            v.put("street", s)
            v.put("number", n)
            v.put("floor", f)
            v.put("door", d)
            v.put("postalCode", pC)
            v.put("province", p)
            v.put("locality", l)
            id = setDatabase().insert("address", null, v)
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            setDatabase().close()
        }
        return id
    }
    fun createBillAddress(idClient: Int, s: String, n: String, f: String, d: String, pC: String, p: String, l: String): Long {
        var id: Long = 0
        setDatabase()

        try {
            val v = ContentValues()
            v.put("idClient", idClient)
            v.put("street", s)
            v.put("number", n)
            v.put("floor", f)
            v.put("door", d)
            v.put("postalCode", pC)
            v.put("province", p)
            v.put("locality", l)
            id = setDatabase().insert("billAddress", null, v)
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            setDatabase().close()
        }
        return id
    }
    fun editShippingAddress(idClient: Int, s: String, n: String, f: String, d: String, pC: String, p: String, l: String): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("UPDATE address SET street = '$s', number = '$n', floor = '$f', door = '$d', postalCode = '$pC', province = '$p', locality = '$l' WHERE idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun editBillAddress(idClient: Int, s: String, n: String, f: String, d: String, pC: String, p: String, l: String): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("UPDATE billAddress SET street = '$s', number = '$n', floor = '$f', door = '$d', postalCode = '$pC', province = '$p', locality = '$l' WHERE idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun queryForShippingAddress(idClient: Int): Address {
        val address = Address()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM address WHERE idClient = '$idClient'", null)
        if (cursor.moveToFirst()) {
            address.idClient = cursor.getInt(0)
            address.street = cursor.getString(1)
            address.number = cursor.getString(2)
            address.floor = cursor.getString(3)
            address.door = cursor.getString(4)
            address.postalCode = cursor.getString(5)
            address.province = cursor.getString(6)
            address.locality = cursor.getString(7)
        }
        setDatabase().close()
        cursor.close()
        return address
    }
    fun queryForBillAddress(idClient: Int): Address {
        val address = Address()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM billAddress WHERE idClient = '$idClient'", null)
        if (cursor.moveToFirst()) {
            address.idClient = cursor.getInt(0)
            address.street = cursor.getString(1)
            address.number = cursor.getString(2)
            address.floor = cursor.getString(3)
            address.door = cursor.getString(4)
            address.postalCode = cursor.getString(5)
            address.province = cursor.getString(6)
            address.locality = cursor.getString(7)
        }
        setDatabase().close()
        cursor.close()
        return address
    }

    // Products methods
    fun queryBasicForGlobalSearch(s: String): ArrayList<Product> {
        val listProduct = ArrayList<Product>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM products WHERE name LIKE '%$s%'", null)
        if (cursor.moveToFirst()) {
            do {
                val product = Product()
                product.id = cursor.getInt(0)
                product.codFab = cursor.getString(1)
                product.name = cursor.getString(2)
                product.idCategory = cursor.getInt(3)
                product.idBrand = cursor.getInt(4)
                product.stock = cursor.getInt(5)
                product.price = cursor.getDouble(6)
                product.image = cursor.getInt(7)
                listProduct.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        setDatabase().close()
        return listProduct
    }
    fun deleteProductFromShopCart(idClient: Int, idProduct: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM shopcart WHERE idClient = '$idClient' AND idProduct = '$idProduct'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun refreshQuantityOfProductOnShopCart(quantity: Int, idProduct: Int, idClient: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("UPDATE shopcart SET quantity = '$quantity' WHERE idProduct = '$idProduct' AND idClient = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun queryForProductsOnCart(idClient: Int): ArrayList<ShopCartItem> {
        val list = ArrayList<ShopCartItem>()
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM shopcart WHERE idClient = '$idClient'", null)
        if (cursor.moveToFirst()) {
            do {
                val shopCartItem = ShopCartItem()
                shopCartItem.idClient = cursor.getInt(0)
                shopCartItem.idProduct = cursor.getInt(1)
                shopCartItem.quantity = cursor.getInt(2)
                list.add(shopCartItem)
            } while (cursor.moveToNext())
        }
        setDatabase().close()
        cursor.close()
        return list
    }
    private fun alreadyExistsProductOnCart(idClient: Int, idProduct: Int): Boolean {
        var result = false
        setDatabase()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM shopcart WHERE idClient = '$idClient' AND idProduct = '$idProduct'", null)
        if (cursor.moveToFirst()) {
            result = true
        }
        setDatabase().close()
        cursor.close()
        return result
    }
    fun addProductToShoppingCart(idClient: Int, idProduct: Int, quantity: Int): Boolean {
        var result = false
        setDatabase()
        if (!alreadyExistsProductOnCart(idClient, idProduct)) {
            try {
                val v = ContentValues()
                v.put("idClient", idClient)
                v.put("idProduct", idProduct)
                v.put("quantity", quantity)
                setDatabase().insert("shopcart", null, v)
                result = true
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        } else {
            val cursorQuantity: Cursor = setDatabase().rawQuery("SELECT quantity FROM shopcart WHERE idClient = '$idClient' AND idProduct = '$idProduct'", null)
            if (cursorQuantity.moveToFirst()) {
                val quantityInit = cursorQuantity.getInt(0)
                val quantityFinal = quantity + quantityInit
                cursorQuantity.close()
                setDatabase().execSQL("UPDATE shopcart SET quantity = $quantityFinal WHERE idClient = '$idClient' AND idProduct = '$idProduct'")
                result = true
            }
        }
        setDatabase().close()
        return result
    }
    fun deleteAllProductsOnShopCart(idClient: Int) {
        setDatabase().execSQL("DELETE FROM shopcart WHERE idClient = '$idClient'")
    }
    fun queryForProduct(i: Int): Product {
        val product = Product()
        setDatabase()
        val cursorProduct: Cursor = setDatabase().rawQuery("SELECT * FROM products WHERE idProduct = '$i'", null)
        if (cursorProduct.moveToFirst()) {
            product.id = cursorProduct.getInt(0)
            product.codFab = cursorProduct.getString(1)
            product.name = cursorProduct.getString(2)
            product.idCategory = cursorProduct.getInt(3)
            product.idBrand = cursorProduct.getInt(4)
            product.stock = cursorProduct.getInt(5)
            product.price = cursorProduct.getDouble(6)
            product.image = cursorProduct.getInt(7)
        }
        setDatabase().close()
        cursorProduct.close()
        return product
    }

    // Category/Brand methods
    fun queryForCategoryCant(i: Int): ArrayList<Int> {
        val idBrandArray = ArrayList<Int>()
        setDatabase()
        val listProduct = ArrayList<Product>()
        val cursorProduct: Cursor = setDatabase().rawQuery("SELECT idBrand, idCategory FROM products WHERE idBrand = '$i' ORDER BY idCategory", null)
        if (cursorProduct.moveToFirst()) {
            do {
                val product = Product()
                product.idBrand = cursorProduct.getInt(0)
                product.idCategory = cursorProduct.getInt(1)
                listProduct.add(product)
            } while (cursorProduct.moveToNext())
        }
        cursorProduct.close()
        setDatabase().close()

        var test = 0
        listProduct.forEach {
            if (it.idCategory != test) {
                idBrandArray.add(it.idCategory)
                test = it.idCategory
            }
        }
        return idBrandArray
    }
    fun queryForBrandCant(i: Int): ArrayList<Int> {
        val idBrandArray = ArrayList<Int>()
        setDatabase()
        val listProduct = ArrayList<Product>()
        val cursorProduct: Cursor = setDatabase().rawQuery("SELECT idBrand, idCategory FROM products WHERE idCategory = $i ORDER BY idBrand", null)
        if (cursorProduct.moveToFirst()) {
            do {
                val product = Product()
                product.idBrand = cursorProduct.getInt(0)
                product.idCategory = cursorProduct.getInt(1)
                listProduct.add(product)
            } while (cursorProduct.moveToNext())
        }
        cursorProduct.close()
        setDatabase().close()

        var test = 0
        listProduct.forEach {
            if (it.idBrand != test) {
                idBrandArray.add(it.idBrand)
                test = it.idBrand
            }
        }
        return idBrandArray
    }
    fun queryForBrand(i: Int): Brand {
        setDatabase()
        val brand = Brand()
        val cursorBrand: Cursor = setDatabase().rawQuery("SELECT * FROM brands WHERE idBrand = '$i'", null)
        if (cursorBrand.moveToFirst()) {
            do {
                brand.id = cursorBrand.getInt(0)
                brand.name = cursorBrand.getString(1)
                brand.image = cursorBrand.getInt(2)
                brand.imageLight = cursorBrand.getInt(3)
            } while (cursorBrand.moveToNext())
        }
        cursorBrand.close()
        setDatabase().close()
        return brand
    }
    fun queryForSubCategory(i: Int): Category {
        setDatabase()
        val category = Category()
        val cursorCategory: Cursor = setDatabase().rawQuery("SELECT * FROM category WHERE idCategory = $i", null)
        if (cursorCategory.moveToFirst()) {
            do {
                category.id = cursorCategory.getInt(0)
                category.name = cursorCategory.getString(1)
            } while (cursorCategory.moveToNext())
        }
        cursorCategory.close()
        setDatabase().close()
        return category
    }
    fun queryForSubCategoryProducts(i: Int): ArrayList<Product> {
        setDatabase()
        val listProduct = ArrayList<Product>()
        val cursorProduct: Cursor = setDatabase().rawQuery("SELECT * FROM products WHERE idCategory = $i ORDER BY RANDOM() LIMIT 0,10", null)
        if (cursorProduct.moveToFirst()) {
            do {
                val product = Product()
                product.id = cursorProduct.getInt(0)
                product.codFab = cursorProduct.getString(1)
                product.name = cursorProduct.getString(2)
                product.idCategory = cursorProduct.getInt(3)
                product.idBrand = cursorProduct.getInt(4)
                product.stock = cursorProduct.getInt(5)
                product.price = cursorProduct.getDouble(6)
                product.image = cursorProduct.getInt(7)
                listProduct.add(product)
            } while (cursorProduct.moveToNext())
        }
        cursorProduct.close()
        setDatabase().close()
        return listProduct
    }
    fun queryForProductsByBrandAndCategory(iB: Int, iC: Int): ArrayList<Product> {
        setDatabase()
        val listProduct = ArrayList<Product>()
        val cursorProduct: Cursor = setDatabase().rawQuery("SELECT * FROM products WHERE idCategory = '$iC' AND idBrand = '$iB'", null)
        if (cursorProduct.moveToFirst()) {
            do {
                val product = Product()
                product.id = cursorProduct.getInt(0)
                product.codFab = cursorProduct.getString(1)
                product.name = cursorProduct.getString(2)
                product.idCategory = cursorProduct.getInt(3)
                product.idBrand = cursorProduct.getInt(4)
                product.stock = cursorProduct.getInt(5)
                product.price = cursorProduct.getDouble(6)
                product.image = cursorProduct.getInt(7)
                listProduct.add(product)
            } while (cursorProduct.moveToNext())
        }
        cursorProduct.close()
        setDatabase().close()
        return listProduct
    }

    // Client methods

    fun deleteClient(idClient: Int): Boolean {
        setDatabase()
        return try {
            setDatabase().execSQL("DELETE FROM clients WHERE id = '$idClient'")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }

    fun queryForClient(email: String): Client {
        setDatabase()
        val client = Client()
        val cursor: Cursor = setDatabase().rawQuery("SELECT * FROM clients WHERE email = '$email'", null)
        if (cursor.moveToFirst()) {
            client.id = cursor.getInt(0)
            client.email = cursor.getString(1) ?: ""
            client.name = cursor.getString(2) ?: ""
            client.birthday = cursor.getString(3) ?: ""
            client.codArea = cursor.getString(4) ?: ""
            client.numCelular = cursor.getString(5) ?: ""
            client.dni = cursor.getString(6) ?: ""
            client.cuit = cursor.getString(7) ?: ""
            client.wantABill = cursor.getString(8) ?: ""
            client.ivaCondition = cursor.getString(9) ?: ""
        }
        cursor.close()
        setDatabase().close()
        return client
    }
    fun editClient(id: Int, n: String, b: String, c: String, nu: String, dni: String, cuit: String, w: String, i: String ?= null): Boolean {
        setDatabase()
        return try {
            if (i != null) {
                setDatabase().execSQL("UPDATE clients SET name = '$n', birthday = '$b', codarea = '$c', numCelular = '$nu', dni = '$dni', cuit = '$cuit', wantABill = '$w', ivaCondition = '$i' WHERE id = '$id'")
            } else {
                setDatabase().execSQL("UPDATE clients SET name = '$n', birthday = '$b', codarea = '$c', numCelular = '$nu', dni = '$dni', cuit = '$cuit', wantABill = '$w' WHERE id = '$id'")
            }
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            setDatabase().close()
        }
    }
    fun createBasicClient(email: String, name: String, dni: String, cuit: String): Long {
        var id: Long = 0
        val wantABill = "No"

        try {
            setDatabase()
            val v = ContentValues()
            v.put("email", email)
            v.put("name", name)
            v.put("dni", dni)
            v.put("cuit", cuit)
            v.put("wantABill", wantABill)

            id = setDatabase().insert("clients", null, v)
            setDatabase().close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return id
    }
}