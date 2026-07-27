<!-- 検索条件 -->
<div class="search_area">
	<!-- 検索条件の入力フォーム 
	<div class="search">
	<label for="keyword">キーワードを検索</label>
		<input type="text" name="keyword" id="keyword" placeholder="キーワードを入力してください">
	</div>
	-->
	
	<!-- 案件名の選択 -->
	<div class="search">
	<label for="caseName">案件名</label>
		<select id="caseName">
			<option value="">すべて</option>
			<c:forEach var="e" items="${taskList}">
				<!-- casename に "e" という名前をつけたよ-->
				<option value="${e.caseName}">${e.caseName}</option>	
				<!-- valueはシステムに送る値 ${e}は画面に表示するものを示す-->
			</c:forEach>
		</select>
	</div>
	<!-- ステータスの選択 -->
	<div class="search">
	<label for="taskStatus">ステータス</label>
		<select id="taskStatus">
			<option value="">すべて</option>
			<option value="進行中">進行中</option>
			<option value="完了">完了</option>
			<option value="保留">保留</option>
			<option value="未着手">未着手</option>			
		</select>
	</div>
	<!-- 担当者の選択 -->
	<div class="search">
	<label for="managerId">担当者</label>
		<select id="managerId">
			<option value="">すべて</option>
			<c:forEach var="e" items="${taskList}">
				<option value="${e.userName}">${e.userName}</option>
			</c:forEach>
		</select>
	</div>
</div>
		
		