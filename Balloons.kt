package net.confuscat

import net.botwithus.api.game.hud.inventories.Bank
import net.botwithus.rs3.game.hud.interfaces.Dialog
import net.botwithus.rs3.game.hud.interfaces.Interfaces
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc
import net.botwithus.rs3.game.scene.entities.`object`.SceneObject
import net.botwithus.rs3.game.vars.VarManager
import net.botwithus.rs3.script.Execution
import java.util.*

class Balloons(private val script: BalloonsScript) {
    val CASTLEWARS = 15
    val GRANDTREE = 16
    val CRAFTINGGUILD = 17
    val ENTRANA = 18
    val TAVERLEY = 19
    val VARROCK = 20

    fun isOpen(): Boolean{
        return Interfaces.isOpen(469)
    }

    fun getCharges(): Int{
        val type = VarManager.getVarDomain(8421)
        return VarManager.getVarValue(type, 8421) / 2
    }

    private fun flyTo(destinationName: String, balloonId: Int){
        val basket: SceneObject? = SceneObjectQuery.newQuery().name("Basket").option("Fly").results().nearest()
        val auguste: Npc? = NpcQuery.newQuery().name("Auguste").option("Fly").results().first()

        if (auguste?.interact("Fly") == true){
            script.println("BALLOONS: Waiting for Balloon Interface")
            Execution.delayUntil(10000){
                isOpen()
            }
        }

        if (basket?.interact("Fly") == true){
            script.println("BALLOONS: Waiting for Balloon Interface")
            Execution.delayUntil(10000){
                isOpen()
            }
        }

        if (isOpen()){
            val destination = ComponentQuery.newQuery(469).componentIndex(balloonId).results().first()

            if(destination?.interact(destinationName) == true){
                Execution.delayWhile(1000){
                    isOpen()
                }
            }
            script.println("BALLOONS: Charges Remaining: ${getCharges()}")
        }
    }

    fun fly(balloonId: Int){
        val destinationName: String = when (balloonId){
            this.CASTLEWARS -> "Castle Wars"
            this.GRANDTREE -> "Grand Tree"
            this.CRAFTINGGUILD -> "Crafting Guild"
            this.ENTRANA -> "Entrana"
            this.TAVERLEY -> "Taverley"
            this.VARROCK -> "Varrock"
            else -> "INVALID"
        }

        if (destinationName == "INVALID"){
            script.println("BALLOONS: Invalid destination name")
            return
        }

        if (getCharges() <= 6){
            script.println("BALLOONS: Not enough balloon charges")
            return
        }

        flyTo(destinationName, balloonId)
    }

    fun bank(){
        script.println("BALLOONS: Attempting Banking")
        flyTo("Entrana", this.ENTRANA)
        Execution.delayUntil(10000){
            Interfaces.isOpen(847)
        }

        if (Interfaces.isOpen(847)){
            val warningOkButton = ComponentQuery.newQuery(847).componentIndex(13).results().first()

            if (warningOkButton?.interact("Select") == true){
                Execution.delayUntil(6000){
                    Bank.isOpen()
                }
                return
            }
        }

        while (Dialog.isOpen()){
            Dialog.next()
            script.println("BALLOONS: Progressing Dialog")
            Execution.delayUntil(600){
                !Dialog.isOpen()
            }
        }

    }
}
