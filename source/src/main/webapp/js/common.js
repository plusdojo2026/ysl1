/**
 * 共通用のJavaScriptファイル
 * 以下の内容は、共通のJavaScript関数やユーティリティを定義するためのファイルです。
 */

/**
 * 共通 JavaScript の初期化処理
 */
document.addEventListener('DOMContentLoaded', function() {
    initFormReset();
    initConfirmActions();
    initRequestMessage();
    initBackButtons();
});

/**
 * リセットボタンのクリックでフォームの内容をリセットします。
 */
function initFormReset() {
    const resetButtons = document.querySelectorAll('input[type="button"].js-form-reset');
    resetButtons.forEach(button => {
        button.addEventListener('click', function() {
            const message = button.dataset.confirm || '本当にリセットしますか？';
            if (!confirmAction(message)) {
                return;
            }
            const form = button.closest('form');
            if (form) {
                form.reset();
            }
        });
    });
}

/**
 * data-confirm 属性を持つボタンの確認ダイアログを処理します。
 */
function initConfirmActions() {
    const confirmButtons = document.querySelectorAll('[data-confirm]');
    confirmButtons.forEach(button => {
        if (button.classList.contains('js-form-reset')) {
            return;
        }
        button.addEventListener('click', function(event) {
            const message = button.dataset.confirm;
            if (message && !confirmAction(message)) {
                event.preventDefault();
            }
        });
    });
}

/**
 * URL のクエリパラメータからリクエストメッセージを取得し、画面に表示します。
 */
function initRequestMessage() {
    const params = new URLSearchParams(window.location.search);
    const message = params.get('msg') || params.get('requestMsg') || params.get('errorMsg');
    if (!message) {
        return;
    }
    showRequestMessage(message);
}

/**
 * 前のページに戻るボタンを初期化します。
 */
function initBackButtons() {
    const backButtons = document.querySelectorAll('.js-back-page');
    backButtons.forEach(button => {
        button.addEventListener('click', function() {
            window.history.back();
        });
    });
}

/**
 * リクエストメッセージをページ上に表示します。
 * @param {string} message - 表示するメッセージ
 */
function showRequestMessage(message) {
    const messageBox = document.querySelector('#requestMsg, .js-request-message');
    if (messageBox) {
        messageBox.textContent = decodeURIComponent(message);
        messageBox.classList.add('visible');
        return;
    }
    alert(decodeURIComponent(message));
}

/**
 * 確認メッセージを表示する共通関数です。
 * @param {string} message - 確認メッセージ
 * @returns {boolean}
 */
function confirmAction(message) {
    return confirm(message || '確認してください。');
}
