use ysl1;

create table tasks(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
case_id int not null,
task_name varchar(100) not null,
manager_id int,
task_status varchar(10) not null default '未着手',
task_priority varchar(10) not null default '中',
deadline timestamp,
progress_rate int default 0,
start_date timestamp,
task_planned_hours decimal(5,1),
task_description varchar(1000),
FOREIGN KEY (case_id) REFERENCES cases(id)
ON UPDATE CASCADE
ON DELETE RESTRICT,
FOREIGN KEY (manager_id) REFERENCES users(id)
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
(
    1,
    '要件定義',
    1,
    '進行中',
    '高',
    '2026-07-31 18:00:00',
    60,
    '2026-07-01 09:00:00',
    80.0,
    '顧客ヒアリングおよび業務要件の整理'
),
(
    2,
    '画面設計',
    2,
    '未着手',
    '中',
    '2026-08-31 18:00:00',
    0,
    '2026-08-01 09:00:00',
    40.0,
    '在庫管理アプリの主要画面設計を実施'
),
(
    3,
    '結合テスト',
    3,
    '完了',
    '低',
    '2026-06-20 18:00:00',
    100,
    '2026-06-01 09:00:00',
    30.5,
    '追加機能の結合テストおよび不具合確認'
);