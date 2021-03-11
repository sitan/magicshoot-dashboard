package nl.magicshoot.dashboard.domain;

import java.io.Serializable;
import javax.persistence.*;
import nl.magicshoot.dashboard.domain.enumeration.LabelType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Label.
 */
@Entity
@Table(name = "label")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Label implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label_id")
    private Integer labelId;

    @Column(name = "label_name")
    private String labelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "label_type")
    private LabelType labelType;

    @Column(name = "label_width")
    private Integer labelWidth;

    @Column(name = "label_height")
    private Integer labelHeight;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public Label labelId(Integer labelId) {
        this.labelId = labelId;
        return this;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public Label labelName(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public LabelType getLabelType() {
        return labelType;
    }

    public Label labelType(LabelType labelType) {
        this.labelType = labelType;
        return this;
    }

    public void setLabelType(LabelType labelType) {
        this.labelType = labelType;
    }

    public Integer getLabelWidth() {
        return labelWidth;
    }

    public Label labelWidth(Integer labelWidth) {
        this.labelWidth = labelWidth;
        return this;
    }

    public void setLabelWidth(Integer labelWidth) {
        this.labelWidth = labelWidth;
    }

    public Integer getLabelHeight() {
        return labelHeight;
    }

    public Label labelHeight(Integer labelHeight) {
        this.labelHeight = labelHeight;
        return this;
    }

    public void setLabelHeight(Integer labelHeight) {
        this.labelHeight = labelHeight;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Label)) {
            return false;
        }
        return id != null && id.equals(((Label) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Label{" +
            "id=" + getId() +
            ", labelId=" + getLabelId() +
            ", labelName='" + getLabelName() + "'" +
            ", labelType='" + getLabelType() + "'" +
            ", labelWidth=" + getLabelWidth() +
            ", labelHeight=" + getLabelHeight() +
            "}";
    }
}
