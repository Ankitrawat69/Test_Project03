<%@page import="in.co.rays.project_3.dto.SuperMarketDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.SuperMarketListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Super Market List</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="hm">

	<div>
		<%@include file="Header.jsp"%>
	</div>

	<form action="<%=ORSView.SUPERMARKET_LIST_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.SuperMarketDTO"
			scope="request" />

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			List list = ServletUtility.getList(request);
			Iterator<SuperMarketDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-primary font-weight-bold pt-3">Super Market List</h1>
		</center>
		<br>

		<!-- Messages -->
		<div class="row">
			<div class="col-md-4"></div>

			<%
				if (!ServletUtility.getSuccessMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-success">
				<%=ServletUtility.getSuccessMessage(request)%>
			</div>
			<%
				}
			%>

			<%
				if (!ServletUtility.getErrorMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-danger">
				<%=ServletUtility.getErrorMessage(request)%>
			</div>
			<%
				}
			%>

			<div class="col-md-4"></div>
		</div>

		<!-- Search -->
		<div class="row">
			<div class="col-sm-3"></div>

			<div class="col-sm-3">
				<input type="text" name="productName" class="form-control"
					placeholder="Product Name"
					value="<%=DataUtility.getStringData(dto.getProductName())%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="available" class="form-control"
					placeholder="Available"
					value="<%=DataUtility.getStringData(dto.getAvailable())%>">
			</div>

			<div class="col-sm-2">
				<input type="submit" name="operation"
					class="btn btn-primary"
					value="<%=SuperMarketListCtl.OP_SEARCH%>">
				<input type="submit" name="operation"
					class="btn btn-dark"
					value="<%=SuperMarketListCtl.OP_RESET%>">
			</div>

			<div class="col-sm-2"></div>
		</div>

		<br>

		<!-- Table -->
		<div class="table-responsive">
			<table class="table table-dark table-bordered table-hover">
				<thead>
					<tr style="background-color: #8C8C8C;">
						<th width="10%">
							<input type="checkbox" id="select_all"> Select All
						</th>
						<th>S.No</th>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Available</th>
						<th>Price</th>
						<th>Edit</th>
					</tr>
				</thead>

				<tbody>
					<%
						while (it.hasNext()) {
							dto = it.next();
					%>
					<tr>
						<td align="center">
							<input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>">
						</td>
						<td align="center"><%=index++%></td>
						<td align="center"><%=dto.getProductName()%></td>
						<td align="center"><%=dto.getQuantity()%></td>
						<td align="center"><%=dto.getAvailable()%></td>
						<td align="center"><%=dto.getPrice()%></td>
						<td align="center">
							<a href="SuperMarketCtl?id=<%=dto.getId()%>">Edit</a>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>

		<!-- Buttons -->
		<table width="100%">
			<tr>
				<td>
					<input type="submit" name="operation"
						class="btn btn-secondary"
						value="<%=SuperMarketListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>>
				</td>

				<td>
					<input type="submit" name="operation"
						class="btn btn-primary"
						value="<%=SuperMarketListCtl.OP_NEW%>">
				</td>

				<td>
					<input type="submit" name="operation"
						class="btn btn-danger"
						value="<%=SuperMarketListCtl.OP_DELETE%>">
				</td>

				<td align="right">
					<input type="submit" name="operation"
						class="btn btn-secondary"
						value="<%=SuperMarketListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>>
				</td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>">
		<input type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>
