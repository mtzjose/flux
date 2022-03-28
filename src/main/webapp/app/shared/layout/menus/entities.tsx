import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/person">
      <Translate contentKey="global.menu.entities.person" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company">
      <Translate contentKey="global.menu.entities.company" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/opportunity">
      <Translate contentKey="global.menu.entities.opportunity" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/job">
      <Translate contentKey="global.menu.entities.job" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/founder">
      <Translate contentKey="global.menu.entities.founder" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-position">
      <Translate contentKey="global.menu.entities.companyPosition" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/city">
      <Translate contentKey="global.menu.entities.city" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      <Translate contentKey="global.menu.entities.country" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/gender">
      <Translate contentKey="global.menu.entities.gender" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/process-stage">
      <Translate contentKey="global.menu.entities.processStage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/college-degree">
      <Translate contentKey="global.menu.entities.collegeDegree" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pronoun">
      <Translate contentKey="global.menu.entities.pronoun" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/school">
      <Translate contentKey="global.menu.entities.school" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/race">
      <Translate contentKey="global.menu.entities.race" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/contact-source">
      <Translate contentKey="global.menu.entities.contactSource" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-category">
      <Translate contentKey="global.menu.entities.companyCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-categories">
      <Translate contentKey="global.menu.entities.companyCategories" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/job-history">
      <Translate contentKey="global.menu.entities.jobHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/person-company-history">
      <Translate contentKey="global.menu.entities.personCompanyHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-range">
      <Translate contentKey="global.menu.entities.employeeRange" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/founder-positions">
      <Translate contentKey="global.menu.entities.founderPositions" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
