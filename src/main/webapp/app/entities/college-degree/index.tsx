import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CollegeDegree from './college-degree';
import CollegeDegreeDetail from './college-degree-detail';
import CollegeDegreeUpdate from './college-degree-update';
import CollegeDegreeDeleteDialog from './college-degree-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CollegeDegreeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CollegeDegreeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CollegeDegreeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CollegeDegree} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CollegeDegreeDeleteDialog} />
  </>
);

export default Routes;
