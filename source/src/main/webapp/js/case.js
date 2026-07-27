/**
 * 
 * 案件一覧のjQueryの処理をするJavaScriptファイル
 *絞り込み検索をするために必要なやつ！
 *	絞り込みたい項目のメソッド２つ書きました
 * 
 */

  jQuery(function($){
    	 // デフォルトの設定を変更（日本語化）--------------------
        $.extend( $.fn.dataTable.defaults, {
            language: {
                url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
            }
        });
        
        $("#cases").DataTable();
        
	//------------------------------------------------------------
    
    // フィルタ処理で複合条件で絞り込みできるようにする	 
	$(function () {

    const table = $("#cases").DataTable();

    function filterCases() {
        const caseStatus = $("#caseStatus").val();
        const casePriority = $("#casePriority").val();
       

        table.column(3).search(caseStatus);
        table.column(4).search(casePriority);

        table.draw();
    }

    $("#caseStatus").on("change", filterCases);
    $("#casePriority").on("change", filterCases);
   

   
    });
	});