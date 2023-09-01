package com.mgbt.fragmentexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Además de por XML (como esta en la vista de Main), los fragments se pueden cargar por código, lo que permite:
        //1. Pasarle parametros
        //2. Mostrar cuando cargue algo o despues de ejecutar una lógica
        if (savedInstanceState == null) {
            val bundle = bundleOf(NAME_BUNDLE to "Matías", ADRESS_BUNDLE to "Casa 1111")
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FirstFragment>(R.id.fragmentContainer2, args = bundle)
            }
        }
        //Si no es necesario pasar parametros, no se necesita crear el bundle y pasarlo como args

        //Si savedInstanceState es null, entonces la pantalla ya fue creada. Si no se hace esa comprobación,
        //cuando el usuario gire la pantalla se recargara la Activity y se creara de nuevo el fragment.
    }
}