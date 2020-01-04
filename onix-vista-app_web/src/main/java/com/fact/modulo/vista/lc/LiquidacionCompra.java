//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.12.12 a las 10:02:33 AM COT 
//


package com.fact.modulo.vista.lc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infoTributaria" type="{}infoTributaria"/>
 *         &lt;element name="infoLiquidacionCompra">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fechaEmision" type="{}fechaEmision"/>
 *                   &lt;element name="dirEstablecimiento" type="{}dirEstablecimiento" minOccurs="0"/>
 *                   &lt;element name="contribuyenteEspecial" type="{}contribuyenteEspecial" minOccurs="0"/>
 *                   &lt;element name="obligadoContabilidad" type="{}obligadoContabilidad" minOccurs="0"/>
 *                   &lt;element name="tipoIdentificacionProveedor" type="{}tipoIdentificacionProveedor"/>
 *                   &lt;element name="razonSocialProveedor" type="{}razonSocialProveedor"/>
 *                   &lt;element name="identificacionProveedor" type="{}identificacionProveedor"/>
 *                   &lt;element name="direccionProveedor" type="{}direccionProveedor" minOccurs="0"/>
 *                   &lt;element name="totalSinImpuestos" type="{}totalSinImpuestos"/>
 *                   &lt;element name="totalDescuento" type="{}totalDescuentos"/>
 *                   &lt;element name="codDocReembolso" type="{}codigoDocumentoReembolso" minOccurs="0"/>
 *                   &lt;element name="totalComprobantesReembolso" type="{}totalComprobantesReembolso" minOccurs="0"/>
 *                   &lt;element name="totalBaseImponibleReembolso" type="{}totalBaseImponibleReembolso" minOccurs="0"/>
 *                   &lt;element name="totalImpuestoReembolso" type="{}totalImpuestoReembolso" minOccurs="0"/>
 *                   &lt;element name="totalConImpuestos">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="totalImpuesto" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="codigo" type="{}codigo"/>
 *                                       &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/>
 *                                       &lt;element name="descuentoAdicional" type="{}descuentoAdicional" minOccurs="0"/>
 *                                       &lt;element name="baseImponible" type="{}baseImponible"/>
 *                                       &lt;element name="tarifa" type="{}tarifa" minOccurs="0"/>
 *                                       &lt;element name="valor" type="{}valor"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="importeTotal" type="{}importeTotal"/>
 *                   &lt;element name="moneda" type="{}moneda" minOccurs="0"/>
 *                   &lt;element name="pagos" type="{}pagos" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="detalles">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="detalle" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="codigoPrincipal" type="{}codigoPrincipal" minOccurs="0"/>
 *                             &lt;element name="codigoAuxiliar" type="{}codigoAuxiliar" minOccurs="0"/>
 *                             &lt;element name="descripcion" type="{}descripcion"/>
 *                             &lt;element name="unidadMedida" type="{}unidadMedida" minOccurs="0"/>
 *                             &lt;element name="cantidad" type="{}cantidad"/>
 *                             &lt;element name="precioUnitario" type="{}precioUnitario"/>
 *                             &lt;element name="precioSinSubsidio" type="{}precioSinSubsidio" minOccurs="0"/>
 *                             &lt;element name="descuento" type="{}descuento"/>
 *                             &lt;element name="precioTotalSinImpuesto" type="{}precioTotalSinImpuesto"/>
 *                             &lt;element name="detallesAdicionales" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="detAdicional" maxOccurs="3">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="nombre" use="required">
 *                                                 &lt;simpleType>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                     &lt;minLength value="1"/>
 *                                                     &lt;maxLength value="300"/>
 *                                                   &lt;/restriction>
 *                                                 &lt;/simpleType>
 *                                               &lt;/attribute>
 *                                               &lt;attribute name="valor" use="required">
 *                                                 &lt;simpleType>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                     &lt;minLength value="1"/>
 *                                                     &lt;maxLength value="300"/>
 *                                                   &lt;/restriction>
 *                                                 &lt;/simpleType>
 *                                               &lt;/attribute>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="impuestos">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="impuesto" type="{}impuesto" maxOccurs="unbounded"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reembolsos" type="{}reembolsos" minOccurs="0"/>
 *         &lt;element name="tipoNegociable" type="{}tipoNegociable" minOccurs="0"/>
 *         &lt;element name="maquinaFiscal" type="{}maquinaFiscal" minOccurs="0"/>
 *         &lt;element name="infoAdicional" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="campoAdicional" maxOccurs="15">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;>campoAdicional">
 *                           &lt;attribute name="nombre" use="required" type="{}nombre" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="comprobante"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "infoTributaria",
    "infoLiquidacionCompra",
    "detalles",
    "reembolsos",
    "tipoNegociable",
    "maquinaFiscal",
    "infoAdicional",
    "signature"
})
@XmlRootElement(name = "liquidacionCompra", namespace = "")
public class LiquidacionCompra {

