package com.ferarias.bmiapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageButton
    private lateinit var tvCategoria: TextView
    private lateinit var tvImc: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var btnRecalcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        initListeners()

        //recogemos el dato que nos envía MainActivity
        val imc = intent.getDoubleExtra(EXTRA_IMC, 0.0)
        mostrarResultado(imc)
    }
    private fun initViews() {
        btnVolver = findViewById(R.id.btnVolver)
        tvCategoria = findViewById(R.id.tvCategoria)
        tvImc = findViewById(R.id.tvImc)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        btnRecalcular = findViewById(R.id.btnRecalcular)
    }
    private fun initListeners() {
        //los dos botones cierran esta pantalla y vuelven a la anterior
        btnVolver.setOnClickListener { finish() }
        btnRecalcular.setOnClickListener { finish() }
    }
    private fun mostrarResultado(imc: Double) {
        val (categoria, descripcion, color) = when {
            imc < IMC_BAJO_PESO -> Triple(R.string.underweight, R.string.desc_underweight, R.color.amarillo)
            imc < IMC_SOBREPRESO -> Triple(R.string.normal, R.string.desc_normal, R.color.verde)
            else -> Triple(R.string.overweight, R.string.desc_overweight, R.color.rojo)
        }
        tvCategoria.setText(categoria)
        tvCategoria.setTextColor(ContextCompat.getColor(this, color))
        tvImc.text = String.format(Locale.getDefault(), "%.2f", imc)
        tvDescripcion.setText(descripcion)
    }
    companion object {
        //clave para pasar el IMC entre pantallas (pública: la usa MainAcivity)
        const val EXTRA_IMC = "extra_imc"

        //limites de las categorías
        private const val IMC_BAJO_PESO = 18.5
        private const val IMC_SOBREPRESO = 25
    }
}