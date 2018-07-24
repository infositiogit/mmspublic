//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.03.15 a las 01:34:11 PM CLT 
//


package mymobilesys.xml.ui;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}Control"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="Panel" type="{}Panel"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="PanelMenu" type="{}PanelMenu"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="includehtml" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onLoad" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onSaveState" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onRestoreState" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onPause" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="onResume" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "panels",
    "panelsMenu"
})
@XmlRootElement(name = "Screen")
public class Screen
    extends Control
{

    @XmlElement(name = "Panel")
    protected List<Panel> panels;
    @XmlElement(name = "PanelMenu")
    protected List<PanelMenu> panelsMenu;
    @XmlAttribute(name = "includehtml")
    protected String includehtml;
    @XmlAttribute(name = "onLoad")
    protected String onLoad;
    @XmlAttribute(name = "onSaveState")
    protected String onSaveState;
    @XmlAttribute(name = "onRestoreState")
    protected String onRestoreState;
    @XmlAttribute(name = "onPause")
    protected String onPause;
    @XmlAttribute(name = "onResume")
    protected String onResume;

    /**
     * Gets the value of the panels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the panels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPanels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Panel }
     * 
     * 
     */
    public List<Panel> getPanels() {
        if (panels == null) {
            panels = new ArrayList<Panel>();
        }
        return this.panels;
    }

    /**
     * Gets the value of the panelsMenu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the panelsMenu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPanelsMenu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PanelMenu }
     * 
     * 
     */
    public List<PanelMenu> getPanelsMenu() {
        if (panelsMenu == null) {
            panelsMenu = new ArrayList<PanelMenu>();
        }
        return this.panelsMenu;
    }

    /**
     * Obtiene el valor de la propiedad includehtml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncludehtml() {
        return includehtml;
    }

    /**
     * Define el valor de la propiedad includehtml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncludehtml(String value) {
        this.includehtml = value;
    }

    /**
     * Obtiene el valor de la propiedad onLoad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnLoad() {
        return onLoad;
    }

    /**
     * Define el valor de la propiedad onLoad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnLoad(String value) {
        this.onLoad = value;
    }

    /**
     * Obtiene el valor de la propiedad onSaveState.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnSaveState() {
        return onSaveState;
    }

    /**
     * Define el valor de la propiedad onSaveState.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnSaveState(String value) {
        this.onSaveState = value;
    }

    /**
     * Obtiene el valor de la propiedad onRestoreState.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnRestoreState() {
        return onRestoreState;
    }

    /**
     * Define el valor de la propiedad onRestoreState.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnRestoreState(String value) {
        this.onRestoreState = value;
    }

    /**
     * Obtiene el valor de la propiedad onPause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnPause() {
        return onPause;
    }

    /**
     * Define el valor de la propiedad onPause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnPause(String value) {
        this.onPause = value;
    }

    /**
     * Obtiene el valor de la propiedad onResume.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnResume() {
        return onResume;
    }

    /**
     * Define el valor de la propiedad onResume.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnResume(String value) {
        this.onResume = value;
    }

}
