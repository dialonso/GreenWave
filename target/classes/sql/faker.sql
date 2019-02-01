-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 14 fév. 2018 à 18:40
-- Version du serveur :  5.7.17
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;



--
-- Déchargement des données de la table `account`
--

INSERT INTO `account` (`id_account`, `created_at`, `login`, `password`) VALUES
(1, '2018-01-16 00:00:00', 'admin', '$2a$10$v0cI08UULAvURlBtJ0mKp.sEABfstivPoiHY8KPOsIaoDMDQF3xaK'),
(2, '2018-02-14 15:09:27', 'kevin', '$2a$10$yma.q.IvPykyB7Q055Tz0.A0KD.GFwCxnnnsFPd/4dSwJRMPZUCkq'),
(3, '2018-02-14 16:07:07', 'jordan', '$2a$10$sQI1j/YVAUq.FXuyfyCuh.ngnjfE2FGgNWuzMLbyIipzwty1U9mE.'),
(4, '2018-02-14 16:07:17', 'alice', '$2a$10$CDAIQiwfXEMEw7KqFkX1r.PVzYEaAroSeu4BljEnyaFQts3dF5RYm'),
(5, '2018-02-14 16:09:26', 'root1234', '$2a$10$wB/HBsYsPrJgzFq.KommBeqCQILoy74nS7P9xMKZu65oCiQHDO9dq');

-- --------------------------------------------------------


--
-- Déchargement des données de la table `account_roles`
--

INSERT INTO `account_roles` (`account_id_account`, `roles_role`) VALUES
(1, 'ORGANIZATION'),
(2, 'ADMIN'),
(2, 'ADMIN'),
(3, 'USER'),
(4, 'USER'),
(5, 'USER');

-- --------------------------------------------------------



