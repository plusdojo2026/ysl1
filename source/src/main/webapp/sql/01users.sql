-- データベース作成
CREATE DATABASE IF NOT EXISTS ysl1
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 使用するデータベースを指定
USE ysl1;

-- DB接続用ユーザー作成
CREATE USER IF NOT EXISTS 'ysl1'@'localhost'
IDENTIFIED BY '<DB_PASSWORD>';

-- ysl1データベースに対する権限を付与
GRANT ALL PRIVILEGES
ON ysl1.*
TO 'ysl1'@'localhost';

-- 権限を反映
FLUSH PRIVILEGES;

-- usersテーブル作成
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

    login_id VARCHAR(30) NOT NULL UNIQUE,

    login_pw VARCHAR(255) NOT NULL,

    user_name VARCHAR(30) NOT NULL,

    mail_address VARCHAR(50),

    authority BOOLEAN NOT NULL,

    active BOOLEAN NOT NULL,

    created_at TIMESTAMP
        NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    update_at TIMESTAMP
        NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- 初期データ登録
INSERT INTO users (
    login_id,
    login_pw,
    user_name,
    mail_address,
    authority,
    active
) VALUES
(
    'tanaka456',
    '$2b$12$cJVMF57mLzWEu9/9B1qoTO7WUF9v3rGOAETUV6wtaC5ajjiFUcg8y',
    '田中恵梨香',
    'tanaka@example.com',
    false,
    true
),
(
    'sato_hanako',
    '$2b$12$6o38rlVGusnvChFKimPu1eTjBTffB/lUBI8jXOGR4fmwz/49i6eCW',
    '佐藤 花子',
    'sato@example.com',
    true,
    true
),
(
    'admin001',
    '$2b$12$gumii4suft2vNgSo7mLm6OTJ0qaZUlTdKfxZ09QVNSxtdMzm8W8l6',
    'システム管理者',
    'admin@example.com',
    true,
    true
);