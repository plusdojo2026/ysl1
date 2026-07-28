<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>案件登録</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" href="<c:url value='/css/cases_regist.css' />">
</head>

<!--データはとれそうだけど、新規登録のような表示になりそう -->
<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
 	<main>
 	
 	<c:if test="${param.buttonId=='新規登録' }">
		<h2>案件登録</h2>
	</c:if>
	<c:if test="${param.buttonId=='編集' or param.buttonId=='案件編集'}">
		<h2>案件編集</h2>
	</c:if>
	
	<form action="/ysl1/Controller" method="post">
		<table class="table">
			<tr>
		        	<td class="label" name="caseName">案件名</td>
		        	<td>
		        	<input type="hidden"  name="id" value="${cases.id }">
		        	<input type="text"  name="caseName" value="${cases.caseName }">
		        	</td>
		   		
		
		   		 <td class="label">	案件コード</td>
		        	<td><input type="text"  name="caseCode" value="${cases.caseCode }"></td>
		    </tr>	
		    <tr>	
		    		
		    		<td class="label">顧客名</td>
		        	<td><input type="text" name="customerName"value="${cases.customerName }"></td>
		    		
		        	<td class="label">担当PM</td>
		        	<td><select name="pmId"value="${cases.pmId }>
		        	<c:forEach var="pm" items="${userList}">
								<option
									value="${pm.userId}"
									${cases.pmId == pm.userId ? 'selected' : ''}>

									${pm.userName}

								</option>
							</c:forEach>
						</select>
		        	</td>
		        	
		    </tr>
		    	
		    <tr>
		    
		    	<td class="label">案件ステータス</td>
			    	<td>
				    	<select name="status">
							    
							    <option value="進行中" ${cases.caseStatus=='進行中' ? 'selected' : ''}>進行中</option>
							    <option value="完了" ${cases.caseStatus=='完了' ? 'selected' : ''}>完了</option>
							    <option value="未着手" ${cases.caseStatus=='未着手' ? 'selected' : ''}>未着手</option>
							    <option value="中止" ${cases.caseStatus=='中止' ? 'selected' : ''}>中止</option>
						 </select>
			    	</td>
		    	
		    	<td class="label">案件優先度</td>
			    	<td>
				    	<select name="priority">
							   
							    <option value="高"
							    ${cases.casePriority=='高' ? 'selected' : ''}>
							    高
							    </option>
							    <option value="中"
							    ${empty cases.casePriority || cases.casePriority=='中' ? 'selected' : ''}>
							    中
							    </option>
							    <option value="低"
							    ${cases.casePriority=='低' ? 'selected' : ''}>
							    低
							    </option>
					    </select>
			    	</td>
			    	
		    	</tr>
		    	
			    	<tr>
				    	<td class="label">開始日</td>
				    	<td>
				    	<input type="date" name="startDate" value="${cases.startDate.substring(0,10) }" required>
				    	</td>
				    	
				    	<td class="label">終了予定日</td>
				    	<td>
				    	<input type="date" name="plannedEndDate" value="${cases.plannedEndDate.substring(0,10) }"required>
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
		    		
		    		<textarea name="description"  rows="6"cols="80">${cases.caseDescription }</textarea>
		    		
		    		</td>
		    	</tr>
		</table>
			
		<div class="buttonArea">
			<input type="hidden"name="pageId"value="C002">	
		   	<input type="button" id="cancel" value="キャンセル"onclick="history.back()">
		   	<c:if test="${param.buttonId=='新規登録' }">
				<input type="submit" id="regist" name=buttonId value="登録">
			</c:if>
			<c:if test="${param.buttonId=='編集'or param.buttonId=='案件編集' }">
				<input type="submit" id="edit" name=buttonId value="編集">
			</c:if>
		</div>
	</form>
	
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
	</main>
</body>
</html>