import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './contact-source.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContactSourceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contactSourceEntity = useAppSelector(state => state.contactSource.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactSourceDetailsHeading">
          <Translate contentKey="fluxApp.contactSource.detail.title">ContactSource</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.contactSource.id">Id</Translate>
            </span>
          </dt>
          <dd>{contactSourceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fluxApp.contactSource.name">Name</Translate>
            </span>
          </dt>
          <dd>{contactSourceEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/contact-source" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact-source/${contactSourceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactSourceDetail;
