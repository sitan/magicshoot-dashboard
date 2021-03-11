import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/admin">
      <Translate contentKey="global.menu.entities.admin" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/reseller">
      <Translate contentKey="global.menu.entities.reseller" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company">
      <Translate contentKey="global.menu.entities.company" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/contact">
      <Translate contentKey="global.menu.entities.contact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer">
      <Translate contentKey="global.menu.entities.customer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/quote">
      <Translate contentKey="global.menu.entities.quote" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/invoice">
      <Translate contentKey="global.menu.entities.invoice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/event">
      <Translate contentKey="global.menu.entities.event" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/device">
      <Translate contentKey="global.menu.entities.device" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/media-in">
      <Translate contentKey="global.menu.entities.mediaIn" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/media-out">
      <Translate contentKey="global.menu.entities.mediaOut" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/label">
      <Translate contentKey="global.menu.entities.label" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/print">
      <Translate contentKey="global.menu.entities.print" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payments">
      <Translate contentKey="global.menu.entities.payments" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
