/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author Oleksandr
 */
@Entity
public class Cloth implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private byte[] cover;
    int price;
    private int quantityOfGoodsPurchased;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Cloth() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public int getQuantityOfGoodsPurchased() {
        return quantityOfGoodsPurchased;
    }

    public void setQuantityOfGoodsPurchased(int quantityOfGoodsPurchased) {
        this.quantityOfGoodsPurchased = quantityOfGoodsPurchased;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Arrays.hashCode(this.cover);
        hash = 47 * hash + this.price;
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
        final Cloth other = (Cloth) obj;
        if (this.price != other.price) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Arrays.equals(this.cover, other.cover)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cloth{" + "id=" + id + ", title=" + title + ", cover=" + cover + ", price=" + price + ", quantityOfGoodsPurchased=" + quantityOfGoodsPurchased + '}';
    }
    
    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(id));
    }
    public StringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }
    public InputStream getCoverAsStream(){
        return new ByteArrayInputStream(this.cover);
    }
}

