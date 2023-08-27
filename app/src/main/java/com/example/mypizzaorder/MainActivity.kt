package com.example.mypizzaorder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

private const val TAG = "MainActivity"
private const val KEY_RESULT = "result"
private var res = "result"

class MainActivity : AppCompatActivity() {
    var order = ""
    var meat_type = ""
    var size = ""
    var cheese_type = ""
    var veggie_type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            res = savedInstanceState.getString(KEY_RESULT).toString()
        }
       // val pref = getPreferences(Context.MODE_PRIVATE)
       // val name = pref.getString("NAME", "")
        //editName.setText(name)
        val pref = getPreferences(Context.MODE_PRIVATE)
        val name = pref.getString("NAME","")
        editName.setText(name)

        var pizza_size_price = 0
        var meat_price = 0
        var cheese_price = 0
        var veggie_price = 0
        var total_price: Int
        Price.text = res;
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.rb1) {
                pizza_size_price = 9
                size = "small"
            }
            if (i == R.id.rb2) {
                pizza_size_price = 10
                size = "medium"
            }
            if (i == R.id.rb3) {
                pizza_size_price = 11
                size = "large"
            }
            total_price = pizza_size_price + meat_price + cheese_price + veggie_price;
            Price.text = "$" + total_price.toString();
        }
        cb1.setOnCheckedChangeListener { compoundButton, b ->
            if (cb1.isChecked) {
                meat_price = 2
                meat_type = "meat"
            } else {
                meat_price = 0
                meat_type = ""
            }
            total_price = pizza_size_price + meat_price + cheese_price + veggie_price;
            Price.text = "$" + total_price.toString()
        }
        cb2.setOnCheckedChangeListener { compoundButton, b ->
            if (cb2.isChecked) {
                cheese_price = 2
                cheese_type = "cheese"
            } else {
                cheese_price = 0
                cheese_type = ""
            }
            total_price = pizza_size_price + meat_price + cheese_price + veggie_price;
            Price.text = "$" + total_price.toString();
        }
        cb3.setOnCheckedChangeListener { compoundButton, b ->
            if (cb3.isChecked) {
                veggie_price = 2;
                veggie_type = "veggie"
            } else {
                veggie_price = 0;
                veggie_type = ""
            }
            total_price = pizza_size_price + meat_price + cheese_price + veggie_price;
            Price.text = "$" + total_price.toString();
        }
        switch1.setOnCheckedChangeListener { compoundButton, SwitchOn ->
            if (SwitchOn) {
                textView4.visibility = View.VISIBLE
                Address.visibility = View.VISIBLE
                btnOk.visibility = View.VISIBLE
            } else {
                textView4.visibility = View.INVISIBLE
                Address.visibility = View.INVISIBLE
                btnOk.visibility = View.INVISIBLE
            }
        }
        btnOk.setOnClickListener {
            Toast.makeText(this, "Delivery Confirmed", Toast.LENGTH_LONG).show()
            Address.isEnabled = false
        }
        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Order Confirmed", Toast.LENGTH_LONG).show()
            cb1.isChecked = false
            cb2.isChecked = false
            cb3.isChecked = false
            rb1.isChecked = false
            rb2.isChecked = false
            rb3.isChecked = false
            Price.text = ""
            switch1.isChecked = false
        }
    }

    override fun onPause() {
        super.onPause();
        Log.i(TAG, "OnPause() was called")
    }

    override fun onStop() {
        super.onStop();
        Log.i(TAG, "OnStop() was called")
    }

    override fun onResume() {
        super.onResume();
        Log.i(TAG, "OnResume() was called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RESULT, res)
    }

      fun onConfirm(view: View) {
        // create shared preferences file
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()

        // save name
        editor.putString("NAME", editName.text.toString())
        // commit changes
        editor.commit()
          // File save starts here
          order = editName.text.toString() + " " + size + " " + meat_type + " " + cheese_type + " " + veggie_type
        val file = "previousOrders.txt"
          val fileOutputStream: FileOutputStream
          try {
              fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
              fileOutputStream.write(file.toByteArray())
          } catch (e: FileNotFoundException) {
              e.printStackTrace()
          } catch (e : Exception) {
              e.printStackTrace()
          }
          showToast("Saved to file")

    }





// function extension for Toast.makeText(...)
    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }

    fun onShow(view: View) {
        val filename = "previousOrders.txt"

        if (filename.toString() != null && filename.trim() != "") {
            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput(filename)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }

            // Displaying data on editText
            txtDisplay.setText(stringBuilder.toString()).toString()
        } else {
            showToast("name of file cannot be blank")
        }
    }
}
