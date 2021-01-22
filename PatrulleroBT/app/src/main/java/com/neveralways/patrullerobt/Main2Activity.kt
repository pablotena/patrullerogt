package com.neveralways.patrullerobt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.hp.bluetoothjhr.BluetoothJhr
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import kotlin.concurrent.thread

class Main2Activity : AppCompatActivity() {

    lateinit var bluetoothJhr: BluetoothJhr
    var changed : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        bluetoothJhr = BluetoothJhr(MainActivity::class.java, this)

        enviar.setOnClickListener {
            var mensaje = editor.text.toString()
            bluetoothJhr.Tx(mensaje)
        }

        // Para recibir datos habría que hacer una corrutina con bluetoothJhr.Rx()

        val adelanteBoton = findViewById<Button>(R.id.btnAdelante)
        val derechaBoton = findViewById<Button>(R.id.btnDerecha)
        val atrasBoton = findViewById<Button>(R.id.btnAtras)
        val izquierdaBoton = findViewById<Button>(R.id.btnIzquierda)


        adelanteBoton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                // Función que mueve el vehículo en base al botón pulsado.
                moveIt(v, event)

                return v?.onTouchEvent(event) ?: true
            }
        })

        derechaBoton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                // Función que mueve el vehículo en base al botón pulsado.
                moveIt(v, event)

                return v?.onTouchEvent(event) ?: true
            }
        })

        atrasBoton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                // Función que mueve el vehículo en base al botón pulsado.
                moveIt(v, event)

                return v?.onTouchEvent(event) ?: true
            }
        })

        izquierdaBoton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                // Función que mueve el vehículo en base al botón pulsado.
                moveIt(v, event)

                return v?.onTouchEvent(event) ?: true
            }
        })

    }

    override fun onResume() {
        super.onResume()
        bluetoothJhr.ConectaBluetooth()
        bluetoothJhr.ResetearRx() // Esto es necesario para no recibir parámetro nulo cuando se inicia la conexión.
    }

    override fun onPause() {
        super.onPause()
        bluetoothJhr.CierraConexion()
    }

    fun moveIt(v : View?, event : MotionEvent?) {
        var mHandler : Handler = Handler()
        var mAction = object : Runnable {
            override fun run() {
                //consola.text = "${consola.text} hola"
                var stringSent = ""

                if(changed) {
                    mHandler.removeCallbacks(this)
                    stringSent = "P"
                    bluetoothJhr.Tx(stringSent)
                    Log.d("Info", "He dejado de enviar ${v?.id}. Termino con una ${stringSent}")
                    consola.text = "Se ha enviado una ${stringSent}. Nos detenemos."
                } else {

                    when(v?.id) {
                        R.id.btnAdelante -> {
                            stringSent = "W"
                        }
                        R.id.btnDerecha -> {
                            stringSent = "D"
                        }
                        R.id.btnAtras -> {
                            stringSent = "S"
                        }
                        R.id.btnIzquierda -> {
                            stringSent = "A"
                        }
                        else -> {
                            Log.d("Info", "No se reconoce ese botón")
                        }
                    }

                    bluetoothJhr.Tx(stringSent)
                    Log.d("Info", "Se ha enviado ${stringSent} por TX con ${v?.id}")
                    consola.text = stringSent

                    mHandler.postDelayed(this, 50)
                }
            }
        }

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                changed = false
                mHandler.removeCallbacks(mAction)
                mHandler.postDelayed(mAction, 1)
                consola.text = "HE PULSADO"
                Log.d("Test", "PULSO ${v?.id}")
            }
            MotionEvent.ACTION_UP -> {
                mHandler.removeCallbacks(mAction)
                changed = true


                Log.d("Test", "LEVANTO ${R.id.btnAdelante}")
            }
        }
    }
}