    @XmlElement(required = true)
    protected InfoTributaria infoTributaria;
    @XmlElement(required = true)
    protected LiquidacionCompra.InfoLiquidacionCompra infoLiquidacionCompra;
    @XmlElement(required = true)
    protected LiquidacionCompra.Detalles detalles;
    protected Reembolsos reembolsos;
    protected TipoNegociable tipoNegociable;
    protected MaquinaFiscal maquinaFiscal;
    protected LiquidacionCompra.InfoAdicional infoAdicional;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "version")
    @XmlSchemaType(name = "anySimpleType")
    protected String version;

    /**
     * Obtiene el valor de la propiedad infoTributaria.
     * 
     * @return
     *     possible object is
     *     {@link InfoTributaria }
     *     
     */
    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    /**
     * Define el valor de la propiedad infoTributaria.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoTributaria }
     *     
     */
    public void setInfoTributaria(InfoTributaria value) {
        this.infoTributaria = value;
    }

    /**
     * Obtiene el valor de la propiedad infoLiquidacionCompra.
     * 
     * @return
     *     possible object is
     *     {@link LiquidacionCompra.InfoLiquidacionCompra }
     *     
     */
    public LiquidacionCompra.InfoLiquidacionCompra getInfoLiquidacionCompra() {
        return infoLiquidacionCompra;
    }

    /**
     * Define el valor de la propiedad infoLiquidacionCompra.
     * 
     * @param value
     *     allowed object is
     *     {@link LiquidacionCompra.InfoLiquidacionCompra }
     *     
     */
    public void setInfoLiquidacionCompra(LiquidacionCompra.InfoLiquidacionCompra value) {
        this.infoLiquidacionCompra = value;
    }

    /**
     * Obtiene el valor de la propiedad detalles.
     * 
     * @return
     *     possible object is
     *     {@link LiquidacionCompra.Detalles }
     *     
     */
    public LiquidacionCompra.Detalles getDetalles() {
        return detalles;
    }

    /**
     * Define el valor de la propiedad detalles.
     * 
     * @param value
     *     allowed object is
     *     {@link LiquidacionCompra.Detalles }
     *     
     */
    public void setDetalles(LiquidacionCompra.Detalles value) {
        this.detalles = value;
    }

    /**
     * Obtiene el valor de la propiedad reembolsos.
     * 
     * @return
     *     possible object is
     *     {@link Reembolsos }
     *     
     */
    public Reembolsos getReembolsos() {
        return reembolsos;
    }

    /**
     * Define el valor de la propiedad reembolsos.
     * 
     * @param value
     *     allowed object is
     *     {@link Reembolsos }
     *     
     */
    public void setReembolsos(Reembolsos value) {
        this.reembolsos = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoNegociable.
     * 
     * @return
     *     possible object is
     *     {@link TipoNegociable }
     *     
     */
    public TipoNegociable getTipoNegociable() {
        return tipoNegociable;
    }

    /**
     * Define el valor de la propiedad tipoNegociable.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNegociable }
     *     
     */
    public void setTipoNegociable(TipoNegociable value) {
        this.tipoNegociable = value;
    }

    /**
     * Obtiene el valor de la propiedad maquinaFiscal.
     * 
     * @return
     *     possible object is
     *     {@link MaquinaFiscal }
     *     
     */
    public MaquinaFiscal getMaquinaFiscal() {
        return maquinaFiscal;
    }

    /**
     * Define el valor de la propiedad maquinaFiscal.
     * 
     * @param value
     *     allowed object is
     *     {@link MaquinaFiscal }
     *     
     */
    public void setMaquinaFiscal(MaquinaFiscal value) {
        this.maquinaFiscal = value;
    }

    /**
     * Obtiene el valor de la propiedad infoAdicional.
     * 
     * @return
     *     possible object is
     *     {@link LiquidacionCompra.InfoAdicional }
     *     
     */
    public LiquidacionCompra.InfoAdicional getInfoAdicional() {
        return infoAdicional;
    }

    /**
     * Define el valor de la propiedad infoAdicional.
     * 
     * @param value
     *     allowed object is
     *     {@link LiquidacionCompra.InfoAdicional }
     *     
     */
    public void setInfoAdicional(LiquidacionCompra.InfoAdicional value) {
        this.infoAdicional = value;
    }

    /**
     *  Conjunto de datos asociados a la liquidacion de compras que garantizaran la autoria y la integridad del mensaje. Se define como opcional para facilitar la verificacion y el transito del fichero. No obstante, debe cumplimentarse este bloque de firma electronica para que se considere una liquidacion de compras electronica valida legalmente frente a terceros.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Define el valor de la propiedad signature.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="detalle" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="codigoPrincipal" type="{}codigoPrincipal" minOccurs="0"/>
     *                   &lt;element name="codigoAuxiliar" type="{}codigoAuxiliar" minOccurs="0"/>
     *                   &lt;element name="descripcion" type="{}descripcion"/>
     *                   &lt;element name="unidadMedida" type="{}unidadMedida" minOccurs="0"/>
     *                   &lt;element name="cantidad" type="{}cantidad"/>
     *                   &lt;element name="precioUnitario" type="{}precioUnitario"/>
     *                   &lt;element name="precioSinSubsidio" type="{}precioSinSubsidio" minOccurs="0"/>
     *                   &lt;element name="descuento" type="{}descuento"/>
     *                   &lt;element name="precioTotalSinImpuesto" type="{}precioTotalSinImpuesto"/>
     *                   &lt;element name="detallesAdicionales" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="detAdicional" maxOccurs="3">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attribute name="nombre" use="required">
     *                                       &lt;simpleType>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                           &lt;minLength value="1"/>
     *                                           &lt;maxLength value="300"/>
     *                                         &lt;/restriction>
     *                                       &lt;/simpleType>
     *                                     &lt;/attribute>
     *                                     &lt;attribute name="valor" use="required">
     *                                       &lt;simpleType>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                           &lt;minLength value="1"/>
     *                                           &lt;maxLength value="300"/>
     *                                         &lt;/restriction>
     *                                       &lt;/simpleType>
     *                                     &lt;/attribute>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="impuestos">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="impuesto" type="{}impuesto" maxOccurs="unbounded"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "detalle"
    })
    public static class Detalles {

        @XmlElement(required = true)
        protected List<LiquidacionCompra.Detalles.Detalle> detalle;

        /**
         * Gets the value of the detalle property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the detalle property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDetalle().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LiquidacionCompra.Detalles.Detalle }
         * 
         * 
         */
        public List<LiquidacionCompra.Detalles.Detalle> getDetalle() {
            if (detalle == null) {
                detalle = new ArrayList<LiquidacionCompra.Detalles.Detalle>();
            }
            return this.detalle;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="codigoPrincipal" type="{}codigoPrincipal" minOccurs="0"/>
         *         &lt;element name="codigoAuxiliar" type="{}codigoAuxiliar" minOccurs="0"/>
         *         &lt;element name="descripcion" type="{}descripcion"/>
         *         &lt;element name="unidadMedida" type="{}unidadMedida" minOccurs="0"/>
         *         &lt;element name="cantidad" type="{}cantidad"/>
         *         &lt;element name="precioUnitario" type="{}precioUnitario"/>
         *         &lt;element name="precioSinSubsidio" type="{}precioSinSubsidio" minOccurs="0"/>
         *         &lt;element name="descuento" type="{}descuento"/>
         *         &lt;element name="precioTotalSinImpuesto" type="{}precioTotalSinImpuesto"/>
         *         &lt;element name="detallesAdicionales" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="detAdicional" maxOccurs="3">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="nombre" use="required">
         *                             &lt;simpleType>
         *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                 &lt;minLength value="1"/>
         *                                 &lt;maxLength value="300"/>
         *                               &lt;/restriction>
         *                             &lt;/simpleType>
         *                           &lt;/attribute>
         *                           &lt;attribute name="valor" use="required">
         *                             &lt;simpleType>
         *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                 &lt;minLength value="1"/>
         *                                 &lt;maxLength value="300"/>
         *                               &lt;/restriction>
         *                             &lt;/simpleType>
         *                           &lt;/attribute>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="impuestos">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="impuesto" type="{}impuesto" maxOccurs="unbounded"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "codigoPrincipal",
            "codigoAuxiliar",
            "descripcion",
            "unidadMedida",
            "cantidad",
            "precioUnitario",
            "precioSinSubsidio",
            "descuento",
            "precioTotalSinImpuesto",
            "detallesAdicionales",
            "impuestos"
        })
        public static class Detalle {

            protected String codigoPrincipal;
            protected String codigoAuxiliar;
            @XmlElement(required = true)
            protected String descripcion;
            protected String unidadMedida;
            @XmlElement(required = true)
            protected BigDecimal cantidad;
            @XmlElement(required = true)
            protected BigDecimal precioUnitario;
            protected BigDecimal precioSinSubsidio;
            @XmlElement(required = true)
            protected BigDecimal descuento;
            @XmlElement(required = true)
            protected BigDecimal precioTotalSinImpuesto;
            protected LiquidacionCompra.Detalles.Detalle.DetallesAdicionales detallesAdicionales;
            @XmlElement(required = true)
            protected LiquidacionCompra.Detalles.Detalle.Impuestos impuestos;

            /**
             * Obtiene el valor de la propiedad codigoPrincipal.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodigoPrincipal() {
                return codigoPrincipal;
            }

            /**
             * Define el valor de la propiedad codigoPrincipal.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodigoPrincipal(String value) {
                this.codigoPrincipal = value;
            }

            /**
             * Obtiene el valor de la propiedad codigoAuxiliar.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodigoAuxiliar() {
                return codigoAuxiliar;
            }

            /**
             * Define el valor de la propiedad codigoAuxiliar.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodigoAuxiliar(String value) {
                this.codigoAuxiliar = value;
            }

            /**
             * Obtiene el valor de la propiedad descripcion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcion() {
                return descripcion;
            }

            /**
             * Define el valor de la propiedad descripcion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcion(String value) {
                this.descripcion = value;
            }

            /**
             * Obtiene el valor de la propiedad unidadMedida.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnidadMedida() {
                return unidadMedida;
            }

            /**
             * Define el valor de la propiedad unidadMedida.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnidadMedida(String value) {
                this.unidadMedida = value;
            }

            /**
             * Obtiene el valor de la propiedad cantidad.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCantidad() {
                return cantidad;
            }

            /**
             * Define el valor de la propiedad cantidad.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCantidad(BigDecimal value) {
                this.cantidad = value;
            }

            /**
             * Obtiene el valor de la propiedad precioUnitario.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPrecioUnitario() {
                return precioUnitario;
            }

            /**
             * Define el valor de la propiedad precioUnitario.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPrecioUnitario(BigDecimal value) {
                this.precioUnitario = value;
            }

            /**
             * Obtiene el valor de la propiedad precioSinSubsidio.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPrecioSinSubsidio() {
                return precioSinSubsidio;
            }

            /**
             * Define el valor de la propiedad precioSinSubsidio.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPrecioSinSubsidio(BigDecimal value) {
                this.precioSinSubsidio = value;
            }

            /**
             * Obtiene el valor de la propiedad descuento.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDescuento() {
                return descuento;
            }

            /**
             * Define el valor de la propiedad descuento.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDescuento(BigDecimal value) {
                this.descuento = value;
            }

            /**
             * Obtiene el valor de la propiedad precioTotalSinImpuesto.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPrecioTotalSinImpuesto() {
                return precioTotalSinImpuesto;
            }

            /**
             * Define el valor de la propiedad precioTotalSinImpuesto.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPrecioTotalSinImpuesto(BigDecimal value) {
                this.precioTotalSinImpuesto = value;
            }

            /**
             * Obtiene el valor de la propiedad detallesAdicionales.
             * 
             * @return
             *     possible object is
             *     {@link LiquidacionCompra.Detalles.Detalle.DetallesAdicionales }
             *     
             */
            public LiquidacionCompra.Detalles.Detalle.DetallesAdicionales getDetallesAdicionales() {
                return detallesAdicionales;
            }

            /**
             * Define el valor de la propiedad detallesAdicionales.
             * 
             * @param value
             *     allowed object is
             *     {@link LiquidacionCompra.Detalles.Detalle.DetallesAdicionales }
             *     
             */
            public void setDetallesAdicionales(LiquidacionCompra.Detalles.Detalle.DetallesAdicionales value) {
                this.detallesAdicionales = value;
            }

            /**
             * Obtiene el valor de la propiedad impuestos.
             * 
             * @return
             *     possible object is
             *     {@link LiquidacionCompra.Detalles.Detalle.Impuestos }
             *     
             */
            public LiquidacionCompra.Detalles.Detalle.Impuestos getImpuestos() {
                return impuestos;
            }

            /**
             * Define el valor de la propiedad impuestos.
             * 
             * @param value
             *     allowed object is
             *     {@link LiquidacionCompra.Detalles.Detalle.Impuestos }
             *     
             */
            public void setImpuestos(LiquidacionCompra.Detalles.Detalle.Impuestos value) {
                this.impuestos = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="detAdicional" maxOccurs="3">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="nombre" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;minLength value="1"/>
             *                       &lt;maxLength value="300"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="valor" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;minLength value="1"/>
             *                       &lt;maxLength value="300"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "detAdicional"
            })
            public static class DetallesAdicionales {

                @XmlElement(required = true)
                protected List<LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional> detAdicional;

                /**
                 * Gets the value of the detAdicional property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the detAdicional property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDetAdicional().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional }
                 * 
                 * 
                 */
                public List<LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional> getDetAdicional() {
                    if (detAdicional == null) {
                        detAdicional = new ArrayList<LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional>();
                    }
                    return this.detAdicional;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="nombre" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;minLength value="1"/>
                 *             &lt;maxLength value="300"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="valor" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;minLength value="1"/>
                 *             &lt;maxLength value="300"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class DetAdicional {

                    @XmlAttribute(name = "nombre", required = true)
                    protected String nombre;
                    @XmlAttribute(name = "valor", required = true)
                    protected String valor;

                    /**
                     * Obtiene el valor de la propiedad nombre.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNombre() {
                        return nombre;
                    }

                    /**
                     * Define el valor de la propiedad nombre.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNombre(String value) {
                        this.nombre = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad valor.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValor() {
                        return valor;
                    }

                    /**
                     * Define el valor de la propiedad valor.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValor(String value) {
                        this.valor = value;
                    }

                }

            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="impuesto" type="{}impuesto" maxOccurs="unbounded"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "impuesto"
            })
            public static class Impuestos {

                @XmlElement(required = true)
                protected List<Impuesto> impuesto;

                /**
                 * Gets the value of the impuesto property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the impuesto property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getImpuesto().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Impuesto }
                 * 
                 * 
                 */
                public List<Impuesto> getImpuesto() {
                    if (impuesto == null) {
                        impuesto = new ArrayList<Impuesto>();
                    }
                    return this.impuesto;
                }

            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="campoAdicional" maxOccurs="15">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;>campoAdicional">
     *                 &lt;attribute name="nombre" use="required" type="{}nombre" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "campoAdicional"
    })
    public static class InfoAdicional {

        @XmlElement(required = true)
        protected List<LiquidacionCompra.InfoAdicional.CampoAdicional> campoAdicional;

        /**
         * Gets the value of the campoAdicional property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the campoAdicional property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCampoAdicional().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LiquidacionCompra.InfoAdicional.CampoAdicional }
         * 
         * 
         */
        public List<LiquidacionCompra.InfoAdicional.CampoAdicional> getCampoAdicional() {
            if (campoAdicional == null) {
                campoAdicional = new ArrayList<LiquidacionCompra.InfoAdicional.CampoAdicional>();
            }
            return this.campoAdicional;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;>campoAdicional">
         *       &lt;attribute name="nombre" use="required" type="{}nombre" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class CampoAdicional {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "nombre", required = true)
            protected String nombre;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Obtiene el valor de la propiedad nombre.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombre() {
                return nombre;
            }

            /**
             * Define el valor de la propiedad nombre.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombre(String value) {
                this.nombre = value;
            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="fechaEmision" type="{}fechaEmision"/>
     *         &lt;element name="dirEstablecimiento" type="{}dirEstablecimiento" minOccurs="0"/>
     *         &lt;element name="contribuyenteEspecial" type="{}contribuyenteEspecial" minOccurs="0"/>
     *         &lt;element name="obligadoContabilidad" type="{}obligadoContabilidad" minOccurs="0"/>
     *         &lt;element name="tipoIdentificacionProveedor" type="{}tipoIdentificacionProveedor"/>
     *         &lt;element name="razonSocialProveedor" type="{}razonSocialProveedor"/>
     *         &lt;element name="identificacionProveedor" type="{}identificacionProveedor"/>
     *         &lt;element name="direccionProveedor" type="{}direccionProveedor" minOccurs="0"/>
     *         &lt;element name="totalSinImpuestos" type="{}totalSinImpuestos"/>
     *         &lt;element name="totalDescuento" type="{}totalDescuentos"/>
     *         &lt;element name="codDocReembolso" type="{}codigoDocumentoReembolso" minOccurs="0"/>
     *         &lt;element name="totalComprobantesReembolso" type="{}totalComprobantesReembolso" minOccurs="0"/>
     *         &lt;element name="totalBaseImponibleReembolso" type="{}totalBaseImponibleReembolso" minOccurs="0"/>
     *         &lt;element name="totalImpuestoReembolso" type="{}totalImpuestoReembolso" minOccurs="0"/>
     *         &lt;element name="totalConImpuestos">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="totalImpuesto" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="codigo" type="{}codigo"/>
     *                             &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/>
     *                             &lt;element name="descuentoAdicional" type="{}descuentoAdicional" minOccurs="0"/>
     *                             &lt;element name="baseImponible" type="{}baseImponible"/>
     *                             &lt;element name="tarifa" type="{}tarifa" minOccurs="0"/>
     *                             &lt;element name="valor" type="{}valor"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="importeTotal" type="{}importeTotal"/>
     *         &lt;element name="moneda" type="{}moneda" minOccurs="0"/>
     *         &lt;element name="pagos" type="{}pagos" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fechaEmision",
        "dirEstablecimiento",
        "contribuyenteEspecial",
        "obligadoContabilidad",
        "tipoIdentificacionProveedor",
        "razonSocialProveedor",
        "identificacionProveedor",
        "direccionProveedor",
        "totalSinImpuestos",
        "totalDescuento",
        "codDocReembolso",
        "totalComprobantesReembolso",
        "totalBaseImponibleReembolso",
        "totalImpuestoReembolso",
        "totalConImpuestos",
        "importeTotal",
        "moneda",
        "pagos"
    })
    public static class InfoLiquidacionCompra {

        @XmlElement(required = true)
        protected String fechaEmision;
        protected String dirEstablecimiento;
        protected String contribuyenteEspecial;
        @XmlSchemaType(name = "string")
        protected ObligadoContabilidad obligadoContabilidad;
        @XmlElement(required = true)
        protected String tipoIdentificacionProveedor;
        @XmlElement(required = true)
        protected String razonSocialProveedor;
        @XmlElement(required = true)
        protected String identificacionProveedor;
        protected String direccionProveedor;
        @XmlElement(required = true)
        protected BigDecimal totalSinImpuestos;
        @XmlElement(required = true)
        protected BigDecimal totalDescuento;
        protected String codDocReembolso;
        protected BigDecimal totalComprobantesReembolso;
        protected BigDecimal totalBaseImponibleReembolso;
        protected BigDecimal totalImpuestoReembolso;
        @XmlElement(required = true)
        protected LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos totalConImpuestos;
        @XmlElement(required = true)
        protected BigDecimal importeTotal;
        protected String moneda;
        protected Pagos pagos;

        /**
         * Obtiene el valor de la propiedad fechaEmision.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechaEmision() {
            return fechaEmision;
        }

        /**
         * Define el valor de la propiedad fechaEmision.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechaEmision(String value) {
            this.fechaEmision = value;
        }

        /**
         * Obtiene el valor de la propiedad dirEstablecimiento.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDirEstablecimiento() {
            return dirEstablecimiento;
        }

        /**
         * Define el valor de la propiedad dirEstablecimiento.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDirEstablecimiento(String value) {
            this.dirEstablecimiento = value;
        }

        /**
         * Obtiene el valor de la propiedad contribuyenteEspecial.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContribuyenteEspecial() {
            return contribuyenteEspecial;
        }

        /**
         * Define el valor de la propiedad contribuyenteEspecial.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContribuyenteEspecial(String value) {
            this.contribuyenteEspecial = value;
        }

        /**
         * Obtiene el valor de la propiedad obligadoContabilidad.
         * 
         * @return
         *     possible object is
         *     {@link ObligadoContabilidad }
         *     
         */
        public ObligadoContabilidad getObligadoContabilidad() {
            return obligadoContabilidad;
        }

        /**
         * Define el valor de la propiedad obligadoContabilidad.
         * 
         * @param value
         *     allowed object is
         *     {@link ObligadoContabilidad }
         *     
         */
        public void setObligadoContabilidad(ObligadoContabilidad value) {
            this.obligadoContabilidad = value;
        }

        /**
         * Obtiene el valor de la propiedad tipoIdentificacionProveedor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoIdentificacionProveedor() {
            return tipoIdentificacionProveedor;
        }

        /**
         * Define el valor de la propiedad tipoIdentificacionProveedor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoIdentificacionProveedor(String value) {
            this.tipoIdentificacionProveedor = value;
        }

        /**
         * Obtiene el valor de la propiedad razonSocialProveedor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRazonSocialProveedor() {
            return razonSocialProveedor;
        }

        /**
         * Define el valor de la propiedad razonSocialProveedor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRazonSocialProveedor(String value) {
            this.razonSocialProveedor = value;
        }

        /**
         * Obtiene el valor de la propiedad identificacionProveedor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentificacionProveedor() {
            return identificacionProveedor;
        }

        /**
         * Define el valor de la propiedad identificacionProveedor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentificacionProveedor(String value) {
            this.identificacionProveedor = value;
        }

        /**
         * Obtiene el valor de la propiedad direccionProveedor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDireccionProveedor() {
            return direccionProveedor;
        }

        /**
         * Define el valor de la propiedad direccionProveedor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDireccionProveedor(String value) {
            this.direccionProveedor = value;
        }

        /**
         * Obtiene el valor de la propiedad totalSinImpuestos.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalSinImpuestos() {
            return totalSinImpuestos;
        }

        /**
         * Define el valor de la propiedad totalSinImpuestos.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalSinImpuestos(BigDecimal value) {
            this.totalSinImpuestos = value;
        }

        /**
         * Obtiene el valor de la propiedad totalDescuento.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalDescuento() {
            return totalDescuento;
        }

        /**
         * Define el valor de la propiedad totalDescuento.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalDescuento(BigDecimal value) {
            this.totalDescuento = value;
        }

        /**
         * Obtiene el valor de la propiedad codDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodDocReembolso() {
            return codDocReembolso;
        }

        /**
         * Define el valor de la propiedad codDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodDocReembolso(String value) {
            this.codDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad totalComprobantesReembolso.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalComprobantesReembolso() {
            return totalComprobantesReembolso;
        }

        /**
         * Define el valor de la propiedad totalComprobantesReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalComprobantesReembolso(BigDecimal value) {
            this.totalComprobantesReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad totalBaseImponibleReembolso.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalBaseImponibleReembolso() {
            return totalBaseImponibleReembolso;
        }

        /**
         * Define el valor de la propiedad totalBaseImponibleReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalBaseImponibleReembolso(BigDecimal value) {
            this.totalBaseImponibleReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad totalImpuestoReembolso.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalImpuestoReembolso() {
            return totalImpuestoReembolso;
        }

        /**
         * Define el valor de la propiedad totalImpuestoReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalImpuestoReembolso(BigDecimal value) {
            this.totalImpuestoReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad totalConImpuestos.
         * 
         * @return
         *     possible object is
         *     {@link LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos }
         *     
         */
        public LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos getTotalConImpuestos() {
            return totalConImpuestos;
        }

        /**
         * Define el valor de la propiedad totalConImpuestos.
         * 
         * @param value
         *     allowed object is
         *     {@link LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos }
         *     
         */
        public void setTotalConImpuestos(LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos value) {
            this.totalConImpuestos = value;
        }

        /**
         * Obtiene el valor de la propiedad importeTotal.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getImporteTotal() {
            return importeTotal;
        }

        /**
         * Define el valor de la propiedad importeTotal.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setImporteTotal(BigDecimal value) {
            this.importeTotal = value;
        }

        /**
         * Obtiene el valor de la propiedad moneda.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMoneda() {
            return moneda;
        }

        /**
         * Define el valor de la propiedad moneda.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMoneda(String value) {
            this.moneda = value;
        }

        /**
         * Obtiene el valor de la propiedad pagos.
         * 
         * @return
         *     possible object is
         *     {@link Pagos }
         *     
         */
        public Pagos getPagos() {
            return pagos;
        }

        /**
         * Define el valor de la propiedad pagos.
         * 
         * @param value
         *     allowed object is
         *     {@link Pagos }
         *     
         */
        public void setPagos(Pagos value) {
            this.pagos = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="totalImpuesto" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="codigo" type="{}codigo"/>
         *                   &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/>
         *                   &lt;element name="descuentoAdicional" type="{}descuentoAdicional" minOccurs="0"/>
         *                   &lt;element name="baseImponible" type="{}baseImponible"/>
         *                   &lt;element name="tarifa" type="{}tarifa" minOccurs="0"/>
         *                   &lt;element name="valor" type="{}valor"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "totalImpuesto"
        })
        public static class TotalConImpuestos {

            @XmlElement(required = true)
            protected List<LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto> totalImpuesto;

            /**
             * Gets the value of the totalImpuesto property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the totalImpuesto property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTotalImpuesto().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto }
             * 
             * 
             */
            public List<LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto> getTotalImpuesto() {
                if (totalImpuesto == null) {
                    totalImpuesto = new ArrayList<LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto>();
                }
                return this.totalImpuesto;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="codigo" type="{}codigo"/>
             *         &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/>
             *         &lt;element name="descuentoAdicional" type="{}descuentoAdicional" minOccurs="0"/>
             *         &lt;element name="baseImponible" type="{}baseImponible"/>
             *         &lt;element name="tarifa" type="{}tarifa" minOccurs="0"/>
             *         &lt;element name="valor" type="{}valor"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "codigo",
                "codigoPorcentaje",
                "descuentoAdicional",
                "baseImponible",
                "tarifa",
                "valor"
            })
            public static class TotalImpuesto {

                @XmlElement(required = true)
                protected String codigo;
                @XmlElement(required = true)
                protected String codigoPorcentaje;
                protected BigDecimal descuentoAdicional;
                @XmlElement(required = true)
                protected BigDecimal baseImponible;
                protected BigDecimal tarifa;
                @XmlElement(required = true)
                protected BigDecimal valor;

                /**
                 * Obtiene el valor de la propiedad codigo.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCodigo() {
                    return codigo;
                }

                /**
                 * Define el valor de la propiedad codigo.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCodigo(String value) {
                    this.codigo = value;
                }

                /**
                 * Obtiene el valor de la propiedad codigoPorcentaje.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCodigoPorcentaje() {
                    return codigoPorcentaje;
                }

                /**
                 * Define el valor de la propiedad codigoPorcentaje.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCodigoPorcentaje(String value) {
                    this.codigoPorcentaje = value;
                }

                /**
                 * Obtiene el valor de la propiedad descuentoAdicional.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getDescuentoAdicional() {
                    return descuentoAdicional;
                }

                /**
                 * Define el valor de la propiedad descuentoAdicional.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setDescuentoAdicional(BigDecimal value) {
                    this.descuentoAdicional = value;
                }

                /**
                 * Obtiene el valor de la propiedad baseImponible.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getBaseImponible() {
                    return baseImponible;
                }

                /**
                 * Define el valor de la propiedad baseImponible.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setBaseImponible(BigDecimal value) {
                    this.baseImponible = value;
                }

                /**
                 * Obtiene el valor de la propiedad tarifa.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getTarifa() {
                    return tarifa;
                }

                /**
                 * Define el valor de la propiedad tarifa.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setTarifa(BigDecimal value) {
                    this.tarifa = value;
                }

                /**
                 * Obtiene el valor de la propiedad valor.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getValor() {
                    return valor;
                }

                /**
                 * Define el valor de la propiedad valor.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setValor(BigDecimal value) {
                    this.valor = value;
                }

            }

        }

    }

}
