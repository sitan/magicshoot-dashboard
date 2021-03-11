package nl.magicshoot.dashboard.domain;

import java.io.Serializable;
import javax.persistence.*;
import nl.magicshoot.dashboard.domain.enumeration.MediaType;
import nl.magicshoot.dashboard.domain.enumeration.MediaUse;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MediaIn.
 */
@Entity
@Table(name = "media_in")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MediaIn implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_in_id")
    private Integer mediaInId;

    @Column(name = "media_in_name")
    private String mediaInName;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_use")
    private MediaUse mediaUse;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_in_type")
    private MediaType mediaInType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMediaInId() {
        return mediaInId;
    }

    public MediaIn mediaInId(Integer mediaInId) {
        this.mediaInId = mediaInId;
        return this;
    }

    public void setMediaInId(Integer mediaInId) {
        this.mediaInId = mediaInId;
    }

    public String getMediaInName() {
        return mediaInName;
    }

    public MediaIn mediaInName(String mediaInName) {
        this.mediaInName = mediaInName;
        return this;
    }

    public void setMediaInName(String mediaInName) {
        this.mediaInName = mediaInName;
    }

    public MediaUse getMediaUse() {
        return mediaUse;
    }

    public MediaIn mediaUse(MediaUse mediaUse) {
        this.mediaUse = mediaUse;
        return this;
    }

    public void setMediaUse(MediaUse mediaUse) {
        this.mediaUse = mediaUse;
    }

    public MediaType getMediaInType() {
        return mediaInType;
    }

    public MediaIn mediaInType(MediaType mediaInType) {
        this.mediaInType = mediaInType;
        return this;
    }

    public void setMediaInType(MediaType mediaInType) {
        this.mediaInType = mediaInType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaIn)) {
            return false;
        }
        return id != null && id.equals(((MediaIn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaIn{" +
            "id=" + getId() +
            ", mediaInId=" + getMediaInId() +
            ", mediaInName='" + getMediaInName() + "'" +
            ", mediaUse='" + getMediaUse() + "'" +
            ", mediaInType='" + getMediaInType() + "'" +
            "}";
    }
}
