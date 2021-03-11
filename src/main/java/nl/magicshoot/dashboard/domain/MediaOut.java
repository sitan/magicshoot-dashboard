package nl.magicshoot.dashboard.domain;

import java.io.Serializable;
import javax.persistence.*;
import nl.magicshoot.dashboard.domain.enumeration.MediaType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MediaOut.
 */
@Entity
@Table(name = "media_out")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MediaOut implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_out_id")
    private Integer mediaOutId;

    @Column(name = "media_out_name")
    private String mediaOutName;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_out_type")
    private MediaType mediaOutType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMediaOutId() {
        return mediaOutId;
    }

    public MediaOut mediaOutId(Integer mediaOutId) {
        this.mediaOutId = mediaOutId;
        return this;
    }

    public void setMediaOutId(Integer mediaOutId) {
        this.mediaOutId = mediaOutId;
    }

    public String getMediaOutName() {
        return mediaOutName;
    }

    public MediaOut mediaOutName(String mediaOutName) {
        this.mediaOutName = mediaOutName;
        return this;
    }

    public void setMediaOutName(String mediaOutName) {
        this.mediaOutName = mediaOutName;
    }

    public MediaType getMediaOutType() {
        return mediaOutType;
    }

    public MediaOut mediaOutType(MediaType mediaOutType) {
        this.mediaOutType = mediaOutType;
        return this;
    }

    public void setMediaOutType(MediaType mediaOutType) {
        this.mediaOutType = mediaOutType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaOut)) {
            return false;
        }
        return id != null && id.equals(((MediaOut) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaOut{" +
            "id=" + getId() +
            ", mediaOutId=" + getMediaOutId() +
            ", mediaOutName='" + getMediaOutName() + "'" +
            ", mediaOutType='" + getMediaOutType() + "'" +
            "}";
    }
}
