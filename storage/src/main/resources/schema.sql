##############################
#  User
##############################

drop database if exists wiyb;
create database if not exists wiyb;
use wiyb;

create table if not exists users
(
    id         bigint       not null
        primary key,
    created_at datetime(6)  not null,
    deleted_at datetime(6)  null,
    updated_at datetime(6)  not null,
    role       varchar(255) not null
);

create table if not exists user_profiles
(
    id         bigint       not null
        primary key,
    created_at datetime(6)  not null,
    deleted_at datetime(6)  null,
    updated_at datetime(6)  not null,
    birth      date         not null,
    gender     varchar(255) not null,
    handy      int          null,
    height     int          null,
    image_url  varchar(255) null,
    nickname   varchar(255) not null,
    weight     int          null,
    user_id    bigint       not null,

    # User (1:1, uk, fk)
    constraint uk_user_profiles_user_id unique (user_id),
    constraint fk_user_profiles_users_user_id foreign key (user_id) references users (id)
);

##############################
#  Auth
##############################

create table if not exists accounts
(
    id              bigint       not null
        primary key,
    created_at      datetime(6)  not null,
    deleted_at      datetime(6)  null,
    updated_at      datetime(6)  not null,
    email           varchar(255) not null,
    social_id       varchar(255) not null,
    social_provider varchar(255) not null,
    user_id         bigint       not null,

    # User fk
    constraint fk_accounts_users_user_id foreign key (user_id) references users (id)
);

create table if not exists authorizations
(
    id         bigint       not null
        primary key,
    created_at datetime(6)  not null,
    deleted_at datetime(6)  null,
    updated_at datetime(6)  not null,
    session_id varchar(255) not null,
    user_id    bigint       not null,

    # User fk
    constraint fk_authorizations_users_user_id foreign key (user_id) references users (id)
);


##############################
#  Golf
##############################

create table if not exists brands
(
    id         bigint       not null
        primary key,
    created_at datetime(6)  not null,
    deleted_at datetime(6)  null,
    updated_at datetime(6)  not null,
    image_url  varchar(255) null,
    name       varchar(255) not null
);

