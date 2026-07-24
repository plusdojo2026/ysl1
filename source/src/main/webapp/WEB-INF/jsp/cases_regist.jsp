<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>案件登録</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>

<!--まだ、新規登録しかできません（まだ、データは持ってきていない状態） -->
<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
 	
	<h2>案件登録</h2>
	
	<form action="/ysl1/ControllerServlet" method="post">
		<table>
			<tr>
		        	<td class="label" name="caseName">案件名</td>
		        	<td><input type="text"  name="caseName" value="${cases.caseName }"></td>
		   		
		
		   		 <td class="label">	案件コード</td>
		        	<td><input type="text"  name="caseCode" value="${cases.caseCode }"></td>
		    </tr>	
		    <tr>	
		    		
		    		<td class="label">顧客名</td>
		        	<td><input type="text" name="customerName"value="${cases.caseName }"></td>
		    		
		        	<td class="label">担当PM</td>
		        	<td><select name="pmId">
		        	<c:forEach var="pm" items="${pmList}">
								<option
									value="${pm.userId}"
									${casesList.pmId == pm.userId ? 'selected' : ''}>

									${pmList.userName}

								</option>
							</c:forEach>
						</select>
		        	</td>
		        	
		    </tr>
		    	
		    <tr>
		    
		    	<td class="label">案件ステータス</td>
			    	<td>
				    	<select name="status">
							    
							    <option value="進行中">進行中</option>
							    <option value="完了">完了</option>
							    <option value="中止">中止</option>
						 </select>
			    	</td>
		    	
		    	<td class="label">案件優先度</td>
			    	<td>
				    	<select name="priority">
							   
							    <option value="高">高</option>
							    <option value="中">中</option>
							    <option value="低">低</option>
					    </select>
			    	</td>
			    	
		    	</tr>
		    	
			    	<tr>
				    	<td class="label">開始日</td>
				    	<td>
				    	<input type="date"name="startDate" value="${cases.startDate }"required>
				    	</td>
				    	
				    	<td class="label">終了予定日</td>
				    	<td>
				    	<input type="date" name="plannedEndDate" value="${cases.plannedEndDate }"required>
			    	</td>
		    	
		    	</tr>
		    	
		    	<tr>
		    	
			    	<td class="label">予定工数(h)</td>
			    	<td colspan="3">
			    	<input type="number" name="casePlannedHours" step="0.5" value="${cases.casePlannedHours }"required>
			    	</td>
		    	
		    	</tr>
		    	
		    	<tr>
		    		<td colspan="4">
		    		<label>案件説明</label><br>
		    		
		    		<textarea name="description" value="${cases.caseDescription }" rows="6"cols="80"></textarea>
		    		
		    		</td>
		    	</tr>
		</table>
			
		<div class="buttonArea">
				
		   	<input type="button"value="キャンセル"onclick="history.back()">
		   		
		   	<input type="submit"value="登録">
		</div>

	</form>
	
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>