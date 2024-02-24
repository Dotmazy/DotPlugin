CREATE TABLE IF NOT EXISTS `members` (
    `id` varchar(100) NOT NULL,
    `name` varchar(255) NOT NULL,
    `country` int(11) NOT NULL DEFAULT -1,
    `isAdmin` tinyint(1) NOT NULL DEFAULT 0,
    `lang` varchar(255) NOT NULL DEFAULT 'english',
    PRIMARY KEY (`id`,`name`)
);

CREATE TABLE IF NOT EXISTS `players` (
    `uuid` varchar(36) NOT NULL,
    `name` varchar(255) NOT NULL,
    `rank` int(11) NOT NULL DEFAULT -1,
    `premiumCoins` double NOT NULL DEFAULT 0,
    `engineerXp` int(11) DEFAULT 0,
    `minerXp` int(11) DEFAULT 0,
    `hunterXp` int(11) DEFAULT 0,
    `alchemistXp` int(11) DEFAULT 0,
    `farmerXp` int(11) DEFAULT 0,
    `firstJoin` timestamp DEFAULT current_timestamp,
    `lastJoin` timestamp DEFAULT current_timestamp,
    PRIMARY KEY (`uuid`,`name`)
);

CREATE TABLE IF NOT EXISTS `link` (
    `memberId` varchar(100) NOT NULL,
    `playerUuid` varchar(36) NOT NULL,
    PRIMARY KEY (`memberId`,`playerUuid`)
);

CREATE TABLE IF NOT EXISTS `countries` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` text NOT NULL,
    `ownerId` varchar(100) NOT NULL,
    `ownerUuid` varchar(36) NOT NULL,
    `type` varchar(255) NOT NULL,
    `color` varchar(20) NOT NULL,
    `roleId` varchar(100) NOT NULL,
    `textChannelId` varchar(100) NOT NULL,
    `voiceChannelId` varchar(100) NOT NULL,
    PRIMARY KEY (`id`,`name`)
)