--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id`, `f_name`, `job`, `l_name`, `id_account`, `id_localization`, `id_organization`) VALUES
(1, 'Kevin', 'Scientifique', 'Kevin', 2, 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `data`
--



--
-- Déchargement des données de la table `data`
--

INSERT INTO `data` (`id_data`, `domain`, `measure_date`, `type`, `value`, `id_category`, `id_localization`, `id_user`, `type_data`) VALUES
(1, 0, '2018-01-28 07:14:10', 'pH', 7, 1, 1, 1, 1),
(2, 0, '2018-01-24 07:14:10', 'pH', 6.5, 1, 1, 1, 1),
(3, 0, '2018-01-05 07:14:10', 'pH', 7, 1, 1, 1, 1),
(4, 0, '2018-02-05 07:14:10', 'pH', 7, 1, 1, 1, 1),
(5, 0, '2017-12-19 07:14:10', 'pH', 7.2, 1, 1, 1, 1),
(6, 0, '2017-12-10 07:14:10', 'pH', 7.04, 1, 1, 1, 1),
(7, 0, '2017-11-10 07:14:10', 'pH', 7.04, 1, 1, 1, 1),
(8, 0, '2017-10-18 07:14:10', 'pH', 6.54, 1, 1, 1, 1),
(9, 0, '2017-09-18 07:14:10', 'pH', 6.54, 1, 1, 1, 1),
(10, 0, '2017-08-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(11, 0, '2017-08-18 07:14:10', 'pH', 7.3, 1, 1, 1, 1),
(12, 0, '2017-08-18 07:14:10', 'pH', 7.34, 1, 1, 1, 1),
(13, 0, '2017-07-18 07:14:10', 'pH', 5.2, 1, 1, 1, 1),
(14, 0, '2017-07-18 07:14:10', 'pH', 5, 1, 1, 1, 1),
(15, 0, '2017-06-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(16, 0, '2017-06-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(17, 0, '2017-06-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(18, 0, '2017-06-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(19, 0, '2017-06-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(20, 0, '2017-06-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(21, 0, '2017-05-18 07:14:10', 'pH', 7, 1, 1, 1, 1),
(22, 0, '2017-04-18 07:14:10', 'pH', 6, 1, 1, 1, 1),
(23, 0, '2017-04-18 07:14:10', 'pH', 6, 1, 1, 1, 1),
(24, 0, '2018-01-28 07:14:10', 'Alcachlore', 0.2, 1, 1, 1, 1),
(25, 0, '2018-01-28 07:14:10', 'Alcachlore', 0.2, 1, 1, 1, 1),
(26, 0, '2018-02-08 07:14:10', 'Alcachlore', 0.5, 1, 1, 1, 1),
(27, 0, '2017-12-18 07:14:10', 'Alcachlore', 0.24, 1, 1, 1, 1),
(28, 0, '2018-01-28 07:14:10', 'Alcachlore', 0.2, 1, 1, 1, 1),
(29, 0, '2018-01-28 07:14:10', 'Alcachlore', 0.38, 1, 1, 1, 1),
(30, 2, '2018-02-12 00:00:00', 'Taux d\'humidité', 20, 4, 1, 1, 4),
(31, 2, '2018-02-10 00:00:00', 'Taux d\'humidité', 21, 4, 1, 1, 4),
(32, 2, '2018-02-10 00:00:01', 'Taux d\'humidité', 18, 4, 1, 1, 4),
(33, 2, '2018-01-11 00:00:01', 'Taux d\'humidité', 16, 4, 1, 1, 4),
(34, 2, '2017-12-18 00:00:01', 'Taux d\'humidité', 16, 4, 1, 1, 4),
(35, 2, '2017-12-18 00:00:01', 'Taux d\'humidité', 16, 4, 1, 1, 4),
(36, 2, '2017-12-18 00:00:01', 'Taux d\'humidité', 16, 4, 1, 1, 4),
(37, 1, '2018-02-06 03:30:00', 'co', 43.62, 3, 3, NULL, 7),
(39, 1, '2018-02-06 03:30:00', 'co', 17.3, 3, 3, NULL, 7),
(41, 1, '2018-02-06 03:30:00', 'pm25', 92.29, 3, 3, NULL, 5),
(42, 1, '2018-02-06 03:30:00', 'pm25', 91.23, 3, 3, NULL, 7),
(43, 1, '2018-01-06 23:30:00', 'co', 18.5, 3, 3, NULL, 7),
(47, 1, '2018-02-06 23:30:00', 'co', 14.1, 3, 3, NULL, 7),
(46, 1, '2018-01-06 23:30:00', 'co', 15.1, 3, 3, NULL, 7),
(48, 1, '2017-12-11 23:30:00', 'co', 15.1, 3, 3, NULL, 7),
(49, 1, '2017-12-11 23:30:00', 'co', 14.1, 3, 3, NULL, 7),
(50, 1, '2017-11-11 23:30:00', 'co', 14.1, 3, 3, NULL, 7),
(51, 1, '2017-10-11 23:30:00', 'co', 17, 3, 3, NULL, 7),
(52, 1, '2018-01-06 03:30:00', 'pm25', 98, 3, 3, NULL, 7),
(53, 1, '2017-12-05 03:30:00', 'pm25', 98, 3, 1, NULL, 7),
(54, 1, '2017-12-05 03:30:00', 'pm25', 94, 3, 1, NULL, 7),
(55, 1, '2017-11-05 03:30:00', 'pm25', 94, 3, 3, NULL, 7),
(56, 1, '2017-12-05 03:30:00', 'pm25', 94, 3, 3, NULL, 7);

-- --------------------------------------------------------


--
-- Déchargement des données de la table `data_category`
--

INSERT INTO `data_category` (`id_category`, `domain`, `name`) VALUES
(1, 0, 'SURFACE_WATER_DATA'),
(2, 0, 'OTHER_WATER_DATA'),
(3, 1, 'AIR'),
(4, 2, 'SOL');

-- --------------------------------------------------------

--
-- Structure de la table `data_file`
--


-- --------------------------------------------------------

--
-- Structure de la table `data_measure`
--

--
-- Déchargement des données de la table `data_measure`
--

INSERT INTO `data_measure` (`id_substance`, `acceptable_ratioosw`, `acceptable_ratiosw`, `category_substance`, `dangerous_ratioosw`, `dangerous_ratiosw`, `domain`, `max`, `min`, `name_substance`, `unity`) VALUES
(1, NULL, NULL, 'OTHER_SUBSTANCES', NULL, NULL, 0, 14, 0, 'pH', NULL),
(2, NULL, NULL, 'OTHER_SUBSTANCES', NULL, NULL, 1, NULL, NULL, 'Température', '°C'),
(4, NULL, NULL, 'OTHER_SUBSTANCES', NULL, NULL, 2, NULL, NULL, 'Taux d\'humidité', '%'),
(5, NULL, NULL, 'OTHER_SUBSTANCES', NULL, NULL, 1, NULL, NULL, 'pm25', 'µg/m3'),
(6, NULL, NULL, 'OTHER_SUBSTANCES', NULL, NULL, 1, NULL, NULL, 'pm10', 'µg/m3'),
(7, NULL, NULL, 'POLLUTING_SUBSTANCES', NULL, NULL, 1, NULL, NULL, 'co', 'µg/m3'),
(8, NULL, NULL, 'POLLUTING_SUBSTANCES', NULL, NULL, 0, NULL, NULL, 'Alcachlore', 'mg/L');

-- --------------------------------------------------------

--
-- Structure de la table `data_open`
--

--
-- Déchargement des données de la table `data_open`
--

INSERT INTO `data_open` (`id`, `api_key`, `date_last_call`, `domain`, `name`, `url`) VALUES
(1, NULL, '2018-02-14 15:17:38', NULL, 'Open AQ', 'https://api.openaq.org/v1/measurements?limit=10');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequences`
--

--
-- Déchargement des données de la table `hibernate_sequences`
--

INSERT INTO `hibernate_sequences` (`sequence_name`, `sequence_next_hi_value`) VALUES
('user', 2);

-- --------------------------------------------------------

--
-- Structure de la table `localization`
--



--
-- Déchargement des données de la table `localization`
--

