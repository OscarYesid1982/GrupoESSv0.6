package com.grupoess.grupoessv05

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.crear_producto.*


class crear_producto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_producto)

        //se llama la clase alertas
        var v_alertas = alertas();
        //se llama el firebase
        val database = FirebaseDatabase.getInstance();
        val myRef = database.getReference("productos");
        val database2 = FirebaseDatabase.getInstance();
        val myRef2 = database2.getReference("categorias");
        //contexto
        var context = this;
        //id de el nuevo producto
        var id_producto_nuevo = 0;
        var validar = false;
        //array que contiene las categorias
        var categorias_array = arrayOf<String>()
        //variable del objeto desplegable
        val Slenguajes = findViewById(R.id.id_producto_categorias) as Spinner
        //variable controladora de el cuadro
        var penstana = 1;

        //se llaman las categorias yse ponen en el objeto desplegable
        //-------------------------------------------------------------------------------------------
        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (array in dataSnapshot.children) {
                    for (categoria in array.children) {
                        if(categoria.key == "Nombre"){
                            categorias_array += categoria.value.toString();
                        }
                    }
                }
                Slenguajes.setAdapter(
                    ArrayAdapter(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        categorias_array
                    )
                )
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Alerta", "Failed to read value.", error.toException())
            }
        });
        //-------------------------------------------------------------------------------------------

        //se escucha el boton de continuar
        id_producto_boton.setOnClickListener {
            if(!validar){
                if(penstana == 1){
                    //Se comprueba que el nombre del producto no este vacio
                    if(id_producto_nombre.text.toString() == ""){
                        v_alertas.mensaje(
                            "Alerta",
                            "Debe llenar el campo Nombre",
                            "Aceptar",
                            context
                        )
                        return@setOnClickListener
                    }
                    else{
                        myRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (array in dataSnapshot.children) {
                                    for (producto in array.children) {
                                        if(producto.key == "Nombre" && producto.value.toString() == id_producto_nombre.text.toString()){
                                            v_alertas.mensaje(
                                                "Alerta",
                                                "El nombre del producto ya existe",
                                                "Aceptar",
                                                context
                                            )
                                            return
                                        }
                                    }
                                    id_producto_nuevo = array.key.toString().toInt() + 1;
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                // Failed to read value
                                Log.w("Alerta", "Failed to read value.", error.toException())
                            }
                        });
                    }

                    id_producto_nombre.visibility = View.INVISIBLE;
                    id_producto_url.visibility = View.INVISIBLE;
                    id_producto_activo.visibility = View.INVISIBLE;

                    id_producto_descripcion.visibility = View.VISIBLE;
                    id_producto_categorias.visibility = View.VISIBLE;
                    id_producto_sub_titulo.visibility = View.VISIBLE;

                    id_producto_boton.text = "Guardar";
                    penstana = 2
                }
                else{
                    validar = true;
                    //se añade el nombre
                    val myRef2 = database.getReference("productos/"+id_producto_nuevo+"/Nombre")
                    myRef2.setValue(id_producto_nombre.text.toString());

                    //se añade la url de la imagen
                    val myRef3 = database.getReference("productos/"+id_producto_nuevo+"/Imagen")
                    myRef3.setValue(id_producto_url.text.toString());

                    //se añade la descripcion
                    val myRef4 = database.getReference("productos/"+id_producto_nuevo+"/Descripcion")
                    myRef4.setValue(id_producto_descripcion.text.toString());

                    //se añade la categoria
                    val myRef5 = database.getReference("productos/"+id_producto_nuevo+"/Id_categoria")
                    myRef5.setValue(id_producto_categorias.getSelectedItem().toString());

                    val intent = Intent(this, MainActivity::class.java)
                    startActivityForResult(intent, 0)
                }
            }
        }
    }
}