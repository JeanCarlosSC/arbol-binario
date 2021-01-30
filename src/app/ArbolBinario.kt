package app

class ArbolBinario {
    var raiz: NodoBinario? = null

    fun insertar(valor: Int) {
        if(raiz == null) {
            raiz = NodoBinario(valor)
        }
    }

    fun isEmpty() = raiz == null
}

class NodoBinario(var valor: Int) {

}