-- iteration 1.1 trivia database
-- 1.1 added foreign keys and on delete/update for primary
-- might add constraint on question number -> q >= 1
-- added user table
-- constraint on password?
-- 1.0
-- ask tom about short answer questions. how much NLP do we need to do? simple questions
-- will add questions in seperate .sql file
-- will translate to sqlite once I get it working.

create table USERS
(
  user_name varchar unique not null,
  user_password varchar
);

create table TRIVIA_QUESTIONS
(
	question_num int(10) unique not null
  on delete cascade on update cascade,
	question_type varchar not null,
	category varchar default null,
	primary key (question_num)
);

create table MULTIPLE_CHOICE
(
  foreign key worksin(question_num)
  references TRIVIA_QUESTIONS(question_num),
	num_options int(1) default 2,
	question varchar not null,
	answer varchar not null,
	option_1 varchar not null,
	option_2 varchar,
  option_3,
	option_4 default null
);

create table TRUE_FALSE
(	
  foreign key worksin(question_num)
  references TRIVIA_QUESTIONS(question_num),
	answer varchar(1) not null,
	question varchar(50) not null
);

create table SHORT_ANSWER
(
  foreign key worksin(question_num)
  references TRIVIA_QUESTIONS(question_num),
	question varchar(50) not null,
	answer_components varchar(100) not null
);