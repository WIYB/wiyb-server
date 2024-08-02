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
    id            bigint       not null
        primary key,
    brand_id      bigint       not null,
    name          varchar(255) not null,
    type          varchar(255) not null,
    released_year varchar(255) not null,
    image_urls    text         null,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,

    -- Brand fk
    constraint fk_equipments_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists equipment_details
(
    id                 bigint       not null
        primary key,
    equipment_id       bigint       not null,
    color              varchar(255) null,
    weight             float        null,
    head_produce_type  varchar(255) null,
    head_design_type   varchar(255) null,
    head_number        int          null,
    head_shape         varchar(255) null,
    head_difficulty    varchar(255) null,
    head_loft_degree   varchar(255) null,
    driver_volume      float        null,
    iron_7_loft_degree varchar(255) null,
    iron_p_loft_degree varchar(255) null,
    putter_neck_shape  varchar(255) null,
    shaft_strength     varchar(255) null,
    shaft_kick_point   varchar(255) null,
    shaft_torque       float        null,
    shaft_texture      varchar(255) null,
    grip_type          varchar(255) null,
    grip_round         float        null,
    ball_piece         int          null,
    ball_cover         varchar(255) null,
    created_at         datetime(6)  not null,
    updated_at         datetime(6)  not null,
    deleted_at         datetime(6)  null,

    -- Equipment fk
    constraint fk_equipment_details_equipments_equipment_id foreign key (equipment_id) references equipments (id)
);

create table if not exists equipment_reviews
(
    id                bigint       not null
        primary key,
    user_id           bigint       not null,
    equipment_id      bigint       not null,
    evaluation_metric int          not null,
    content           varchar(255) not null,
    image_urls        text         null,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,

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
