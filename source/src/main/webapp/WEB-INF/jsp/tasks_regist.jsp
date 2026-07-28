<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>

	<meta charset="UTF-8">

	<title>
		<c:choose>

			<c:when test="${mode == 'edit'}">
				タスク編集
			</c:when>

			<c:otherwise>
				タスク新規登録
			</c:otherwise>

		</c:choose>
	</title>

	<link rel="stylesheet"
		href="<c:url value='/css/common.css'/>">

	<link rel="stylesheet"
		href="<c:url value='/css/tasks_regist.css'/>">

</head>

<body>

<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp"%>

<div class="container">

	<main class="main">

		<h2>
			<c:choose>

				<c:when test="${mode=='edit'}">
					タスク編集
				</c:when>

				<c:otherwise>
					タスク新規登録
				</c:otherwise>

			</c:choose>
		</h2>

		<form action="${pageContext.request.contextPath}/Controller" method="post">

			<input
				type="hidden"
				name="pageId"
				value="T002">

			<input
				type="hidden"
				name="taskId"
				value="${taskList.id}">

		<!-- タスク名（大きく表示） -->

			<div class="task-name-area">
				<label>
					タスク名
					<span class="required">必須</span>
				</label>

				<input
					class="task-name-input"
					type="text"
					name="taskName"
					maxlength="50"
					required
					value="${taskList.taskName}">

			</div>

		<!-- メインカード -->

		<div class="task-card">

		<!-- 左側 -->

		<div class="left-column">

		<div class="form-group">

		<label>

		案件

		<span class="required">

		必須

		</span>

		</label>

		<select name="caseId">

		<c:forEach
			var="cases"
			items="${casesList}">

		<c:choose>

		<c:when test="${mode=='edit'}">

		<option
			value="${cases.id}"
			${cases.id==taskList.caseId?'selected':''}>

		${cases.caseName}

		</option>

		</c:when>

		<c:otherwise>

		<option
			value="${cases.id}"
			${cases.id==caseId?'selected':''}>

		${cases.caseName}

		</option>

		</c:otherwise>

		</c:choose>

		</c:forEach>

		</select>

		</div>

		<div class="form-group">

		<label>

		担当者

		</label>

		<select name="managerId">

		<c:forEach
			var="pm"
			items="${pmList}">

		<option
			value="${pm.userId}"
			${taskList.managerId==pm.userId?'selected':''}>

		${pm.userName}

		</option>

		</c:forEach>

		</select>

		</div>

		<div class="form-group">

		<label>

		優先度

		<span class="required">

		必須

		</span>

		</label>

		<select
			name="taskPriority"
			required>

		<option
			value="高"
			${taskList.taskPriority=='高'?'selected':''}>

		高

		</option>

		<option
			value="中"
			${empty taskList.taskPriority || taskList.taskPriority=='中'?'selected':''}>

		中

		</option>

		<option
			value="低"
			${taskList.taskPriority=='低'?'selected':''}>

		低

		</option>

		</select>

		</div>

		<div class="form-group">

		<label>

		見積工数（h）

		</label>

		<input
			type="number"
			name="taskPlannedHours"
			min="0"
			step="0.5"
			value="${taskList.taskPlannedHours}">

		</div>

		<div class="form-group">

		<label>

		開始日

		</label>

		<input
			type="date"
			name="startDate"
			value="${fn:substring(taskList.startDate,0,10)}">

		</div>

		<div class="form-group">

		<label>

		期限

		</label>

		<input
			type="date"
			name="deadline"
			value="${fn:substring(taskList.deadline,0,10)}">

		</div>

		</div>
		<!-- =========================
			右側
		========================= -->

		<div class="right-column">

			<!-- 進捗率 -->
			<div class="form-group progress-group">

				<label>進捗率</label>

				<div class="slider-area">

					<input
						type="range"
						id="progressRate"
						name="progressRate"
						min="0"
						max="100"
						step="5"
						value="${empty taskList.progressRate ? 0 : taskList.progressRate}">

					<span id="progressValue"></span>

				</div>

			</div>

			<!-- ステータス -->
			<div class="form-group">

				<label>

					ステータス

					<span class="required">必須</span>

				</label>

				<select
					name="taskStatus"
					required>

					<option
						value="未着手"
						${empty taskList.taskStatus || taskList.taskStatus=='未着手' ? 'selected' : ''}>

						未着手

					</option>

					<option
						value="進行中"
						${taskList.taskStatus=='進行中' ? 'selected' : ''}>

						進行中

					</option>

					<option
						value="保留"
						${taskList.taskStatus=='保留' ? 'selected' : ''}>

						保留

					</option>

					<option
						value="完了"
						${taskList.taskStatus=='完了' ? 'selected' : ''}>

						完了

					</option>

				</select>

			</div>

			<!-- 説明 -->
			<div class="form-group description-group">

				<label>説明</label>

				<textarea
					name="taskDescription"
					maxlength="1000"
					rows="12">${taskList.taskDescription}</textarea>

			</div>

		</div>

		<!-- task-card -->
		</div>

		<!-- =========================
			ボタン
		========================= -->

		<div class="button-area">

			<button
				type="reset">

				リセット

			</button>

			<c:choose>

				<c:when test="${mode=='edit'}">

					<button
						type="submit"
						name="buttonId"
						value="更新">

						更新する

					</button>

				</c:when>

				<c:otherwise>

					<button
						type="submit"
						name="buttonId"
						value="登録">

						登録する

					</button>

				</c:otherwise>

			</c:choose>

		</div>

		</form>
	</main>
</div>

<script>
	const slider = document.getElementById("progressRate");
	const label = document.getElementById("progressValue");

	function updateValue(){
		label.textContent = slider.value + "%";
	}

	slider.addEventListener("input", updateValue);
	updateValue();
</script>

</body>

</html>