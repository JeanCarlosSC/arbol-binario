package app

import javax.swing.JOptionPane
import kotlin.math.max
import kotlin.math.pow

class ArbolBinario {

    companion object {
        const val MODO_NUMERICO = 0
        const val MODO_ABECEDARIO = 1
    }

    var modo = MODO_NUMERICO //modo por defecto
    var raiz: NodoBinario? = null

    fun establecerModo(modo: Int) {
        if(modo == MODO_ABECEDARIO)
            this.modo = MODO_ABECEDARIO
        else this.modo = MODO_NUMERICO
    }

    /**
     * Inserta valores numéricos en el árbol
     */
    fun insertar(valor: Int) {
        //si no hay árbol
        if(raiz == null) {
            raiz = NodoBinario("$valor")
            return
        }

        //si ya está en el árbol
        if(estaEnArbol(raiz, "$valor")) {
            JOptionPane.showMessageDialog(null, "El valor ya está en el árbol")
            return
        }

        var p = raiz
        var q: NodoBinario? = null
        val nodo = NodoBinario("$valor")

        while (p != null) {
            if(valor < p.valor.toInt()) {
                moverHaciaDerecha(p, false)
                q = p
                p = p.izquierda
            }
            else if(valor > p.valor.toInt()) {
                q = p
                p = p.derecha
            }
        }
        if(valor < q!!.valor.toInt()) {
            nodo.x = q.x-60
            q.izquierda = nodo
        }
        else {
            nodo.x = q.x+60
            q.derecha = nodo
        }
        nodo.y = q.y+60
    }

    /**
     * Retira valores numéricos o cadenas del árbol
     */
    fun retirar(valor: String) {
        //El arbol tiene que existir y el valor tiene que estar en el árbol o el árbol no se modificará
        if(!estaEnArbol(raiz, valor)) {
            return
        }

        //si no tiene hijos, se elimina el nodo
        val nodo: NodoBinario =obtenerNodo(raiz!!, valor)!!
        if(nodo.izquierda == null && nodo.derecha == null) {
            eliminar(raiz!!, nodo.valor)
            return
        }

        //si no tiene sucesor
        val sucesor = obtenerSucesorIn(nodo)
        val antecesor = obtenerPariente(raiz!!, nodo)

        if(sucesor==null) {
            //si tiene antecesor
            if(antecesor!=null) {
                antecesor.derecha = nodo.izquierda
            }
            else {
                raiz = nodo.izquierda
            }
        }
        else {
            //si existe pariente
            val pariente = obtenerPariente(raiz!!, sucesor)

            if(pariente != null) {
                //si el nodo es el pariente
                if (pariente.valor == nodo.valor) {
                     nodo.derecha = sucesor.derecha
                }
                else {
                    //si el sucesor es antecesor
                    if(antecesor!=null && sucesor.valor == antecesor!!.valor) {
                        antecesor.izquierda = nodo.izquierda
                    }
                    else{
                        //si el pariente es derecho
                        if (pariente?.izquierda != null && pariente.izquierda!!.valor == sucesor.valor) {
                            pariente.izquierda = sucesor.derecha
                        } else {
                            pariente!!.derecha = sucesor.derecha
                        }
                    }
                }
            }
            else {
                raiz!!.izquierda = nodo.izquierda
            }
            nodo.valor = sucesor.valor
        }

    }

    private fun obtenerPariente(raiz: NodoBinario, nodo: NodoBinario): NodoBinario? {
        //si el que se busca es la raiz
        if (raiz.valor == nodo.valor)
            return null
        //si la raiz no tiene hijos
        if (raiz.izquierda==null && raiz.derecha == null)
            return null
        //si la raiz dada es pariente del nodo
        if (raiz.izquierda != null && raiz.izquierda!!.valor == nodo.valor || raiz.derecha!=null && raiz.derecha!!.valor == nodo.valor)
            return raiz
        //else, busca en los sub-árboles
        if (raiz.izquierda != null && obtenerPariente(raiz.izquierda!!, nodo)!=null) {
            return obtenerPariente(raiz.izquierda!!, nodo)
        }
        return obtenerPariente(raiz.derecha!!, nodo)
    }

    private fun obtenerSucesorIn(nodo: NodoBinario): NodoBinario? {
        var sucesor: NodoBinario?
        if (nodo.derecha!=null) {
            sucesor = nodo.derecha
            while (sucesor!!.izquierda != null) {
                sucesor = sucesor.izquierda
            }
            return sucesor
        }
        //si tiene pariente derecho
        val pariente = obtenerPariente(raiz!!, nodo)
        if (pariente?.izquierda != null && pariente.izquierda!!.valor == nodo.valor) {
            return pariente
        }
        return null
    }

