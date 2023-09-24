package com.example.practica06_rodriguez_salazar_hector_mauricio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Mostrar : AppCompatActivity() {

    private lateinit var datos: TextView
    //private lateinit var objConciertos: Array<Concierto?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar)

        // Inicializar instancia del arreglo de conciertos
        //objConciertos = arrayOfNulls<Concierto?>(tamanio)

        datos = findViewById(R.id.txtDatos)
        val objetoConcierto = Concierto()

        //instancia para recibir informacion
        val infoRecibida = intent.extras


//        val prefs = getSharedPreferences("Conciertos", Context.MODE_PRIVATE)
        objetoConcierto.codigo  = infoRecibida?.getInt("codigo")!!
        objetoConcierto.artista = infoRecibida?.getString("artista")!!
        objetoConcierto.lugar = infoRecibida?.getString("lugar")!!
        objetoConcierto.asiento = infoRecibida?.getString("asiento")!!
        objetoConcierto.costo = infoRecibida?.getDouble("costo")!!
        objetoConcierto.horario = infoRecibida?.getString("hora")!!
        //Definir la asiento
        var asiento : String? = null
        if(objetoConcierto.asiento == "normal") asiento = "Asiento Normal"
        if(objetoConcierto.asiento== "premium") asiento = "Asiento Premium"
          datos.text = "\n\nCodigo: "+objetoConcierto.codigo +
                  "\n\nArtita: "+objetoConcierto.artista +
                  "\n\nAsiento: "+objetoConcierto.asiento +
                  "\n\nLugar: "+objetoConcierto.lugar +
                  "\n\nCosto: "+objetoConcierto.costo+
                  "\n\nHorario: "+objetoConcierto.horario
    }

    fun regresar(view: View){
        //Intancia para lanzar activivity Detalle
        val intent = Intent(this, MainActivity::class.java)
        //Lanzar la activity
        startActivity(intent)
    }

}