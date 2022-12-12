package wodrich.rowena.iapps.fileuploader.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

@Component
public class XMLFileValidator implements FileValidator {

    Logger logger = LoggerFactory.getLogger(XMLFileValidator.class);

    public final static String EPAPER_REQUEST_XSD_SCHEMA_PATH = "xsdSchemas/epaperRequest.xsd";

    public XMLFileValidator() {}

    public boolean isValidAgainstSchema(MultipartFile file, String schemaPathPart) {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // this way it works also within the .jar file
        try (InputStream in = new ClassPathResource(schemaPathPart).getInputStream()) {
            Source source = new StreamSource(in);
            Schema schema = factory.newSchema(source);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file.getInputStream()));
        } catch (SAXException | IOException e) {
            logger.error("an error occurred while validating the xml file: " + e.getMessage());
            return false;
        }
        return true;
    }
}
