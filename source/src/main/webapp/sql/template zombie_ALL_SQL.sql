
drop table works;
drop table tasks;
drop table cases;
drop table users;

-- 使用するデータベースを指定
USE ysl1;

-- DB接続用ユーザー作成
-- CREATE USER IF NOT EXISTS 'ysl1'@'localhost'
-- IDENTIFIED BY '<DB_PASSWORD>';

-- ysl1データベースに対する権限を付与
-- GRANT ALL PRIVILEGES
-- ON ysl1.*
-- TO 'ysl1'@'localhost';

-- 権限を反映
-- FLUSH PRIVILEGES;

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

delete from users;
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


-- case
USE ysl1;

CREATE TABLE IF NOT EXISTS cases (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

    case_name VARCHAR(100) NOT NULL ,

    case_code VARCHAR(20) NOT NULL UNIQUE,

    customer_name VARCHAR(30),

    case_priority VARCHAR(10) NOT NULL DEFAULT '中',

    pm_id INT,

    case_status VARCHAR(10) NOT NULL DEFAULT '進行中',

    start_date TIMESTAMP NULL,

    planned_end_date TIMESTAMP NULL,

    case_description VARCHAR(1000),

    case_planned_hours DECIMAL(10,1),

    CONSTRAINT chk_cases_planned_hours
        CHECK (
            case_planned_hours IS NULL
            OR case_planned_hours >= 0
        ),

    CONSTRAINT chk_cases_date_range
        CHECK (
            start_date IS NULL
            OR planned_end_date IS NULL
            OR planned_end_date >= start_date
        ),

    CONSTRAINT fk_cases_pm
        FOREIGN KEY (pm_id)
        REFERENCES users(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

INSERT IGNORE INTO cases (
    case_name,
    case_code,
    customer_name,
    case_priority,
    pm_id,
    case_status,
    start_date,
    planned_end_date,
    case_description,
    case_planned_hours
) VALUES
(
    '販売管理システム刷新',
    'CASE001',
    '株式会社ABC商事',
    '高',
    1,
    '進行中',
    '2026-07-01 09:00:00',
    '2026-09-30 18:00:00',
    '既存販売管理システムのリニューアル案件',
    500
),
(
    '在庫管理アプリ開発',
    'CASE002',
    '株式会社XYZ物流',
    '中',
    2,
    '未着手',
    '2026-08-01 09:00:00',
    '2026-11-30 18:00:00',
    'スマートフォン向け在庫管理アプリの新規開発',
    350
),
(
    '顧客ポータル機能追加',
    'CASE003',
    '株式会社サンプルサービス',
    '低',
    3,
    '完了',
    '2026-04-01 09:00:00',
    '2026-06-30 18:00:00',
    '既存顧客ポータルへの問い合わせ管理機能追加',
    200
);

INSERT INTO cases (
    case_name,
    case_code,
    customer_name,
    case_priority,
    pm_id,
    case_status,
    start_date,
    planned_end_date,
    case_description,
    case_planned_hours
) VALUES
(
    '学園イベント管理システム',
    'CHARA-C001',
    '童実野学園',
    '中',
    @honda_id,
    '進行中',
    '2026-05-01 09:00:00',
    '2026-09-30 18:00:00',
    'イベントの日程、参加者、担当者を管理するシステム',
    180
),
(
    '動物健康管理システム',
    'CHARA-C002',
    '木ノ葉動物医療センター',
    '高',
    @kiba_id,
    '進行中',
    '2026-05-15 09:00:00',
    '2026-10-31 18:00:00',
    '動物の健康状態、診療履歴、投薬状況を管理するシステム',
    240
),
(
    '医療機器在庫管理システム',
    'CHARA-C003',
    '海洋医療研究所',
    '高',
    @natsuki_id,
    '進行中',
    '2026-06-01 09:00:00',
    '2026-11-30 18:00:00',
    '医療機器の在庫、貸出、点検履歴を管理するシステム',
    300
),
(
    '広告制作進捗管理システム',
    'CHARA-C004',
    '目黒広告社',
    '高',
    @kamiya_id,
    '進行中',
    '2026-05-20 09:00:00',
    '2026-11-15 18:00:00',
    '広告制作の工程、担当者、レビュー状況を管理するシステム',
    280
),
(
    '機器メンテナンス管理システム',
    'CHARA-C005',
    'レジスタンス技術部',
    '中',
    @devola_id,
    '進行中',
    '2026-04-01 09:00:00',
    '2026-09-15 18:00:00',
    '機器の点検履歴、修理履歴、交換部品を管理するシステム',
    220
);

-- tasks

USE ysl1;

CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

    case_id INT NOT NULL,

    task_name VARCHAR(100) NOT NULL,

    manager_id INT,

    task_status VARCHAR(10) NOT NULL DEFAULT '未着手',

    task_priority VARCHAR(10) NOT NULL DEFAULT '中',

    deadline TIMESTAMP NULL,

    progress_rate INT NOT NULL DEFAULT 0,

    start_date TIMESTAMP NULL,

    task_planned_hours DECIMAL(5, 1),

    task_description VARCHAR(1000),

    CONSTRAINT chk_tasks_progress_rate
        CHECK (progress_rate BETWEEN 0 AND 100),

    CONSTRAINT chk_tasks_planned_hours
        CHECK (
            task_planned_hours IS NULL
            OR task_planned_hours >= 0
        ),

    CONSTRAINT uk_tasks_case_name
        UNIQUE (case_id, task_name),

    CONSTRAINT fk_tasks_case
        FOREIGN KEY (case_id)
        REFERENCES cases(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_tasks_manager
        FOREIGN KEY (manager_id)
        REFERENCES users(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

INSERT INTO tasks (
    case_id,
    task_name,
    manager_id,
    task_status,
    task_priority,
    deadline,
    progress_rate,
    start_date,
    task_planned_hours,
    task_description
) VALUES

-- -----------------------------------------------------
-- CHARA-C001 学園イベント管理システム
-- 完了2件 / 5件 = 40%
-- -----------------------------------------------------
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C001'),
    'イベント要件定義',
    (SELECT id FROM users WHERE login_id = 'anime_honda'),
    '完了', '高', '2026-05-31 18:00:00', 100,
    '2026-05-01 09:00:00', 24.0,
    'イベント管理に必要な機能と画面を整理する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C001'),
    'イベント一覧画面の作成',
    (SELECT id FROM users WHERE login_id = 'anime_rika'),
    '進行中', '中', '2026-08-15 18:00:00', 60,
    '2026-06-01 09:00:00', 40.0,
    'イベント一覧と検索機能を作成する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C001'),
    '参加者登録機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_otogi'),
    '進行中', '中', '2026-09-10 18:00:00', 10,
    '2026-07-20 09:00:00', 36.0,
    'イベント参加者を登録する機能を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C001'),
    'イベント詳細画面の作成',
    (SELECT id FROM users WHERE login_id = 'anime_rika'),
    '完了', '中', '2026-07-31 18:00:00', 100,
    '2026-06-15 09:00:00', 28.0,
    'イベントの詳細情報を表示する画面を作成する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C001'),
    'イベント通知機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_honda'),
    '進行中', '低', '2026-09-20 18:00:00', 40,
    '2026-07-15 09:00:00', 24.0,
    '開催予定のイベントを利用者へ通知する'
),

