"use strict";

document.addEventListener("DOMContentLoaded", function () {

    // フォームを取得
    const form = document.getElementById("userRegistForm");

    // フォームが存在しない場合は処理を終了
    if (form === null) {
        return;
    }

    // 入力欄を取得
    const loginId = document.getElementById("loginId");
    const userName = document.getElementById("userName");
    const mailAddress = document.getElementById("mailAddress");
    const loginPw = document.getElementById("loginPw");

    // 権限のラジオボタンを取得
    const authorityRadios =
        form.querySelectorAll('input[name="authority"]');

    // ログインID：4～30文字の半角英数字
    const loginIdRegex = /^[A-Za-z0-9]{4,30}$/;

    // メールアドレス
    const mailRegex =
        /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    // パスワード：8～20文字、英字と数字を含む
    const passwordRegex =
        /^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$/;


    /*
     * ページ読み込み時のエラー表示
     *
     * バックエンドからエラーメッセージが返された場合は表示する。
     * エラーメッセージが空の場合はエラー欄を非表示にする。
     */
    form.querySelectorAll(".error").forEach(function (errorElement) {

        if (errorElement.textContent.trim() !== "") {
            errorElement.classList.add("is-visible");
        } else {
            errorElement.classList.remove("is-visible");
        }
    });


    /*
     * ログインID
     * 入力欄からフォーカスが外れたときに確認する
     */
    loginId.addEventListener("blur", function () {
        validateLoginId();
    });


    /*
     * 氏名
     * 入力欄からフォーカスが外れたときに確認する
     */
    userName.addEventListener("blur", function () {
        validateUserName();
    });


    /*
     * メールアドレス
     * 入力欄からフォーカスが外れたときに確認する
     */
    mailAddress.addEventListener("blur", function () {
        validateMailAddress();
    });


    /*
     * 初期パスワード
     * 入力欄からフォーカスが外れたときに確認する
     */
    loginPw.addEventListener("blur", function () {
        validateLoginPw();
    });


    /*
     * 権限
     * ラジオボタンを選択したときに確認する
     */
    authorityRadios.forEach(function (radio) {

        radio.addEventListener("change", function () {
            validateAuthority();
        });
    });


    /*
     * フォーム送信時
     */
    form.addEventListener("submit", function (event) {

        // 全項目を確認する
        const isLoginIdValid = validateLoginId();
        const isUserNameValid = validateUserName();
        const isMailValid = validateMailAddress();
        const isPasswordValid = validateLoginPw();
        const isAuthorityValid = validateAuthority();

        const hasError =
            !isLoginIdValid ||
            !isUserNameValid ||
            !isMailValid ||
            !isPasswordValid ||
            !isAuthorityValid;

        // エラーがある場合は送信しない
        if (hasError) {
            event.preventDefault();

            // 最初にエラーが発生した入力欄へフォーカス
            const firstErrorElement =
                form.querySelector(".input-error");

            if (firstErrorElement !== null) {
                firstErrorElement.focus();
            }
        }
    });


    /*
     * ログインID確認
     */
    function validateLoginId() {

        const value = loginId.value.trim();

        // 未入力
        if (value === "") {

            showError(
                loginId,
                "errorMsgLoginId",
                "ログインIDを入力してください。"
            );

            return false;
        }

        // 4～30文字の半角英数字以外
        if (!loginIdRegex.test(value)) {

            showError(
                loginId,
                "errorMsgLoginId",
                "ログインIDは4～30文字の半角英数字で入力してください。"
            );

            return false;
        }

        // 正常の場合はエラーを消す
        clearError(loginId, "errorMsgLoginId");

        return true;
    }


    /*
     * 氏名確認
     */
    function validateUserName() {

        const value = userName.value.trim();

        // 未入力
        if (value === "") {

            showError(
                userName,
                "errorMsgName",
                "氏名を入力してください。"
            );

            return false;
        }

        // データベースのVARCHAR(30)に合わせる
        if (value.length > 30) {

            showError(
                userName,
                "errorMsgName",
                "氏名は30文字以内で入力してください。"
            );

            return false;
        }

        // 正常の場合はエラーを消す
        clearError(userName, "errorMsgName");

        return true;
    }


    /*
     * メールアドレス確認
     */
    function validateMailAddress() {

        const value = mailAddress.value.trim();

        // メールアドレスは空欄を許可する
        if (value === "") {

            clearError(
                mailAddress,
                "errorMsgMail"
            );

            return true;
        }

        // データベースのVARCHAR(50)に合わせる
        if (value.length > 50) {

            showError(
                mailAddress,
                "errorMsgMail",
                "メールアドレスは50文字以内で入力してください。"
            );

            return false;
        }

        // メールアドレス形式確認
        if (!mailRegex.test(value)) {

            showError(
                mailAddress,
                "errorMsgMail",
                "メールアドレスの形式が正しくありません。"
            );

            return false;
        }

        // 正常の場合はエラーを消す
        clearError(
            mailAddress,
            "errorMsgMail"
        );

        return true;
    }


    /*
     * 初期パスワード確認
     */
    function validateLoginPw() {

        const value = loginPw.value;

        // 未入力
        if (value === "") {

            showError(
                loginPw,
                "errorMsgLoginPw",
                "初期パスワードを入力してください。"
            );

            return false;
        }

        // 8～20文字、英字と数字を両方含む
        if (!passwordRegex.test(value)) {

            showError(
                loginPw,
                "errorMsgLoginPw",
                "パスワードは8～20文字の半角英数字で、英字と数字を両方含めてください。"
            );

            return false;
        }

        // 正常の場合はエラーを消す
        clearError(
            loginPw,
            "errorMsgLoginPw"
        );

        return true;
    }


    /*
     * 権限確認
     */
    function validateAuthority() {

        const authority = form.querySelector(
            'input[name="authority"]:checked'
        );

        // 権限が選択されていない場合
        if (authority === null) {

            showRadioError(
                "errorMsgAuthority",
                "権限を選択してください。"
            );

            authorityRadios.forEach(function (radio) {
                radio.classList.add("input-error");
            });

            return false;
        }

        // 正常の場合はエラーを消す
        clearRadioError("errorMsgAuthority");

        authorityRadios.forEach(function (radio) {
            radio.classList.remove("input-error");
        });

        return true;
    }


    /*
     * 入力欄のエラーを表示
     */
    function showError(inputElement, errorElementId, message) {

        const errorElement =
            document.getElementById(errorElementId);

        // 入力欄にエラー用CSSを追加
        inputElement.classList.add("input-error");

        if (errorElement !== null) {
            errorElement.textContent = message;
            errorElement.classList.add("is-visible");
        }
    }


    /*
     * 入力欄のエラーを消す
     */
    function clearError(inputElement, errorElementId) {

        const errorElement =
            document.getElementById(errorElementId);

        // 入力欄からエラー用CSSを削除
        inputElement.classList.remove("input-error");

        if (errorElement !== null) {
            errorElement.textContent = "";
            errorElement.classList.remove("is-visible");
        }
    }


    /*
     * ラジオボタンのエラーを表示
     */
    function showRadioError(errorElementId, message) {

        const errorElement =
            document.getElementById(errorElementId);

        if (errorElement !== null) {
            errorElement.textContent = message;
            errorElement.classList.add("is-visible");
        }
    }


    /*
     * ラジオボタンのエラーを消す
     */
    function clearRadioError(errorElementId) {

        const errorElement =
            document.getElementById(errorElementId);

        if (errorElement !== null) {
            errorElement.textContent = "";
            errorElement.classList.remove("is-visible");
        }
    }
});
