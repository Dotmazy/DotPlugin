CREATE TABLE IF NOT EXISTS `players` (
    `uuid` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `nickName` varchar(255) DEFAULT NULL,
    `rank` int(11) NOT NULL DEFAULT 0,
    `freeze` tinyint(1) NOT NULL DEFAULT false,
    `mute` tinyint(1) NOT NULL DEFAULT false,
    `firstJoin` datetime DEFAULT current_timestamp,
    `lastJoin` datetime DEFAULT current_timestamp,
    `password` varchar(255) DEFAULT '',
    `online` tinyint(1) DEFAULT 1,
    `playlistMode` int(1) DEFAULT 0,
    `musicVolume` int(3) DEFAULT 50,
    `playedPlaylist` int(11) DEFAULT null,
    PRIMARY KEY (`uuid`),
    UNIQUE (`name`)
);

CREATE TABLE IF NOT EXISTS `warns` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `playerUuid` varchar(255) NOT NULL,
    `warnerUuid` varchar(255) NOT NULL,
    `reason` text NOT NULL
);

CREATE TABLE IF NOT EXISTS `homes` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `playerUuid` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `x` double(11) NOT NULL,
    `y` double(11) NOT NULL,
    `z` double(11) NOT NULL,
    `yaw` float(11) NOT NULL,
    `pitch` float(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS `ranks` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `name` varchar(255) NOT NULL UNIQUE,
    `prefix` varchar(255) NOT NULL,
    `suffix` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `perms` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `rankId` int(11) NOT NULL,
    `name` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `musics` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `name` varchar(255) NOT NULL,
    `music` varchar(255) NOT NULL,
    `icon` varchar(255),
    `time` int(11) NOT NULL DEFAULT 1,
    `youtubeVideo` varchar(100) DEFAULT null
);

CREATE TABLE IF NOT EXISTS `playlists` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `name` varchar(255) NOT NULL,
    `icon` text NOT NULL,
    `ownerUuid` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `playlistMusics` (
    `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    `playlistId` int(11) NOT NULL,
    `musicId` varchar(255) NOT NULL
);