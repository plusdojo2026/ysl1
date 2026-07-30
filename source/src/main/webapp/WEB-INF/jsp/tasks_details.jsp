<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
    タスク詳細
</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/tasks_details.css">

</head>
<body>
<!-- ヘッダー、サイドメニュー -->
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

	<div class="container">
		<main class="main">
            <h3>タスク詳細:</h3><br>
            <h2>${detailsList.taskName}</h2>

            <!-- 編集ボタン -->
            <div class="top-button-area">
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <input type="hidden" name="pageId" value="T003">
                    <input type="hidden" name="taskId" value="${detailsList.taskId}">
                    <button type="submit" name="buttonId" value="編集">編集</button>
                </form>
            </div>

            <!-- タスク詳細情報 -->
            <div class="task-info-area">
                <!-- 左側(案件名、顧客名、担当者名、ステータス、優先度、予定工数、期限、開始日、終了予定日) -->
                <div class="info-left">
                    <div class="info-row">
                        <label>案件名</label>
                        <p>${detailsList.caseName}</p>
                    </div>

                    <div class="info-row">
                        <label>顧客名</label>
                        <p>${detailsList.customerName}</p>
                    </div>

                    <div class="info-row">
                        <label>担当者</label>
                        <p>${detailsList.userName}</p>
                    </div>

                    <div class="info-row">
                        <label>ステータス</label>
                        <span class="status-badge">${detailsList.taskStatus}</span>
                    </div>

                    <div class="info-row">
                        <label>優先度</label>
                        <span class="priority-badge">${detailsList.taskPriority}</span>
                    </div>

                    <div class="info-row">
                        <label>予定工数</label>
                        <p>${detailsList.taskPlannedHours} h</p>
                    </div>

                    <div class="info-row">
                        <label>期限</label>
                        <p>${detailsList.deadline.substring(0,10)}</p>
                    </div>

                    <div class="info-row">
                        <label>開始日</label>
                        <p>${detailsList.startDate.substring(0,10)}</p>
                    </div>

                    <div class="info-row">
                        <label>終了予定日</label>
                        <!-- <p>${detailsList.plannedEndDate}</p> -->
                    </div>

                </div>

                <!-- 右側(進捗率、状態変更btn、タスク説明) -->
                <div class="info-right">
                    <div class="progress-area">
                        <label>進捗率</label>

                        <div class="progress-bar">
                            <div class="progress-fill"
                                style="width:${detailsList.taskProgressRate}%;">
                            </div>
                        </div>

                        <p>${detailsList.taskProgressRate}%</p>

                    </div>
    
                    <div class="status-change-area">
                        <label>状態変更</label>

                        <div class="status-buttons">
                            <form action="${pageContext.request.contextPath}/Controller" method="post">

                                <input type="hidden" name="pageId" value="T003">
                                <input type="hidden" name="taskId" value="${detailsList.taskId}">

                                <c:if test="${detailsList.taskStatus != '未着手'}">
                                    <button type="submit" name="buttonId" value="未着手">
                                        未着手
                                    </button>
                                </c:if>

                                <c:if test="${detailsList.taskStatus != '進行中'}">
                                    <button type="submit" name="buttonId" value="進行中">
                                        進行中
                                    </button>
                                </c:if>

                                <c:if test="${detailsList.taskStatus != '保留'}">
                                    <button type="submit" name="buttonId" value="保留">
                                        保留
                                    </button>
                                </c:if>

                                <c:if test="${detailsList.taskStatus != '完了'}">
                                    <button type="submit" name="buttonId" value="完了">
                                        完了
                                    </button>
                                </c:if>

                            </form>
                        </div>
                    </div>

                    <div class="description-area">
                        <label>タスク説明</label>

                        <div class="description-box">
                            ${detailsList.taskDescription}
                        </div>
                    </div>
                </div>
            </div>

            <!-- 工数実績 -->
            <div class="works-area">
                <div class="works-header">
                    <h3>工数実績</h3>

                    <form action="${pageContext.request.contextPath}/Controller" method="post">
                        <input type="hidden" name="pageId" value="T003">
                        <input type="hidden" name="taskId" value="${detailsList.taskId}">
                        <input type="hidden" name="caseName" value="${detailsList.caseName}">
                        <input type="hidden" name="taskName" value="${detailsList.taskName}">
                        <button type="submit" name="buttonId" value="工数入力">
                            工数入力
                        </button>
                    </form>
                </div>

                <table>
                    <thead>

                        <tr>
                            <th>作業内容</th>
                            <th>作業日</th>
                            <th>実績工数</th>
                            <th></th>
                        </tr>

                    </thead>

                    <tbody>
                        <c:forEach var="worksDTO" items="${worksList}">

                            <tr>

                                <td>${worksDTO.workDescription}</td>
                                <td>${worksDTO.workDate.substring(0,16)}</td>
                                <td>${worksDTO.actualHours}</td>
                                <td>

                                    <form action="${pageContext.request.contextPath}/Controller" method="post">

                                        <input type="hidden" name="pageId" value="T003">
                                        <input type="hidden" name="taskId" value="${detailsList.taskId}">
                                        <input type="hidden" name="id" value="${worksDTO.workId}">

                                        <button
                                            type="submit" name="buttonId" value="削除" onclick="return delete1()">

                                            🗑

                                        </button>

                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- 工数入力モーダル（後で実装） -->
            <div id="workModal" class="modal" style="display:none;">
                <div class="modal-content">

                </div>
            </div>
		</main>
	</div>
	<script>
	function delete1(){
			if (!confirm("削除します。よろしいですか？")) {
				return false;
				}
		}
	</script>
</body>
</html>