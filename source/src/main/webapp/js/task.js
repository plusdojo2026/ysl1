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
        
	//------------------------------------------------------------
    
    // フィルタ処理で複合条件で絞り込みできるようにする	 
	$(function () {

    const table = $("#tasksTable").DataTable();

    function filterTasks() {
        const caseName = $("#caseName").val();
        const manager = $("#managerId").val();
        const status = $("#taskStatus").val();

        table.column(0).search(caseName);
        table.column(2).search(manager);
        table.column(3).search(status);

        table.draw();
    }

    $("#caseName").on("change", filterTasks);
    $("#managerId").on("change", filterTasks);
    $("#taskStatus").on("change", filterTasks);

    // 案件名の重複削除
    const caseValues = new Set();
    $("#caseName option").each(function () {
        const value = $(this).val();

        if (value && caseValues.has(value)) {
            $(this).remove();
        } else {
            caseValues.add(value);
        }
    });

    // 担当者の重複削除
    const managerValues = new Set();
    $("#managerId option").each(function () {
        const value = $(this).val();

        if (value && managerValues.has(value)) {
            $(this).remove();
        } else {
            managerValues.add(value);
        }
    });
	});
});

	
			
	

   