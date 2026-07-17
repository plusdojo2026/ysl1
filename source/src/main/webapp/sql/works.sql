use ysl1;

create table works(
id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
user_id int not null,
task_id int not null,
work_date timestamp not null DEFAULT CURRENT_TIMESTAMP,
actual_hours decimal(5,1) not null,
work_description varchar(1000),
FOREIGN KEY (user_id) REFERENCES users(id)
ON UPDATE CASCADE
ON DELETE RESTRICT,
FOREIGN KEY (task_id) REFERENCES tasks(id)
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
(
    1,
    1,
    '2026-07-05 17:30:00',
    4.0,
    '顧客ヒアリング実施および議事録作成'
),
(
    2,
    2,
    '2026-08-03 18:00:00',
    6.5,
    '在庫一覧画面と検索機能の設計'
),
(
    3,
    3,
    '2026-06-10 16:45:00',
    3.0,
    '結合テスト実施および不具合確認'
);