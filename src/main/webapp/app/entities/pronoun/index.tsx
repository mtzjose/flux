import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pronoun from './pronoun';
import PronounDetail from './pronoun-detail';
import PronounUpdate from './pronoun-update';
import PronounDeleteDialog from './pronoun-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PronounUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PronounUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PronounDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pronoun} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PronounDeleteDialog} />
  </>
);

export default Routes;
