import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './college-degree.reducer';
import { ICollegeDegree } from 'app/shared/model/college-degree.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CollegeDegree = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const collegeDegreeList = useAppSelector(state => state.collegeDegree.entities);
  const loading = useAppSelector(state => state.collegeDegree.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="college-degree-heading" data-cy="CollegeDegreeHeading">
        <Translate contentKey="fluxApp.collegeDegree.home.title">College Degrees</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fluxApp.collegeDegree.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fluxApp.collegeDegree.home.createLabel">Create new College Degree</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {collegeDegreeList && collegeDegreeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fluxApp.collegeDegree.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.collegeDegree.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {collegeDegreeList.map((collegeDegree, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${collegeDegree.id}`} color="link" size="sm">
                      {collegeDegree.id}
                    </Button>
                  </td>
                  <td>{collegeDegree.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${collegeDegree.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${collegeDegree.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${collegeDegree.id}/delete`}
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
              <Translate contentKey="fluxApp.collegeDegree.home.notFound">No College Degrees found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CollegeDegree;
