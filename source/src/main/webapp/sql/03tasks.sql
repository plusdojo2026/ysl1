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
    '追加機能の結合テストおよび不具合確認');