
use msg_training;


insert into users values (1, 0, 'marius_pop@msggroup.com', 'Marius', 'Pop', +49012003400, 'abcdef', 'popm', false);
insert into users values (2, 0, 'ioana_dumitrescu@msggroup.com', 'Ioana', 'Dumitrescu', +49012003401, 'abcdef', 'dumiti', false);
insert into users values (3, 0, 'veronica_bogza@msggroup.com', 'Veronica', 'Bogza', +49012003402, 'abcdef', 'bogzav', false);
insert into users values (4, 0, 'iulian_oprea@msggroup.com', 'Iulian', 'Oprea', +49012003403, 'abcdef', 'opreai', false);
insert into users values (5, 0, 'tudor_nedelcu@msggroup.com', 'Tudor', 'Nedelcu', +49012003404, 'abcdef', 'nedelt', false);


insert into bugs values(1,'bug1', 'e grav bugu asta', '1.0', '2000-01-09 01:10:20', 'NEW', '1.1','CRITICAL', 'popm', 'dumiti');
insert into bugs values(2,'bug2', 'e slab bugu asta', '2.0', '2001-01-08 02:10:20', 'NEW', '1.1','HIGH', 'popm', 'dumiti');
insert into bugs values(3,'bug3', 'mere bugu asta', '1.1', '2002-01-07 03:10:20', 'FIXED', '1.1','CRITICAL', 'bogzav', 'dumiti');
insert into bugs values(4,'bug4', 'se pote mai bine bugu asta', '1.9', '2003-01-06 04:10:20', 'NEW', '1.1','CRITICAL', 'popm', 'popm');
insert into bugs values(5,'bug5', 'imposibil bugu asta', '1.2', '2004-01-05 05:10:20', 'CLOSED', '1.1','LOW', 'opreai', 'dumiti');
insert into bugs values(6,'bug6', 'e a Lu Radu bugu asta', '3.0', '2005-01-04 06:10:20', 'REJECTED', '1.1','CRITICAL', 'nedelt', 'dumiti');
insert into bugs values(7,'bug7', 'E a lu Stefan bugu asta', '4.0', '2006-03-01 07:10:20', 'INFO_NEEDED', '1.1','MEDIUM', 'popm', 'bogzav');
insert into bugs values(8,'bug8', 'A mieu nu ie bugu asta', '5.0', '2007-01-02 08:10:20', 'NEW', '1.1','LOW', 'dumiti', 'popm');


insert into comments value('da sa ma mir de bugu vostru', '2000-01-09 01:10:20', 1, 1, 1);
insert into comments value('da sa ma mir de bugu vostru', '2001-02-10 02:11:20', 2, 2, 3);
insert into comments value('da sa ma mir de bugu vostru', '2002-03-11 03:12:20', 3, 3, 4);
insert into comments value('da sa ma mir de bugu vostru', '2003-04-12 04:13:20', 4, 4, 5);
insert into comments value('da sa ma mir de bugu vostru', '2004-05-13 05:14:20', 5, 5, 7);

insert into permissions values
(1,'This is a really great description for A','USER_MANAGEMENT'),
(2,'Another great description, this time for B','BUG_MANAGEMENT'),
(3,'I describe C this time','BUG_CLOSE'),
(4,'Description D','PERMISSION_MANAGEMENT'),
(5,'Description E','BUG_EXPORT_PDF');

 

insert into roles values
(1,'ADM'),
(2,'PM'),
(3,'TM'),
(4,'DEV'),
(5,'TEST');

 

insert into roles_permissions values
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(2,1),
(2,2),
(2,3),
(3,1),
(3,2),
(3,3),
(4,1),
(4,3),
(4,4),
(4,5);

 

insert into users_roles values
(1,1),
(1,2),
(1,3),
(2,2),
(2,3),
(3,4),
(3,1),
(4,1),
(5,2);



insert into notifications values(1, '2003-01-09 01:10:20', 'welcome user', 'WELCOME_NEW_USER', 'bug1', 5);
insert into notifications values(2, '2002-02-09 01:10:20', 'updated user', 'USER_UPDATED', 'bug1', 4);
insert into notifications values(3, '2002-01-10 01:10:20', 'deleted user', 'USER_DELETED', 'bug2', 3);
insert into notifications values(4, '2002-06-13 01:10:20', 'close bug', 'BUG_CLOSED', 'bug7', 2);
insert into notifications values(5, '2002-12-02 01:10:20', 'welcome user', 'USER_DEACTIVATED', 'bug6', 1);
insert into notifications values(6, '2001-09-09 01:10:20', 'welcome user', 'BUG_STATUS_UPDATED', 'bug4', 2);
insert into notifications values(7, '2001-09-09 01:10:20', 'welcome user', 'BUG_UPDATED', 'bug5', 2);



insert into attachments values (1, '', 1);
insert into attachments values (2, '', 2);
insert into attachments values (3, '', 3);
insert into attachments values (4, '', 4);
insert into attachments values (5, '', 5);
insert into attachments values (6, '', 6);
insert into attachments values (7, '', 7);
insert into attachments values (8, '', 8);

