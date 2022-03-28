import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContactSource from './contact-source';
import ContactSourceDetail from './contact-source-detail';
import ContactSourceUpdate from './contact-source-update';
import ContactSourceDeleteDialog from './contact-source-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactSourceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactSourceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactSourceDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContactSource} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContactSourceDeleteDialog} />
  </>
);

export default Routes;
