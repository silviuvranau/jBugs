use msg_training;


insert into users values (1, 0, 'marius_pop@msggroup.com', 'Marius', 'Pop', '+40746532213', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 'popm', false);
insert into users values (2, 0, 'ioana_dumitrescu@msggroup.com', 'Ioana', 'Dumitrescu', '+40746532325', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 'dumiti', false);
insert into users values (3, 0, 'veronica_bogza@msggroup.com', 'Veronica', 'Bogza', '+40746532448', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 'bogzav', false);
insert into users values (4, 0, 'iulian_oprea@msggroup.com', 'Iulian', 'Oprea', '+40746532287', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 'opreai', false);
insert into users values (5, 0, 'tudor_nedelcu@msggroup.com', 'Tudor', 'Nedelcu', '+40746532921', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 'nedelt', false);


insert into bugs values(1,'BugOne', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '1.0', '2019-01-09 01:10:20', 'NEW', '1.0','CRITICAL', 'popm', 'nedelt');
insert into bugs values(2,'BugTwo', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '3.0', '2018-10-12 02:10:20', 'FIXED', '2.8','MEDIUM', 'popm', 'dumiti');
insert into bugs values(3,'BugThree', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '1.1', '2018-01-07 03:10:20', 'REJECTED', '1.1','LOW', 'bogzav', 'dumiti');
insert into bugs values(4,'BugFour', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '1.9', '2018-09-06 04:10:20', 'INFO_NEEDED', '1.1','MEDIUM', 'popm', 'popm');
insert into bugs values(5,'BugFive', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.a', '1.2', '2019-07-05 05:10:20', 'IN_PROGRESS', '1.2','HIGH', 'opreai', 'dumiti');
insert into bugs values(6,'BugSix', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '2.0', '2019-04-04 06:10:20', 'IN_PROGRESS', '2.0','CRITICAL', 'nedelt', 'dumiti');
insert into bugs values(7,'BugSeven', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '4.0', '2019-04-01 07:10:20', 'INFO_NEEDED', '1.1','HIGH', 'popm', 'bogzav');
insert into bugs values(8,'BugEight', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '5.0', '2017-11-02 08:10:20', 'CLOSED', '4.9','LOW', 'dumiti', 'popm');
insert into bugs values(9,'BugNine', 'Praesent porta volutpat felis non mollis. Nullam quis eleifend sem. Praesent hendrerit ut lorem ut gravida. Suspendisse ut laoreet lacus, ut pharetra turpis. Curabitur pretium dolor ut congue mattis. Quisque semper velit vel tortor porta, et volutpat ante tincidunt. Aenean porttitor tincidunt lorem, eget faucibus tellus luctus eget.', '3.2', '2018-10-02 08:10:20', 'REJECTED', '3.1','LOW', 'dumiti', 'opreai');


insert into comments value('Random comment.', '2018-10-09 01:10:20', 1, 1, 3);
insert into comments value('Random comment.', '2019-02-10 02:11:20', 2, 2, 1);
insert into comments value('Random comment.', '2018-12-11 03:12:20', 3, 3, 2);
insert into comments value('Random comment.', '2019-07-12 04:13:20', 4, 4, 6);
insert into comments value('Random comment.', '2018-05-13 05:14:20', 5, 5, 8);

insert into permissions values
(1,'See, add, edit and delete users.','USER_MANAGEMENT'),
(2,'See, add, edit and delete bugs.','BUG_MANAGEMENT'),
(3,'Close a bug','BUG_CLOSE'),
(4,'Change role permissions.','PERMISSION_MANAGEMENT'),
(5,'Export bug pdf.','BUG_EXPORT_PDF');

 

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
(2,4),
(3,2),
(3,3),
(3,5),
(4,1),
(4,2),
(4,4);

 

insert into users_roles values
(1,1),
(2,2),
(3,3),
(4,4),
(5,5);



insert into notifications values(1, '2019-08-12 01:10:20', 'welcome user', 'WELCOME_NEW_USER', '', 1);
insert into notifications values(2, '2019-08-12 01:10:20', 'welcome user', 'WELCOME_NEW_USER', '', 2);
insert into notifications values(3, '2019-08-12 01:10:20', 'welcome user', 'WELCOME_NEW_USER', '', 3);
insert into notifications values(4, '2019-08-12 01:10:20', 'welcome user', 'WELCOME_NEW_USER', '', 4);
insert into notifications values(5, '2019-08-12 01:10:20', 'welcome user', 'WELCOME_NEW_USER', '', 5);
insert into notifications values(6, '2019-08-12 01:10:20', 'updated bug', 'BUG_UPDATED', 'http://localhost:4200/dashboard/bugs?bugId=1', 1);
insert into notifications values(7, '2001-09-09 01:10:20', 'updated bug', 'BUG_UPDATED', 'http://localhost:4200/dashboard/bugs?bugId=5', 4);
insert into notifications values(8, '2001-09-09 01:10:20', 'welcome user', 'BUG_STATUS_UPDATED', 'http://localhost:4200/dashboard/bugs?bugId=1', 1);
