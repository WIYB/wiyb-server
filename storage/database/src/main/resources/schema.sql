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
-- #  Community
-- ##############################

create table if not exists posts
(
    id            bigint       not null
        primary key,
    author_id     bigint       not null,
    category      varchar(255) not null,
    title         varchar(255) not null,
    content       text         not null,
    view_count    bigint       not null,
    comment_count bigint       not null,
    image_urls    text         null,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,

    -- User fk
    constraint fk_post_users_id foreign key (author_id) references users (id)
);

create table if not exists comments
(
    id         bigint       not null
        primary key,
    author_id  bigint       not null,
    post_id    bigint       not null,
    content    text         not null,
    reply_id   bigint       null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null,

    -- User fk
    constraint fk_comment_users_id foreign key (author_id) references users (id),

    -- Post fk
    constraint fk_comment_posts_id foreign key (post_id) references posts (id)
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
    released_year           varchar(255) null,
    image_urls              text         null,
    created_at              datetime(6)  not null,
    updated_at              datetime(6)  not null,
    deleted_at              datetime(6)  null,

    -- Brand fk
    constraint fk_equipments_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists equipment_evaluated_metrics
(
    id               bigint      not null
        primary key,
    evaluated_count  bigint      not null,
    evaluated_total  float       not null,
    rating_weight    float       not null,
    forgiveness      float       null,
    distance         float       null,
    accuracy         float       null,
    impact_feel      float       null,
    impact_sound     float       null,
    backspin         float       null,
    distance_control float       null,
    stiffness        float       null,
    weight           float       null,
    trajectory       float       null,
    touch            float       null,
    grip_comfort     float       null,
    durability       float       null,
    created_at       datetime(6) not null,
    updated_at       datetime(6) not null,
    deleted_at       datetime(6) null,

    evaluated_average float as (case when evaluated_count > 0 then (evaluated_total / rating_weight) / evaluated_count else 0 end) stored,
    forgiveness_average float as (case when evaluated_count > 0 then forgiveness / evaluated_count else 0 end) stored,
    distance_average float as (case when evaluated_count > 0 then distance / evaluated_count else 0 end) stored,
    accuracy_average float as (case when evaluated_count > 0 then accuracy / evaluated_count else 0 end) stored,
    impact_feel_average float as (case when evaluated_count > 0 then impact_feel / evaluated_count else 0 end) stored,
    impact_sound_average float as (case when evaluated_count > 0 then impact_sound / evaluated_count else 0 end) stored,
    backspin_average float as (case when evaluated_count > 0 then backspin / evaluated_count else 0 end) stored,
    distance_control_average float as (case when evaluated_count > 0 then distance_control / evaluated_count else 0 end) stored,
    stiffness_average float as (case when evaluated_count > 0 then stiffness / evaluated_count else 0 end) stored,
    weight_average float as (case when evaluated_count > 0 then weight / evaluated_count else 0 end) stored,
    trajectory_average float as (case when evaluated_count > 0 then trajectory / evaluated_count else 0 end) stored,
    touch_average float as (case when evaluated_count > 0 then touch / evaluated_count else 0 end) stored,
    grip_comfort_average float as (case when evaluated_count > 0 then grip_comfort / evaluated_count else 0 end) stored,
    durability_average float as (case when evaluated_count > 0 then durability / evaluated_count else 0 end) stored,

    -- Equipment fk
    constraint fk_equipment_evaluated_metrics_equipments_id foreign key (id) references equipments (id),

    -- Index
    index idx_evaluated_count (evaluated_count),
    index idx_evaluated_average (evaluated_average),
    index idx_forgiveness_average (forgiveness_average),
    index idx_distance_average (distance_average),
    index idx_accuracy_average (accuracy_average),
    index idx_impact_feel_average (impact_feel_average),
    index idx_impact_sound_average (impact_sound_average),
    index idx_backspin_average (backspin_average),
    index idx_distance_control_average (distance_control_average),
    index idx_stiffness_average (stiffness_average),
    index idx_weight_average (weight_average),
    index idx_trajectory_average (trajectory_average),
    index idx_touch_average (touch_average),
    index idx_grip_comfort_average (grip_comfort_average),
    index idx_durability_average (durability_average)
);

create table if not exists drivers
(
    id                   bigint       not null
        primary key,
    volume               float        null,
    loft_degree          varchar(255) null,
    is_loft_changeable   boolean      null,
    is_weight_changeable boolean      null,
    is_weight_movable    boolean      null,
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