-- -----------------------------------------------------
-- CHARA-C002 動物健康管理システム
-- 完了3件 / 5件 = 60%
-- -----------------------------------------------------
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C002'),
    '健康管理データベース設計',
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    '完了', '高', '2026-06-30 18:00:00', 100,
    '2026-05-15 09:00:00', 30.0,
    '動物情報と診療履歴のテーブルを設計する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C002'),
    '診療履歴登録機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_kiba'),
    '進行中', '高', '2026-08-20 18:00:00', 75,
    '2026-06-20 09:00:00', 48.0,
    '診療内容と健康状態を登録する機能を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C002'),
    '診療履歴検索画面の作成',
    (SELECT id FROM users WHERE login_id = 'anime_naho'),
    '進行中', '中', '2026-09-20 18:00:00', 25,
    '2026-07-15 09:00:00', 32.0,
    '動物名と診療日で検索する画面を作成する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C002'),
    '投薬情報登録機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_kiba'),
    '完了', '高', '2026-07-20 18:00:00', 100,
    '2026-06-10 09:00:00', 36.0,
    '薬の種類、投薬量、投薬日時を登録する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C002'),
    '健康状態レポートの作成',
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    '完了', '中', '2026-08-10 18:00:00', 100,
    '2026-06-25 09:00:00', 30.0,
    '健康状態を期間別に集計して表示する'
),

-- -----------------------------------------------------
-- CHARA-C003 医療機器在庫管理システム
-- 完了1件 / 5件 = 20%
-- -----------------------------------------------------
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C003'),
    '医療機器一覧画面の作成',
    (SELECT id FROM users WHERE login_id = 'anime_natsuki'),
    '進行中', '高', '2026-08-10 18:00:00', 80,
    '2026-06-01 09:00:00', 36.0,
    '医療機器の一覧と検索機能を作成する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C003'),
    '機器貸出機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_genma'),
    '進行中', '中', '2026-09-10 18:00:00', 45,
    '2026-07-10 09:00:00', 44.0,
    '医療機器の貸出と返却を登録する機能を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C003'),
    '在庫不足通知機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_honda'),
    '未着手', '中', '2026-10-15 18:00:00', 0,
    NULL, 28.0,
    '在庫数が基準値を下回ったときに通知する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C003'),
    '医療機器マスタの作成',
    (SELECT id FROM users WHERE login_id = 'anime_natsuki'),
    '完了', '高', '2026-06-30 18:00:00', 100,
    '2026-06-01 09:00:00', 26.0,
    '医療機器の名称、分類、保管場所を管理する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C003'),
    '機器点検履歴画面の作成',
    (SELECT id FROM users WHERE login_id = 'anime_genma'),
    '進行中', '中', '2026-09-30 18:00:00', 30,
    '2026-07-20 09:00:00', 32.0,
    '医療機器の点検履歴を一覧表示する'
),

