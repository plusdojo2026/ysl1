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