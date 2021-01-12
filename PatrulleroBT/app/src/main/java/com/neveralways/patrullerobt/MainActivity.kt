package com.neveralways.patrullerobt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.hp.bluetoothjhr.BluetoothJhr
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothJhr = BluetoothJhr(this, lista_dispositivos)
        bluetoothJhr.EncenderBluetooth()

        lista_dispositivos.setOnItemClickListener { adapterView, view, i, l ->
            bluetoothJhr.Disp_Seleccionado(view, i, Main2Activity::class.java)
        }
    }
}
