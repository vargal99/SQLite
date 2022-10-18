package com.example.android.sqllite

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase

        var rs = db.rawQuery("SELECT * FROM USERS", null)

        val btnFirst: Button = findViewById(R.id.button)
        val btnNext: Button = findViewById(R.id.btn_next)
        val btnLast: Button = findViewById(R.id.btn_last)
        val btnPrev: Button = findViewById(R.id.btn_prev)

        val name: EditText = findViewById(R.id.editTextTextPersonName)
        val email: EditText = findViewById(R.id.editTextTextEmailAddress)
        val age: EditText = findViewById(R.id.editTextNumberDecimal)

        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnClear: Button = findViewById(R.id.btn_clear)
        val btnUpdate: Button = findViewById(R.id.btn_update)
        val btnDelete: Button = findViewById(R.id.btn_delete)


        btnFirst.setOnClickListener {
            if (rs.moveToFirst()) {
                name.setText(rs.getString(1))
                email.setText(rs.getString(2))
                age.setText(rs.getInt(3).toString())
            } else {
                Toast.makeText(applicationContext, "Nincs adat", Toast.LENGTH_SHORT).show()
            }
        }

        btnLast.setOnClickListener {
            if (rs.moveToLast()) {
                name.setText(rs.getString(1))
                email.setText(rs.getString(2))
                age.setText(rs.getInt(3).toString())
            } else {
                Toast.makeText(applicationContext, "Nincs adat", Toast.LENGTH_SHORT).show()
            }
        }

        btnNext.setOnClickListener {
            if (rs.moveToNext()) {
                name.setText(rs.getString(1))
                email.setText(rs.getString(2))
                age.setText(rs.getInt(3).toString())
            } else if (rs.moveToFirst()) {
                rs.moveToFirst()
                name.setText(rs.getString(1))
                email.setText(rs.getString(2))
                age.setText(rs.getInt(3).toString())

            } else {
                Toast.makeText(applicationContext, "Nincs adat", Toast.LENGTH_SHORT).show()
            }
        }

        btnPrev.setOnClickListener {
            if (rs.moveToPrevious()) {
                name.setText(rs.getString(1))
                email.setText(rs.getString(2))
                age.setText(rs.getInt(3).toString())
            } else if (rs.moveToLast()) {
                rs.moveToLast()
                name.setText(rs.getString(1))
                email.setText(rs.getString(2))
                age.setText(rs.getInt(3).toString())

            } else {
                Toast.makeText(applicationContext, "Nincs adat", Toast.LENGTH_SHORT).show()
            }
        }

        btnClear.setOnClickListener {

            name.setText("")
            email.setText("")
            age.setText("")
            name.requestFocus()
        }

        btnAdd.setOnClickListener {

            if (name.text.toString() != "" && email.text.toString() != "" && age.text.toString() != "") {
                val cv = ContentValues()
                cv.put("name", name.text.toString())
                cv.put("email", email.text.toString())
                cv.put("age", age.text.toString().toInt())

                db.insert("USERS", null, cv)
                rs.requery()


                val ad = AlertDialog.Builder(this)
                ad.setTitle("Elem hozzáadása")
                ad.setMessage("Az új elem hozzáadása sikerült")
                ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    name.setText("")
                    email.setText("")
                    age.setText("")
                    name.requestFocus()
                })
                ad.show()
            } else {
                Toast.makeText(applicationContext, "Üres mező!", Toast.LENGTH_SHORT).show()
            }
        }


        btnUpdate.setOnClickListener {

            if (name.text.toString() != "" && email.text.toString() != "" && age.text.toString() != "") {
                val cv = ContentValues()
                cv.put("name", name.text.toString())
                cv.put("email", email.text.toString())
                cv.put("age", age.text.toString().toInt())

                db.update("USERS", cv, "id=?", arrayOf(rs.getString(0)))
                rs.requery()


                val ad = AlertDialog.Builder(this)
                ad.setTitle("Elem firssitése")
                ad.setMessage("Az elem módosításasa sikerült")
                ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                })
                ad.show()
            } else {
                Toast.makeText(applicationContext, "Üres mező!", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            if (name.text.toString() != "" && email.text.toString() != "" && age.text.toString() != "") {
            db.delete("USERS", "id=?", arrayOf(rs.getString(0)))
            rs.requery()

                val ad = AlertDialog.Builder(this)
                ad.setTitle("Elem törlése")
                ad.setMessage("Az elem törlése sikerült")
                ad.setPositiveButton("OK", DialogInterface.OnClickListener
                { dialogInterface, i ->
                    if (rs.moveToFirst()) {
                        name.setText(rs.getString(1))
                        email.setText(rs.getString(2))
                        age.setText(rs.getString(3))
                    } else {
                        name.setText("Nincs adat")
                        email.setText("Nincs adat")
                        age.setText("Nincs adat")

                    }
                })
                ad.show()
            }else {
                Toast.makeText(applicationContext, "Üres mező!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

