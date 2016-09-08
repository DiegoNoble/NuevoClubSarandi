
package smsmasivos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the javaapplication9 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _String_QNAME = new QName("http://servicio.smsmasivos.com.ar/ws/", "string");
    private final static QName _ArrayOfClsRespuesta_QNAME = new QName("http://servicio.smsmasivos.com.ar/ws/", "ArrayOfClsRespuesta");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: javaapplication9
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfClsRespuesta }
     * 
     */
    public ArrayOfClsRespuesta createArrayOfClsRespuesta() {
        return new ArrayOfClsRespuesta();
    }

    /**
     * Create an instance of {@link EnviarSMS }
     * 
     */
    public EnviarSMS createEnviarSMS() {
        return new EnviarSMS();
    }

    /**
     * Create an instance of {@link RecibirSMS }
     * 
     */
    public RecibirSMS createRecibirSMS() {
        return new RecibirSMS();
    }

    /**
     * Create an instance of {@link EnviarSMSResponse }
     * 
     */
    public EnviarSMSResponse createEnviarSMSResponse() {
        return new EnviarSMSResponse();
    }

    /**
     * Create an instance of {@link RecibirSMSResponse }
     * 
     */
    public RecibirSMSResponse createRecibirSMSResponse() {
        return new RecibirSMSResponse();
    }

    /**
     * Create an instance of {@link ClsRespuesta }
     * 
     */
    public ClsRespuesta createClsRespuesta() {
        return new ClsRespuesta();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.smsmasivos.com.ar/ws/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfClsRespuesta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.smsmasivos.com.ar/ws/", name = "ArrayOfClsRespuesta")
    public JAXBElement<ArrayOfClsRespuesta> createArrayOfClsRespuesta(ArrayOfClsRespuesta value) {
        return new JAXBElement<ArrayOfClsRespuesta>(_ArrayOfClsRespuesta_QNAME, ArrayOfClsRespuesta.class, null, value);
    }

}
