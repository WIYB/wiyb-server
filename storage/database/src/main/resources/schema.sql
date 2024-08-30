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
    email           varchar(255) null,
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
    name_ko    varchar(255) null,
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

create table if not exists drivers
(
    id                   bigint       not null
        primary key,
    volume               float        null,
    loft_degree          varchar(255) null,
    is_loft_changeable   boolean      null,
    is_weight_changeable boolean      null,
    created_at           datetime(6)  not null,
    updated_at           datetime(6)  not null,
    deleted_at           datetime(6)  null,

    -- Equipment fk
    constraint fk_drivers_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists woods
(
    id                   bigint       not null
        primary key,
    numbers              varchar(255) null,
    loft_degree          varchar(255) null,
    lie_angle            varchar(255) null,
    is_loft_changeable   boolean      null,
    is_weight_changeable boolean      null,
    created_at           datetime(6)  not null,
    updated_at           datetime(6)  not null,
    deleted_at   datetime(6)  null,

    -- Equipment fk
    constraint fk_woods_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists hybrids
(
    id                   bigint       not null
        primary key,
    numbers              varchar(255) null,
    loft_degree          varchar(255) null,
    lie_angle            varchar(255) null,
    is_loft_changeable   boolean      null,
    is_weight_changeable boolean      null,
    created_at           datetime(6)  not null,
    updated_at           datetime(6)  not null,
    deleted_at           datetime(6)  null,

    -- Equipment fk
    constraint fk_hybrids_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists irons
(
    id           bigint       not null
        primary key,
    produce_type  varchar(255) null,
    design_type   varchar(255) null,
    numbers       varchar(255) null,
    loft_degree   varchar(255) null,
    lie_angle     varchar(255) null,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,

    -- Equipment fk
    constraint fk_irons_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists wedges
(
    id           bigint       not null
        primary key,
    produce_type varchar(255) null,
    model        varchar(255) null,
    loft_degree  varchar(255) null,
    bounce       varchar(255) null,
    grind        varchar(255) null,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    deleted_at   datetime(6)  null,

    -- Equipment fk
    constraint fk_wedges_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists putters
(
    id           bigint       not null
        primary key,
    loft_degree  varchar(255) null,
    weight       varchar(255) null,
    neck_shape   varchar(255) null,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    deleted_at   datetime(6)  null,

    -- Equipment fk
    constraint fk_putters_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists shafts
(
    id            bigint       not null
        primary key,
    weight        varchar(255) null,
    strength      varchar(255) null,
    kick_point    varchar(255) null,
    torque        varchar(255) null,
    texture       varchar(255) null,
    tip_diameter  varchar(255) null,
    butt_diameter varchar(255) null,
    spin          varchar(255) null,
    launch        varchar(255) null,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,

    -- Equipment fk
    constraint fk_shafts_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists grips
(
    id           bigint       not null
        primary key,
    weight       varchar(255) null,
    size         varchar(255) null,
    core_size    varchar(255) null,
    feel         varchar(255) null,
    material     varchar(255) null,
    torque       varchar(255) null,
    diameter     varchar(255) null,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    deleted_at   datetime(6)  null,

    -- Equipment fk
    constraint fk_grips_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists balls
(
    id           bigint       not null
        primary key,
    piece        varchar(255) null,
    spin         varchar(255) null,
    launch       varchar(255) null,
    feel         varchar(255) null,
    dimple       varchar(255) null,
    texture      varchar(255) null,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    deleted_at   datetime(6)  null,

    -- Equipment fk
    constraint fk_balls_equipments_equipment_id foreign key (id) references equipments (id)
);

create table if not exists equipment_reviews
(
    id                bigint       not null
        primary key,
    user_id           bigint       not null,
    equipment_id      bigint       not null,
    evaluation_metric varchar(255) not null,
    like_count        int          not null,
    content           text         null,
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

create table if not exists user_equipment_review_likes
(
    id                  bigint      not null
        primary key,
    user_id             bigint      not null,
    equipment_review_id bigint      not null,
    created_at          datetime(6) not null,
    updated_at          datetime(6) not null,
    deleted_at          datetime(6) null,

    -- User fk
    constraint fk_user_equipment_review_likes_users_id foreign key (user_id) references users (id),

    -- Equipment fk
    constraint fk_user_equipment_review_likes_equipment_reviews_id foreign key (equipment_review_id) references equipment_reviews (id)
);

create table if not exists user_equipment_bookmarks
(
    id              bigint      not null
        primary key,
    user_id         bigint      not null,
    equipment_id    bigint      not null,
    created_at      datetime(6) not null,
    updated_at      datetime(6) not null,
    deleted_at      datetime(6) null,

    -- User fk
    constraint fk_user_equipment_bookmarks_users_user_id foreign key (user_id) references users (id),

    -- Equipment fk
    constraint fk_user_equipment_bookmarks_equipments_equipment_id foreign key (equipment_id) references equipments (id)
);
