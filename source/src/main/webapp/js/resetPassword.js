/**
 * 
 */

 /* HTML要素をオブジェクトとして取得する */
	const passwordInput = document.getElementById('passwordNow');
	const showPasswordCheckbox = document.getElementById('show-password');
		
/* [パスワードを表示する]チェックボックスを切り替えたときの処理 */
	if (showPasswordCheckbox && passwordInput) {
		 showPasswordCheckbox.onchange = function() {
		   if (showPasswordCheckbox.checked) {
		     // チェックが入ったら中身を見せる
		     passwordInput.type = 'text';
		   } else {
		     // チェックが外れたら隠す
		     passwordInput.type = 'password';
		   }
		 };
	}