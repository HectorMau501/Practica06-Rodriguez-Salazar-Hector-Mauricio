package com.example.practica06_rodriguez_salazar_hector_mauricio

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Mostrar : AppCompatActivity() {

    //Intanciamos
    private lateinit var datos: TextView
    private lateinit var objConcierto: Concierto

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar)

        datos = findViewById(R.id.txtDatos)
        objConcierto = Concierto() //instancia de la clase concierto, para acceder a sus atributos

        //instancia para recibir informacion
        val infoRecibida = intent.extras

        //Recibimos el contenido
        objConcierto.codigo  = infoRecibida?.getInt("codigo")!!
        objConcierto.artista = infoRecibida?.getString("artista")!!
        objConcierto.lugar = infoRecibida?.getString("lugar")!!
        objConcierto.asiento = infoRecibida?.getString("asiento")!!
        objConcierto.costo = infoRecibida?.getDouble("costo")!!
        objConcierto.horario = infoRecibida?.getString("hora")!!
        //Definir la asiento
        var asiento : String? = null
        if(objConcierto.asiento == "normal") asiento = "Asiento Normal"
        if(objConcierto.asiento== "premium") asiento = "Asiento Premium"

        //colocar la informacion en el textView
          datos.text = "\n\nCodigo: "+objConcierto.codigo +
                  "\n\nArtita: "+objConcierto.artista +
                  "\n\nAsiento: "+objConcierto.asiento +
                  "\n\nLugar: "+objConcierto.lugar +
                  "\n\nCosto: "+objConcierto.costo+
                  "\n\nHorario: "+objConcierto.horario
    }

    fun regresar(view: View){
        //Intancia para lanzar activivity Detalle
        val intent = Intent(this, MainActivity::class.java)

        finish() //finish para que no se inicializar de nuevo la mainActivity y asi no se pieda los datos del arreglo

    }//regresar
}