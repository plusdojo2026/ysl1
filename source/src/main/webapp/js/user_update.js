"use strict";

document.addEventListener("DOMContentLoaded", function () {
    const resetAreaButton = document.getElementById("resetAreaButton");
    const passwordResetArea = document.getElementById("passwordResetArea");
    const loginPw = document.getElementById("loginPw");
    const loginPwCheck = document.getElementById("loginPwCheck");

    // 必要なHTML要素が存在しない場合は処理を終了する
    if (!resetAreaButton || !passwordResetArea) {
        return;
    }

    /**
     * パスワードリセットエリアを表示する。
     */
    function openPasswordResetArea() {
        passwordResetArea.hidden = false;
        resetAreaButton.textContent = "パスワードリセットを閉じる";
        resetAreaButton.setAttribute("aria-expanded", "true");
    }

    /**
     * パスワードリセットエリアを非表示にする。
     * 入力済みのパスワードもクリアする。
     */
    function closePasswordResetArea() {
        passwordResetArea.hidden = true;
        resetAreaButton.textContent = "パスワードリセット";
        resetAreaButton.setAttribute("aria-expanded", "false");

        if (loginPw) {
            loginPw.value = "";
        }

        if (loginPwCheck) {
            loginPwCheck.value = "";
        }
    }

    /**
     * パスワードリセットボタン押下時の処理。
     */
    resetAreaButton.addEventListener("click", function () {
        if (passwordResetArea.hidden) {
            openPasswordResetArea();

            if (loginPw) {
                loginPw.focus();
            }
        } else {
            closePasswordResetArea();
        }
    });

    /**
     * サーバー側のパスワードチェックでエラーになった場合、
     * JSPが出力したdata属性を確認して入力エリアを再表示する。
     */
    const hasPasswordError =
        passwordResetArea.dataset.hasPasswordError === "true";

    if (hasPasswordError) {
        openPasswordResetArea();
    } else {
        closePasswordResetArea();
    }
});
