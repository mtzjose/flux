import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Company = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const companyList = useAppSelector(state => state.company.entities);
  const loading = useAppSelector(state => state.company.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="company-heading" data-cy="CompanyHeading">
        <Translate contentKey="fluxApp.company.home.title">Companies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fluxApp.company.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fluxApp.company.home.createLabel">Create new Company</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {companyList && companyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fluxApp.company.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.meta">Meta</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.logo">Logo</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.color">Color</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.legalName">Legal Name</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.oneLiner">One Liner</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.foundingDate">Founding Date</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.socialLinks">Social Links</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.addressId">Address Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.employeeRange">Employee Range</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.addressId">Address Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.company.employeeRange">Employee Range</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyList.map((company, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${company.id}`} color="link" size="sm">
                      {company.id}
                    </Button>
                  </td>
                  <td>{company.meta}</td>
                  <td>
                    {company.logo ? (
                      <div>
                        {company.logoContentType ? (
                          <a onClick={openFile(company.logoContentType, company.logo)}>
                            <img src={`data:${company.logoContentType};base64,${company.logo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {company.logoContentType}, {byteSize(company.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{company.color}</td>
                  <td>{company.name}</td>
                  <td>{company.legalName}</td>
                  <td>{company.oneLiner}</td>
                  <td>{company.description}</td>
                  <td>
                    {company.foundingDate ? <TextFormat type="date" value={company.foundingDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{company.socialLinks}</td>
                  <td>{company.addressId}</td>
                  <td>{company.employeeRange}</td>
                  <td>{company.addressId ? <Link to={`address/${company.addressId.id}`}>{company.addressId.id}</Link> : ''}</td>
                  <td>
                    {company.employeeRange ? <Link to={`employee-range/${company.employeeRange.id}`}>{company.employeeRange.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${company.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${company.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${company.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="fluxApp.company.home.notFound">No Companies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Company;
