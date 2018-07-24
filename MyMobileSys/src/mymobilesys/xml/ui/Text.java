//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.03.15 a las 01:34:11 PM CLT 
//


package mymobilesys.xml.ui;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Text complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Text"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}Control"&gt;
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="inputtype" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="maxlength" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="data-options" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="data-format" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="placeholder" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Text")
public class Text
    extends Control
{

    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "inputtype")
    protected String inputtype;
    @XmlAttribute(name = "maxlength")
    protected BigInteger maxlength;
    @XmlAttribute(name = "data-options")
    protected String dataOptions;
    @XmlAttribute(name = "data-format")
    protected String dataFormat;
    @XmlAttribute(name = "placeholder")
    protected String placeholder;

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
     * Obtiene el valor de la propiedad label.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Define el valor de la propiedad label.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Obtiene el valor de la propiedad inputtype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputtype() {
        return inputtype;
    }

    /**
     * Define el valor de la propiedad inputtype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputtype(String value) {
        this.inputtype = value;
    }

    /**
     * Obtiene el valor de la propiedad maxlength.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxlength() {
        return maxlength;
    }

    /**
     * Define el valor de la propiedad maxlength.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxlength(BigInteger value) {
        this.maxlength = value;
    }

    /**
     * Obtiene el valor de la propiedad dataOptions.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataOptions() {
        return dataOptions;
    }

    /**
     * Define el valor de la propiedad dataOptions.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataOptions(String value) {
        this.dataOptions = value;
    }

    /**
     * Obtiene el valor de la propiedad dataFormat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFormat() {
        return dataFormat;
    }

    /**
     * Define el valor de la propiedad dataFormat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFormat(String value) {
        this.dataFormat = value;
    }

    /**
     * Obtiene el valor de la propiedad placeholder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Define el valor de la propiedad placeholder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceholder(String value) {
        this.placeholder = value;
    }

}
