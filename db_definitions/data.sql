create table
  public.users (
    id text not null,
    created_at timestamp with time zone not null default now(),
    nick text not null,
    hashed_password text not null,
    constraint users_pkey primary key (id),
    constraint users_id_key unique (id),
    constraint users_nick_key unique (nick)
  ) tablespace pg_default;


create unique index users_nick on users (nick);

create table
  public.sessions (
    id text not null,
    created_at timestamp with time zone not null,
    user_id text not null,
    updated_at timestamp with time zone not null,
    expires_at timestamp with time zone not null,
    constraint sessions_pkey primary key (id),
    constraint sessions_user_id_fkey foreign key (user_id) references users (id) on delete cascade
  ) tablespace pg_default;


create index sessions_user on sessions (user_id);

create table
  public.groups (
    id text not null,
    created_at timestamp with time zone not null default now(),
    name text not null,
    constraint groups_pkey primary key (id)
  ) tablespace pg_default;

create table
  public.users_groups (
    created_at timestamp with time zone not null default now(),
    access_level smallint null default '0'::smallint,
    user_id text not null,
    group_id text not null,
    constraint users_groups_pkey primary key (user_id, group_id),
    constraint users_groups_group_id_fkey foreign key (group_id) references groups (id) on delete cascade,
    constraint users_groups_user_id_fkey foreign key (user_id) references users (id) on delete cascade
  ) tablespace pg_default;

create index users_groups_user on users_groups (user_id);
create index users_groups_group on users_groups (group_id);

create table
  public.sets (
    id text not null,
    created_at timestamp with time zone null default now(),
    user_id text null,
    group_id text null,
    is_public Boolean not null default false,
    name text null,
    constraint sets_pkey primary key (id),
    constraint sets_group_id_fkey foreign key (group_id) references groups (id) on delete cascade,
    constraint sets_user_id_fkey foreign key (user_id) references users (id) on delete cascade
  ) tablespace pg_default;

create index sets_user on sets (user_id);
create index sets_group on sets (group_id);

create table
  public.cards (
    id text not null,
    created_at timestamp with time zone null default now(),
    question text null,
    answer text null,
    set_id text not null,
    constraint cards_pkey primary key (id),
    constraint cards_set_id_fkey foreign key (set_id) references sets (id) on delete cascade
  ) tablespace pg_default;

create index cards_set on cards (set_id);

create table
  public.group_achievements (
    name text not null,
    group_id text not null,
    constraint group_achievements_pkey primary key (name, group_id),
    constraint group_achievements_group_id_fkey foreign key (group_id) references groups (id) on delete cascade
  ) tablespace pg_default;

create index group_achievements_group on group_achievements (group_id);

create table
  public.user_achievements (
    name text not null,
    group_id text not null,
    user_id text not null,
    constraint user_achievements_pkey primary key (name, group_id, user_id),
    constraint user_achievements_group_id_fkey foreign key (group_id) references groups (id) on delete cascade,
    constraint user_achievements_user_id_fkey foreign key (user_id) references users (id) on delete cascade
  ) tablespace pg_default;

create index user_achievements_user on user_achievements (user_id);
create index user_achievements_group on user_achievements (group_id);

create table
  public.answers (
    id text not null,
    started_at timestamp with time zone not null default now(),
    set_id text not null,
    end_at timestamp with time zone null,
    user_id text not null,
    constraint answers_pkey primary key (id),
    constraint answers_set_id_fkey foreign key (set_id) references sets (id) on delete cascade,
    constraint answers_user_id_fkey foreign key (user_id) references users (id) on delete cascade
  ) tablespace pg_default;


create index answers_set on answers (set_id);
create index answers_user on answers (user_id);
create unique index answers_current on answers (user_id) where end_at is null;
