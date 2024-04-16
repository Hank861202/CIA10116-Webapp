package product.image.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageJDBCDAO implements ImageDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/udemytestdb?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "asd883963";

	private static final String INSERT_STMT = "INSERT INTO general_product_image (product_id,image) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT image_id,product_id,image FROM general_product_image order by image_id";
	private static final String GET_ONE_STMT = "SELECT image_id,product_id,image FROM general_product_image where image_id = ?";
	private static final String GET_PRODUCT_STMT = "SELECT image_id,product_id,image FROM general_product_image where product_id = ?";
	private static final String DELETE = "DELETE FROM general_product_image where image_id = ?";
	private static final String UPDATE = "UPDATE general_product_image set product_id=?, image=? where image_id = ?";

	@Override
	public void insert(ImageVO imageVO) {
		try {
			Class.forName(driver);
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					PreparedStatement ps = con.prepareStatement(INSERT_STMT)) {

				ps.setInt(1, imageVO.getProductId());
				ps.setBytes(2, imageVO.getImage());

				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
	}

	@Override
	public void update(ImageVO imageVO) {
		try {
			Class.forName(driver);
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					PreparedStatement ps = con.prepareStatement(UPDATE);) {

				ps.setInt(1, imageVO.getProductId());
				ps.setBytes(2, imageVO.getImage());
				ps.setInt(3, imageVO.getImageId());

				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
	}

	@Override
	public void delete(Integer imageId) {
		try {
			Class.forName(driver);
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					PreparedStatement ps = con.prepareStatement(DELETE)) {

				ps.setInt(1, imageId);

				ps.executeUpdate();

			}
		} catch (Exception e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
	}

	@Override
	public ImageVO findByPrimaryKey(Integer ImageId) {

		ImageVO imageVO = null;
		try {
			Class.forName(driver);
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					PreparedStatement ps = con.prepareStatement(GET_ONE_STMT)) {

				ps.setInt(1, ImageId);

				try (ResultSet rs = ps.executeQuery()) {

					if (rs.next()) {
						imageVO = new ImageVO();
						imageVO.setImageId(rs.getInt("image_id"));
						imageVO.setProductId(rs.getInt("product_id"));
						imageVO.setImage(rs.getBytes("image"));
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
		return imageVO;
	}
	
	@Override
	public List<ImageVO> findByProductId(Integer productId) {
		List<ImageVO> list = new ArrayList<ImageVO>();
		ImageVO imageVO = null;
		try {
			Class.forName(driver);
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					PreparedStatement ps = con.prepareStatement(GET_PRODUCT_STMT)) {

				ps.setInt(1, productId);

				try (ResultSet rs = ps.executeQuery()) {
					
					while (rs.next()) {
						imageVO = new ImageVO();
						imageVO.setImageId(rs.getInt("image_id"));
						imageVO.setProductId(rs.getInt("product_id"));
						imageVO.setImage(rs.getBytes("image"));

						list.add(imageVO);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
		return list;
	}

	@Override
	public List<ImageVO> getAll() {

		List<ImageVO> list = new ArrayList<ImageVO>();
		ImageVO imageVO = null;
		try {
			Class.forName(driver);
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					PreparedStatement ps = con.prepareStatement(GET_ALL_STMT);
					ResultSet rs = ps.executeQuery();) {

				while (rs.next()) {
					imageVO = new ImageVO();
					imageVO.setImageId(rs.getInt("image_id"));
					imageVO.setProductId(rs.getInt("product_id"));
					imageVO.setImage(rs.getBytes("image"));

					list.add(imageVO);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}
		return list;
	}
}
