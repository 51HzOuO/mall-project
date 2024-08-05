package mall.bean;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

public class Furn {
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Company cannot be empty")
    private String company;

    @Range(min = 0, max = 100000, message = "Price must be between 0 and 100000")
    private BigDecimal price;

    private Integer sales;

    @Range(min = 0, max = 100000, message = "Stock must be between 0 and 100000")
    private Integer stock;

    @NotBlank(message = "ImgPath cannot be empty")
    private String imgPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }
}
