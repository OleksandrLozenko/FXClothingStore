package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private User user;
    
    @OneToOne
    private Cloth cloth;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date giveClothToReaderDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date returnCloth;
    
    private double price;

    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    public Date getGiveClothToReaderDate() {
        return giveClothToReaderDate;
    }

    public void setGiveClothToReaderDate(Date giveClothToReaderDate) {
        this.giveClothToReaderDate = giveClothToReaderDate;
    }

    public Date getReturnCloth() {
        return returnCloth;
    }

    public void setReturnCloth(Date returnCloth) {
        this.returnCloth = returnCloth;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.user);
        hash = 47 * hash + Objects.hashCode(this.cloth);
        hash = 47 * hash + Objects.hashCode(this.giveClothToReaderDate);
        hash = 47 * hash + Objects.hashCode(this.returnCloth);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final History other = (History) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.cloth, other.cloth)) {
            return false;
        }
        if (!Objects.equals(this.giveClothToReaderDate, other.giveClothToReaderDate)) {
            return false;
        }
        if (!Objects.equals(this.returnCloth, other.returnCloth)) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "History{" + "id=" + id + ", user=" + user.getFirstname() + " " + user.getLastname() + ", cloth=" + cloth.getTitle() + ", giveClothToReaderDate=" + giveClothToReaderDate + ", returnCloth=" + returnCloth + ", price=" + price + '}';
    }

    public StringProperty giveUpDateProperty() {
        return new SimpleStringProperty(this.giveClothToReaderDate.toString());
    }

    public StringProperty idProperty() {
        return new SimpleStringProperty(String.valueOf(id));
    }
}
