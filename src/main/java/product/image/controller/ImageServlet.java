package product.image.controller;

import java.io.IOException;
import java.io.InputStream;
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
			byte[] image = null;
			for (Part part : parts) {

				if (part.getSubmittedFileName() != null && part.getContentType() != null) {

					InputStream fin = part.getInputStream();
					image = fin.readAllBytes();
					imageVO.setProductId(productId);
					imageVO.setImage(image);

					fin.close();
				}
			}
			
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("imageVO", imageVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/image/addImage.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/

			ImageService empSvc = new ImageService();
			imageVO = empSvc.addImage(productId, image);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/image/listAllImage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
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
			ImageVO imageVO = imageSvc.getOneEmp(imageId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("imageVO", imageVO); 
			String url = "/image/update_image_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

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

			ImageVO imageVO = new ImageVO();
			byte[] image = null;
			for (Part part : parts) {

				if (part.getSubmittedFileName() != null && part.getContentType() != null) {

					InputStream fin = part.getInputStream();
					image = fin.readAllBytes();
					imageVO.setImageId(imageId);
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
			req.setAttribute("imageVO", imageVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/image/listOneImage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
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
				errorMsgs.add("員工編號格式不正確");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/image/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始查詢資料 *****************************************/
			ImageService imageSvc = new ImageService();
			ImageVO imageVO = imageSvc.getOneEmp(imageId);
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
