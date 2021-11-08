import kotlin.random.Random

fun main() {
    val guerrero1 = Guerrero("Paquito")
    val superJefe = SuperJefe("HiperJefe")

    println(guerrero1)
    println(superJefe)

    do {
        guerrero1.atacar(1,6, superJefe)
        if (superJefe.estaVivo())
            superJefe.atacar(1,4, guerrero1)
    } while (guerrero1.estaVivo() && superJefe.estaVivo())

    println(guerrero1)
    println(superJefe)

}


open class Personaje(var nombre: String, var vida: Int){

    open fun atacar(valorMinimo: Int, valorMaximo:Int, personaje: Personaje) {
        personaje.quitarVida(Random.nextInt(valorMinimo, valorMaximo + 1))
    }

    fun estaVivo() : Boolean {
        return vida > 0
    }

    open fun quitarVida(dano: Int){
        vida -= dano
    }

    override fun toString(): String {
        return "Soy $nombre y tengo $vida puntos de vida"
    }
}

class Guerrero(alias : String) : Personaje(alias, 50) {

    override fun atacar(valorMinimo: Int, valorMaximo: Int, personaje: Personaje) {
        super.atacar(valorMinimo, valorMaximo, personaje)
        if (!personaje.estaVivo()){
            curarse()
        }
    }

    private fun curarse(){
        if (Random.nextInt(0,100) < 5) {
            vida += 5
        }
    }

}

class Minion(alias : String) : Personaje(alias,10) {

}

class SuperJefe(alias:String) : Personaje(alias, 25) {

    val listaMinions = mutableListOf(Minion("M1"), Minion("M2"), Minion("M3"))

    override fun quitarVida(dano: Int) {
        if (listaMinions.isNotEmpty()) {
            listaMinions[0].quitarVida(dano)
            if (!listaMinions[0].estaVivo()) {
                listaMinions.removeAt(0)
            }
        } else {
            super.quitarVida(dano)
        }
    }

    override fun toString(): String {
        var output = super.toString()
        listaMinions.forEach {
            output += "\t $it"
        }
        return output
    }
}