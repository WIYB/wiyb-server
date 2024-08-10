-- ##############################
-- #  Auth
-- ##############################

create table if not exists users
(
    id         bigint       not null
        primary key,
    role       varchar(255) not null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists user_profiles
(
    id         bigint       not null
        primary key,
    user_id    bigint       not null,
    nickname   varchar(255) not null,
    gender     varchar(255) not null,
    birth      date         not null,
    handy      int          null,
    height     int          null,
    weight     int          null,
    image_url  varchar(255) null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null,

    -- User (1:1, uk, fk)
    constraint uk_user_profiles_user_id unique (user_id),
    constraint fk_user_profiles_users_user_id foreign key (user_id) references users (id)
);

-- ##############################
-- #  Auth
-- ##############################

create table if not exists accounts
(
    id              bigint       not null
        primary key,
    user_id         bigint       not null,
    social_provider varchar(255) not null,
    social_id       varchar(255) not null,
    email           varchar(255) not null,
    created_at      datetime(6)  not null,
    updated_at      datetime(6)  not null,
    deleted_at      datetime(6)  null,

    -- User fk
    constraint fk_accounts_users_user_id foreign key (user_id) references users (id)
);

create table if not exists authorizations
(
    id         bigint       not null
        primary key,
    user_id    bigint       not null,
    session_id varchar(255) not null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null,

    -- User fk
    constraint fk_authorizations_users_user_id foreign key (user_id) references users (id)
);


-- ##############################
-- #  Golf
-- ##############################

create table if not exists brands
(
    id         bigint       not null
        primary key,
    name       varchar(255) not null,
    image_url  varchar(255) null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists equipments
(
    id                      bigint       not null
        primary key,
    brand_id                bigint       not null,
    name                    varchar(255) not null,
    type                    varchar(255) not null,
    view_count              bigint       not null,
    evaluated_count         bigint       not null,
    evaluation_metric_total varchar(255) not null,
    released_year           varchar(255) null,
    image_urls              text         null,
    created_at              datetime(6)  not null,
    updated_at              datetime(6)  not null,
    deleted_at              datetime(6)  null,

    -- Brand fk
    constraint fk_equipments_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists equipment_details
(
    id                  bigint       not null
        primary key,
    equipment_id        bigint       not null,
    color               varchar(255) null,
    weight              varchar(255) null,
    launch              varchar(255) null,
    spin                varchar(255) null,
    gender              varchar(255) null,
    bounce              varchar(255) null,
    grind               varchar(255) null,
    head_produce_type   varchar(255) null,
    head_design_type    varchar(255) null,
    head_number         varchar(255) null,
    head_shape          varchar(255) null,
    head_difficulty     varchar(255) null,
    head_loft_degree    varchar(255) null,
    driver_volume       float        null,
    iron_7_loft_degree  varchar(255) null,
    iron_p_loft_degree  varchar(255) null,
    putter_neck_shape   varchar(255) null,
    shaft_strength      varchar(255) null,
    shaft_kick_point    varchar(255) null,
    shaft_torque        varchar(255) null,
    shaft_texture       varchar(255) null,
    shaft_bend          varchar(255) null,
    shaft_tip_diameter  varchar(255) null,
    shaft_butt_diameter varchar(255) null,
    grip_type           varchar(255) null,
    grip_round          float        null,
    ball_piece          int          null,
    ball_cover          varchar(255) null,
    ball_feel           varchar(255) null,
    ball_dimple         varchar(255) null,
    created_at          datetime(6)  not null,
    updated_at          datetime(6)  not null,
    deleted_at          datetime(6)  null,

    -- Equipment fk
    constraint fk_equipment_details_equipments_equipment_id foreign key (equipment_id) references equipments (id)
);

create table if not exists equipment_reviews
(
    id                bigint       not null
        primary key,
    user_id           bigint       not null,
    equipment_id      bigint       not null,
    content           text         not null,
    evaluation_metric varchar(255) null,
    image_urls        text         null,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,

    -- Unique
    constraint uk_equipment_reviews_user_id_equipment_id_deleted_at unique (user_id, equipment_id, deleted_at),

    -- User fk
    constraint fk_equipment_reviews_users_user_id foreign key (user_id) references users (id),

    -- Equipment fk
    constraint fk_equipment_reviews_equipments_equipment_id foreign key (equipment_id) references equipments (id)
);

create table if not exists user_equipments
(
    id              bigint      not null
        primary key,
    user_id         bigint      not null,
    equipment_id    bigint      not null,
    created_at      datetime(6) not null,
    updated_at      datetime(6) not null,
    deleted_at      datetime(6) null,

    -- User fk
    constraint fk_user_equipments_users_user_id foreign key (user_id) references users (id),

    -- Equipment fk
    constraint fk_user_equipments_equipments_equipment_id foreign key (equipment_id) references equipments (id)
);
