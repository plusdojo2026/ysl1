"use strict";

document.addEventListener("DOMContentLoaded", function () {

    /* ========================================
       HTML要素取得
    ======================================== */

    var passwordNow =
        document.getElementById("passwordNow");

    var loginPw =
        document.getElementById("loginPw");

    var loginPwConfirm =
        document.getElementById("loginPwConfirm");

    var showPasswordCheckbox =
        document.getElementById("show-password");

    /*
     * 対象画面ではない場合は処理を終了する
     */
    if (
        !passwordNow ||
        !loginPw ||
        !loginPwConfirm
    ) {
        return;
    }

    /*
     * パスワード入力欄を基準に対象フォームを取得する
     */
    var form =
        passwordNow.closest("form");

    if (!form) {
        return;
    }

    var submitButton =
        form.querySelector(
            'input[type="submit"]'
        );

    /*
     * パスワード入力欄一覧
     */
    var passwordInputs = [
        passwordNow,
        loginPw,
        loginPwConfirm
    ];

    /*
     * パスワード：
     * 8～20文字の半角英数字
     * 英字と数字を両方含む
     */
    var passwordRegex =
        /^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$/;

    /*
     * 乱码表示に使用する文字
     */
    var scrambleCharacters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "abcdefghijklmnopqrstuvwxyz"
        + "0123456789"
        + "#@$%&*!?";

    /*
     * 確認済みの送信かどうか
     */
    var submitConfirmed = false;

    /*
     * パスワード表示アニメーション中かどうか
     */
    var isPasswordAnimating = false;

    /* ========================================
       パスワード表示切替
    ======================================== */

    if (showPasswordCheckbox) {

        showPasswordCheckbox.addEventListener(
            "change",
            function () {

                if (isPasswordAnimating) {
                    return;
                }

                isPasswordAnimating = true;
                showPasswordCheckbox.disabled = true;

                var showPassword =
                    showPasswordCheckbox.checked;

                /*
                 * 入力済みパスワードを保存する
                 */
                var originalValues =
                    passwordInputs.map(
                        function (input) {
                            return input.value;
                        }
                    );

                /*
                 * 前回のアニメーション状態を解除する
                 */
                document.body.classList.remove(
                    "password-scan"
                );

                passwordInputs.forEach(
                    function (input) {
                        input.classList.remove(
                            "password-text-changing",
                            "password-text-revealed"
                        );
                    }
                );

                /*
                 * スキャンアニメーションを再実行するため
                 * 強制的に再描画する
                 */
                void document.body.offsetWidth;

                /*
                 * スキャン開始
                 */
                document.body.classList.add(
                    "password-scan"
                );

                /*
                 * Promiseで順番に実行する
                 */
                wait(250)
                    .then(function () {

                        if (showPassword) {

                            /*
                             * 表示する場合は一度textへ変更し、
                             * 乱码から実際のパスワードへ解読する
                             */
                            passwordInputs.forEach(
                                function (input) {
                                    input.type = "text";
                                }
                            );

                            document.body.classList.add(
                                "preview-mode"
                            );

                            return Promise.all(
                                passwordInputs.map(
                                    function (input, index) {
                                        return scrambleReveal(
                                            input,
                                            originalValues[index]
                                        );
                                    }
                                )
                            );
                        }

                        /*
                         * 非表示にする場合は実際の文字から変換
                         */
                        return Promise.all(
                            passwordInputs.map(
                                function (input, index) {
                                    return scrambleHide(
                                        input,
                                        originalValues[index]
                                    );
                                }
                            )
                        ).then(function () {

                            /*
                             * 元の値へ戻してpassword型へ変更する
                             */
                            passwordInputs.forEach(
                                function (input, index) {
                                    input.value =
                                        originalValues[index];

                                    input.type = "password";
                                }
                            );

                            document.body.classList.remove(
                                "preview-mode"
                            );
                        });
                    })
                    .then(function () {

                        /*
                         * 文字表示完了クラスを一時的に付与する
                         */
                        passwordInputs.forEach(
                            function (input) {
                                input.classList.add(
                                    "password-text-revealed"
                                );
                            }
                        );

                        return wait(280);
                    })
                    .then(function () {

                        passwordInputs.forEach(
                            function (input) {
                                input.classList.remove(
                                    "password-text-revealed"
                                );
                            }
                        );

                        /*
                         * スキャン終了
                         */
                        document.body.classList.remove(
                            "password-scan"
                        );

                        showPasswordCheckbox.disabled = false;
                        isPasswordAnimating = false;
                    })
                    .catch(function (error) {

                        console.error(error);

                        /*
                         * エラー時も操作不能にならないように復帰する
                         */
                        document.body.classList.remove(
                            "password-scan"
                        );

                        showPasswordCheckbox.disabled = false;
                        isPasswordAnimating = false;
                    });
            }
        );
    }

    /* ========================================
       暗号化からパスワードを表示する
    ======================================== */

    function scrambleReveal(
        input,
        originalValue
    ) {

        return new Promise(function (resolve) {

            /*
             * 空欄の場合はアニメーションしない
             */
            if (originalValue === "") {
                input.value = "";
                resolve();
                return;
            }

            var revealedLength = 0;

            var timer = window.setInterval(
                function () {

                    var displayValue = "";
                    var index;

                    for (
                        index = 0;
                        index < originalValue.length;
                        index++
                    ) {

                        if (index < revealedLength) {

                            displayValue +=
                                originalValue.charAt(index);

                        } else {

                            displayValue +=
                                getRandomCharacter();
                        }
                    }

                    input.value = displayValue;

                    revealedLength++;

                    if (
                        revealedLength >
                        originalValue.length
                    ) {

                        window.clearInterval(timer);

                        input.value = originalValue;

                        resolve();
                    }
                },
                45
            );
        });
    }

    /* ========================================
       パスワードを乱码へ変更して非表示にする
    ======================================== */

    function scrambleHide(
        input,
        originalValue
    ) {

        return new Promise(function (resolve) {

            /*
             * 空欄の場合はアニメーションしない
             */
            if (originalValue === "") {
                resolve();
                return;
            }

            var hiddenLength = 0;

            var timer = window.setInterval(
                function () {

                    var displayValue = "";
                    var index;

                    for (
                        index = 0;
                        index < originalValue.length;
                        index++
                    ) {

                        if (
                            index <
                            originalValue.length -
                            hiddenLength
                        ) {

                            displayValue +=
                                originalValue.charAt(index);

                        } else {

                            displayValue +=
                                getRandomCharacter();
                        }
                    }

                    input.value = displayValue;

                    hiddenLength++;

                    if (
                        hiddenLength >
                        originalValue.length
                    ) {

                        window.clearInterval(timer);

                        resolve();
                    }
                },
                35
            );
        });
    }

    /* ========================================
       ランダム文字取得
    ======================================== */

    function getRandomCharacter() {

        var index =
            Math.floor(
                Math.random()
                * scrambleCharacters.length
            );

        return scrambleCharacters.charAt(index);
    }

    /* ========================================
       指定時間待機
    ======================================== */

    function wait(milliseconds) {

        return new Promise(function (resolve) {

            window.setTimeout(
                resolve,
                milliseconds
            );
        });
    }

    /* ========================================
       フォーカスアウト時の入力確認
    ======================================== */

    passwordNow.addEventListener(
        "blur",
        validateCurrentPassword
    );

    loginPw.addEventListener(
        "blur",
        validateNewPassword
    );

    loginPwConfirm.addEventListener(
        "blur",
        validateConfirmPassword
    );

    /* ========================================
       入力中のエラー解除
    ======================================== */

    passwordNow.addEventListener(
        "input",
        function () {

            if (isPasswordAnimating) {
                return;
            }

            if (passwordNow.value.trim() !== "") {

                clearError(
                    passwordNow,
                    "passwordNowError"
                );
            }
        }
    );

    loginPw.addEventListener(
        "input",
        function () {

            if (isPasswordAnimating) {
                return;
            }

            if (loginPw.value !== "") {

                clearError(
                    loginPw,
                    "loginPwError"
                );
            }

            /*
             * 確認用パスワードが入力済みの場合は
             * 一致状態を再確認する
             */
            if (loginPwConfirm.value !== "") {
                validateConfirmPassword();
            }
        }
    );

    loginPwConfirm.addEventListener(
        "input",
        function () {

            if (isPasswordAnimating) {
                return;
            }

            if (loginPwConfirm.value !== "") {

                clearError(
                    loginPwConfirm,
                    "loginPwConfirmError"
                );
            }
        }
    );

    /* ========================================
       フォーム送信時
    ======================================== */

    form.addEventListener(
        "submit",
        function (event) {

            /*
             * アニメーション中は送信しない
             */
            if (isPasswordAnimating) {
                event.preventDefault();
                return;
            }

            var currentPasswordValid =
                validateCurrentPassword();

            var newPasswordValid =
                validateNewPassword();

            var confirmPasswordValid =
                validateConfirmPassword();

            /*
             * 入力エラーがある場合は送信しない
             */
            if (
                !currentPasswordValid ||
                !newPasswordValid ||
                !confirmPasswordValid
            ) {
                event.preventDefault();

                var firstError =
                    form.querySelector(
                        ".input-error"
                    );

                if (firstError) {
                    firstError.focus();
                }

                return;
            }

            /*
             * 確認済みの場合はそのまま送信する
             */
            if (submitConfirmed) {
                return;
            }

            /*
             * 入力確認後に確認ダイアログを表示する
             */
            event.preventDefault();

            var message =
                "この内容でパスワードを"
                + "リセットしますか？";

            /*
             * common.jsのconfirmActionが存在する場合は利用し、
             * 存在しない場合は標準confirmを利用する
             */
            var confirmed =
                typeof confirmAction === "function"
                    ? confirmAction(message)
                    : window.confirm(message);

            if (!confirmed) {
                return;
            }

            submitConfirmed = true;

            /*
             * form.submit()では送信ボタンのname/valueが送信されないため、
             * buttonIdをhidden項目として追加する
             */
            var buttonIdInput =
                form.querySelector(
                    'input[type="hidden"][name="buttonId"]'
                );

            if (!buttonIdInput) {

                buttonIdInput =
                    document.createElement("input");

                buttonIdInput.type = "hidden";
                buttonIdInput.name = "buttonId";

                form.appendChild(buttonIdInput);
            }

            buttonIdInput.value = "保存";

            /*
             * submitイベントを再発火させず直接送信する
             */
            HTMLFormElement.prototype.submit.call(form);
        }
    );

    /* ========================================
       フォームリセット時
    ======================================== */

    form.addEventListener(
        "reset",
        function () {

            window.setTimeout(
                function () {

                    submitConfirmed = false;
                    isPasswordAnimating = false;

                    if (showPasswordCheckbox) {

                        showPasswordCheckbox.checked =
                            false;

                        showPasswordCheckbox.disabled =
                            false;
                    }

                    passwordInputs.forEach(
                        function (input) {

                            input.type = "password";

                            input.classList.remove(
                                "input-error",
                                "password-text-changing",
                                "password-text-revealed"
                            );
                        }
                    );

                    clearError(
                        passwordNow,
                        "passwordNowError"
                    );

                    clearError(
                        loginPw,
                        "loginPwError"
                    );

                    clearError(
                        loginPwConfirm,
                        "loginPwConfirmError"
                    );

                    document.body.classList.remove(
                        "preview-mode",
                        "password-scan"
                    );
                },
                0
            );
        }
    );

    /* ========================================
       現在のパスワード確認
    ======================================== */

    function validateCurrentPassword() {

        var value =
            passwordNow.value.trim();

        if (value === "") {

            showError(
                passwordNow,
                "passwordNowError",
                "現在のパスワードを入力してください。"
            );

            return false;
        }

        clearError(
            passwordNow,
            "passwordNowError"
        );

        return true;
    }

    /* ========================================
       新しいパスワード確認
    ======================================== */

    function validateNewPassword() {

        var value =
            loginPw.value;

        if (value === "") {

            showError(
                loginPw,
                "loginPwError",
                "新しいパスワードを入力してください。"
            );

            return false;
        }

        if (!passwordRegex.test(value)) {

            showError(
                loginPw,
                "loginPwError",
                "パスワードは8～20文字の"
                    + "半角英数字で、英字と数字を"
                    + "両方含めてください。"
            );

            return false;
        }

        clearError(
            loginPw,
            "loginPwError"
        );

        return true;
    }

    /* ========================================
       確認用パスワード確認
    ======================================== */

    function validateConfirmPassword() {

        var value =
            loginPwConfirm.value;

        if (value === "") {

            showError(
                loginPwConfirm,
                "loginPwConfirmError",
                "確認用パスワードを入力してください。"
            );

            return false;
        }

        if (value !== loginPw.value) {

            showError(
                loginPwConfirm,
                "loginPwConfirmError",
                "新しいパスワードが一致しません。"
            );

            return false;
        }

        clearError(
            loginPwConfirm,
            "loginPwConfirmError"
        );

        return true;
    }

    /* ========================================
       エラー表示
    ======================================== */

    function showError(
        inputElement,
        errorElementId,
        message
    ) {

        var errorElement =
            document.getElementById(
                errorElementId
            );

        inputElement.classList.add(
            "input-error"
        );

        inputElement.setAttribute(
            "aria-invalid",
            "true"
        );

        inputElement.setAttribute(
            "aria-describedby",
            errorElementId
        );

        if (!errorElement) {
            return;
        }

        var textElement =
            errorElement.querySelector(
                ".form-error__text"
            );

        if (textElement) {
            textElement.textContent = message;
        }

        errorElement.classList.add(
            "is-visible"
        );
    }

    /* ========================================
       エラー解除
    ======================================== */

    function clearError(
        inputElement,
        errorElementId
    ) {

        var errorElement =
            document.getElementById(
                errorElementId
            );

        inputElement.classList.remove(
            "input-error"
        );

        inputElement.setAttribute(
            "aria-invalid",
            "false"
        );

        inputElement.removeAttribute(
            "aria-describedby"
        );

        if (!errorElement) {
            return;
        }

        var textElement =
            errorElement.querySelector(
                ".form-error__text"
            );

        if (textElement) {
            textElement.textContent = "";
        }

        errorElement.classList.remove(
            "is-visible"
        );
    }
});
