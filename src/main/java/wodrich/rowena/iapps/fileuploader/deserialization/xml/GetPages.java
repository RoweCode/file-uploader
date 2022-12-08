package wodrich.rowena.iapps.fileuploader.deserialization.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDate;
import java.util.Date;

public class GetPages {

    @JacksonXmlProperty(isAttribute = true)
    private Integer editionDefId;
    @JacksonXmlProperty(isAttribute = true)
    private Date publicationDate;

    public GetPages() {}

    public Integer getEditionDefId() {
        return editionDefId;
    }

    public void setEditionDefId(Integer editionDefId) {
        this.editionDefId = editionDefId;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
