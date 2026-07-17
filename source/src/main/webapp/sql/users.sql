CREATE DATABASE IF NOT 'ysl1' EXISITS;


create database ysl1;
use ysl1;

-- ユーザー作成
CREATE USER 'ysl1'@'localhost' IDENTIFIED BY 'GvJ28VvAB9AFkRKa';

-- 権限を付与（全DB・全テーブルへのフルアクセス）
GRANT ALL PRIVILEGES ON *.* TO 'ysl1'@'localhost';

-- 反映
FLUSH PRIVILEGES;

create table users (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
login_id VARCHAR(30) NOT NULL UNIQUE,
login_pw VARCHAR(1000) NOT NULL CHECK (CHAR_LENGTH(login_pw) >= 6),
user_name VARCHAR(30) NOT NULL,
mail_address VARCHAR(50),
authority BOOLEAN NOT NULL,
active BOOLEAN NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    'tanaka456',
    'password',
    '田中恵梨香',
    'tanaka@example.com',
    false,
    true
),
(
    'sato_hanako',
    'hanako2024',
    '佐藤 花子',
    'sato@example.com',
    true,
    true
),
(
    'admin001',
    'adminpass999',
    'システム管理者',
    'admin@example.com',
    false,
    true
);
