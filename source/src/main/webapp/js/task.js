/**
 *　タスク一覧のjQueryの処理をするJavaScriptファイル
 *　絞り込み検索をするために必要なやつ！
 *	絞り込みたい項目のメソッド３つ書きました
 */

    jQuery(function($){
    	 // デフォルトの設定を変更（日本語化）--------------------
        $.extend( $.fn.dataTable.defaults, {
            language: {
                url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
            }
        });
        
    	 //-----------------------------------------------------------
    	 
    	//データテーブルを使用
        $("#tasksTable").DataTable();
          
              
        //担当者で絞り込み
        $("manegerId").on("change",function(){
			const manager = $(this).val();
			
			//全件表示
			if (manager == "") {
				$("#tasksTable tr").show();
				return;			
		}
		
			//クリアにする
				$("#tasksTable tr").hide();
			
			//選択された案件のみ表示
				$('#tasksTable tr[data-manager="' + manager + '"]').show();
		});
		
		
		//ステータスで絞り込み
        $("taskStatus").on("change",function(){
			const task = $(this).val();
			
			//全件表示
			if (task == "") {
				$("#tasksTable tr").show();
				return;			
		}
		
			//クリアにする
				$("#tasksTable tr").hide();
			
			//選択された案件のみ表示
				$('#tasksTable tr[data-status="' + task + '"]').show();
		});
		
		
		//案件名で絞り込み
        $("caseName").on("change",function(){
			const cases = $(this).val();
			
			//全件表示
			if (cases == "") {
				$("#tasksTable tr").show();
				return;			
		}
		
			//クリアにする
				$("#tasksTable tr").hide();
			
			//選択された案件のみ表示
				$('#tasksTable tr[data-case="' + cases + '"]').show();
		});
		
			
	});


   