create table if not exists club_heads
(
    id                 bigint       not null
        primary key,
    created_at         datetime(6)  not null,
    deleted_at         datetime(6)  null,
    updated_at         datetime(6)  not null,
    image_urls         text         null,
    name               varchar(255) not null,
    released_year      varchar(255) not null,
    brand_id           bigint       not null,
    color              varchar(255) not null,
    design_type        varchar(255) not null,
    difficulty         varchar(255) not null,
    driver_volume      float        null,
    head_number        int          not null,
    head_shape         varchar(255) not null,
    head_type          varchar(255) not null,
    iron_7_loft_degree varchar(255) null,
    iron_p_loft_degree varchar(255) null,
    loft_degree        varchar(255) not null,
    produce_type       varchar(255) not null,
    putter_neck_shape  varchar(255) null,
    weight             float        not null,

    # Brand fk
    constraint fk_club_heads_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists club_shafts
(
    id            bigint       not null
        primary key,
    created_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,
    updated_at    datetime(6)  not null,
    image_urls    text         null,
    name          varchar(255) not null,
    released_year varchar(255) not null,
    brand_id      bigint       not null,
    kick_point    varchar(255) not null,
    strength      varchar(255) not null,
    texture       varchar(255) not null,
    torque        float        not null,
    weight        float        not null,

    # Brand fk
    constraint fk_club_shafts_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists club_grips
(
    id            bigint       not null
        primary key,
    created_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,
    updated_at    datetime(6)  not null,
    image_urls    text         null,
    name          varchar(255) not null,
    released_year varchar(255) not null,
    brand_id      bigint       not null,
    grip_type     varchar(255) not null,
    round         float        not null,
    weight        float        not null,

    # Brand fk
    constraint fk_club_grips_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists golf_balls
(
    id            bigint       not null
        primary key,
    created_at    datetime(6)  not null,
    deleted_at    datetime(6)  null,
    updated_at    datetime(6)  not null,
    image_urls    text         null,
    name          varchar(255) not null,
    released_year varchar(255) not null,
    brand_id      bigint       not null,
    cover         varchar(255) not null,
    piece         int          not null,

    # Brand fk
    constraint fk_golf_balls_brands_brand_id foreign key (brand_id) references brands (id)
);

create table if not exists golf_other_equipments
(
    id             bigint       not null
        primary key,
    created_at     datetime(6)  not null,
    deleted_at     datetime(6)  null,
    updated_at     datetime(6)  not null,
    image_urls     text         null,
    name           varchar(255) not null,
    released_year  varchar(255) not null,
    brand_id       bigint       not null,
    equipment_type varchar(255) not null,

    # Brand fk
    constraint fk_golf_other_equipments_brands_brand_id foreign key (brand_id) references brands (id)
);


##############################
#  Review
##############################

create table if not exists club_head_reviews
(
    id                bigint       not null
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    updated_at        datetime(6)  not null,
    content           varchar(255) not null,
    evaluation_metric int          not null,
    image_urls        text         null,
    equipment_id      bigint       not null,
    user_id           bigint       not null,

    # User fk
    constraint fk_club_head_reviews_users_user_id foreign key (user_id) references users (id),

    # Club grip fk
    constraint fk_club_head_reviews_club_heads_equipment_id foreign key (equipment_id) references club_heads (id)
);


create table if not exists club_shaft_reviews
(
    id                bigint       not null
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    updated_at        datetime(6)  not null,
    content           varchar(255) not null,
    evaluation_metric int          not null,
    image_urls        text         null,
    equipment_id      bigint       not null,
    user_id           bigint       not null,

    # User fk
    constraint fk_club_shaft_reviews_users_user_id foreign key (user_id) references users (id),

    # Club grip fk
    constraint fk_club_shaft_reviews_club_shafts_equipment_id foreign key (equipment_id) references club_shafts (id)
);

create table if not exists club_grip_reviews
(
    id                bigint       not null
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    updated_at        datetime(6)  not null,
    content           varchar(255) not null,
    evaluation_metric int          not null,
    image_urls        text         null,
    equipment_id      bigint       not null,
    user_id           bigint       not null,

    # User fk
    constraint fk_club_grip_reviews_users_user_id foreign key (user_id) references users (id),

    # Club grip fk
    constraint fk_club_grip_reviews_club_grips_equipment_id foreign key (equipment_id) references club_grips (id)
);


create table if not exists golf_ball_reviews
(
    id                bigint       not null
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    updated_at        datetime(6)  not null,
    content           varchar(255) not null,
    evaluation_metric int          not null,
    image_urls        text         null,
    equipment_id      bigint       not null,
    user_id           bigint       not null,

    # User fk
    constraint fk_golf_ball_reviews_users_user_id foreign key (user_id) references users (id),

    # Club grip fk
    constraint fk_gold_ball_reviews_golf_balls_equipment_id foreign key (equipment_id) references golf_balls (id)
);

create table if not exists golf_other_equipment_reviews
(
    id                bigint       not null
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    updated_at        datetime(6)  not null,
    content           varchar(255) not null,
    evaluation_metric int          not null,
    image_urls        text         null,
    equipment_id      bigint       not null,
    user_id           bigint       not null,

    # User fk
    constraint fk_golf_other_equipment_reviews_users_user_id foreign key (user_id) references users (id),

    # Club grip fk
    constraint fk_golf_other_equipment_reviews_goes_equipment_id foreign key (equipment_id) references golf_other_equipments (id)
);

create table if not exists user_equipments
(
    id              bigint      not null
        primary key,
    created_at      datetime(6) not null,
    deleted_at      datetime(6) null,
    updated_at      datetime(6) not null,
    ball_id         bigint      not null,
    driver_grip_id  bigint      not null,
    driver_head_id  bigint      not null,
    driver_shaft_id bigint      not null,
    iron_grip_id    bigint      not null,
    iron_head_id    bigint      not null,
    iron_shaft_id   bigint      not null,
    putter_grip_id  bigint      not null,
    putter_head_id  bigint      not null,
    putter_shaft_id bigint      not null,
    user_id         bigint      not null,
    wedge_grip_id   bigint      not null,
    wedge_head_id   bigint      not null,
    wedge_shaft_id  bigint      not null,
    wood_grip_id    bigint      not null,
    wood_head_id    bigint      not null,
    wood_shaft_id   bigint      not null,

    # User fk
    constraint fk_user_equipments_users_user_id foreign key (user_id) references users (id),

    # Driver fk
    constraint fk_user_equipments_club_heads_driver_head_id foreign key (driver_head_id) references club_heads (id),
    constraint fk_user_equipments_club_shafts_driver_shaft_id foreign key (driver_shaft_id) references club_shafts (id),
    constraint fk_user_equipments_club_grips_driver_grip_id foreign key (driver_grip_id) references club_grips (id),

    # Wood fk
    constraint fk_user_equipments_club_heads_wood_head_id foreign key (wood_head_id) references club_heads (id),
    constraint fk_user_equipments_club_shafts_wood_shaft_id foreign key (wood_shaft_id) references club_shafts (id),
    constraint fk_user_equipments_club_grips_wood_grip_id foreign key (wood_grip_id) references club_grips (id),

    # Iron fk
    constraint fk_user_equipments_club_heads_iron_head_id foreign key (iron_head_id) references club_heads (id),
    constraint fk_user_equipments_club_shafts_iron_shaft_id foreign key (iron_shaft_id) references club_shafts (id),
    constraint fk_user_equipments_club_grips_iron_grip_id foreign key (iron_grip_id) references club_grips (id),

    # Wedge fk
    constraint fk_user_equipments_club_heads_wedge_head_id foreign key (wedge_head_id) references club_heads (id),
    constraint fk_user_equipments_club_shafts_wedge_shaft_id foreign key (wedge_shaft_id) references club_shafts (id),
    constraint fk_user_equipments_club_grips_wedge_grip_id foreign key (wedge_grip_id) references club_grips (id),

    # Putter fk
    constraint fk_user_equipments_club_heads_putter_head_id foreign key (putter_head_id) references club_heads (id),
    constraint fk_user_equipments_club_shafts_putter_shaft_id foreign key (putter_shaft_id) references club_shafts (id),
    constraint fk_user_equipments_club_grips_putter_grip_id foreign key (putter_grip_id) references club_grips (id),

    # Ball fk
    constraint fk_user_equipments_golf_balls_ball_id foreign key (ball_id) references golf_balls (id)
);
