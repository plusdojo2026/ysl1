/**
 * タスク一覧のjQueryの処理をするJavaScriptファイル
 */

jQuery(function ($) {

    // DataTables日本語化
    $.extend($.fn.dataTable.defaults, {
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
        }
    });
	
	
	//テーブルのスクロール指定
        $('#tasksTable').DataTable({
		    scrollY: '250px',      // スクロールさせたい高さ
		    scrollCollapse: true,  // データが少ない時に高さを縮める
		    autoWidth: false,
		    scrollX: true
		    	   
		});
		
		
    // DataTable初期化
    const table = $("#tasksTable").DataTable();

    // 絞り込み処理
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


    // 案件名の重複削除---------------------------------------------------------
    const caseValues = new Set();

    $("#caseName option").each(function () {
        const value = $(this).val();

        if (value === "") {
            return;
        }

        if (caseValues.has(value)) {
            $(this).remove();
        } else {
            caseValues.add(value);
        }
    });

    // 担当者の重複削除------------------------------------------------------------
    const managerValues = new Set();

    $("#managerId option").each(function () {
        const value = $(this).val();

        if (value === "") {
            return;
        }

        if (managerValues.has(value)) {
            $(this).remove();
        } else {
            managerValues.add(value);
        }
    });

});