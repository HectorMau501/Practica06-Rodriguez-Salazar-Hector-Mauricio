package com.example.practica06_rodriguez_salazar_hector_mauricio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //Intancia de la clase concierto con arreglo
    val tamanio = 10
    val objConcierto = Array<Concierto?>(tamanio) {null}
    var contadorConciertos = 0


    //intancias
    private lateinit var seach: EditText
//    private lateinit var code: EditText
    private lateinit var artist: Spinner
    private lateinit var seat: RadioGroup
    private lateinit var normal: RadioButton
    private lateinit var gold: RadioButton
    private lateinit var place: ListView
    private lateinit var cost: EditText
    private lateinit var horary: Spinner
    private var artistSel: String = "Luis Miguel"
    private var placeSel: String = "Auditorio Telmex"
    private var horarySel: String = "10:00pm"

//    private val concierto1 = mutableMapOf<Int, Concierto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seach = findViewById(R.id.editSeach)
//        code = findViewById(R.id.editCodigo)
        artist = findViewById(R.id.spnArtista)
        seat = findViewById(R.id.rgpAsientos)
        normal = findViewById(R.id.rbNormal)
        gold = findViewById(R.id.rbPremium)
        place = findViewById(R.id.ltvLugar)
        cost = findViewById(R.id.editCosto)
        horary = findViewById(R.id.spnHorario)

        //Arreglo de artistas
        val lstArtista = resources.getStringArray(R.array.listaArtistas)
        //Definir el tipo de losta y relacion con las marcas
        val adaptadorSpn = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lstArtista)
        //Asociar el adaptador con las instancias
        artist.adapter = adaptadorSpn
        //Escucha para la lista de marca y almacenar el valor seleccionado
        artist.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                artistSel = lstArtista[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Arreglo de marcas
        val lstPlace = listOf("Auditorio Telmex","Teatro Diana","Estadio Akron","Estadio Jalisco")
        //Definir el tipo de lista y relacion con las marcas
        val adaptador = ArrayAdapter(this,android.R.layout.simple_list_item_1,lstPlace)
        //Asociar el adaptador con las instancias
        place.adapter = adaptador
        //Escucha para la lista de marca y almacenar el valor seleccionado
        place.setOnItemClickListener { parent, view, position, id ->
            placeSel = parent.getItemAtPosition(position).toString()
            Toast.makeText(this,"Lugar: $placeSel",Toast.LENGTH_LONG).show()
        }


        //Arreglo de artistas
        val lstHorario = resources.getStringArray(R.array.listaHorario)
        //Definir el tipo de losta y relacion con las marcas
        val adaptadorSpn2 = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lstHorario)
        //Asociar el adaptador con las instancias
        horary.adapter = adaptadorSpn2
        //Escucha para la lista de marca y almacenar el valor seleccionado
        horary.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                horarySel = lstHorario[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }//onCreate

    fun onClick(v: View?){
        when(v?.id){
            R.id.ibtnAgregar -> {
                agregar()
            }
            R.id.ibtnBuscar -> {
                buscar()
            }
            R.id.ibtnEliminar -> {
                eliminar()
            }
            R.id.ibtnLimpiar -> {
                limpiar()
            }
        }
    }

    private fun agregar() {
        //Validar que las cajas de texto tengan contenido
        if(seach.text.isNotEmpty()){
//            val id = seach.text.toString().toInt()
//            val index = objConcierto.indexOfFirst { it == null }
            if(contadorConciertos < tamanio){
                //agregar los valores al arreglo

                val objetoConcierto = Concierto()

                objetoConcierto.codigo = seach.text.toString().toInt()
                objetoConcierto.artista = artistSel
                if (normal.isChecked) objetoConcierto.asiento = "normal"
                if (gold.isChecked) objetoConcierto.asiento = "premium"
                objetoConcierto.lugar = placeSel
                objetoConcierto.horario = horarySel
                objetoConcierto.costo = cost.text.toString().toDouble()

                // Agregar el objeto Concierto al arreglo en la posición actual
                objConcierto[contadorConciertos] = objetoConcierto
                contadorConciertos++

                limpiar()
                Toast.makeText(this,"Se ha Registrado",
                    Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Esta lleno el arreglo",
                    Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"No se pudo registrar",
                Toast.LENGTH_LONG).show()
        }
    }//agregar

    private fun limpiar() {
        seach.text.clear()
        seat.clearCheck()
        cost.text.clear()
        seach.requestFocus()
        Toast.makeText(this,"Cajas de texto Limpias", Toast.LENGTH_LONG).show()
    }//Limpiar

    private fun eliminar() {
    }//Eliminar

    private fun buscar() {
        val idBuscar = seach.text.toString()

        if (idBuscar.isNotEmpty()) {
            val id = idBuscar.toInt()

            for(i in 0 until contadorConciertos){
                val objetoConciertoEncontrado = objConcierto[i]

                if (objetoConciertoEncontrado?.codigo == id) {
                    //Instancia para lanzar Activity Detalle
                    val intent = Intent(this, Mostrar::class.java)
                    //Agregar los parametros para enviar a la Activity
                    intent.putExtra("codigo", objetoConciertoEncontrado.codigo)
                    intent.putExtra("artista",objetoConciertoEncontrado.artista)
                    intent.putExtra("asiento",objetoConciertoEncontrado.asiento)
                    intent.putExtra("lugar",objetoConciertoEncontrado.lugar)
                    intent.putExtra("costo",objetoConciertoEncontrado.costo)
                    intent.putExtra("hora",objetoConciertoEncontrado.horario)

                    //Lanzar la Activity
                    startActivity(intent)
                    return
                } else {
                    Toast.makeText(this, "Código no encontrado", Toast.LENGTH_LONG).show()
                }
            }
        }else {
            Toast.makeText(this, "Debe ingresar un código", Toast.LENGTH_LONG).show()
        }
    }
}//class