SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

--
-- Database: `tecadigitale_db`
--

--
-- Dump dei dati per la tabella `teca_user`
--

use tecadigitale_db;

INSERT INTO `teca_user` (`id`, `name`, `surname`, `username`, `password`, `email`, `enabled`, `id_role`, `deleted`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', '', b'1', 1, b'0'),
(2, 'operator', 'operator', 'operator', 'operator', '', b'1', 2, b'0'),
(3, 'consultor', 'consultor', 'consultor', 'consultor', '', b'1', 3, b'0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
