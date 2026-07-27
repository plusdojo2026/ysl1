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

    CONSTRAINT uk_works_user_task_date
        UNIQUE (user_id, task_id, work_date),

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