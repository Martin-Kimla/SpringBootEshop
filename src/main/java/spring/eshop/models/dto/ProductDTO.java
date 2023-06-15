package spring.eshop.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    private long productId;

    @NotBlank(message = "Vyplňte název")
    @NotNull(message = "Vyplňte název")
    private String nazev;

    @NotBlank(message = "Vyplňte popis")
    @NotNull(message = "Vyplňte popis")
    private String popis;

    @NotBlank(message = "Vyplňte cenu")
    @NotNull(message = "Vyplňte cenu")
    private String cena;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }
}