-- -----------------------------------------------------
-- CHARA-C004 広告制作進捗管理システム
-- 完了3件 / 5件 = 60%
-- -----------------------------------------------------
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C004'),
    '制作工程マスタの作成',
    (SELECT id FROM users WHERE login_id = 'anime_kamiya'),
    '完了', '高', '2026-06-30 18:00:00', 100,
    '2026-05-20 09:00:00', 20.0,
    '広告制作で使用する工程マスタを作成する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C004'),
    '制作進捗画面の実装',
    (SELECT id FROM users WHERE login_id = 'anime_rika'),
    '進行中', '高', '2026-08-31 18:00:00', 65,
    '2026-07-05 09:00:00', 50.0,
    '制作物ごとの進捗を表示する画面を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C004'),
    'レビュー承認機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_otogi'),
    '進行中', '中', '2026-09-30 18:00:00', 30,
    '2026-07-18 09:00:00', 38.0,
    '成果物へのコメントと承認機能を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C004'),
    '担当者割当機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_kamiya'),
    '完了', '高', '2026-07-15 18:00:00', 100,
    '2026-06-10 09:00:00', 30.0,
    '広告制作工程ごとに担当者を割り当てる'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C004'),
    '制作期限通知機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_otogi'),
    '完了', '中', '2026-08-15 18:00:00', 100,
    '2026-07-01 09:00:00', 26.0,
    '制作期限が近いタスクを担当者へ通知する'
),

-- -----------------------------------------------------
-- CHARA-C005 機器メンテナンス管理システム
-- 完了3件 / 5件 = 60%
-- -----------------------------------------------------
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C005'),
    '点検項目マスタの作成',
    (SELECT id FROM users WHERE login_id = 'anime_devola'),
    '完了', '中', '2026-05-31 18:00:00', 100,
    '2026-04-10 09:00:00', 20.0,
    '機器ごとの点検項目を登録するマスタを作成する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C005'),
    '点検履歴登録機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    '進行中', '高', '2026-07-31 18:00:00', 90,
    '2026-05-20 09:00:00', 42.0,
    '点検内容と点検結果を登録する機能を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C005'),
    '修理履歴検索機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_genma'),
    '進行中', '中', '2026-08-31 18:00:00', 50,
    '2026-07-10 09:00:00', 34.0,
    '機器名と修理日で履歴を検索する機能を実装する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C005'),
    '交換部品マスタの作成',
    (SELECT id FROM users WHERE login_id = 'anime_devola'),
    '完了', '中', '2026-06-20 18:00:00', 100,
    '2026-05-15 09:00:00', 22.0,
    '交換部品の名称、型番、在庫数を管理する'
),
(
    (SELECT id FROM cases WHERE case_code = 'CHARA-C005'),
    '定期点検通知機能の実装',
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    '完了', '高', '2026-07-20 18:00:00', 100,
    '2026-06-15 09:00:00', 28.0,
    '次回点検日が近い機器を通知する'
);

-- works
USE ysl1;

CREATE TABLE IF NOT EXISTS works (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

    user_id INT NOT NULL,

    task_id INT NOT NULL,

    work_date TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    actual_hours DECIMAL(5, 1) NOT NULL,

    work_description VARCHAR(1000),

    CONSTRAINT chk_works_actual_hours
        CHECK (actual_hours >= 0 AND actual_hours <= 24),


    CONSTRAINT fk_works_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_works_task
        FOREIGN KEY (task_id)
        REFERENCES tasks(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

INSERT INTO works (
    user_id,
    task_id,
    work_date,
    actual_hours,
    work_description
) VALUES

-- =====================================================
-- CHARA-C001 学園イベント管理システム 5件
-- =====================================================
(
    (SELECT id FROM users WHERE login_id = 'anime_honda'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C001' AND t.task_name = 'イベント要件定義'),
    '2026-05-08 09:00:00', 4.0,
    'イベント管理要件のヒアリング'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_rika'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C001' AND t.task_name = 'イベント一覧画面の作成'),
    '2026-06-08 09:00:00', 6.0,
    'イベント一覧画面のレイアウト作成'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_otogi'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C001' AND t.task_name = '参加者登録機能の実装'),
    '2026-07-21 09:00:00', 2.5,
    '参加者登録機能の仕様確認'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_rika'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C001' AND t.task_name = 'イベント詳細画面の作成'),
    '2026-07-24 09:00:00', 5.0,
    'イベント詳細画面の実装と確認'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_honda'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C001' AND t.task_name = 'イベント通知機能の実装'),
    '2026-07-27 09:00:00', 3.5,
    'イベント通知条件の設計'
),

