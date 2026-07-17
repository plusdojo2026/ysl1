use ysl1;

create table cases(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
case_name varchar(100) not null unique,
case_code varchar(20) not null unique,
customer_name varchar(30),
case_priority varchar(10) not null default '中',
pm_id int,
case_status varchar(10) not null default '進行中',
start_date timestamp,
planned_end_date timestamp,
case_description varchar(1000),
case_planned_hours int,
FOREIGN KEY (pm_id) REFERENCES users(id)
ON UPDATE CASCADE
ON DELETE RESTRICT
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