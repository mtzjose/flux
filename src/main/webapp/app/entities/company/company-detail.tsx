import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">
          <Translate contentKey="fluxApp.company.detail.title">Company</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.company.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="meta">
              <Translate contentKey="fluxApp.company.meta">Meta</Translate>
            </span>
          </dt>
          <dd>{companyEntity.meta}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="fluxApp.company.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {companyEntity.logo ? (
              <div>
                {companyEntity.logoContentType ? (
                  <a onClick={openFile(companyEntity.logoContentType, companyEntity.logo)}>
                    <img src={`data:${companyEntity.logoContentType};base64,${companyEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {companyEntity.logoContentType}, {byteSize(companyEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="color">
              <Translate contentKey="fluxApp.company.color">Color</Translate>
            </span>
          </dt>
          <dd>{companyEntity.color}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fluxApp.company.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="legalName">
              <Translate contentKey="fluxApp.company.legalName">Legal Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.legalName}</dd>
          <dt>
            <span id="oneLiner">
              <Translate contentKey="fluxApp.company.oneLiner">One Liner</Translate>
            </span>
          </dt>
          <dd>{companyEntity.oneLiner}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="fluxApp.company.description">Description</Translate>
            </span>
          </dt>
          <dd>{companyEntity.description}</dd>
          <dt>
            <span id="foundingDate">
              <Translate contentKey="fluxApp.company.foundingDate">Founding Date</Translate>
            </span>
          </dt>
          <dd>
            {companyEntity.foundingDate ? (
              <TextFormat value={companyEntity.foundingDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="socialLinks">
              <Translate contentKey="fluxApp.company.socialLinks">Social Links</Translate>
            </span>
          </dt>
          <dd>{companyEntity.socialLinks}</dd>
          <dt>
            <span id="addressId">
              <Translate contentKey="fluxApp.company.addressId">Address Id</Translate>
            </span>
          </dt>
          <dd>{companyEntity.addressId}</dd>
          <dt>
            <span id="employeeRange">
              <Translate contentKey="fluxApp.company.employeeRange">Employee Range</Translate>
            </span>
          </dt>
          <dd>{companyEntity.employeeRange}</dd>
          <dt>
            <Translate contentKey="fluxApp.company.addressId">Address Id</Translate>
          </dt>
          <dd>{companyEntity.addressId ? companyEntity.addressId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.company.employeeRange">Employee Range</Translate>
          </dt>
          <dd>{companyEntity.employeeRange ? companyEntity.employeeRange.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;
