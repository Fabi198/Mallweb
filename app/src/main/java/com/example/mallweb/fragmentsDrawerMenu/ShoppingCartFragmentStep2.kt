package com.example.mallweb.fragmentsDrawerMenu

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentShoppingCartStep2Binding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Client
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment
import com.example.mallweb.html.*
import java.util.*


@Suppress("DEPRECATION")
class ShoppingCartFragmentStep2 : Fragment(R.layout.fragment_shopping_cart_step2) {

    private lateinit var binding: FragmentShoppingCartStep2Binding
    private var flagSpinnerIVA = false
    private lateinit var dbMallweb: DbMallweb


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShoppingCartStep2Binding.bind(view)

        setShippingVisibility()
        setAddressFromPostalCode()
        setClientData()
        setBillCheckers()
        bothSameAddress()
    }

    private fun setShippingVisibility() {
        binding.cvShippingAddress.visibility = if (arguments?.getBoolean("withShipping") == true) { View.VISIBLE } else { View.GONE }
    }
    private fun bothSameAddress() {
        binding.cbBothSameAddress.setOnClickListener {
            if (binding.cbBothSameAddress.isChecked) {
                binding.streetShippingAddress.text = binding.streetBillAddress.text
                binding.heightShippingAddress.text = binding.heightBillAddress.text
                binding.floorShippingAddress.text = binding.floorBillAddress.text
                binding.doorShippingAddress.text = binding.doorBillAddress.text
                binding.postalCodeShippingAddress.text = binding.postalCodeBillAddress.text
                setKnownProvinceSpinner(binding.spinnerProvinceShippingAddress, binding.spinnerProvinceBillAddress.selectedItem.toString())
                setKnownLocalitySpinner(binding.spinnerLocalityShippingAddress, binding.spinnerLocalityBillAddress.selectedItem.toString())
            } else if (!binding.cbBothSameAddress.isChecked) {
                binding.streetShippingAddress.setText("")
                binding.heightShippingAddress.setText("")
                binding.floorShippingAddress.setText("")
                binding.doorShippingAddress.setText("")
                binding.postalCodeShippingAddress.setText("")
                setKnownProvinceSpinner(binding.spinnerProvinceShippingAddress, "")
                setKnownLocalitySpinner(binding.spinnerLocalityShippingAddress, "")
            }
        }
    }
    private fun notFoundBillLocality() {
        if (binding.cbLocalityBillNotFound.isChecked) {
            setSpinnerAllProvinces(binding.spinnerProvinceBillAddress)
            binding.spinnerLocalityBillAddress.visibility = View.GONE
            binding.localityBillAddress.setTextColor(resources.getColor(R.color.white))
            binding.localityBillAddress.visibility = View.VISIBLE
        } else {
            binding.spinnerLocalityBillAddress.visibility = View.VISIBLE
            binding.localityBillAddress.visibility = View.GONE
        }
    }
    private fun notFoundShippingLocality() {
        if (binding.cbLocalityShippingNotFound.isChecked) {
            setSpinnerAllProvinces(binding.spinnerProvinceShippingAddress)
            binding.spinnerLocalityShippingAddress.visibility = View.GONE
            binding.localityShippingAddress.setTextColor(resources.getColor(R.color.white))
            binding.localityShippingAddress.visibility = View.VISIBLE
        } else {
            binding.spinnerLocalityShippingAddress.visibility = View.VISIBLE
            binding.localityShippingAddress.visibility = View.GONE
        }
    }
    private fun setAddressFromPostalCode() {
        binding.postalCodeBillAddress.setOnKeyListener { _, keyCode, event -> if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) { hideKeyboard(); setSpinnerProvince(binding.postalCodeBillAddress.text.toString(), binding.spinnerProvinceBillAddress); return@setOnKeyListener true }; false }
        binding.postalCodeShippingAddress.setOnKeyListener { _, keyCode, event -> if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) { hideKeyboard(); setSpinnerProvince(binding.postalCodeShippingAddress.text.toString(), binding.spinnerProvinceShippingAddress); return@setOnKeyListener true }; false }
    }
    private fun setIVASpinner(alreadyKnownOptions: Int? = null) {
        val adapterIVA = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, setIVAConditions())
        adapterIVA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding.spinnerIVACondition) {
            adapter = adapterIVA
            if (alreadyKnownOptions != null) {setSelection(alreadyKnownOptions)} else {setSelection(0)}
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) { Toast.makeText(requireContext(), "NothingSelected", Toast.LENGTH_SHORT).show() }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { if (!flagSpinnerIVA) { flagSpinnerIVA = true } else { binding.spinnerIVACondition.selectedItem.toString() } }
            }
        }
    }
    private fun setKnownProvinceSpinner(spinner: Spinner, province_name: String) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf(province_name))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }
    private fun setKnownLocalitySpinner(spinner: Spinner, city_name: String) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf(city_name))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }
    private fun setSpinnerAllProvinces(spinner: Spinner) {
        dbMallweb = DbMallweb(requireContext())
        val list = ArrayList<String>()
        dbMallweb.queryForAllProvinces().forEach { list.add(it.name) }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { spinner.selectedItem.toString() }
        }
    }
    private fun getArrayForAdapterProvinceSpinner(postalCode: String): ArrayList<String> {
        dbMallweb = DbMallweb(requireContext())
        val list: ArrayList<String> = dbMallweb.queryForProvinceForSpinner(postalCode)
        if (list.size == 0) { Toast.makeText(requireContext(), "No existe el codigo postal", Toast.LENGTH_SHORT).show() }
        return list
    }
    private fun getAdapterForProvinceSpinner(postalCode: String): ArrayAdapter<String> {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, getArrayForAdapterProvinceSpinner(postalCode))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
    private fun setSpinnerProvince(postalCode: String, spinner: Spinner) {
        spinner.adapter = getAdapterForProvinceSpinner(postalCode)
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { if (spinner == binding.spinnerProvinceBillAddress) { setLocalitySpinner(binding.spinnerLocalityBillAddress, postalCode, spinner.selectedItem.toString()) } else if (spinner == binding.spinnerProvinceShippingAddress) { setLocalitySpinner(binding.spinnerLocalityShippingAddress, postalCode, spinner.selectedItem.toString()) } }
        }
    }
    private fun getArrayForAdapterLocalitySpinner(postalCode: String, province_name: String): ArrayList<String> {
        dbMallweb = DbMallweb(requireContext())
        return dbMallweb.queryForCitysForSpinner(postalCode, province_name)
    }
    private fun setAdapterForLocalitySpinner(postalCode: String, province_name: String): ArrayAdapter<String> {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, getArrayForAdapterLocalitySpinner(postalCode, province_name))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
    private fun setLocalitySpinner(spinner: Spinner, postalCode: String, province_name: String) {
        spinner.adapter = setAdapterForLocalitySpinner(postalCode, province_name)
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { spinner.selectedItem.toString() }
        }
    }
    private fun setClientData() {
        dbMallweb = DbMallweb(requireContext())
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        with(prefs.getString("email", null)?.let { dbMallweb.queryForClient(it) }) {
            this?.let {
                fillClientData(it)
                if (dbMallweb.queryForShippingAddress(id).idClient > 0) { fillShippingAddress(id) } else { binding.postalCodeShippingAddress.setText(arguments?.getInt("postalCode").toString()); binding.cbLocalityShippingNotFound.setOnClickListener { notFoundShippingLocality() } }
                if (dbMallweb.queryForBillAddress(id).idClient > 0) { fillBillAddress(id) } else { binding.cbLocalityBillNotFound.setOnClickListener { notFoundBillLocality() } }

                var flag1 = true
                var flag2 = true
                var flag3 = true
                var flag4 = true

                binding.btnActProfile.setOnClickListener {_ ->
                    if (binding.cbWantABillYES.isChecked) { flag1 = editClientBillAYes(id) }
                    if (binding.cbWantABillNO.isChecked) { flag2 = editClientBillANo(id) }
                    if (binding.cvBillAddress.visibility == View.VISIBLE) { if (areBillAddressFieldEmpty()) { if (dbMallweb.queryForBillAddress(id).idClient > 0) { if (binding.spinnerLocalityBillAddress.visibility == View.VISIBLE) { flag3 = editBillAddress(id) } else if (binding.localityBillAddress.visibility == View.VISIBLE) { flag3 = editBillAddressWithUnknownLocality(id) } } else { if (binding.spinnerLocalityBillAddress.visibility == View.VISIBLE) { flag3 = createBillAddress(id) > 0 } else if (binding.localityBillAddress.visibility == View.VISIBLE) { flag3 = createBillAddressWithUnknownLocality(id) > 0 } } } else { flag3 = false } }
                    if (binding.cvShippingAddress.visibility == View.VISIBLE) { if (areShippingAddressFieldEmpty()) { if (dbMallweb.queryForShippingAddress(id).idClient > 0) { if (binding.spinnerLocalityShippingAddress.visibility == View.VISIBLE) { flag4 = editShippingAddress(id) } else if (binding.localityShippingAddress.visibility == View.VISIBLE) { flag4 = editShippingAddressWithUnknownLocality(id) } } else { if (binding.spinnerLocalityShippingAddress.visibility == View.VISIBLE) { flag4 = createShippingAddress(id) > 0 } else if (binding.localityShippingAddress.visibility == View.VISIBLE) { flag4 = createShippingAddressWithUnknownLocality(id) > 0 } } } else { flag4 = false } }
                    if (flag1 && flag2 && flag3 && flag4) {
                        showAlertSuccess(arguments?.getInt("ContainerID"))
                    } else {
                        showAlertError()
                    }
                }
            }
        }
    }
    private fun createShippingAddressWithUnknownLocality(id: Int): Long { return dbMallweb.createShippingAddress(id, binding.streetShippingAddress.text.toString(), binding.heightShippingAddress.text.toString(), binding.floorShippingAddress.text.toString(), binding.doorShippingAddress.text.toString(), binding.postalCodeShippingAddress.text.toString(), binding.spinnerProvinceShippingAddress.selectedItem.toString(), binding.spinnerLocalityShippingAddress.selectedItem.toString()) }
    private fun editShippingAddressWithUnknownLocality(id: Int): Boolean { return dbMallweb.editShippingAddress(id, binding.streetShippingAddress.text.toString(), binding.heightShippingAddress.text.toString(), binding.floorShippingAddress.text.toString(), binding.doorShippingAddress.text.toString(), binding.postalCodeShippingAddress.text.toString(), binding.spinnerProvinceShippingAddress.selectedItem.toString(), binding.spinnerLocalityShippingAddress.selectedItem.toString()) }
    private fun createBillAddressWithUnknownLocality(id: Int): Long { return dbMallweb.createBillAddress(id, binding.streetBillAddress.text.toString(), binding.heightBillAddress.text.toString(), binding.floorBillAddress.text.toString(), binding.doorBillAddress.text.toString(), binding.postalCodeBillAddress.text.toString(), binding.spinnerProvinceBillAddress.selectedItem.toString(), binding.localityBillAddress.text.toString()) }
    private fun editBillAddressWithUnknownLocality(id: Int): Boolean { return dbMallweb.editBillAddress(id, binding.streetBillAddress.text.toString(), binding.heightBillAddress.text.toString(), binding.floorBillAddress.text.toString(), binding.doorBillAddress.text.toString(), binding.postalCodeBillAddress.text.toString(), binding.spinnerProvinceBillAddress.selectedItem.toString(), binding.localityBillAddress.text.toString()) }
    private fun createShippingAddress(id: Int): Long { return dbMallweb.createShippingAddress(id, binding.streetShippingAddress.text.toString(), binding.heightShippingAddress.text.toString(), binding.floorShippingAddress.text.toString(), binding.doorShippingAddress.text.toString(), binding.postalCodeShippingAddress.text.toString(), binding.spinnerProvinceShippingAddress.selectedItem.toString(), binding.spinnerLocalityShippingAddress.selectedItem.toString()) }
    private fun editShippingAddress(id: Int): Boolean { return dbMallweb.editShippingAddress(id, binding.streetShippingAddress.text.toString(), binding.heightShippingAddress.text.toString(), binding.floorShippingAddress.text.toString(), binding.doorShippingAddress.text.toString(), binding.postalCodeShippingAddress.text.toString(), binding.spinnerProvinceShippingAddress.selectedItem.toString(), binding.spinnerLocalityShippingAddress.selectedItem.toString()) }
    private fun createBillAddress(id: Int): Long { return dbMallweb.createBillAddress(id, binding.streetBillAddress.text.toString(), binding.heightBillAddress.text.toString(), binding.floorBillAddress.text.toString(), binding.doorBillAddress.text.toString(), binding.postalCodeBillAddress.text.toString(), binding.spinnerProvinceBillAddress.selectedItem.toString(), binding.spinnerLocalityBillAddress.selectedItem.toString()) }
    private fun editBillAddress(id: Int): Boolean { return dbMallweb.editBillAddress(id, binding.streetBillAddress.text.toString(), binding.heightBillAddress.text.toString(), binding.floorBillAddress.text.toString(), binding.doorBillAddress.text.toString(), binding.postalCodeBillAddress.text.toString(), binding.spinnerProvinceBillAddress.selectedItem.toString(), binding.spinnerLocalityBillAddress.selectedItem.toString()) }
    private fun editClientBillANo(id: Int): Boolean { return dbMallweb.editClient(id, binding.nameClient.text.toString(), binding.dateBirthClient.text.toString(), binding.codAreaNumber.text.toString(), binding.numberCellphoneMallwebClient.text.toString(), binding.dniClientMallweb.text.toString(), binding.cuitClientMallweb.text.toString(), "no") }
    private fun editClientBillAYes(id: Int): Boolean { return dbMallweb.editClient(id, binding.nameClient.text.toString(), binding.dateBirthClient.text.toString(), binding.codAreaNumber.text.toString(), binding.numberCellphoneMallwebClient.text.toString(), binding.dniClientMallweb.text.toString(), binding.cuitClientMallweb.text.toString(), "si", binding.spinnerIVACondition.selectedItem.toString()) }
    private fun fillBillAddress(idClient: Int) { with(dbMallweb.queryForBillAddress(idClient)) { binding.streetBillAddress.setText(street); binding.heightBillAddress.setText(number); binding.floorBillAddress.setText(floor); binding.doorBillAddress.setText(door); binding.postalCodeBillAddress.setText(postalCode); setKnownProvinceSpinner(binding.spinnerProvinceBillAddress, province); setKnownLocalitySpinner(binding.spinnerLocalityBillAddress, locality); binding.cbLocalityBillNotFound.setOnClickListener { notFoundBillLocality() } } }
    private fun fillShippingAddress(idClient: Int) { with(dbMallweb.queryForShippingAddress(idClient)) { binding.streetShippingAddress.setText(street); binding.heightShippingAddress.setText(number); binding.floorShippingAddress.setText(floor); binding.doorShippingAddress.setText(door); binding.postalCodeShippingAddress.setText(postalCode); setKnownProvinceSpinner(binding.spinnerProvinceShippingAddress, province); setKnownLocalitySpinner(binding.spinnerLocalityShippingAddress, locality); binding.cbLocalityShippingNotFound.setOnClickListener { notFoundShippingLocality() } } }
    private fun fillClientData(client: Client) { with(client) { binding.idclient.text = id.toString(); binding.emailClient.text = email; binding.nameClient.setText(name); binding.dateBirthClient.setText(birthday); binding.codAreaNumber.setText(codArea); binding.numberCellphoneMallwebClient.setText(numCelular); binding.dniClientMallweb.setText(dni); binding.cuitClientMallweb.setText(cuit); if (wantABill.lowercase() == "si" && ivaCondition != "") { setBillBoolean(wantABill, ivaCondition) } else if (wantABill.lowercase() == "no" || wantABill.lowercase() == "si") { setBillBoolean(wantABill) }; binding.dateBirthClient.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) { openCalendar(); binding.dateBirthClient.setOnClickListener { openCalendar() } } } } }
    private fun setBillCheckers() {
        binding.cbWantABillNO.setOnClickListener { if (binding.cbWantABillNO.isChecked) { binding.cbWantABillYES.isChecked = false; if (binding.llwantBillA.visibility == View.VISIBLE) { binding.llwantBillA.visibility = View.GONE } } }
        binding.cbWantABillYES.setOnClickListener { if (binding.cbWantABillYES.isChecked) { binding.cbWantABillNO.isChecked = false; binding.llwantBillA.visibility = View.VISIBLE; if (binding.cuitClientMallweb.text.toString().isNotEmpty()) { binding.cuit2ClientMallweb.text = binding.cuitClientMallweb.text } } }
    }
    private fun areBillAddressFieldEmpty(): Boolean { return (binding.spinnerProvinceBillAddress.adapter != null && binding.streetBillAddress.text.toString().isNotEmpty() && binding.heightBillAddress.text.toString().isNotEmpty() && binding.postalCodeBillAddress.text.toString().isNotEmpty() && isLocalityBillEmpty()) }
    private fun areShippingAddressFieldEmpty(): Boolean { return (binding.spinnerProvinceBillAddress.adapter != null && binding.streetShippingAddress.text.toString().isNotEmpty() && binding.heightShippingAddress.text.toString().isNotEmpty() && binding.postalCodeShippingAddress.text.toString().isNotEmpty() && isLocalityShippingEmpty()) }
    private fun isLocalityBillEmpty(): Boolean { return (binding.spinnerLocalityBillAddress.visibility == View.VISIBLE && binding.spinnerLocalityBillAddress.adapter != null) || (binding.localityBillAddress.visibility == View.VISIBLE && binding.localityBillAddress.text.toString().isNotEmpty()) }
    private fun isLocalityShippingEmpty(): Boolean { return (binding.spinnerLocalityShippingAddress.visibility == View.VISIBLE && binding.spinnerLocalityShippingAddress.adapter != null) || (binding.localityShippingAddress.visibility == View.VISIBLE && binding.localityShippingAddress.text.toString().isNotEmpty()) }
    private fun setBillBoolean(s: String, iClient: String? = null) {
        if (s.lowercase() == "si") {
            binding.cbWantABillYES.isChecked = true
            binding.llwantBillA.visibility = View.VISIBLE
            if (binding.cuitClientMallweb.text.toString().isNotEmpty()) { binding.cuit2ClientMallweb.text = binding.cuitClientMallweb.text }
            if (iClient != null) { setIVAConditions().forEachIndexed { index, i -> if (iClient == i) { setIVASpinner(index) } } } else { setIVASpinner() }
        } else if (s.lowercase() == "no") {
            binding.cbWantABillNO.isChecked = true
            setIVASpinner()
            binding.llwantBillA.visibility = View.GONE
        }

    }
    private fun openCalendar() {
        val cal: Calendar = Calendar.getInstance()
        val dpd = DatePickerDialog(
            requireContext(), 0,
            { _, year, month, dayOfMonth ->
                lateinit var fecha: String
                if ((month + 1) in 0..9 && dayOfMonth in 10..31) { fecha = "$year-0${month + 1}-$dayOfMonth" } else if ((month + 1) in 0..9 && dayOfMonth in 0..9) { fecha = "$year-0${month + 1}-0$dayOfMonth" } else if ((month + 1) in 10..12 && dayOfMonth in 0..9) { fecha = "$year-${month + 1}-0$dayOfMonth" } else if ((month + 1) in 10..12 && dayOfMonth in 10..31) { fecha = "$year-${month + 1}-$dayOfMonth" }
                binding.dateBirthClient.setText(fecha) }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dpd.show()
    }
    private fun showAlertSuccess(idFragment: Int?) {
        val builder = AlertDialog.Builder(requireContext())
        val existingOrder = arguments?.getInt(getString(R.string.existingOrder))
        builder.setMessage("Sus datos han sido actualizados")
        builder.setPositiveButton("Ir al Checkout"){ _, _ ->
            if (idFragment != null) {
                if (binding.cvShippingAddress.visibility == View.VISIBLE) {
                    if (existingOrder != null) {
                        if (existingOrder > 0) {
                            showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id, postalCode = Integer.parseInt(binding.postalCodeShippingAddress.text.toString()), withShipping = true, existingOrder = existingOrder)
                        } else if (existingOrder == 0) {
                            showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id, postalCode = Integer.parseInt(binding.postalCodeShippingAddress.text.toString()), withShipping = true)
                        } else {
                            showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id, postalCode = Integer.parseInt(binding.postalCodeShippingAddress.text.toString()), withShipping = true)
                        }
                    } else {
                        showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id, postalCode = Integer.parseInt(binding.postalCodeShippingAddress.text.toString()), withShipping = true)
                    }
                } else if (binding.cvShippingAddress.visibility == View.GONE) {
                    if (existingOrder != null) {
                        if (existingOrder > 0) {
                            showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id, existingOrder = existingOrder)
                        } else if (existingOrder == 0) {
                            showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id)
                        } else {
                            showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id)
                        }
                    } else {
                        showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep3(), "ShoppingCartFragmentStep3", idFragment, idClient = id)
                    }
                }
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showAlertError() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Sus datos no han sido actualizados\nRevise si ha completado todos los campos necesarios")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun setIVAConditions(): Array<String> { return arrayOf("Seleccione", "Inscripto", "Excento", "Consumidor finál", "Monotributo", "No categorizado") }
    private fun hideKeyboard() { val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager; imm.hideSoftInputFromWindow(binding.svSCFStep2.windowToken, 0) }

}