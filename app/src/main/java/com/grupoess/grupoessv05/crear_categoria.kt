package com.grupoess.grupoessv05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.crear_categoria.*

class crear_categoria : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_categoria)

        //se llama la clase alertas
        var v_alertas = alertas();
        //se llama el firebase
        val database = FirebaseDatabase.getInstance();
        val myRef = database.getReference("categorias");
        myRef.keepSynced(false)
        //contexto
        var context = this;
        //id de la nueva categoria
        var id_categoria_nueva = 0;
        var validar = false;

        //se escucha el boton de de las categorias
        id_categoria_boton.setOnClickListener {
            //Se comprueba que la descripcion de la categoria no este vacia
            if(id_categoria_descripcion.text.toString() == ""){
                v_alertas.mensaje(
                    "Alerta",
                    "Debe llenar el campo Nombre",
                    "Aceptar",
                    context
                )
                return@setOnClickListener
            }

            //se llama el resultado de la consulta que busca las categorias
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(!validar){
                        for (array in dataSnapshot.children) {
                            for (categoria in array.children) {
                                if(categoria.key == "Nombre" && categoria.value.toString() == id_categoria_descripcion.text.toString()){
                                    v_alertas.mensaje(
                                        "Alerta",
                                        "El nombre de la categoria ya existe",
                                        "Aceptar",
                                        context
                                    )
                                    return
                                }
                            }
                            id_categoria_nueva = array.key.toString().toInt() + 1;
                        }

                        //se añade el nombre
                        val myRef2 = database.getReference("categorias/"+id_categoria_nueva+"/Nombre")
                        myRef2.setValue(id_categoria_descripcion.text.toString());

                        //se añade la url de la imagen
                        val myRef3 = database.getReference("categorias/"+id_categoria_nueva+"/Imagen")
                        myRef3.setValue(id_categoria_url.text.toString());
                        validar = true;
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Alerta", "Failed to read value.", error.toException())
                }
            });
        }
    }
}