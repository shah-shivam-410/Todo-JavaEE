CREATE DATABASE tododb;

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO tasks (title, description, completed) VALUES
    ('Learn Java Servlets', 'Study servlet basics and lifecycle', false),
    ('Setup PostgreSQL', 'Install and configure PostgreSQL database', true),
    ('Create FreeMarker templates', 'Design HTML templates using FreeMarker', false
);

drop table tasks;

select * from tasks;