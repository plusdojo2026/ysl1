"use strict";

document.addEventListener("DOMContentLoaded", function () {

    const form = document.querySelector(".login-form");

    if (!form) {
        return;
    }

    const loginId = document.getElementById("loginId");
    const loginPw = document.getElementById("loginPw");

    /*
     * ログインID
     */
    loginId.addEventListener("blur", validateLoginId);

    /*
     * パスワード
     */
    loginPw.addEventListener("blur", validatePassword);

    /*
     * 入力中にエラーを解除
     */
    loginId.addEventListener("input", function () {

        if (loginId.value.trim() !== "") {
            clearError(
                loginId,
                "loginIdError"
            );
        }
    });

    loginPw.addEventListener("input", function () {

        if (loginPw.value.trim() !== "") {
            clearError(
                loginPw,
                "loginPwError"
            );
        }
    });

    /*
     * フォーム送信時
     */
    form.addEventListener("submit", function (event) {

        const isLoginIdValid = validateLoginId();
        const isPasswordValid = validatePassword();

        if (!isLoginIdValid || !isPasswordValid) {

            event.preventDefault();

            const firstError =
                form.querySelector(".input-error");

            if (firstError) {
                firstError.focus();
            }
        }
    });

    /*
     * ログインID確認
     */
    function validateLoginId() {

        const value = loginId.value.trim();

        if (value === "") {

            showError(
                loginId,
                "loginIdError",
                "ログインIDを入力してください。"
            );

            return false;
        }

        clearError(
            loginId,
            "loginIdError"
        );

        return true;
    }

    /*
     * パスワード確認
     */
    function validatePassword() {

        const value = loginPw.value.trim();

        if (value === "") {

            showError(
                loginPw,
                "loginPwError",
                "パスワードを入力してください。"
            );

            return false;
        }

        clearError(
            loginPw,
            "loginPwError"
        );

        return true;
    }

    /*
     * エラー表示
     */
    function showError(
        inputElement,
        errorElementId,
        message
    ) {

        const errorElement =
            document.getElementById(errorElementId);

        inputElement.classList.add("input-error");

        if (!errorElement) {
            return;
        }

        const textElement =
            errorElement.querySelector(
                ".login-field-error__text"
            );

        if (textElement) {
            textElement.textContent = message;
        }

        errorElement.classList.add("is-visible");
    }

    /*
     * エラー解除
     */
    function clearError(
        inputElement,
        errorElementId
    ) {

        const errorElement =
            document.getElementById(errorElementId);

        inputElement.classList.remove("input-error");

        if (!errorElement) {
            return;
        }

        const textElement =
            errorElement.querySelector(
                ".login-field-error__text"
            );

        if (textElement) {
            textElement.textContent = "";
        }

        errorElement.classList.remove("is-visible");
    }
});
