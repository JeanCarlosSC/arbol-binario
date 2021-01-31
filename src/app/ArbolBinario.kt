package app

import javax.swing.JOptionPane
import kotlin.math.max

class ArbolBinario {

    var raiz: NodoBinario? = null

    /**
     * Inserta valores numéricos en el árbol
     */
    fun insertar(valor: Int) {

        if(raiz == null) {
            raiz = NodoBinario("$valor")
            return
        }

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
            nodo.y+=60
        }
        if(valor < q!!.valor.toInt()) {
            nodo.x = q.x-60
            q.izquierda = nodo
        }
        else {
            nodo.x = q.x+60
            q.derecha = nodo
        }
    }

    /**
     * Retira valores numéricos o cadenas del árbol
     */
    fun retirar(valor: String) {
        //El valor tiene que estar en el árbol o el árbol no se modificará
        if(!estaEnArbol(raiz, valor)) {
            return
        }
        val sucesorIn = obtenerSucesorIn(obtenerNodo(raiz!!, valor)!!)

        //si no tiene sucesor, se elimina el nodo
        if(sucesorIn == null) {
            eliminar(raiz, valor)
            return
        }
        //si tiene sucesor
        val sucesorInValor = sucesorIn.valor
        if(sucesorIn.derecha!=null) {
            sucesorIn.izquierda = sucesorIn.derecha!!.izquierda
            sucesorIn.derecha = sucesorIn.derecha!!.derecha
            sucesorIn.valor = sucesorIn.derecha!!.valor
        }
        else eliminar(raiz, sucesorInValor)
        obtenerNodo(raiz!!, valor)!!.valor = sucesorInValor
    }

    private fun obtenerSucesorIn(nodo: NodoBinario): NodoBinario? {
        var sucesor: NodoBinario? = null
        if (nodo.derecha!=null) {
            sucesor = nodo.derecha
            while (sucesor!!.izquierda != null) {
                sucesor = sucesor.izquierda
            }
        }
        return sucesor
    }

    private fun eliminar(nodo: NodoBinario?, valor: String) {

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

    fun gordura(nodo: NodoBinario?): String {
        return ""
    }

    fun altura(nodo: NodoBinario?): Int {
        if(nodo == null)
            return 0
        return max(altura(nodo.izquierda), altura(nodo.derecha)) +1
    }

    /**
     * Busca números o cadenas
     */
    private fun estaEnArbol(nodo: NodoBinario?, valor: String): Boolean {
        if(nodo == null)
            return false
        if (nodo.valor==valor)
            return true
        return estaEnArbol(nodo.izquierda, valor) || estaEnArbol(nodo.derecha, valor)
    }

    fun completo(nodo: NodoBinario?): Boolean {
        return false
    }

    fun numeroDeHojas(): Int {
        return 0
    }

    private fun moverHaciaDerecha(nodo: NodoBinario?, izquierda: Boolean) {
        if(nodo != null) {
            nodo.x += 60
            moverHaciaDerecha(nodo.derecha, true)
            if(izquierda)
                moverHaciaDerecha(nodo.izquierda, true)
        }
    }

    fun isEmpty() = raiz == null

}

class NodoBinario(var valor: String) {
    var izquierda: NodoBinario? = null
    var derecha: NodoBinario? = null

    var x = 32
    var y = 32
}