package ru.armishev.intellekta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="sales_period")
@NoArgsConstructor
@Getter
@Setter
public class SalesPeriodJpaDemo {
    @Id
    /*@Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_period_id_seq")
    @SequenceGenerator(name="sales_period_id_seq", sequenceName = "sales_period_id_seq", allocationSize = 1)*/
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "price")
    private long price;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @OneToOne
    @JoinColumn(name="product", referencedColumnName = "id", nullable = false)
    private Product product;

    public SalesPeriodJpaDemo(long id, long price, Date dateFrom, Date dateTo, Product product) {
        this.id = id;
        this.price = price;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesPeriodJpaDemo that = (SalesPeriodJpaDemo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SalesPeriodJpaDemo{" +
                "id=" + id +
                ", price=" + price +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", product=" + product +
                '}';
    }
}
