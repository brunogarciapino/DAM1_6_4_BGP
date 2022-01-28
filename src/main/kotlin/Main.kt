package un5.eje5_4

import java.io.File
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory
import java.util.logging.Level
import java.util.logging.LogManager
/**
 * Una clase para poder leer un archivo xml realizando distintos métodos para comprobar si existe el libro
   y otra para coger información del archivo xml
 * @constructor almacena la propiedad cargador y tambien en el init la ruta del archivo xml y te dice si se encuentra o no
 * @property la propiedad cargador sirve para cargar el archivo xml*/
open class CatalogoLibrosXML(private val cargador:String) {
    val archivo = File(cargador)
    val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
    init {
        if(archivo.exists()){
            l.info("El fichero existe")
        }else l.info("El fichero no existe")
    }

/**
* El método existeLibro te dice si encuentra el libro o no, y si lo encuentra te devuelve un true y si no te devuelve un
 false
* @param idLibro reconoce el id del libro entre distintos nodos */
    open fun existeLibro(idLibro: String): Boolean {
        val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
        var xmlDoc = readXml("..\\5_4\\src\\main\\kotlin\\Catalogo.xml")

        //https://runebook.dev/es/docs/dom/node/normalize
        xmlDoc.documentElement.normalize()

        val lista = obtenerListaNodosPorNombre(xmlDoc, "book")

        lista.forEach {
            if (it.getNodeType() === Node.ELEMENT_NODE) {
                val elem = it as Element
                val mMap = obtenerAtributosEnMapKV(elem)
                val nMap = mMap.toString()
                var ida = "{id=$idLibro}"
                if (ida == nMap) {
                    l.info("true")
                    return true
                } else {
                    val adios = "o"
                }
            }
        }
        l.info("false")
        return false
    }


/**
* El método infoLibro guarda en un map la información del xml(los nodos) y te lo saca a través de un log, mostrandote
 como se veria el xml.
* @param idLibro reconoce el id del libro entre distintos nodos */
    open fun infoLibro(idLibro: String): Map<String, Any> {
        val mapVacio = mutableMapOf<String, String>("" to "")
        val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
        var xmlDoc = readXml("..\\5_4\\src\\main\\kotlin\\Catalogo.xml")

        //https://runebook.dev/es/docs/dom/node/normalize
        xmlDoc.documentElement.normalize()

        val lista = obtenerListaNodosPorNombre(xmlDoc, "book")

        lista.forEach {
            if (it.getNodeType() === Node.ELEMENT_NODE) {
                val elem = it as Element
                val mMap = obtenerAtributosEnMapKV(elem)
                val nMap = mMap.toString()
                var ida = "{id=$idLibro}"

                if (ida == nMap) {
                    l.info("true")
                    l.info("- ${it.nodeName} - $mMap")
                    l.info("- Autor: ${it.getElementsByTagName("author").item(0).textContent}")
                    l.info("- Titulo: ${it.getElementsByTagName("title").item(0).textContent}")
                    l.info("- Genero: ${it.getElementsByTagName("genre").item(0).textContent}")
                    l.info("- Precio: ${it.getElementsByTagName("price").item(0).textContent}")
                    l.info("- Fecha de Publicacion: ${it.getElementsByTagName("publish_date").item(0).textContent}")
                    l.info("- Descripcion: ${it.getElementsByTagName("description").item(0).textContent}")

                    var mapa = mapOf(
                        "id" to "$mMap",
                        "Autor" to "${it.getElementsByTagName("author").item(0).textContent}",
                        "Titulo" to "${it.getElementsByTagName("title").item(0).textContent}",
                        "Género" to "${it.getElementsByTagName("genre").item(0).textContent}",
                        "Precio" to "${it.getElementsByTagName("price").item(0).textContent}",
                        "Fecha de Publicacion" to "${it.getElementsByTagName("publish_date").item(0).textContent}",
                        "Descripcion" to "${it.getElementsByTagName("description").item(0).textContent}"
                    )
                    return mapa
                } else {
                    val adios = "o"
                }
            }
        }
        l.info("No se ha encontrado")
        return mapVacio
    }

/**
* El método readmiXml sirve para leer el archivo xml
* @param pathName encuentra la ruta del fichero */
    private fun readXml(pathName: String): Document {
        val xmlFile = File(pathName)
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile)
    }

/**
* El método obtenerAtributosEnMapKV sirve para obtener los nodos del xml a trabes de un mapa
* @param element encuentra la ruta del fichero */
    private fun obtenerAtributosEnMapKV(e: Element): MutableMap<String, String> {
        val mMap = mutableMapOf<String, String>()
        for (j in 0..e.attributes.length - 1)
            mMap.putIfAbsent(e.attributes.item(j).nodeName, e.attributes.item(j).nodeValue)
        return mMap
    }
/**
 * El método obtenerListaNodosPorNombre te devuelve una lista con los nodos que te piden
 * @param tagName te devuelve el id del libro, por ejemplo "bk101"
 * @param doc es el documento xml */
    private fun obtenerListaNodosPorNombre(doc: Document, tagName: String): MutableList<Node> {
        val bookList: NodeList = doc.getElementsByTagName(tagName)
        val lista = mutableListOf<Node>()
        for (i in 0..bookList.length - 1)
            lista.add(bookList.item(i))
        return lista
    }
}

fun main() {
    var texto = CatalogoLibrosXML("..\\5_4\\src\\main\\kotlin\\Catalogo.xml")
    texto.existeLibro("bk101")
    texto.infoLibro("bk102")

/*
<ItemSet>
    <Item type="T0" count="1">
        <Subitem> Valor T0.TT1 </Subitem>
    </Item>
    <Item type="T1" count="2">
        <Subitem> Valor T1.TT1 </Subitem>
    </Item>
    <Item type="T2" count="1">
         <Subitem> Valor T2.TT1 </Subitem>
    </Item>
    <Item type="T3" count="1">
         <Subitem> Valor T3.TT1 </Subitem>
    </Item>
</ItemSet>
*/


}