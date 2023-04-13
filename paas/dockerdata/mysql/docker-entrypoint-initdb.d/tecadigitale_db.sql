SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

--
-- Database: `tecadigitale_db`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `delete_mag`
--

create database tecadigitale_db;

use tecadigitale_db;

CREATE TABLE `delete_mag` (
  `id` int(11) NOT NULL,
  `id_job` varchar(250) DEFAULT NULL,
  `id_mag` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `export`
--

CREATE TABLE `export` (
  `id` int(11) NOT NULL,
  `file` varchar(255) NOT NULL,
  `id_user` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `import_detail`
--

CREATE TABLE `import_detail` (
  `id` int(11) NOT NULL,
  `id_job` varchar(250) DEFAULT NULL,
  `flag_update` int(11) NOT NULL DEFAULT '0',
  `num_mag` int(11) DEFAULT NULL,
  `processed_mag` int(11) DEFAULT NULL,
  `indexed_mag` int(11) DEFAULT NULL,
  `mag_OK` int(11) DEFAULT NULL,
  `mag_KO` int(11) DEFAULT NULL,
  `timestamp_start_validation` bigint(20) DEFAULT NULL,
  `timestamp_end_validation` bigint(20) DEFAULT NULL,
  `public_flag` int(11) DEFAULT NULL,
  `dress_flag` int(11) NOT NULL DEFAULT '0',
  `usage_a` varchar(100) DEFAULT NULL,
  `usage_e` varchar(100) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `configuration` longtext,
  `base_install` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `import_mag`
--

CREATE TABLE `import_mag` (
  `id` int(11) NOT NULL,
  `mag` varchar(300) DEFAULT NULL,
  `id_job` varchar(250) DEFAULT NULL,
  `id_project` int(11) NOT NULL,
  `result` varchar(5) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `status_message` varchar(500) DEFAULT NULL,
  `validation_time` bigint(20) DEFAULT NULL,
  `warnings` longtext,
  `errors` longtext,
  `fatal_errors` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `oai_identifier`
--

CREATE TABLE `oai_identifier` (
  `identifier` varchar(255) NOT NULL,
  `count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `project`
--

CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `last_operation` varchar(255) NOT NULL,
  `last_modified` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `project_history`
--

CREATE TABLE `project_history` (
  `id` int(11) NOT NULL,
  `id_project` int(11) NOT NULL,
  `id_job` varchar(255) NOT NULL,
  `operation_type` varchar(255) NOT NULL,
  `timestamp_operation` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `processed_mag` int(11) NOT NULL DEFAULT '0',
  `indexed_mag` int(11) NOT NULL DEFAULT '0',
  `import_type` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `publication_mag`
--

CREATE TABLE `publication_mag` (
  `id` int(11) NOT NULL,
  `id_job` varchar(250) DEFAULT NULL,
  `id_mag` varchar(300) DEFAULT NULL,
  `publicate` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `teca_process`
--

CREATE TABLE `teca_process` (
  `id` varchar(250) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `timestamp_start` bigint(20) DEFAULT NULL,
  `timestamp_end` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `message` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `teca_user`
--

CREATE TABLE `teca_user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `id_role` int(11) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_role`
--

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_manager` bit(1) NOT NULL DEFAULT b'0',
  `role_manager` bit(1) NOT NULL DEFAULT b'0',
  `upload_new_manager` bit(1) NOT NULL DEFAULT b'1',
  `upload_update_manager` bit(1) NOT NULL DEFAULT b'1',
  `import_manager` bit(1) NOT NULL DEFAULT b'1',
  `update_manager` bit(1) NOT NULL DEFAULT b'1',
  `change_usage_manager` bit(1) NOT NULL DEFAULT b'1',
  `delete_project_manager` bit(1) NOT NULL DEFAULT b'1',
  `export_panel_manager` bit(1) NOT NULL DEFAULT b'1',
  `search_manager` bit(1) NOT NULL DEFAULT b'1',
  `publication_manager` bit(1) NOT NULL DEFAULT b'1',
  `delete_manager` bit(1) NOT NULL DEFAULT b'1',
  `normalize_manager` bit(1) NOT NULL DEFAULT b'1',
  `export_manager` bit(1) NOT NULL DEFAULT b'1',
  `stats_manager` bit(1) NOT NULL DEFAULT b'1',
  `draft_manager` bit(1) NOT NULL DEFAULT b'1',
  `oaiset_manager` bit(1) NOT NULL DEFAULT b'1',
  `digital_object_stats_manager` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `delete_mag`
--
ALTER TABLE `delete_mag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_delete_mag_1_idx` (`id_job`);

--
-- Indici per le tabelle `export`
--
ALTER TABLE `export`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Indici per le tabelle `import_detail`
--
ALTER TABLE `import_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_import_detail_1_idx` (`id_job`),
  ADD KEY `id_job` (`id_job`);

--
-- Indici per le tabelle `import_mag`
--
ALTER TABLE `import_mag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_import_mag_1_idx` (`id_job`),
  ADD KEY `id_job` (`id_job`),
  ADD KEY `id_project` (`id_project`);

--
-- Indici per le tabelle `oai_identifier`
--
ALTER TABLE `oai_identifier`
  ADD PRIMARY KEY (`identifier`);

--
-- Indici per le tabelle `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `project_history`
--
ALTER TABLE `project_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_job` (`id_job`),
  ADD KEY `id_project` (`id_project`);

--
-- Indici per le tabelle `publication_mag`
--
ALTER TABLE `publication_mag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_publication_mag_1_idx` (`id_job`);

--
-- Indici per le tabelle `teca_process`
--
ALTER TABLE `teca_process`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Indici per le tabelle `teca_user`
--
ALTER TABLE `teca_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_role` (`id_role`);

--
-- Indici per le tabelle `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `delete_mag`
--
ALTER TABLE `delete_mag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `export`
--
ALTER TABLE `export`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `import_detail`
--
ALTER TABLE `import_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `import_mag`
--
ALTER TABLE `import_mag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `project`
--
ALTER TABLE `project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `project_history`
--
ALTER TABLE `project_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `publication_mag`
--
ALTER TABLE `publication_mag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `teca_user`
--
ALTER TABLE `teca_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `delete_mag`
--
ALTER TABLE `delete_mag`
  ADD CONSTRAINT `fk_delete_mag_1` FOREIGN KEY (`id_job`) REFERENCES `teca_process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `export`
--
ALTER TABLE `export`
  ADD CONSTRAINT `export_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `teca_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `import_detail`
--
ALTER TABLE `import_detail`
  ADD CONSTRAINT `fk_import_detail_1` FOREIGN KEY (`id_job`) REFERENCES `teca_process` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `import_mag`
--
ALTER TABLE `import_mag`
  ADD CONSTRAINT `fk_import_mag_1` FOREIGN KEY (`id_job`) REFERENCES `teca_process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `import_mag_ibfk_1` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `project_history`
--
ALTER TABLE `project_history`
  ADD CONSTRAINT `project_history_ibfk_1` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `project_history_ibfk_2` FOREIGN KEY (`id_job`) REFERENCES `teca_process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `publication_mag`
--
ALTER TABLE `publication_mag`
  ADD CONSTRAINT `fk_publication_mag_1` FOREIGN KEY (`id_job`) REFERENCES `teca_process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `teca_process`
--
ALTER TABLE `teca_process`
  ADD CONSTRAINT `teca_process_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `teca_user` (`id`);

--
-- Limiti per la tabella `teca_user`
--
ALTER TABLE `teca_user`
  ADD CONSTRAINT `role_fk` FOREIGN KEY (`id_role`) REFERENCES `user_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
