import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Person from './person';
import Company from './company';
import Opportunity from './opportunity';
import Address from './address';
import Job from './job';
import Founder from './founder';
import CompanyPosition from './company-position';
import City from './city';
import Country from './country';
import Gender from './gender';
import ProcessStage from './process-stage';
import CollegeDegree from './college-degree';
import Pronoun from './pronoun';
import School from './school';
import Race from './race';
import ContactSource from './contact-source';
import CompanyCategory from './company-category';
import CompanyCategories from './company-categories';
import JobHistory from './job-history';
import PersonCompanyHistory from './person-company-history';
import EmployeeRange from './employee-range';
import FounderPositions from './founder-positions';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}person`} component={Person} />
      <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}opportunity`} component={Opportunity} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}job`} component={Job} />
      <ErrorBoundaryRoute path={`${match.url}founder`} component={Founder} />
      <ErrorBoundaryRoute path={`${match.url}company-position`} component={CompanyPosition} />
      <ErrorBoundaryRoute path={`${match.url}city`} component={City} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}gender`} component={Gender} />
      <ErrorBoundaryRoute path={`${match.url}process-stage`} component={ProcessStage} />
      <ErrorBoundaryRoute path={`${match.url}college-degree`} component={CollegeDegree} />
      <ErrorBoundaryRoute path={`${match.url}pronoun`} component={Pronoun} />
      <ErrorBoundaryRoute path={`${match.url}school`} component={School} />
      <ErrorBoundaryRoute path={`${match.url}race`} component={Race} />
      <ErrorBoundaryRoute path={`${match.url}contact-source`} component={ContactSource} />
      <ErrorBoundaryRoute path={`${match.url}company-category`} component={CompanyCategory} />
      <ErrorBoundaryRoute path={`${match.url}company-categories`} component={CompanyCategories} />
      <ErrorBoundaryRoute path={`${match.url}job-history`} component={JobHistory} />
      <ErrorBoundaryRoute path={`${match.url}person-company-history`} component={PersonCompanyHistory} />
      <ErrorBoundaryRoute path={`${match.url}employee-range`} component={EmployeeRange} />
      <ErrorBoundaryRoute path={`${match.url}founder-positions`} component={FounderPositions} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
