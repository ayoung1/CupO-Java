-- iteration 1 trivia database
-- ask tom about short answer questions. how much NLP do we need to do? simple questions
-- will add questions in seperate .sql file
-- will translate to sqlite once I get it working.
create table TRIVIA_QUESTIONS
(
	question_num int(10) unique not null,
	question_type varchar not null,
	category varchar default null,
	primary key (question_num)
);

create table MULTIPLE_CHOICE
(
	question_num int(10) unique not null,
	num_options int(1) default 2,
	question varchar not null,
	answer varchar not null,
	option_1 varchar not null,
	option_2 varchar not null,
	option_3 varchar,
	option_4 varchar,
	option_5 varchar default null
);

create table TRUE_FALSE
(	
	question_num int(10) unique not null,
	answer varchar(1) not null,
	question varchar(50) not null
);

create table SHORT_ANSWER
(
	question_num int(10) unique not null,
	question varchar(50) not null,
	answer_components varchar(100) not null
);