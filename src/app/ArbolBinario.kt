package app

import lib.sRAD.logic.toMutableList
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
    var minimo = 32

    fun cambiarModo() {
        modo = if(modo == MODO_ABECEDARIO) MODO_NUMERICO else MODO_ABECEDARIO
        raiz = null
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
            JOptionPane.showMessageDialog(null, "El valor $valor ya está en el árbol")
            return
        }

        var p = raiz
        var q: NodoBinario? = null
        val nodo = NodoBinario("$valor")

        while (p != null) {
            if(valor < p.valor.toInt()) {
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
                    if(antecesor!=null && sucesor.valor == antecesor.valor) {
                        antecesor.izquierda = nodo.izquierda
                    }
                    else{
                        //si el pariente es derecho
                        if (pariente.izquierda != null && pariente.izquierda!!.valor == sucesor.valor) {
                            pariente.izquierda = sucesor.derecha
                        } else {
                            pariente.derecha = sucesor.derecha
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

    private fun obtenerPariente(raiz: NodoBinario?, nodo: NodoBinario): NodoBinario? {
        //si la raiz es nula
        if (raiz == null)
            return null
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
            return obtenerPariente(raiz.izquierda, nodo)
        }
        return obtenerPariente(raiz.derecha, nodo)
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

    private fun moverHaciaDerecha(nodo: NodoBinario?) {
        if(nodo != null) {
            nodo.x += (minimo-32)*-1
            moverHaciaDerecha(nodo.izquierda)
            moverHaciaDerecha(nodo.derecha)
        }
    }

    fun organizar() {
        if (altura(raiz)<2)
            return
        minimo = 32
        reestablecerCoordenadas(raiz)
        moverNodo(raiz, 32, 32)
        moverHaciaDerecha(raiz)
    }

    private fun reestablecerCoordenadas(nodo: NodoBinario?) {
        if(nodo == null)
            return
        nodo.x = 0
        nodo.y = 0
        reestablecerCoordenadas(nodo.izquierda)
        reestablecerCoordenadas(nodo.derecha)
    }

    private fun moverNodo(nodo: NodoBinario?, x: Int, y: Int) {
        if (nodo == null)
            return
        if (x < minimo) {
            minimo = x
        }
        if(isNotPosFree(raiz!!, x, y)) {
            var pariente = obtenerPariente(raiz!!, nodo)
            val xi = pariente!!.x
            val yi = pariente.y+60
            while (pariente != null) {
                pariente.x+=45
                pariente = obtenerPariente(raiz!!, pariente)
            }
            moverNodo(nodo, xi, yi)
        }
        else {
            nodo.x = x
            nodo.y = y
            if (nodo.izquierda != null)
                moverNodo(nodo.izquierda, nodo.x - 45, nodo.y + 60)
            if (nodo.derecha != null) {
                moverNodo(nodo.derecha, nodo.x + 45, nodo.y + 60)
                if (nodo.izquierda != null) {
                    nodo.x = (nodo.izquierda!!.x + nodo.derecha!!.x)/2
                }
            }
        }
    }

    private fun isNotPosFree(nodo: NodoBinario?, x: Int, y: Int): Boolean {
        if (nodo == null)
            return false
        if ((x >= nodo.x && x <=nodo.x +60) && nodo.y == y)
            return true
        return isNotPosFree(nodo.izquierda, x, y) || isNotPosFree(nodo.derecha, x, y)
    }

    fun inOrden(nodo: NodoBinario?): String {
        if(nodo!=null) {
            return "${inOrden(nodo.izquierda)}${nodo.valor} ${inOrden(nodo.derecha)}"
        }
        return ""
    }

    fun preOrden(nodo: NodoBinario?): String {
        if(nodo!=null) {
            return "${nodo.valor} ${preOrden(nodo.izquierda)}${preOrden(nodo.derecha)}"
        }
        return ""
    }

    fun posOrden(nodo: NodoBinario?): String {
        if(nodo!=null) {
            return "${posOrden(nodo.izquierda)}${posOrden(nodo.derecha)} ${nodo.valor}"
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

    fun completo(): Boolean {
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

    fun construirInPre(inOrden: String, preOrden: String): NodoBinario? {
        val mListIn = inOrden.toMutableList()
        val mListPre = preOrden.toMutableList()

        //deben tener al menos un elemento
        if(mListIn.isEmpty())
            return null

        //deben tener la misma cantidad de elementos inicialmente
        if(mListIn.size != mListPre.size) {
            if(raiz == null)
                JOptionPane.showMessageDialog(null, "Los datos ingresados no tienen la misma cantidad de elementos", "Error",
                    JOptionPane.ERROR_MESSAGE)
            return null
        }

        //calcula valores para el sub-árbol izquierdo y derecho
        var inOrdenIzquierdo = ""
        var inOrdenDerecho = ""
        var preOrdenIzquierdo = ""
        var preOrdenDerecho = ""
        var bool = false

        for (i in mListIn) {
            if (mListPre.isNotEmpty() && i == mListPre[0]) {
                bool = true
                continue
            }
             if(bool)
                 inOrdenDerecho += "$i "
            else inOrdenIzquierdo += "$i "
        }

        val inIzquierdo = inOrdenIzquierdo.toMutableList()
        for (i in mListPre) {
            if(i != mListPre[0]) {
                if (inIzquierdo.size == preOrdenIzquierdo.toMutableList().size) {
                    bool = false
                }
                if (bool)
                    preOrdenIzquierdo += "$i "
                else preOrdenDerecho += "$i "
            }
        }

        //crea el nodo
        val nodo = NodoBinario(mListPre[0])

        //si no hay árbol
        if(raiz == null)
            raiz = nodo

        //llamada recursiva
        nodo.izquierda = construirInPre(inOrdenIzquierdo, preOrdenIzquierdo)
        nodo.derecha = construirInPre(inOrdenDerecho, preOrdenDerecho)

        return nodo
    }

    fun construirInPos(inOrden: String, posOrden: String): NodoBinario? {
        val mListIn = inOrden.toMutableList()
        val mListPos = posOrden.toMutableList()

        //deben tener al menos un elemento
        if(mListIn.isEmpty())
            return null

        //deben tener la misma cantidad de elementos
        if(mListIn.size != mListPos.size) {
            if (raiz == null)
                JOptionPane.showMessageDialog(null, "Los datos ingresados no tienen la misma cantidad de elementos", "Error",
                    JOptionPane.ERROR_MESSAGE)
            return null
        }

        //calcula valores para el sub-árbol izquierdo y derecho
        var inOrdenIzquierdo = ""
        var inOrdenDerecho = ""
        var posOrdenIzquierdo = ""
        var posOrdenDerecho = ""
        var bool = false

        for (i in mListIn) {
            if (i == mListPos.last()) {
                bool = true
                continue
            }
            if(bool)
                inOrdenDerecho += "$i "
            else inOrdenIzquierdo += "$i "
        }

        val inIzquierdo = inOrdenIzquierdo.toMutableList()
        for (i in mListPos) {
            if(i != mListPos.last()) {
                if (inIzquierdo.size == posOrdenIzquierdo.toMutableList().size) {
                    bool = false
                }
                if (bool)
                    posOrdenIzquierdo += "$i "
                else posOrdenDerecho += "$i "
            }
        }

        //crea el nodo
        val nodo = NodoBinario(mListPos.last())

        //si no hay árbol
        if(raiz == null)
            raiz = nodo

        //llamada recursiva
        nodo.izquierda = construirInPos(inOrdenIzquierdo, posOrdenIzquierdo)
        nodo.derecha = construirInPos(inOrdenDerecho, posOrdenDerecho)

        return nodo
    }

}

class NodoBinario(var valor: String) {
    var izquierda: NodoBinario? = null
    var derecha: NodoBinario? = null

    var x = 32
    var y = 32
}

fun getSize(nodo: NodoBinario?):Int {
    if (nodo == null)
        return 0
    return getSize(nodo.izquierda)+getSize(nodo.derecha)+1
}
