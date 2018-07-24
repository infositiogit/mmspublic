//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.03.15 a las 01:34:11 PM CLT 
//


package mymobilesys.xml.ui;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Custom complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Custom"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}Control"&gt;
 *       &lt;attribute name="htmlfile" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="nativeside" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Custom")
public class Custom
    extends Control
{

    @XmlAttribute(name = "htmlfile")
    protected String htmlfile;
    @XmlAttribute(name = "nativeside")
    protected Boolean nativeside;

    /**
     * Obtiene el valor de la propiedad htmlfile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHtmlfile() {
        return htmlfile;
    }

    /**
     * Define el valor de la propiedad htmlfile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHtmlfile(String value) {
        this.htmlfile = value;
    }

    /**
     * Obtiene el valor de la propiedad nativeside.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNativeside() {
        if (nativeside == null) {
            return true;
        } else {
            return nativeside;
        }
    }

    /**
     * Define el valor de la propiedad nativeside.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNativeside(Boolean value) {
        this.nativeside = value;
    }

}
