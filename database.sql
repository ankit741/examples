/*
-- Query: SELECT * FROM db1.user
LIMIT 0, 1000

-- Date: 2021-05-24 22:03
*/
CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(65) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `` (`user_id`,`name`,`password`,`date`) VALUES (1,'ankit','$2a$10$meQMvf63SaU5DBA4FWwjAeEFKAo3wnnWZszniTDzijJl9rfzYKJra','2021-05-22 22:58:12');
INSERT INTO `` (`user_id`,`name`,`password`,`date`) VALUES (5,'admin','$2a$12$CwEEzf51ZE10DjFULErUUeMBPkVSa7dMOODrpbUtJkp61TtY95MMe','2019-04-28 20:15:15');

