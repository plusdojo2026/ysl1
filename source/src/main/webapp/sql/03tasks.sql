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