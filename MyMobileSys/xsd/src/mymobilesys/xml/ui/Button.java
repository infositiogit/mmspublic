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
 * <p>Clase Java para Button complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Button"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}Control"&gt;
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="icontype" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="iconpos" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Button")
public class Button
    extends Control
{

    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "icontype")
    protected String icontype;
    @XmlAttribute(name = "iconpos")
    protected String iconpos;

    /**
     * Obtiene el valor de la propiedad text.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Define el valor de la propiedad text.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Obtiene el valor de la propiedad icontype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcontype() {
        return icontype;
    }

    /**
     * Define el valor de la propiedad icontype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcontype(String value) {
        this.icontype = value;
    }

    /**
     * Obtiene el valor de la propiedad iconpos.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIconpos() {
        return iconpos;
    }

    /**
     * Define el valor de la propiedad iconpos.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIconpos(String value) {
        this.iconpos = value;
    }

}
