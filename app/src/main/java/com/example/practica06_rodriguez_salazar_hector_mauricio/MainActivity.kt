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
    var objConcierto = Array<Concierto?>(tamanio) {null}
    var contadorConciertos = 0
    private var encontrado: Boolean = false


    //intancias
    private lateinit var seach: EditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seach = findViewById(R.id.editSeach)
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
            R.id.ibtnAgregar -> agregar()   //Llamas a la funcion agregar y la asocias con el boton Agregar
            R.id.ibtnBuscar -> buscar()
            R.id.ibtnEliminar -> eliminar()
            R.id.ibtnLimpiar -> limpiar()
        }
    }//onClick

    private fun agregar() {
        if (seach.text.isNotEmpty()) {   // Validar que las cajas de texto tengan contenido
            val codigo = seach.text.toString().toInt() //Variable codigo para

            if (!codigoExiste(codigo)) {    // Verificar si el código ya existe con la funcion codigoExiste
                if (contadorConciertos < tamanio) {

                    //Instancia del objeto Concierto para poder acceder a los atributos y podelos poner en un objeto del arreglo
                    val objetoConcierto = Concierto()

                    objetoConcierto.codigo = codigo
                    objetoConcierto.artista = artistSel
                    if (normal.isChecked) objetoConcierto.asiento = "normal"
                    if (gold.isChecked) objetoConcierto.asiento = "premium"
                    objetoConcierto.lugar = placeSel
                    objetoConcierto.horario = horarySel
                    objetoConcierto.costo = cost.text.toString().toDouble()

                    // Agregar el objeto Concierto al arreglo en la posición actual
                    objConcierto[contadorConciertos] = objetoConcierto

                    //Se va incrementando el contanador hasta que sea menor al tamanio para que no se pueda agregar mas
                    contadorConciertos++

                    limpiar()

                    Toast.makeText(this, "Se ha Registrado", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "El arreglo está lleno", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "El código ya existe, no se puede registrar nuevamente", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "No se pudo registrar", Toast.LENGTH_LONG).show()
        }
    }//agregar

    private fun eliminar() {
        //idEliminar como variable en la caja de texto de buscar para verificar si existe
        val idEliminar = seach.text.toString().toIntOrNull()

        //Verificamos si no es nulo
        if (idEliminar != null) {
            val encontrado = buscarElemento(idEliminar) //instanciamos una variable para enviar el parametro enviar a buscar elemento

            if (encontrado) {
                Toast.makeText(this, "Eliminado exitosamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No se pudo encontrar el elemento con código $idEliminar", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Ingresa un código válido", Toast.LENGTH_LONG).show()
        }
    }

    private fun buscarElemento(id: Int): Boolean {
        for (i in 0 until contadorConciertos) { //buscamos
            val objetoConciertoEncontrado = objConcierto[i]

            if (objetoConciertoEncontrado?.codigo == id) {
                // Mover todos los elementos a la izquierda para llenar el espacio vacío
                for (j in i until contadorConciertos - 1) {
                    objConcierto[j] = objConcierto[j + 1]
                }

                // Establecer el último elemento como nulo y reducir el contador
                objConcierto[contadorConciertos - 1] = null //recorremos el contador
                contadorConciertos--

                return true
            }
        }
        return false
    }

    private fun buscar() {
        val idBuscar = seach.text.toString() //Declaramos una variable buscar y lo igualamos al buscador

        if (idBuscar.isNotEmpty()) { //Validamos que haya un numero en la caja de texto
            val id = idBuscar.toInt() // declaramos una variable id donde la igualamos a idBuscar y la convertimos a Int

            var encontrado = false  // Agregar esta variable para rastrear si se encontró el concierto

            for (i in 0 until contadorConciertos) { //Iteramos entre el contador o bien buscamos el lugar
                val objetoConciertoEncontrado = objConcierto[i]

                if (objetoConciertoEncontrado?.codigo == id) {
                    // Si se encuentra el concierto, configuramos la bandera  encontrado a true
                    encontrado = true

                    // Instancia para lanzar Activity Detalle
                    val intent = Intent(this, Mostrar::class.java)
                    // Agregar los parámetros para enviar a la Activity (indentificadores para recibir la info)
                    intent.putExtra("codigo", objetoConciertoEncontrado?.codigo)
                    intent.putExtra("artista", objetoConciertoEncontrado?.artista)
                    intent.putExtra("asiento", objetoConciertoEncontrado?.asiento)
                    intent.putExtra("lugar", objetoConciertoEncontrado?.lugar)
                    intent.putExtra("costo", objetoConciertoEncontrado?.costo)
                    intent.putExtra("hora", objetoConciertoEncontrado?.horario)

                    // Lanzar la Activity
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Código no encontrado", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Debe ingresar un código", Toast.LENGTH_LONG).show()
        }
    }//buscar



    private fun codigoExiste(codigo: Int): Boolean { //recibe como parametro la variable codigo y es de tipo boolean
        for (i in 0 until contadorConciertos) { //hace una busqueda entre el contadorConciertos
            val objetoConcierto = objConcierto[i] // se declara objetoConcierto del arreglo objConcierto donde se van a guardar
            if (objetoConcierto?.codigo == codigo) { //Se valida que el codigo de ObjetoConcierto sea igual al codigo
                return true
            }
        }
        return false
    }//codigoExiste

    private fun limpiar() {
        seach.text.clear()
        seat.clearCheck()
        cost.text.clear()
        seach.requestFocus()
    }//Limpiar

}//class