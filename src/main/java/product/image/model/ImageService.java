package product.image.model;

import java.util.List;

public class ImageService {
	private ImageDAO_interface dao;

	public ImageService() {
		dao = new ImageJDBCDAO();
	}

	public ImageVO addImage(Integer productId, byte[] image) {

		if (productId == null) {
			throw new IllegalArgumentException("productId 不能為空");
		}

		ImageVO imageVO = new ImageVO();

		imageVO.setProductId(productId);
		imageVO.setImage(image);
		dao.insert(imageVO);

		return imageVO;
	}

	public ImageVO updateImage(Integer imageId, Integer productId, byte[] image) {

		if (productId == null ) {
			throw new IllegalArgumentException("productId 不能為空");
		}

		ImageVO imageVO = new ImageVO();
		
		imageVO.setImageId(imageId);
		imageVO.setProductId(productId);
		imageVO.setImage(image);
		dao.update(imageVO);

		return imageVO;
	}

	public void deleteImage(Integer imageId) {
		dao.delete(imageId);
	}

	public ImageVO getOneEmp(Integer imageId) {
		return dao.findByPrimaryKey(imageId);
	}

	public List<ImageVO> getAll() {
		return dao.getAll();
	}
}
