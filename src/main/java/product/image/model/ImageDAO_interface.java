package product.image.model;

import java.util.List;

public interface ImageDAO_interface {
	public void insert(ImageVO imageVO);
	public void update(ImageVO imageVO);
	public void delete(Integer imageId);
	public ImageVO findByPrimaryKey(Integer imageId);
	public List<ImageVO> getAll();
}
