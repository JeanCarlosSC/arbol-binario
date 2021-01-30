package app

import javax.swing.JOptionPane

class ArbolBinario {

    var raiz: NodoBinario? = null

    fun insertar(valor: Int) {
        if(raiz == null) {
            raiz = NodoBinario(valor)
            return
        }

        if(estaEnArbol(raiz, valor)) {
            JOptionPane.showMessageDialog(null, "El valor ya está en el árbol")
            return
        }

        var p = raiz
        var q: NodoBinario? = null
        val nodo = NodoBinario(valor)

        while (p != null) {
            if(valor < p.valor) {
                moverHaciaDerecha(p, false)
                q = p
                p = p.izquierda
            }
            else if(valor > p.valor) {
                q = p
                p = p.derecha
            }
            nodo.y+=60
        }
        if(valor < q!!.valor) {
            nodo.x = q.x-60
            q.izquierda = nodo
        }
        else {
            nodo.x = q.x+60
            q.derecha = nodo
        }
    }

    private fun estaEnArbol(nodo: NodoBinario?, valor: Int): Boolean {
        if(nodo == null)
            return false
        if (nodo.valor==valor)
            return true
        return estaEnArbol(nodo.izquierda, valor) || estaEnArbol(nodo.derecha, valor)
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

class NodoBinario(var valor: Int) {
    var izquierda: NodoBinario? = null
    var derecha: NodoBinario? = null

    var x = 32
    var y = 32
}