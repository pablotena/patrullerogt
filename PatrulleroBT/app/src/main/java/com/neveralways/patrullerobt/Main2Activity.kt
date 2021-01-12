package com.neveralways.patrullerobt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hp.bluetoothjhr.BluetoothJhr
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.*
import android.util.Log
import kotlin.concurrent.thread

class Main2Activity : AppCompatActivity() {

    lateinit var bluetoothJhr: BluetoothJhr
    var hiloEjecutando:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        bluetoothJhr = BluetoothJhr(MainActivity::class.java, this)
        hiloEjecutando = true

        enviar.setOnClickListener {
            var mensaje = editor.text.toString()
            bluetoothJhr.Tx(mensaje)
        }

        // Para recibir datos habría que hacer una corrutina con bluetoothJhr.Rx()

    }

    override fun onResume() {
        super.onResume()
        bluetoothJhr.ConectaBluetooth()
        bluetoothJhr.ResetearRx() // Esto es necesario para no recibir parámetro nulo cuando se inicia la conexión.
    }

    override fun onPause() {
        super.onPause()
        bluetoothJhr.CierraConexion()
        hiloEjecutando = false
    }
}
