import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './opportunity.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OpportunityDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const opportunityEntity = useAppSelector(state => state.opportunity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opportunityDetailsHeading">
          <Translate contentKey="fluxApp.opportunity.detail.title">Opportunity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.opportunity.id">Id</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.id}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="fluxApp.opportunity.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.companyId}</dd>
          <dt>
            <span id="applyDate">
              <Translate contentKey="fluxApp.opportunity.applyDate">Apply Date</Translate>
            </span>
          </dt>
          <dd>
            {opportunityEntity.applyDate ? (
              <TextFormat value={opportunityEntity.applyDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contactSourceId">
              <Translate contentKey="fluxApp.opportunity.contactSourceId">Contact Source Id</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.contactSourceId}</dd>
          <dt>
            <span id="processStageId">
              <Translate contentKey="fluxApp.opportunity.processStageId">Process Stage Id</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.processStageId}</dd>
          <dt>
            <Translate contentKey="fluxApp.opportunity.companyId">Company Id</Translate>
          </dt>
          <dd>{opportunityEntity.companyId ? opportunityEntity.companyId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.opportunity.contactSourceId">Contact Source Id</Translate>
          </dt>
          <dd>{opportunityEntity.contactSourceId ? opportunityEntity.contactSourceId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.opportunity.processStageId">Process Stage Id</Translate>
          </dt>
          <dd>{opportunityEntity.processStageId ? opportunityEntity.processStageId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/opportunity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opportunity/${opportunityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpportunityDetail;
