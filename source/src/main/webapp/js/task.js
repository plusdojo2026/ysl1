/**
 *　タスク一覧のjQueryの処理をするJavaScriptファイル
 *　絞り込み検索をするために必要なやつ！
 */

    jQuery(function($){
    	 // デフォルトの設定を変更（日本語化）--------------------
        $.extend( $.fn.dataTable.defaults, {
            language: {
                url: "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
            }
        });
    	 //------------------------------------------------
    	//データテーブルを使用
        $("#tasks_table").DataTable();
    });
