SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

--
-- Database: `tecadigitale_db`
--

--
-- Dump dei dati per la tabella `user_role`
--
use tecadigitale_db;

INSERT INTO `user_role` (`id`, `name`, `user_manager`, `role_manager`, `upload_new_manager`, `upload_update_manager`, `import_manager`, `update_manager`, `change_usage_manager`, `delete_project_manager`, `export_panel_manager`, `search_manager`, `publication_manager`, `delete_manager`, `normalize_manager`, `export_manager`, `stats_manager`, `draft_manager`, `oaiset_manager`, `digital_object_stats_manager`) VALUES
(1, 'AMMINISTRATORE', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1'),
(2, 'OPERATORE', b'0', b'0', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1'),
(3, 'CONSULTATORE', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'0', b'1');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
