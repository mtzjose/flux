import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './person.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Person = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const personList = useAppSelector(state => state.person.entities);
  const loading = useAppSelector(state => state.person.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="person-heading" data-cy="PersonHeading">
        <Translate contentKey="fluxApp.person.home.title">People</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fluxApp.person.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fluxApp.person.home.createLabel">Create new Person</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {personList && personList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fluxApp.person.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.meta">Meta</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.profilePicture">Profile Picture</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.firstname">Firstname</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.lastname">Lastname</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.middlename">Middlename</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.bio">Bio</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.school">School</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.major">Major</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.socialLinks">Social Links</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.nationalityId">Nationality Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.genderId">Gender Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.pronounId">Pronoun Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.raceId">Race Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.addressId">Address Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.birthdate">Birthdate</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.school">School</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.major">Major</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.nationalityId">Nationality Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.genderId">Gender Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.pronounId">Pronoun Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.raceId">Race Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fluxApp.person.addressId">Address Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personList.map((person, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${person.id}`} color="link" size="sm">
                      {person.id}
                    </Button>
                  </td>
                  <td>{person.meta}</td>
                  <td>
                    {person.profilePicture ? (
                      <div>
                        {person.profilePictureContentType ? (
                          <a onClick={openFile(person.profilePictureContentType, person.profilePicture)}>
                            <img
                              src={`data:${person.profilePictureContentType};base64,${person.profilePicture}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {person.profilePictureContentType}, {byteSize(person.profilePicture)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{person.firstname}</td>
                  <td>{person.lastname}</td>
                  <td>{person.middlename}</td>
                  <td>{person.bio}</td>
                  <td>{person.school}</td>
                  <td>{person.major}</td>
                  <td>{person.socialLinks}</td>
                  <td>{person.nationalityId}</td>
                  <td>{person.genderId}</td>
                  <td>{person.pronounId}</td>
                  <td>{person.raceId}</td>
                  <td>{person.addressId}</td>
                  <td>{person.birthdate ? <TextFormat type="date" value={person.birthdate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{person.school ? <Link to={`school/${person.school.id}`}>{person.school.id}</Link> : ''}</td>
                  <td>{person.major ? <Link to={`college-degree/${person.major.id}`}>{person.major.id}</Link> : ''}</td>
                  <td>{person.nationalityId ? <Link to={`country/${person.nationalityId.id}`}>{person.nationalityId.id}</Link> : ''}</td>
                  <td>{person.genderId ? <Link to={`gender/${person.genderId.id}`}>{person.genderId.id}</Link> : ''}</td>
                  <td>{person.pronounId ? <Link to={`pronoun/${person.pronounId.id}`}>{person.pronounId.id}</Link> : ''}</td>
                  <td>{person.raceId ? <Link to={`race/${person.raceId.id}`}>{person.raceId.id}</Link> : ''}</td>
                  <td>{person.addressId ? <Link to={`address/${person.addressId.id}`}>{person.addressId.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${person.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${person.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${person.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="fluxApp.person.home.notFound">No People found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Person;
