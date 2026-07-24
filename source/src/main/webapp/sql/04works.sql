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

INSERT IGNORE INTO works (
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