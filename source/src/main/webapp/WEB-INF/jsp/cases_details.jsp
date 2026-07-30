<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>案件詳細</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" href="<c:url value='/css/cases_detail.css' />">
</head>

<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
	
 <main>
<h1>案件詳細</h1>
<h2></h2>

<!-- タブ全体のコンテナ -->
<div class="tab-container">

  <!-- タブ切り替えボタン部分。クリックするとswitchTab()が呼ばれる -->
  <div class="tab-buttons">
    <!-- 第1引数：表示したいタブのid、第2引数：クリックされたボタン自身（this） -->
    <button class="tab-btn active" onclick="switchTab('tab1', this)">基本情報</button>
    <button class="tab-btn" onclick="switchTab('tab2', this)">タスク一覧</button>
    <button class="tab-btn" onclick="switchTab('tab3', this)">工数ログ</button>
  </div>

  <!-- タブ1の中身：初期表示状態（activeクラス付き） -->
  <div id="tab1" class="tab-content active">
    <h3>基本情報</h3>
    
    <div class="table" id="cases">
	<!-- <h2>基本情報</h2> -->
	<table class="table" id="cases_table">
	
	<form action="/ysl1/Controller" method="post">
		<input type="hidden" name="pageId" value="C003">
		
		
		<c:forEach var="c" items="${casesList}">
			<tr>
				<td>案件コード</td>
				<td>
					<input type ="hidden" name="id" value="${c.caseId}">
					<input type ="hidden" name="caseId" value="${c.caseId}">
					${c.caseCode}
				</td>
			</tr>

			<tr>
				<td>案件名</td>
				<td>${c.caseName}</td>
			</tr>
			<tr>
				<td>顧客名</td>
				<td>${c.customerName}</td>
			</tr>
			<tr>
				<td>PM</td>
				<td>${c.userName}</td>
			</tr>
			<tr>
				<td>ステータス</td>
				<td>${c.caseStatus}
				<c:if test="${c.caseStatus!='未着手'}"> 
				<input type="submit" name="buttonId" value="未着手にする">
				</c:if>
				<c:if test="${c.caseStatus!='進行中'}"> 
				<input type="submit" name="buttonId" value="進行中にする">
				</c:if>
				<c:if test="${c.caseStatus!='完了'}">
				<input type="submit" name="buttonId" value="完了にする" onclick="return complete()">
				</c:if>
				<c:if test="${c.caseStatus!='中止'}">
				<input type="submit" name="buttonId" value="中止にする">
				</c:if>
				</td>
				
				
			</tr>
			<tr>
				<td>優先度</td>
				<td>${c.casePriority}</td>
			</tr>
	
		
	
			<tr>
				<td>期間</td>
				<td>${c.plannedEndDate}</td>
			</tr>
		
	
			<tr>
				<td>予定工数</td>
				<td>${c.casePlannedHours}</td>
		
		
		
			</tr>
			<!-- tasksから -->
			<tr>
				<td>実績工数</td>
				<td>${c.actualHoursSum}</td>
			</tr>
			<tr>
				<td>完成したタスクの進捗</td>
				<td>${c.caseProgressRate}％</td>
			</tr>
		
			</c:forEach>
		<c:forEach var="c" items="${casesList}">
			<tr>
				<td>説明</td>
				<td>${c.caseDescription}<input type="submit" name="buttonId" value="案件編集"></td>
				
			</tr>
		</c:forEach>
	</form>
	</table>
	
	</div>	
</div>

  <!-- タブ2の中身：初期状態は非表示 -->
  <div id="tab2" class="tab-content">
    <h3>タスク一覧</h3>
    
   <div class="table" id="tasks">
   <form action="/ysl1/Controller" method="post">
  	 <input type="submit" name="buttonId" value="タスク追加">
  	 
  	 <input type="hidden" name="pageId" value="C003">
  	 <input type ="hidden" name="caseId" value="${casesList[0].caseId}">
  	 <input type ="hidden" name="caseName" value="${casesList[0].caseName}">
  	 
  	</form>
	<!-- <h2>タスク一覧</h2> -->
	<form action="/ysl1/Controller" method="post">
		<!-- <input type="submit" name="buttonId" value="タスク追加"> -->
		
		<table class="table" id="tasks_table" style="white-space: nowrap">
		<tr>
			<th>タスク名</th>
			<th>担当者</th>
			<th>ステータス</th>
			<th>優先度</th>
			<th>期限</th>
			<th colspan="2">予定/実績工数</th>
			<th>進捗</th>
			<th >操作</th>
		</tr>
		<c:forEach var="t" items="${tasksList}">
		<tr>
			<input type="hidden" name="pageId" value="C003">
			<input type="hidden" name="taskId" value="${t.taskId }">
			<input type="hidden" name="caseId" value="${param.caseId}">
			<td>${t.taskName}</td>
			<td>${t.userName}</td>
			<td>${t.taskStatus}</td>
			<td>${t.taskPriority}</td>
			<td>${t.deadline.substring(0, 10)}</td>
			
			<td colspan="2">${t.taskPlannedHours}/${t.actualHours}</td>
			<td>${t.taskProgressRate}</td>
			
			
			<td><input type="submit" name="buttonId" value="タスク編集">
			<input type="submit" name="buttonId" value="タスク削除" onclick="return delete1()">
			<input type="submit" name="buttonId" value="工数入力"></td>
		</tr>
		</c:forEach>
		</table>
	</form>
</div>
  </div>

  <!-- タブ3の中身：初期状態は非表示 -->
  <div id="tab3" class="tab-content">
    <h3>工数ログ</h3>
   <div class="table" id="works">
<!-- 	<h2>工数ログ</h2> -->

	<c:forEach var="w" items="${worksList}">
		<table class="table" id="works_table">
		<tr>
		<form action="/ysl1/Controller" method="post">
			<td>作業日</td><td>${w.workDate}</td>
			<td>タスク名</td><td>${w.taskName}</td>
			<td>担当者</td><td>${w.userName}</td>
			<td>工数</td><td>${w.actualHours}</td>
			<td>作業内容</td><td>${w.workDescription}</td>
			
			<!-- <td><input type="submit" name="buttonId" value="工数入力"></td>
			<td><input type="submit" name="buttonId" value="すべて見る"></td> -->
		</form>
		</tr>
		</table>
	</c:forEach>
	</div>
  </div>
</div>





</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
  // タブ切り替え処理
  // tabId：表示したいタブ内容のid（例："tab1"）
  // btnElement：クリックされたボタン要素自身（this経由で受け取る）
  function switchTab(tabId, btnElement) {

    // まず全てのタブ内容からactiveクラスを外す（一旦全部非表示にする）
    document.querySelectorAll('.tab-content').forEach(function(el) {
      el.classList.remove('active');
    });

    // 全てのタブボタンからもactiveクラスを外す（選択状態をリセット）
    document.querySelectorAll('.tab-btn').forEach(function(el) {
      el.classList.remove('active');
    });

    // クリックされたタブに対応する中身だけにactiveを付けて表示する
    document.getElementById(tabId).classList.add('active');

    // クリックされたボタン自身にもactiveを付けて選択状態を見せる
    btnElement.classList.add('active');
  }
  //削除ボタンのアラート
  function delete1(){
		if (!confirm("削除します。よろしいですか？")) {
			return false;
			}
	}

	function complete(){
		if(!confirm("完了にしますか？")) {
			return
		}
    }


</script>
</body>
</html>