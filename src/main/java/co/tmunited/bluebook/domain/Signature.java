package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Signature.
 */
@Entity
@Table(name = "signature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "signature")
public class Signature extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

//    @Column(name = "signature_tmp")
//    private String signatureTmp;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    @Column(name = "ip")
    private String ip;

    @Column(name = "date")
    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Signature signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public Signature signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public String getIp() {
        return ip;
    }

    public Signature ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Signature date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

//    public String getSignatureTmp() {
//        return signatureTmp;
//    }
//
//    public void setSignatureTmp(String signatureTmp) {
//        this.signatureTmp = signatureTmp;
//    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Signature signature = (Signature) o;
        if(signature.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, signature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Signature{" +
            "id=" + id +
            ", signature='" + signature + "'" +
//            ", signatureTmp='" + signatureTmp + "'" +
            ", signatureContentType='" + signatureContentType + "'" +
            ", ip='" + ip + "'" +
            ", date='" + date + "'" +
            '}';
    }

}
