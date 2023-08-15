CREATE TABLE IF NOT EXISTS `players` (
    `uuid` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `rank` int(11) NOT NULL DEFAULT 0,
    `freeze` tinyint(1) NOT NULL DEFAULT 0,
    `mute` tinyint(1) NOT NULL DEFAULT 0,
    `vanish` tinyint(1) NOT NULL DEFAULT 0,
    `firstConnection` datetime NOT NULL DEFAULT current_timestamp,
    `lastConnection` timestamp NOT NULL DEFAULT current_timestamp,
    `online` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`uuid`,`name`)
);

CREATE TABLE IF NOT EXISTS `warns` (
    `id` int(11) NOT NULL PRIMARY KEY AUTOINCREMENT,
    `playerUuid` varchar(255) NOT NULL,
    `info` text NOT NULL
);

CREATE TABLE IF NOT EXISTS `ranks` (
    `id` int(11) NOT NULL PRIMARY KEY AUTOINCREMENT,
    `name` varchar(255) NOT NULL,
    `prefix` varchar(255) NOT NULL,
    `suffix` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `permissions` (
    `id` int(11) NOT NULL PRIMARY KEY AUTOINCREMENT,
    `rankId` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `homes` (
    `id` int(11) NOT NULL PRIMARY KEY AUTOINCREMENT,
    `playerUuid` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `x` double(11) NOT NULL,
    `y` double(11) NOT NULL,
    `z` double(11) NOT NULL,
    `yaw` double(11) NOT NULL,
    `pitch` double(11) NOT NULL
)