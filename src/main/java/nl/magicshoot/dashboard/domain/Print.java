package nl.magicshoot.dashboard.domain;

import java.io.Serializable;
import javax.persistence.*;
import nl.magicshoot.dashboard.domain.enumeration.PrintType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Print.
 */
@Entity
@Table(name = "print")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Print implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "print_id")
    private Integer printId;

    @Column(name = "print_name")
    private String printName;

    @Enumerated(EnumType.STRING)
    @Column(name = "print_type")
    private PrintType printType;

    @Column(name = "print_width")
    private Integer printWidth;

    @Column(name = "print_height")
    private Integer printHeight;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrintId() {
        return printId;
    }

    public Print printId(Integer printId) {
        this.printId = printId;
        return this;
    }

    public void setPrintId(Integer printId) {
        this.printId = printId;
    }

    public String getPrintName() {
        return printName;
    }

    public Print printName(String printName) {
        this.printName = printName;
        return this;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public PrintType getPrintType() {
        return printType;
    }

    public Print printType(PrintType printType) {
        this.printType = printType;
        return this;
    }

    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }

    public Integer getPrintWidth() {
        return printWidth;
    }

    public Print printWidth(Integer printWidth) {
        this.printWidth = printWidth;
        return this;
    }

    public void setPrintWidth(Integer printWidth) {
        this.printWidth = printWidth;
    }

    public Integer getPrintHeight() {
        return printHeight;
    }

    public Print printHeight(Integer printHeight) {
        this.printHeight = printHeight;
        return this;
    }

    public void setPrintHeight(Integer printHeight) {
        this.printHeight = printHeight;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Print)) {
            return false;
        }
        return id != null && id.equals(((Print) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Print{" +
            "id=" + getId() +
            ", printId=" + getPrintId() +
            ", printName='" + getPrintName() + "'" +
            ", printType='" + getPrintType() + "'" +
            ", printWidth=" + getPrintWidth() +
            ", printHeight=" + getPrintHeight() +
            "}";
    }
}
