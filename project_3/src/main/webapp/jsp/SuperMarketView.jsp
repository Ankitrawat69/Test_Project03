<%@page import="in.co.rays.project_3.controller.SuperMarketCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Super Market View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>
</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.SUPERMARKET_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.SuperMarketDTO"
			scope="request" />

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Product</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Product</h3>
						<%
							}
						%>

						<!-- Messages -->
						<h4 align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success">
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<h4 align="center">
							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger">
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Hidden Fields -->
						<input type="hidden" name="id" value="<%=dto.getId()%>">
						<input type="hidden" name="createdBy"
							value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>">
						<input type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<!-- Product Name -->
						<label><b>Product Name</b><span style="color:red">*</span></label>
						<input type="text" name="productName" class="form-control"
							placeholder="Enter Product Name"
							value="<%=DataUtility.getStringData(dto.getProductName())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("productName", request)%>
						</font>
						<br>

						<!-- Quantity -->
						<label><b>Quantity</b><span style="color:red">*</span></label>
						<input type="text" name="quantity" class="form-control"
							placeholder="Enter Quantity"
							value="<%=DataUtility.getStringData(dto.getQuantity())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("quantity", request)%>
						</font>
						<br>

						<!-- Available -->
						<label><b>Available</b><span style="color:red">*</span></label>
						<input type="text" name="available" class="form-control"
							placeholder="Yes / No"
							value="<%=DataUtility.getStringData(dto.getAvailable())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("available", request)%>
						</font>
						<br>

						<!-- Price -->
						<label><b>Price</b><span style="color:red">*</span></label>
						<input type="text" name="price" class="form-control"
							placeholder="Enter Price"
							value="<%=DataUtility.getStringData(dto.getPrice())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("price", request)%>
						</font>
						<br><br>

						<!-- Buttons -->
						<div class="text-center">
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=SuperMarketCtl.OP_UPDATE%>">
							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=SuperMarketCtl.OP_CANCEL%>">
							<%
								} else {
							%>
							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=SuperMarketCtl.OP_SAVE%>">
							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=SuperMarketCtl.OP_RESET%>">
							<%
								}
							%>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>
	</main>

	<%@include file="FooterView.jsp"%>

</body>
</html>
