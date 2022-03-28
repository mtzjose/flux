import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './opportunity.reducer';
import { IOpportunity } from 'app/shared/model/opportunity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Opportunity = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const opportunityList = useAppSelector(state => state.opportunity.entities);
  const loading = useAppSelector(state => state.opportunity.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="opportunity-heading" data-cy="OpportunityHeading">
        <Translate contentKey="fluxApp.opportunity.home.title">Opportunities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fluxApp.opportunity.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fluxApp.opportunity.home.createLabel">Create new Opportunity</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {opportunityList && opportunityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fluxApp.opportunity.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.applyDate">Apply Date</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.contactSourceId">Contact Source Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.processStageId">Process Stage Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.contactSourceId">Contact Source Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.opportunity.processStageId">Process Stage Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {opportunityList.map((opportunity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${opportunity.id}`} color="link" size="sm">
                      {opportunity.id}
                    </Button>
                  </td>
                  <td>{opportunity.companyId}</td>
                  <td>
                    {opportunity.applyDate ? <TextFormat type="date" value={opportunity.applyDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{opportunity.contactSourceId}</td>
                  <td>{opportunity.processStageId}</td>
                  <td>{opportunity.companyId ? <Link to={`company/${opportunity.companyId.id}`}>{opportunity.companyId.id}</Link> : ''}</td>
                  <td>
                    {opportunity.contactSourceId ? (
                      <Link to={`contact-source/${opportunity.contactSourceId.id}`}>{opportunity.contactSourceId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {opportunity.processStageId ? (
                      <Link to={`process-stage/${opportunity.processStageId.id}`}>{opportunity.processStageId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${opportunity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${opportunity.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${opportunity.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="fluxApp.opportunity.home.notFound">No Opportunities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Opportunity;
