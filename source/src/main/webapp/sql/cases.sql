USE ysl1;

CREATE TABLE IF NOT EXISTS cases (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,

    case_name VARCHAR(100) NOT NULL UNIQUE,

    case_code VARCHAR(20) NOT NULL UNIQUE,

    customer_name VARCHAR(30),

    case_priority VARCHAR(10) NOT NULL DEFAULT '中',

    pm_id INT,

    case_status VARCHAR(10) NOT NULL DEFAULT '進行中',

    start_date TIMESTAMP NULL,

    planned_end_date TIMESTAMP NULL,

    case_description VARCHAR(1000),

    case_planned_hours INT,

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