-- ユーザー作成
CREATE USER 'ysl1'@'localhost' IDENTIFIED BY 'GvJ28VvAB9AFkRKa';

-- 権限を付与（全DB・全テーブルへのフルアクセス）
GRANT ALL PRIVILEGES ON *.* TO 'ysl1'@'localhost';

-- 反映
FLUSH PRIVILEGES;
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
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '田中恵梨香',
    'tanaka@example.com',
    false,
    true
),
(
    'sato_hanako',
    '$2a$12$UjsS9xhZ5PypWlaMwMyAWOPuxDJBGC5H2oaRz2lmsXqjTpTI2L/lq',
    '佐藤 花子',
    'sato@example.com',
    true,
    true
),
(
    'admin001',
    '$2a$12$bIMk7jQ9JzBb0Vy36Cu1suxaZo4WIJvOdMOCdEuB9yRJboFHxZ8VK',
    'システム管理者',
    'admin@example.com',
    true,
    true
);

INSERT INTO users (
    login_id,
    login_pw,
    user_name,
    mail_address,
    authority,
    active
) VALUES
(
    'anime_honda',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '本田 ヒロト',
    'honda@anime-demo.example',
    FALSE,
    TRUE
),
(
    'anime_kiba',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '犬塚 キバ',
    'kiba@anime-demo.example',
    FALSE,
    TRUE
),
(
    'anime_naho',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '高田 なほ',
    'naho@anime-demo.example',
    FALSE,
    TRUE
),
(
    'anime_natsuki',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '安岐 ナツキ',
    'natsuki@anime-demo.example',
    TRUE,
    TRUE
),
(
    'anime_shino',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '油女 シノ',
    'shino@anime-demo.example',
    FALSE,
    TRUE
),
(
    'anime_genma',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '不知火 ゲンマ',
    'genma@anime-demo.example',
    TRUE,
    TRUE
),
(
    'anime_kamiya',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '神谷 雄介',
    'kamiya@anime-demo.example',
    TRUE,
    TRUE
),
(
    'anime_rika',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '篠崎 里香',
    'rika@anime-demo.example',
    FALSE,
    TRUE
),
(
    'anime_otogi',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    '御伽 龍児',
    'otogi@anime-demo.example',
    FALSE,
    TRUE
),
(
    'anime_devola',
    '$2a$12$Sqh1UTh8D5j9ixHedaNkHO.f8.jmyc4dU2akAXNOWZKiTWHhRta8y',
    'デボル',
    'devola@anime-demo.example',
    FALSE,
    TRUE
);

SET @honda_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_honda'
);

SET @kiba_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_kiba'
);

SET @naho_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_naho'
);

SET @natsuki_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_natsuki'
);

SET @shino_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_shino'
);

SET @genma_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_genma'
);

SET @kamiya_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_kamiya'
);

SET @rika_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_rika'
);

SET @otogi_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_otogi'
);

SET @devola_id = (
    SELECT id FROM users
    WHERE login_id = 'anime_devola'
);
