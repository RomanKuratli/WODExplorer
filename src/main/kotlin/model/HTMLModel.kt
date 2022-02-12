package model

import java.io.File
import java.io.FileReader
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

open class HTMLNode(open val tagName: String, val children: MutableList<HTMLNode> = mutableListOf<HTMLNode>()) {
    open val isWebObject = false;
    open val isLeaf: Boolean get() = false
}

data class WebObject(val args: MutableMap<String, Object>) : HTMLNode("WEBOBJECT") {
    override val isWebObject get() = true;
    val type: String = args["NAME"].toString()
}



data class LeafNode(override val tagName: String, val content: String): HTMLNode(tagName) {
    override val isLeaf: Boolean
        get() = false
}

object HTMLModel {
    // TODO: adapt copied code
    private val wodComponents: MutableList<WODComponent> = mutableListOf()
    private val wodComponentsMap: MutableMap<String, Any> = mutableMapOf<String, Any>()

    fun parseFile(htmlFile: File):  HTMLNode {
        val fr = FileReader(htmlFile)
        var comp: WODComponent? = null;
        val lines = fr.readLines()
        val doc: Document = Jsoup.connect(htmlFile.path).get()
        val body: Elements = doc.select("HTML BODY")
        val bodyHtmlNode: HTMLNode = HTMLNode("Body")
        body.forEach {

        }

    }
}
