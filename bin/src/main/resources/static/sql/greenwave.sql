-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Lun 29 Janvier 2018 à 16:10
-- Version du serveur :  5.7.21-0ubuntu0.16.04.1
-- Version de PHP :  7.0.27-1+ubuntu16.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `greenwave`
--

-- --------------------------------------------------------

--
-- Structure de la table `account`
--

CREATE TABLE `account` (
  `id_account` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `account`
--

INSERT INTO `account` (`id_account`, `created_at`, `login`, `password`) VALUES
(1, NULL, 'admin', '$2a$10$v0cI08UULAvURlBtJ0mKp.sEABfstivPoiHY8KPOsIaoDMDQF3xaK');

-- --------------------------------------------------------

--
-- Structure de la table `account_roles`
--

CREATE TABLE `account_roles` (
  `account_id_account` bigint(20) NOT NULL,
  `roles_role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `account_roles`
--

INSERT INTO `account_roles` (`account_id_account`, `roles_role`) VALUES
(1, 'ORGANIZATION');

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL,
  `f_name` varchar(255) NOT NULL,
  `job` varchar(255) NOT NULL,
  `l_name` varchar(255) NOT NULL,
  `id_account` bigint(20) DEFAULT NULL,
  `id_localization` bigint(20) DEFAULT NULL,
  `id_organization` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `data`
--

CREATE TABLE `data` (
  `id_data` bigint(20) NOT NULL,
  `domain` int(11) DEFAULT NULL,
  `measure_date` datetime DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `value` double NOT NULL,
  `id_category` bigint(20) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  `type_data` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `data_category`
--

CREATE TABLE `data_category` (
  `id_category` bigint(20) NOT NULL,
  `domain` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `data_measures`
--

CREATE TABLE `data_measures` (
  `id_substance` bigint(20) NOT NULL,
  `acceptable_ratioosw` double NOT NULL,
  `acceptable_ratiosw` double NOT NULL,
  `category_substance` varchar(255) NOT NULL,
  `dangerous_ratioosw` double NOT NULL,
  `dangerous_ratiosw` double NOT NULL,
  `domain` int(11) DEFAULT NULL,
  `max` double NOT NULL,
  `min` double NOT NULL,
  `name_substance` varchar(255) NOT NULL,
  `unity` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `file`
--

CREATE TABLE `file` (
  `id_file` bigint(20) NOT NULL,
  `file_size` bigint(20) NOT NULL,
  `link` varchar(255) NOT NULL,
  `max_file_size` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `id_data` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequences`
--

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) NOT NULL,
  `sequence_next_hi_value` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `localization`
--

CREATE TABLE `localization` (
  `id_localization` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `region` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `organization`
--

CREATE TABLE `organization` (
  `id_organization` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `id` bigint(20) DEFAULT NULL,
  `id_localization` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `role` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `role`
--

INSERT INTO `role` (`role`, `description`) VALUES
('admin', 'admin role'),
('ORGANIZATION', 'orga'),
('USER', 'utilisateur lambda');

-- --------------------------------------------------------

--
-- Structure de la table `simple_user`
--

CREATE TABLE `simple_user` (
  `id` bigint(20) NOT NULL,
  `f_name` varchar(255) NOT NULL,
  `job` varchar(255) NOT NULL,
  `l_name` varchar(255) NOT NULL,
  `id_account` bigint(20) DEFAULT NULL,
  `id_localization` bigint(20) DEFAULT NULL,
  `id_admin` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `simple_user`
--

INSERT INTO `simple_user` (`id`, `f_name`, `job`, `l_name`, `id_account`, `id_localization`, `id_admin`) VALUES
(0, 'Simple', 'Scientifique', 'User', 1, NULL, NULL);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id_account`);

--
-- Index pour la table `account_roles`
--
ALTER TABLE `account_roles`
  ADD KEY `FKhjax9taq2wlxcw2uoknttocjo` (`roles_role`),
  ADD KEY `FKli7cg1srw2b02eak1hpve0sw1` (`account_id_account`);

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgkeo6smde6coogjgyp4r072qs` (`id_organization`),
  ADD KEY `FK_gwbx6ub760ndit06l30975jd1` (`id_account`),
  ADD KEY `FK_hatni3mvs0jb7qmrl0l7i0hk4` (`id_localization`);

--
-- Index pour la table `data`
--
ALTER TABLE `data`
  ADD PRIMARY KEY (`id_data`),
  ADD KEY `FKj2cojfgrglbxpqdt4hukqruwt` (`id_category`),
  ADD KEY `FK9b32bau4hdrv27nfeu5etsopc` (`type_data`);

--
-- Index pour la table `data_category`
--
ALTER TABLE `data_category`
  ADD PRIMARY KEY (`id_category`);

--
-- Index pour la table `data_measures`
--
ALTER TABLE `data_measures`
  ADD PRIMARY KEY (`id_substance`);

--
-- Index pour la table `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`id_file`),
  ADD KEY `FKie76vfs9u7ovdod9wjjlbplr8` (`id_data`);

--
-- Index pour la table `hibernate_sequences`
--
ALTER TABLE `hibernate_sequences`
  ADD PRIMARY KEY (`sequence_name`);

--
-- Index pour la table `localization`
--
ALTER TABLE `localization`
  ADD PRIMARY KEY (`id_localization`);

--
-- Index pour la table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`id_organization`),
  ADD KEY `FK5tcxdwx9wg89b66krbkm35d3d` (`id`),
  ADD KEY `FKfuljhck3a1nhrnsaefq4iuxix` (`id_localization`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role`);

--
-- Index pour la table `simple_user`
--
ALTER TABLE `simple_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKanu4pplufc2xwsiq22yo402b7` (`id_admin`),
  ADD KEY `FK_9yp1a66pl6sxfemhpxswrjc7` (`id_account`),
  ADD KEY `FK_cn93wr4mdscb6xkmtokn1nf1l` (`id_localization`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `account`
--
ALTER TABLE `account`
  MODIFY `id_account` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `data`
--
ALTER TABLE `data`
  MODIFY `id_data` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `data_category`
--
ALTER TABLE `data_category`
  MODIFY `id_category` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `data_measures`
--
ALTER TABLE `data_measures`
  MODIFY `id_substance` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `file`
--
ALTER TABLE `file`
  MODIFY `id_file` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `localization`
--
ALTER TABLE `localization`
  MODIFY `id_localization` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `organization`
--
ALTER TABLE `organization`
  MODIFY `id_organization` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `account_roles`
--
ALTER TABLE `account_roles`
  ADD CONSTRAINT `FKhjax9taq2wlxcw2uoknttocjo` FOREIGN KEY (`roles_role`) REFERENCES `role` (`role`),
  ADD CONSTRAINT `FKli7cg1srw2b02eak1hpve0sw1` FOREIGN KEY (`account_id_account`) REFERENCES `account` (`id_account`);

--
-- Contraintes pour la table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `FK_gwbx6ub760ndit06l30975jd1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id_account`),
  ADD CONSTRAINT `FK_hatni3mvs0jb7qmrl0l7i0hk4` FOREIGN KEY (`id_localization`) REFERENCES `localization` (`id_localization`),
  ADD CONSTRAINT `FKgkeo6smde6coogjgyp4r072qs` FOREIGN KEY (`id_organization`) REFERENCES `organization` (`id_organization`);

--
-- Contraintes pour la table `data`
--
ALTER TABLE `data`
  ADD CONSTRAINT `FK9b32bau4hdrv27nfeu5etsopc` FOREIGN KEY (`type_data`) REFERENCES `data_measures` (`id_substance`),
  ADD CONSTRAINT `FKj2cojfgrglbxpqdt4hukqruwt` FOREIGN KEY (`id_category`) REFERENCES `data_category` (`id_category`);

--
-- Contraintes pour la table `file`
--
ALTER TABLE `file`
  ADD CONSTRAINT `FKie76vfs9u7ovdod9wjjlbplr8` FOREIGN KEY (`id_data`) REFERENCES `data` (`id_data`);

--
-- Contraintes pour la table `organization`
--
ALTER TABLE `organization`
  ADD CONSTRAINT `FK5tcxdwx9wg89b66krbkm35d3d` FOREIGN KEY (`id`) REFERENCES `account` (`id_account`),
  ADD CONSTRAINT `FKfuljhck3a1nhrnsaefq4iuxix` FOREIGN KEY (`id_localization`) REFERENCES `localization` (`id_localization`);

--
-- Contraintes pour la table `simple_user`
--
ALTER TABLE `simple_user`
  ADD CONSTRAINT `FK_9yp1a66pl6sxfemhpxswrjc7` FOREIGN KEY (`id_account`) REFERENCES `account` (`id_account`),
  ADD CONSTRAINT `FK_cn93wr4mdscb6xkmtokn1nf1l` FOREIGN KEY (`id_localization`) REFERENCES `localization` (`id_localization`),
  ADD CONSTRAINT `FKanu4pplufc2xwsiq22yo402b7` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
