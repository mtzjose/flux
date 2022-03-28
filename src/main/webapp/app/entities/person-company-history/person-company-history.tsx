import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './person-company-history.reducer';
import { IPersonCompanyHistory } from 'app/shared/model/person-company-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonCompanyHistory = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const personCompanyHistoryList = useAppSelector(state => state.personCompanyHistory.entities);
  const loading = useAppSelector(state => state.personCompanyHistory.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="person-company-history-heading" data-cy="PersonCompanyHistoryHeading">
        <Translate contentKey="fluxApp.personCompanyHistory.home.title">Person Company Histories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fluxApp.personCompanyHistory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fluxApp.personCompanyHistory.home.createLabel">Create new Person Company History</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {personCompanyHistoryList && personCompanyHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fluxApp.personCompanyHistory.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.personCompanyHistory.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.personCompanyHistory.personId">Person Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.personCompanyHistory.investor">Investor</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.personCompanyHistory.founder">Founder</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personCompanyHistoryList.map((personCompanyHistory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${personCompanyHistory.id}`} color="link" size="sm">
                      {personCompanyHistory.id}
                    </Button>
                  </td>
                  <td>{personCompanyHistory.companyId}</td>
                  <td>{personCompanyHistory.personId}</td>
                  <td>{personCompanyHistory.investor ? 'true' : 'false'}</td>
                  <td>{personCompanyHistory.founder ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${personCompanyHistory.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${personCompanyHistory.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${personCompanyHistory.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="fluxApp.personCompanyHistory.home.notFound">No Person Company Histories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PersonCompanyHistory;
