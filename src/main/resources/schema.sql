CREATE TABLE pride_ticket_mh.users
(
    id          BIGSERIAL PRIMARY KEY,
    full_name   VARCHAR(150) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    mobile      VARCHAR(20),
    designation VARCHAR(100),
    office      VARCHAR(150),
    active_flag BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  int8 NULL,
    updated_by  int8 NULL,
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE pride_ticket_mh.role
(
    id          int8        NOT NULL,
    role_name   text        NOT NULL,
    active_flag int2 NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  int8 NULL,
    updated_by  int8 NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);



CREATE TABLE pride_ticket_mh.user_roles
(
    user_id int8 NOT NULL,
    role_id int8 NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES pride_ticket_mh.roles (id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES pride_ticket_mh.users (id) ON DELETE CASCADE
);


DROP TABLE pride_ticket_mh.users cascade;

CREATE TABLE pride_ticket_mh.users
(
    id          bigserial    NOT NULL,
    full_name   varchar(150) NOT NULL,
    email       varchar(255) NOT NULL,
    "password"  varchar(255) NOT NULL,
    mobile      varchar(20) NULL,
    designation varchar(100) NULL,
    office      varchar(150) NULL,
    active_flag int2 NULL,
    created_at  timestamptz DEFAULT now() NULL,
    updated_at  timestamptz DEFAULT now() NULL,
    created_by  int8 NULL,
    updated_by  int8 NULL,
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);


CREATE TABLE pride_ticket_mh.client_org
(
    id          bigserial    NOT NULL,
    org_name    varchar(150) NOT NULL,

    active_flag int2 NULL,
    created_at  timestamptz DEFAULT now() NULL,
    updated_at  timestamptz DEFAULT now() NULL,
    created_by  int8 NULL,
    updated_by  int8 NULL,

    CONSTRAINT client_org_pk PRIMARY KEY (id)
);

CREATE TABLE pride_ticket_mh.projects
(
    id           bigserial    NOT NULL,
    project_name varchar(150) NOT NULL,
    project_code varchar(50) NULL,
    description  TEXT NULL,
    active_flag  int2 NULL,
    created_at   timestamptz DEFAULT now() NULL,
    updated_at   timestamptz DEFAULT now() NULL,
    created_by   int8 NULL,
    updated_by   int8 NULL,
    CONSTRAINT uq_project_code UNIQUE (project_code),
    CONSTRAINT projects_pk PRIMARY KEY (id)
);

CREATE TABLE pride_ticket_mh.project_memberships
(
    id          bigserial NOT NULL,
    project_id  int8      NOT NULL,
    user_id     int8      NOT NULL,

    active_flag int2 null default 1,
    created_at  timestamptz DEFAULT now() NULL,
    updated_at  timestamptz DEFAULT now() NULL,
    created_by  int8 NULL,
    updated_by  int8 NULL,

    CONSTRAINT project_memberships_pk PRIMARY KEY (id),
    CONSTRAINT fk_projects FOREIGN KEY (project_id) REFERENCES pride_ticket_mh.projects (id)

);

DROP TABLE pride_ticket_mh.modules;

CREATE TABLE pride_ticket_mh.modules
(
    id          BIGSERIAL PRIMARY KEY,
    project_id  int8         NOT NULL,
    module_name VARCHAR(100) NOT NULL,

    active_flag SMALLINT    DEFAULT 1,
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW(),
    created_by  BIGINT,
    updated_by  BIGINT,

    CONSTRAINT uq_project_module UNIQUE (project_id, module_name),
    CONSTRAINT fk_projects FOREIGN KEY (project_id)
        REFERENCES pride_ticket_mh.projects (id) ON DELETE CASCADE
);

CREATE TABLE pride_ticket_mh.issues
(
    id                  BIGSERIAL PRIMARY KEY,

    title               VARCHAR(255) NOT NULL,
    description         TEXT,
    type                VARCHAR(50)  NOT NULL,
    priority            VARCHAR(20)  NOT NULL,
    stage               VARCHAR(30)  NOT NULL,
    verification_status VARCHAR(30),

    project_id          int8         NOT NULL,
    module_id           int8,
    reporter_id         int8         NOT NULL,

    active_flag         int2        DEFAULT 1,
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW(),
    created_by          BIGINT,
    updated_by          BIGINT,

    CONSTRAINT fk_issues_project FOREIGN KEY (project_id)
        REFERENCES pride_ticket_mh.projects (id) ON DELETE RESTRICT,

    CONSTRAINT fk_issues_module FOREIGN KEY (module_id)
        REFERENCES pride_ticket_mh.modules (id) ON DELETE RESTRICT,

    CONSTRAINT fk_issues_reporter FOREIGN KEY (reporter_id)
        REFERENCES pride_ticket_mh.users (id) ON DELETE RESTRICT
);

