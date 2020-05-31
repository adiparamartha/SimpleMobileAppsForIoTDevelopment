package com.paramarthaxyz.aplikasicerdas

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readDB();

        getDHT();

        findViewById<Button>(R.id.button).setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val clickDB = database.getReference("devCondition/ledStatusOn")

            clickDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value =
                        dataSnapshot.getValue(Boolean::class.java)
                    if(value == true){
                        findViewById<Button>(R.id.button).text="Matikan";
                        clickDB.setValue(false);
                    }else{
                        findViewById<Button>(R.id.button).text="Hidupkan";
                        clickDB.setValue(true);
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value

                }
            })
        }
    }

    private fun readDB(){

        val database = FirebaseDatabase.getInstance()
        val checkDBRef = database.getReference("devCondition/ledStatusOn")

        // Read from the database
        checkDBRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value =
                    dataSnapshot.getValue(Boolean::class.java)
                    if(value == true){
                        findViewById<Button>(R.id.button).text="Matikan";
                    }else{
                        findViewById<Button>(R.id.button).text="Hidupkan";
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
    }

    private fun getDHT(){

        val database = FirebaseDatabase.getInstance()
        val checkDHT = database.getReference("DHT")

        // Read from the database
        checkDHT.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val valueTemperature =
                    dataSnapshot.child("temperature").getValue(Float::class.java).toString()
                    findViewById<TextView>(R.id.temperature).text=valueTemperature;

                val valueHumidity =
                    dataSnapshot.child("humidity").getValue(Float::class.java).toString()
                findViewById<TextView>(R.id.humidity).text=valueHumidity;
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
    }

}
