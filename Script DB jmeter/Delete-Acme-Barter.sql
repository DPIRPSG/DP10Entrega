start transaction;

use `Acme-Barter`;

revoke all privileges on `Acme-Barter`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Barter`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Barter`;

commit;