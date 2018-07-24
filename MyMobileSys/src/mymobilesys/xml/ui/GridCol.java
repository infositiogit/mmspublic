//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.03.15 a las 01:34:11 PM CLT 
//


package mymobilesys.xml.ui;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para GridCol complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="GridCol"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}Control"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="Container" type="{}Container" minOccurs="0"/&gt;
 *           &lt;element name="Button" type="{}Button" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Text" type="{}Text" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Check" type="{}Check" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="RadioGroup" type="{}RadioGroup" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Combo" type="{}Combo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="List" type="{}List" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Image" type="{}Image" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Form" type="{}Form" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Grid" type="{}Grid" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Html" type="{}Html" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="Custom" type="{}Custom" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GridCol", propOrder = {
    "controls"
})
public class GridCol
    extends Control
{

    @XmlElements({
        @XmlElement(name = "Container", type = Container.class),
        @XmlElement(name = "Button", type = Button.class),
        @XmlElement(name = "Text", type = Text.class),
        @XmlElement(name = "Check", type = Check.class),
        @XmlElement(name = "RadioGroup", type = RadioGroup.class),
        @XmlElement(name = "Combo", type = Combo.class),
        @XmlElement(name = "List", type = mymobilesys.xml.ui.List.class),
        @XmlElement(name = "Image", type = Image.class),
        @XmlElement(name = "Form", type = Form.class),
        @XmlElement(name = "Grid", type = Grid.class),
        @XmlElement(name = "Html", type = Html.class),
        @XmlElement(name = "Custom", type = Custom.class)
    })
    protected java.util.List<Control> controls;

    /**
     * Gets the value of the controls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Container }
     * {@link Button }
     * {@link Text }
     * {@link Check }
     * {@link RadioGroup }
     * {@link Combo }
     * {@link mymobilesys.xml.ui.List }
     * {@link Image }
     * {@link Form }
     * {@link Grid }
     * {@link Html }
     * {@link Custom }
     * 
     * 
     */
    public java.util.List<Control> getControls() {
        if (controls == null) {
            controls = new ArrayList<Control>();
        }
        return this.controls;
    }

}
