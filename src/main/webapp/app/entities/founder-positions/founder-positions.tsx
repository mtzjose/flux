import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './founder-positions.reducer';
import { IFounderPositions } from 'app/shared/model/founder-positions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FounderPositions = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const founderPositionsList = useAppSelector(state => state.founderPositions.entities);
  const loading = useAppSelector(state => state.founderPositions.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="founder-positions-heading" data-cy="FounderPositionsHeading">
        <Translate contentKey="fluxApp.founderPositions.home.title">Founder Positions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fluxApp.founderPositions.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fluxApp.founderPositions.home.createLabel">Create new Founder Positions</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {founderPositionsList && founderPositionsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fluxApp.founderPositions.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.founderPositions.positionId">Position Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.founderPositions.companyId">Company Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {founderPositionsList.map((founderPositions, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${founderPositions.id}`} color="link" size="sm">
                      {founderPositions.id}
                    </Button>
                  </td>
                  <td>{founderPositions.positionId}</td>
                  <td>{founderPositions.companyId}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${founderPositions.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${founderPositions.id}/edit`}
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
                        to={`${match.url}/${founderPositions.id}/delete`}
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
              <Translate contentKey="fluxApp.founderPositions.home.notFound">No Founder Positions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FounderPositions;
