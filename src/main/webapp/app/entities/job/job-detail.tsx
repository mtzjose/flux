import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './job.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const JobDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const jobEntity = useAppSelector(state => state.job.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobDetailsHeading">
          <Translate contentKey="fluxApp.job.detail.title">Job</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.job.id">Id</Translate>
            </span>
          </dt>
          <dd>{jobEntity.id}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="fluxApp.job.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{jobEntity.companyId}</dd>
          <dt>
            <span id="jobPositionId">
              <Translate contentKey="fluxApp.job.jobPositionId">Job Position Id</Translate>
            </span>
          </dt>
          <dd>{jobEntity.jobPositionId}</dd>
          <dt>
            <Translate contentKey="fluxApp.job.companyId">Company Id</Translate>
          </dt>
          <dd>{jobEntity.companyId ? jobEntity.companyId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.job.jobPositionId">Job Position Id</Translate>
          </dt>
          <dd>{jobEntity.jobPositionId ? jobEntity.jobPositionId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.job.companyId">Company Id</Translate>
          </dt>
          <dd>{jobEntity.companyId ? jobEntity.companyId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.job.jobPositionId">Job Position Id</Translate>
          </dt>
          <dd>{jobEntity.jobPositionId ? jobEntity.jobPositionId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job/${jobEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobDetail;
