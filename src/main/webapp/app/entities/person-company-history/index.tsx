import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonCompanyHistory from './person-company-history';
import PersonCompanyHistoryDetail from './person-company-history-detail';
import PersonCompanyHistoryUpdate from './person-company-history-update';
import PersonCompanyHistoryDeleteDialog from './person-company-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PersonCompanyHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PersonCompanyHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonCompanyHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={PersonCompanyHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PersonCompanyHistoryDeleteDialog} />
  </>
);

export default Routes;
