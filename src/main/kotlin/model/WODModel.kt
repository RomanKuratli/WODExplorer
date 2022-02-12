package model

import java.io.File
import java.io.FileReader


data class WODComponent(val name: String, val type: String, val args: MutableMap<String, String>)

object WODModel {

    private val wodComponents: MutableList<WODComponent> = mutableListOf()
    private val wodComponentsMap: MutableMap<String, WODComponent> = mutableMapOf<String, WODComponent>()

    fun parseFile(wodFile: File): MutableMap<String, WODComponent>  {
        val fr = FileReader(wodFile)
        var comp: WODComponent? = null;
        val lines = fr.readLines()
        lines.forEach { line ->
            if (line.contains("{") ) {
                val lineParts = line.split(": ")
                comp = WODComponent(
                    name=lineParts[0],
                    type=lineParts[1].split(" {")[0],
                    args= mutableMapOf<String,String>()
                )
            }
            else if (line.contains("}")) {
                comp?.let{
                    wodComponents.add(it)
                    comp = null
                }
            }
            else {
                val line2 = line.trimStart()
                val lineParts = line2.split(" = ", ";")
                comp?.let {
                    val k = lineParts[0].trim()
                    val v = lineParts[1].trim()
                    it.args.put(k, v)
                }
            }
        }

        wodComponents.forEach {
            wodComponentsMap[it.name] = it
        }
        return wodComponentsMap
    }



    fun getComponents(): List<WODComponent> = wodComponents
}