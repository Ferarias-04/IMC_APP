package com.ferarias.bmiapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    //Estado de la pantalla
    private var altura = ALTURA_INICIAL
    private var peso = PESO_INICIAL
    private var edad = EDAD_INICIAL
    private var esHombre = true

    //vistas
    private lateinit var cardMale: LinearLayout
    private lateinit var cardFemale: LinearLayout
    private lateinit var tvAltura: TextView
    private lateinit var sbAltura: SeekBar
    private lateinit var tvPeso: TextView
    private lateinit var btnRestarPeso: ImageButton
    private lateinit var btnSumarPeso: ImageButton
    private lateinit var tvEdad: TextView
    private lateinit var btnRestarEdad: ImageButton
    private lateinit var btnSumarEdad: ImageButton
    private lateinit var btnCalcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //evita que las barras del sistema tapen el contenido, que se vea bien completo
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        initListeners()
        actualizarUI()
    }

    //conecta cada variable con su vista del layout
    private fun initViews() {
        cardMale = findViewById(R.id.cardMale)
        cardFemale = findViewById(R.id.cardFemale)
        tvAltura = findViewById(R.id.tvAltura)
        sbAltura = findViewById(R.id.sbAltura)
        tvPeso = findViewById(R.id.tvPeso)
        btnRestarPeso = findViewById(R.id.btnRestarPeso)
        btnSumarPeso = findViewById(R.id.btnSumarPeso)
        tvEdad = findViewById(R.id.tvEdad)
        btnRestarEdad = findViewById(R.id.btnRestarEdad)
        btnSumarEdad = findViewById(R.id.btnSumarEdad)
        btnCalcular = findViewById(R.id.btnCalcular)
    }

    //define que pasa cuando el usuario toca cada control
    private fun initListeners() {
        //sexo
        cardMale.setOnClickListener {
            esHombre = true
            actualizarUI()
        }
        cardFemale.setOnClickListener {
            esHombre = false
            actualizarUI()
        }
        //altura
        sbAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                altura = progress + ALTURA_MIN
                tvAltura.text = altura.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        //peso
        btnRestarPeso.setOnClickListener {
            peso = (peso -1).coerceAtLeast(PESO_MIN)
            actualizarUI()
        }
        btnSumarPeso.setOnClickListener {
            peso = (peso + 1).coerceAtMost(PESO_MAX)
            actualizarUI()
        }
        //edad
        btnRestarEdad.setOnClickListener {
            edad = (edad -1).coerceAtLeast(EDAD_MIN)
            actualizarUI()
        }
        btnSumarEdad.setOnClickListener {
            edad = (edad + 1).coerceAtMost(EDAD_MAX)
            actualizarUI()
        }
        //calcular
        btnCalcular.setOnClickListener {
            abrirResultado()
        }
    }

    //pinta en pantalla los valores actuales
    private fun actualizarUI() {
        cardMale.isSelected = esHombre
        cardFemale.isSelected = !esHombre
        tvAltura.text = altura.toString()
        tvEdad.text = edad.toString()
        tvPeso.text = peso.toString()
    }

    //fórmula del IMC con libras y pulgadas
    private fun calcularImc(): Double {
        val alturaMetros = altura / 100.0
        return peso / (alturaMetros * alturaMetros)
    }

    //abre la pantalla de resultados pasándole el IMC
    private fun abrirResultado() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_IMC, calcularImc())
        }
        startActivity(intent)
    }

    companion object {
        private const val ALTURA_MIN =120
        private const val ALTURA_INICIAL = 175
        private const val PESO_MIN = 20
        private const val PESO_MAX = 300
        private const val PESO_INICIAL = 70
        private const val EDAD_MIN = 1
        private const val EDAD_MAX = 120
        private const val EDAD_INICIAL = 18

    }
}