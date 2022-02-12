package model

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.isDirectory



class Component(val name: String, val path: Path, val project: Project) {
    private var apiFile: File? = null

    get() {
        if (field == null) {
            val parentPathStr: String = path.toString();
            val apiPath = Paths.get(parentPathStr, name.replace(".wo", ".api"))
            field = File(apiPath.toString())
        }
        assert(field != null)
        return field
    }

    private var javaFile: File? = null
        get() {
            if (field == null) {
                val parentPathStr: String = path.toString();
                val apiPath = Paths.get(parentPathStr, name.replace(".wo", ".java"))
                field = File(apiPath.toString())
            }
            assert(field != null)
            return field
        }

    private var htmlFile: File? = null
        get() {
            if (field == null) {
                val parentPathStr: String = path.toString();
                val htmlPath = Paths.get(parentPathStr, name.replace(".wo", ".html"))
                field = File(htmlPath.toString())
            }
            assert(field != null)
            return field
        }


    private var wodFile: File? = null
        get() {
            if (field == null) {
                val parentPathStr: String = path.toString();
                val htmlPath = Paths.get(parentPathStr, name.replace(".wo", ".wod"))
                field = File(htmlPath.toString())
            }
            assert(field != null)
            return field
        }

    public fun getWODModel() = WODModel.parseFile(wodFile!!)

    public fun getHTMLModel() = model.HTMLModel.parseFile(htmlFile!!)
}

class Project(val name: String, val path: Path) {
    val componentNames: MutableList<String> = mutableListOf<String>()
    var components: List<Component>? = null
    get() {
        if (componentNames.isEmpty()) {
            path.iterator().forEach { compCand: Path ->
                if (compCand.startsWith("CST") && compCand.isDirectory()) {
                    componentNames.add(compCand.toString())
                }
            }
        }
        components = componentNames.map { Component(
                                            name=it,
                                            path=Paths.get(path.toString()),
                                            project=this)
                                        }
        return components;
    }

}