-- =====================================================
-- CHARA-C002 動物健康管理システム 5件
-- =====================================================
(
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C002' AND t.task_name = '健康管理データベース設計'),
    '2026-05-20 09:00:00', 6.0,
    '健康管理テーブルと診療履歴テーブルの設計'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_kiba'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C002' AND t.task_name = '診療履歴登録機能の実装'),
    '2026-06-25 09:00:00', 7.0,
    '診療履歴登録処理の実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_naho'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C002' AND t.task_name = '診療履歴検索画面の作成'),
    '2026-07-18 09:00:00', 3.0,
    '診療履歴検索画面の項目作成'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_kiba'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C002' AND t.task_name = '投薬情報登録機能の実装'),
    '2026-06-18 09:00:00', 5.5,
    '投薬情報登録処理と入力チェックの実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C002' AND t.task_name = '健康状態レポートの作成'),
    '2026-07-23 09:00:00', 4.5,
    '健康状態レポートの集計処理を作成'
),

-- =====================================================
-- CHARA-C003 医療機器在庫管理システム 5件
-- =====================================================
(
    (SELECT id FROM users WHERE login_id = 'anime_natsuki'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C003' AND t.task_name = '医療機器一覧画面の作成'),
    '2026-06-05 09:00:00', 7.5,
    '医療機器一覧の表示処理を実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_genma'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C003' AND t.task_name = '機器貸出機能の実装'),
    '2026-07-13 09:00:00', 6.5,
    '機器貸出登録処理の実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_honda'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C003' AND t.task_name = '在庫不足通知機能の実装'),
    '2026-07-28 09:00:00', 2.0,
    '在庫不足通知機能の事前調査'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_natsuki'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C003' AND t.task_name = '医療機器マスタの作成'),
    '2026-06-15 09:00:00', 5.0,
    '医療機器マスタの登録とテスト'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_genma'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C003' AND t.task_name = '機器点検履歴画面の作成'),
    '2026-07-20 09:00:00', 4.0,
    '機器点検履歴画面のレイアウト作成'
),

-- =====================================================
-- CHARA-C004 広告制作進捗管理システム 5件
-- =====================================================
(
    (SELECT id FROM users WHERE login_id = 'anime_kamiya'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C004' AND t.task_name = '制作工程マスタの作成'),
    '2026-05-25 09:00:00', 5.0,
    '制作工程マスタの項目設計'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_rika'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C004' AND t.task_name = '制作進捗画面の実装'),
    '2026-07-10 09:00:00', 7.0,
    '制作進捗一覧画面の作成'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_otogi'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C004' AND t.task_name = 'レビュー承認機能の実装'),
    '2026-07-22 09:00:00', 4.5,
    'レビューコメント登録処理の実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_kamiya'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C004' AND t.task_name = '担当者割当機能の実装'),
    '2026-06-12 09:00:00', 5.5,
    '工程別の担当者割当処理を実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_otogi'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C004' AND t.task_name = '制作期限通知機能の実装'),
    '2026-07-15 09:00:00', 4.0,
    '制作期限通知の判定処理を実装'
),

-- =====================================================
-- CHARA-C005 機器メンテナンス管理システム 5件
-- =====================================================
(
    (SELECT id FROM users WHERE login_id = 'anime_devola'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C005' AND t.task_name = '点検項目マスタの作成'),
    '2026-04-15 09:00:00', 4.0,
    '点検項目マスタの設計'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C005' AND t.task_name = '点検履歴登録機能の実装'),
    '2026-06-22 09:00:00', 7.5,
    '点検履歴登録処理の実装'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_genma'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C005' AND t.task_name = '修理履歴検索機能の実装'),
    '2026-07-16 09:00:00', 5.5,
    '修理履歴の検索SQLを作成'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_devola'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C005' AND t.task_name = '交換部品マスタの作成'),
    '2026-05-18 09:00:00', 4.5,
    '交換部品マスタの登録と確認'
),
(
    (SELECT id FROM users WHERE login_id = 'anime_shino'),
    (SELECT t.id FROM tasks t INNER JOIN cases c ON t.case_id = c.id
     WHERE c.case_code = 'CHARA-C005' AND t.task_name = '定期点検通知機能の実装'),
    '2026-07-08 09:00:00', 5.0,
    '定期点検通知の対象抽出処理を実装'
);