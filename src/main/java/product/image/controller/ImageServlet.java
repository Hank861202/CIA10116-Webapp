package product.image.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import product.image.model.ImageService;
import product.image.model.ImageVO;

@WebServlet("/image/ImageServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ImageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// =====================新增========================
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = null;
			try {
				productId = Integer.valueOf(req.getParameter("productId").trim());
				if (productId == null) {
					errorMsgs.add("職位請勿空白");
				}
			} catch (NumberFormatException e) {
				productId = 0;
				errorMsgs.add("請填數字.");
			}

			Collection<Part> parts = req.getParts();

			ImageVO imageVO = new ImageVO();
			List<byte[]> images = new ArrayList<>();
			for (Part part : parts) {
				if (part.getSubmittedFileName()!=null && part.getSubmittedFileName()!= "" && part.getContentType()!=null) {
					InputStream fin = part.getInputStream();
					byte[] image = fin.readAllBytes();
					images.add(image);
					fin.close();
				}
			}
			if(images.size() == 0) {
				images.add(null); 	
			}
			
			imageVO.setProductId(productId);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("imageVO", imageVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/image/addImage.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/

			ImageService empSvc = new ImageService();
			empSvc.addImages(productId, images);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/image/listAllImage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		// =====================修改========================

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer imageId = Integer.valueOf(req.getParameter("imageId"));

			/*************************** 2.開始查詢資料 ****************************************/
			ImageService imageSvc = new ImageService();
			ImageVO imageVO = imageSvc.getOneImage(imageId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("imageVO", imageVO);
			String url = "/image/update_image_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer imageId = Integer.valueOf(req.getParameter("imageId"));

			Integer productId = null;

			try {
				productId = Integer.valueOf(req.getParameter("productId").trim());
				if (productId == null) {
					errorMsgs.add("職位請勿空白");
				}
			} catch (NumberFormatException e) {
				productId = 0;
				errorMsgs.add("請填數字.");
			}

			Collection<Part> parts = req.getParts();
			byte[] image = null;
			ImageVO imageVO = new ImageVO();
			for (Part part : parts) {
				if (part.getSubmittedFileName()!=null && part.getSubmittedFileName()!= "" && part.getContentType()!=null) {
					InputStream fin = part.getInputStream();
					image = fin.readAllBytes();
					imageVO.setProductId(productId);
					imageVO.setImage(image);
					fin.close();
				}
			}

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("imageVO", imageVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/image/update_image_input.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			ImageService imageSvc = new ImageService();
			imageVO = imageSvc.updateImage(imageId, productId, image);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("imageVO", imageVO);
			String url = "/image/listOneImage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		// =====================查詢========================

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("imageId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入圖片編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer imageId = null;
			try {
				imageId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("圖片編號格式不正確");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始查詢資料 *****************************************/
			ImageService imageSvc = new ImageService();
			ImageVO imageVO = imageSvc.getOneImage(imageId);
			if (imageVO == null) {
				errorMsgs.add("查無資料");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("imageVO", imageVO);
			String url = "/image/listOneImage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if ("getOneP_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("productId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入商品編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer productId = null;
			try {
				productId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("商品編號格式不正確");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始查詢資料 *****************************************/
			List<ImageVO> imageVOs = new ArrayList<>();
			ImageService imageSvc = new ImageService();
			imageVOs = imageSvc.getOneProduct(productId);
			if (imageVOs.size() == 0) {
				errorMsgs.add("查無資料");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("imageVOs", imageVOs);
			String url = "/image/listOneProduct.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		// =====================刪除========================

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer imageId = Integer.valueOf(req.getParameter("imageId"));

			/*************************** 2.開始刪除資料 ***************************************/
			ImageService imageSvc = new ImageService();
			imageSvc.deleteImage(imageId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/image/listAllImage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}

}
