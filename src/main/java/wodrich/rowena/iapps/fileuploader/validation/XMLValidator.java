package wodrich.rowena.iapps.fileuploader.validation;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

@Component
public class XMLValidator {

    public final static String EPAPER_REQUEST_XSD_SCHEMA_PATH = "xsdSchemas/epaperRequest.xsd";

    public XMLValidator() {}

    public boolean isValidAgainstXSDSchema(MultipartFile file, String xsdSchemaPath) {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            File xsd = new ClassPathResource(xsdSchemaPath).getFile();
            Schema schema = factory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file.getInputStream()));
        } catch (SAXException | IOException e) {
            System.out.println("e: " + e.getMessage());
            return false;
        }
        return true;
    }
}