    private fun eliminar(nodo: NodoBinario?, valor: String) {
        //verifica cualquier nodo nulo
        if (nodo == null)
            return
        //verifica si el valor corresponde a la raiz
        if (raiz!!.valor==valor) {
            raiz = null
            return
        }
        //verifica el sub-árbol izquierdo
        if (nodo.izquierda != null) {
            if(nodo.izquierda!!.valor == valor) {
                nodo.izquierda = null
                return
            }
            eliminar(nodo.izquierda, valor)
        }
        //verifica el sub-árbol derecho
        if (nodo.derecha != null) {
            if (nodo.derecha!!.valor == valor) {
                nodo.derecha = null
                return
            }
            eliminar(nodo.derecha, valor)
        }
    }

    private fun obtenerNodo(nodo: NodoBinario, valor: String): NodoBinario?{
        if(nodo.valor == valor){
            return nodo
        }
        if(nodo.izquierda != null && obtenerNodo(nodo.izquierda!!, valor)!=null) {
            return obtenerNodo(nodo.izquierda!!, valor)
        }
        if(nodo.derecha != null && obtenerNodo(nodo.derecha!!, valor)!=null) {
            return obtenerNodo(nodo.derecha!!, valor)
        }
        return null
    }

    private fun moverHaciaArriba(nodo: NodoBinario?) {
        if (nodo == null)
            return
        nodo.y -= 60
        moverHaciaArriba(nodo.izquierda)
        moverHaciaArriba(nodo.derecha)
    }

    private fun moverHaciaDerecha(nodo: NodoBinario?, izquierda: Boolean) {
        if(nodo != null) {
            nodo.x += 60
            moverHaciaDerecha(nodo.derecha, true)
            if(izquierda)
                moverHaciaDerecha(nodo.izquierda, true)
        }
    }

    fun inOrden(nodo: NodoBinario?): String {
        if(nodo!=null) {
            return "${inOrden(nodo.izquierda)} ${nodo.valor} ${inOrden(nodo.derecha)}"
        }
        return ""
    }

    fun preOrden(nodo: NodoBinario?): String {
        if(nodo!=null) {
            return "${nodo.valor} ${preOrden(nodo.izquierda)} ${preOrden(nodo.derecha)}"
        }
        return ""
    }

    fun posOrden(nodo: NodoBinario?): String {
        if(nodo!=null) {
            return "${posOrden(nodo.izquierda)} ${posOrden(nodo.derecha)} ${nodo.valor}"
        }
        return ""
    }

    fun niveles(nodo: NodoBinario?): String {
        if(nodo == null) return ""

        var niveles = ""
        val cola = mutableListOf<NodoBinario>()
        cola.add(nodo)
        var p: NodoBinario
        while(cola.size>0) {
            p = cola[0]
            niveles += "${p.valor} "
            cola.removeAt(0)
            if(p.izquierda != null)
                cola.add(p.izquierda!!)
            if(p.derecha != null)
                cola.add(p.derecha!!)
        }

        return niveles
    }

    fun gordura(nodo: NodoBinario?): Int {
        //arbol vacío
        if (nodo == null)
            return 0
        //si no tiene hijos
        if (nodo.izquierda == null && nodo.derecha == null)
            return 1
        //si tiene hijos
        var cantidadHijos = 0
        if(nodo.izquierda!=null) cantidadHijos++
        if(nodo.derecha!=null) cantidadHijos++
        return max(cantidadHijos, gordura(nodo.izquierda)+gordura(nodo.derecha))
    }

    fun altura(nodo: NodoBinario?): Int {
        if(nodo == null)
            return 0
        return max(altura(nodo.izquierda), altura(nodo.derecha)) +1
    }

    /**
     * Busca números o cadenas
     */
    fun estaEnArbol(nodo: NodoBinario?, valor: String): Boolean {
        if(nodo == null)
            return false
        if (nodo.valor==valor)
            return true
        return estaEnArbol(nodo.izquierda, valor) || estaEnArbol(nodo.derecha, valor)
    }

    fun completo(nodo: NodoBinario?): Boolean {
        return gordura(raiz) == 2.0.pow(altura(raiz) - 1.0).toInt()
    }

    fun numeroDeHojas(nodo: NodoBinario?): Int {
        if (nodo!=null) {
            return if (nodo.izquierda == null && nodo.derecha == null)
                1
            else numeroDeHojas(nodo.izquierda)+numeroDeHojas(nodo.derecha)
        }
        return 0
    }

    fun isEmpty() = raiz == null

}

class NodoBinario(var valor: String) {
    var izquierda: NodoBinario? = null
    var derecha: NodoBinario? = null

    var x = 32
    var y = 32
}