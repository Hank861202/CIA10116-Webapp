package product.image.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "general_product_image")	
public class ImageVO implements Serializable {
	@Id
	@GeneratedValue()
	@Column(name = "image_id", updatable = false)
	private Integer imageId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "image", columnDefinition = "mediumblob")
	private byte[] image;

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
