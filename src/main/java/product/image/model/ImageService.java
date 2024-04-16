package product.image.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ImageService {
	private ImageDAO_interface dao;

	public ImageService() {
		dao = new ImageJDBCDAO();
	}

	public List<ImageVO> addImages(Integer productId, List<byte[]> images) {
	    if (productId == null) {
	        throw new IllegalArgumentException("productId 不能為空");
	    }

	    List<ImageVO> imageVOs = new ArrayList<>();

	    for (byte[] image : images) {
	        ImageVO imageVO = new ImageVO();
	        imageVO.setProductId(productId);
	        imageVO.setImage(image);
	        dao.insert(imageVO);
	        imageVOs.add(imageVO);
	    }

	    return imageVOs;
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

	public ImageVO getOneImage(Integer imageId) {
		return dao.findByPrimaryKey(imageId);
	}
	
	public List<ImageVO> getOneProduct(Integer productId) {
		return dao.findByProductId(productId);
	}

	public List<ImageVO> getAll() {
		return dao.getAll();
	}
}
