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
        
        //テーブルのスクロール指定
        $('#cases').DataTable({
		    scrollY: '250px',      // スクロールさせたい高さ
		    scrollCollapse: true,  // データが少ない時に高さを縮める
		    autoWidth: false,
		    scrollX: true
		   
		});
        
	//------------------------------------------------------------
    
    // フィルタ処理で複合条件で絞り込みできるようにする	 


    const table = $("#cases").DataTable();

    function filterCases() {
        const status = $("#caseStatus").val();
        const priority = $("#casePriority").val();
       

        table.column(3).search(status);
        table.column(4).search(priority);

        table.draw();
    }

    $("#caseStatus").on("change", filterCases);
    $("#casePriority").on("change", filterCases);
   
   
    });