INSERT INTO `localization` (`id_localization`, `address`, `country`, `department`, `latitude`, `longitude`, `region`) VALUES
(1, '1 Avenue des Célestins, 03200 Vichy', 'France', 'Allier', 46.119763, 3.425256, 'Auvergne-Rhône-Alpes'),
(2, '63000 Clermont-Ferrand, France', 'France', 'Puy-de-Dome', 45.771264, 3.1198023, 'Auvergne-Rhône-Alpes'),
(3, 'Maharashtra Pollution Control Board - Solapur Solapur', 'India', 'Pune', 17.6599188, 75.9063906, 'Maharashtra'),
(4, 'Vichy, France', 'France', 'Allier', 46.131859, 3.425488, 'Auvergne-Rhône-Alpes');

-- --------------------------------------------------------

--
-- Structure de la table `organization`
--



--
-- Déchargement des données de la table `organization`
--

INSERT INTO `organization` (`id_organization`, `name`, `type`, `id`, `id_localization`) VALUES
(1, 'MASTER SIPPE', 'organization', 1, 1);

-- --------------------------------------------------------


-- --------------------------------------------------------


--
-- Déchargement des données de la table `simple_user`
--

INSERT INTO `simple_user` (`id`, `f_name`, `job`, `l_name`, `id_account`, `id_localization`, `id_admin`) VALUES
(32768, 'Jordan', 'Chercheur', 'Jordan', 3, 4, 1),
(32769, 'Alice', 'Chercheur', 'Alice', 4, 4, 1),
(32770, 'Admin', 'Chercheur', 'Super', 5, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `wiki`
--



--
-- Déchargement des données de la table `wiki`
--

INSERT INTO `wiki` (`id`, `description`, `max`, `min`, `nom`, `ratio1`, `ratio2`, `ratio3`, `ratio4`, `risque`, `unite`, `id_domain`) VALUES
(1, 'Le PH exprime le niveau d’acidité de l’eau. Il se mesure sur une échelle de 0 à 14. Un PH neutre se situe à 7.0. Il est à noter qu’un PH de 6.0 est dix (10) fois plus acide qu’un PH de 7.0 et un PH de 6.0 est cent (100) fois plus acide qu’un PH de 8.0. Un PH acide dans l’eau aura pour effet de corroder la tuyauterie et le chauffe-eau causant des dommages irréversibles.\n\n', 14, 0, 'PH', NULL, NULL, NULL, NULL, 'Niveau recommandé dans l\'eau : entre 6.5 et 8.5 sur l’échelle PH', NULL, 0),
(2, 'Les solides dissous totaux représentent une évaluation totale des sels minéraux contenus dans l’eau (en solution). Cette valeur traduit une évaluation totale sur le taux de minéraux qui ont été dissous dans l’eau.   [0  50  100]', 0, 50, 'Salinité', NULL, NULL, NULL, NULL, 'Niveau recommandé dans l\'eau : 0.45 milligrammes par litre', '0/00', 0),
(3, NULL, NULL, NULL, 'Température', NULL, NULL, NULL, NULL, NULL, '°C', 1),
(4, NULL, NULL, NULL, 'Taux d\'humidité', NULL, NULL, NULL, NULL, NULL, '%', 2),
(5, 'La couleur de l\'eau peut être d\'origine minérale (fer ferrique) ou organique (tannin). Une eau colorée est normalement jaunâtre. La présence de turbidité affecte souvent la couleur de l\'eau. La « couleur vraie » correspond à la mesure effectuée sur des échantillons d\'eau débarrassés des particules en suspension', 5, 0, 'Transparence', NULL, NULL, NULL, NULL, 'Niveau recommandé dans l\'eau : 15 unités de couleur vraie', 'dm', 0),
(7, 'La conductivité est la mesure de la capacité de l\'eau à conduire un courant électrique. Elle varie en fonction de la présence d’ions, de leur concentration et de la température. Le point de repère utilisé est à 20 °C.  Les sels minéraux sont de bons conducteurs alors que la matière organique ne l’est pas. En général, plus la conductivité est élevée, plus il y a de minéraux dissous dans l\'eau.', 1, 0.001, 'Conductivité', NULL, NULL, NULL, NULL, 'Niveau recommandé dans l\'eau : 750 micro-siemens par centimètres (µS/cm)', 'µS/cm', 0),
(8, 'Le chlorure de sodium (NaCl), le sel de table, est fréquent. Un taux de chlorure élevé dans l’eau, en plus de donner un goût salé, laisse des taches et des dépôts blancs. De plus, les chlorures rendent l’eau plus corrosive.', 15, 1, 'Chlorure', NULL, NULL, NULL, NULL, 'Niveau recommandé dans l\'eau : 250 milligrammes par litre', 'mg/L', 0),
(11, 'Le dioxyde d’azote est un composé chimique de formule NO2. Il s’agit d’un gaz brun-rouge toxique suffocant à l’odeur âcre et piquante caractéristique. Il constitue le polluant majeur de l’atmosphère terrestre. Il est notamment produit par les moteurs à combustion interne et les centrales thermiques.', 200, 40, 'Dioxyde d’azote (NO2)', NULL, NULL, NULL, NULL, 'C\'est un gaz toxique entrâinant une inflammation importante des voies respiratoires.', NULL, 1);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
