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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import mymobilesys.xml.ui.base.ControlBase;


/**
 * <p>Clase Java para Control complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Control"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onclick" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onfocus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onfocusout" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="jqtheme" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mini" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="fieldcontain" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="replacehtml" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="enhancejq" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Control", propOrder = {
    "data"
})
@XmlSeeAlso({
    Image.class,
    Button.class,
    Text.class,
    Html.class,
    Custom.class,
    Check.class,
    Radio.class,
    GridCol.class,
    Grid.class,
    Combo.class,
    RadioGroup.class,
    List.class,
    Container.class,
    Screen.class
})
public class Control
    extends ControlBase
{

    protected String data;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "onclick")
    protected String onclick;
    @XmlAttribute(name = "onfocus")
    protected String onfocus;
    @XmlAttribute(name = "onfocusout")
    protected String onfocusout;
    @XmlAttribute(name = "jqtheme")
    protected String jqtheme;
    @XmlAttribute(name = "mini")
    protected Boolean mini;
    @XmlAttribute(name = "fieldcontain")
    protected Boolean fieldcontain;
    @XmlAttribute(name = "class")
    protected String clazz;
    @XmlAttribute(name = "replacehtml")
    protected String replacehtml;
    @XmlAttribute(name = "enhancejq")
    protected Boolean enhancejq;

    /**
     * Obtiene el valor de la propiedad data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad onclick.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * Define el valor de la propiedad onclick.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnclick(String value) {
        this.onclick = value;
    }

    /**
     * Obtiene el valor de la propiedad onfocus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnfocus() {
        return onfocus;
    }

    /**
     * Define el valor de la propiedad onfocus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnfocus(String value) {
        this.onfocus = value;
    }

    /**
     * Obtiene el valor de la propiedad onfocusout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnfocusout() {
        return onfocusout;
    }

    /**
     * Define el valor de la propiedad onfocusout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnfocusout(String value) {
        this.onfocusout = value;
    }

    /**
     * Obtiene el valor de la propiedad jqtheme.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJqtheme() {
        return jqtheme;
    }

    /**
     * Define el valor de la propiedad jqtheme.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJqtheme(String value) {
        this.jqtheme = value;
    }

    /**
     * Obtiene el valor de la propiedad mini.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMini() {
        return mini;
    }

    /**
     * Define el valor de la propiedad mini.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMini(Boolean value) {
        this.mini = value;
    }

    /**
     * Obtiene el valor de la propiedad fieldcontain.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isFieldcontain() {
        if (fieldcontain == null) {
            return false;
        } else {
            return fieldcontain;
        }
    }

    /**
     * Define el valor de la propiedad fieldcontain.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFieldcontain(Boolean value) {
        this.fieldcontain = value;
    }

    /**
     * Obtiene el valor de la propiedad clazz.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Define el valor de la propiedad clazz.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * Obtiene el valor de la propiedad replacehtml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplacehtml() {
        return replacehtml;
    }

    /**
     * Define el valor de la propiedad replacehtml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplacehtml(String value) {
        this.replacehtml = value;
    }

    /**
     * Obtiene el valor de la propiedad enhancejq.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnhancejq() {
        if (enhancejq == null) {
            return true;
        } else {
            return enhancejq;
        }
    }

    /**
     * Define el valor de la propiedad enhancejq.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnhancejq(Boolean value) {
        this.enhancejq = value;
    }

}
