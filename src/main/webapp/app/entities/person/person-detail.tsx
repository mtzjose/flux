import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './person.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personEntity = useAppSelector(state => state.person.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personDetailsHeading">
          <Translate contentKey="fluxApp.person.detail.title">Person</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fluxApp.person.id">Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.id}</dd>
          <dt>
            <span id="meta">
              <Translate contentKey="fluxApp.person.meta">Meta</Translate>
            </span>
          </dt>
          <dd>{personEntity.meta}</dd>
          <dt>
            <span id="profilePicture">
              <Translate contentKey="fluxApp.person.profilePicture">Profile Picture</Translate>
            </span>
          </dt>
          <dd>
            {personEntity.profilePicture ? (
              <div>
                {personEntity.profilePictureContentType ? (
                  <a onClick={openFile(personEntity.profilePictureContentType, personEntity.profilePicture)}>
                    <img
                      src={`data:${personEntity.profilePictureContentType};base64,${personEntity.profilePicture}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {personEntity.profilePictureContentType}, {byteSize(personEntity.profilePicture)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="firstname">
              <Translate contentKey="fluxApp.person.firstname">Firstname</Translate>
            </span>
          </dt>
          <dd>{personEntity.firstname}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="fluxApp.person.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{personEntity.lastname}</dd>
          <dt>
            <span id="middlename">
              <Translate contentKey="fluxApp.person.middlename">Middlename</Translate>
            </span>
          </dt>
          <dd>{personEntity.middlename}</dd>
          <dt>
            <span id="bio">
              <Translate contentKey="fluxApp.person.bio">Bio</Translate>
            </span>
          </dt>
          <dd>{personEntity.bio}</dd>
          <dt>
            <span id="school">
              <Translate contentKey="fluxApp.person.school">School</Translate>
            </span>
          </dt>
          <dd>{personEntity.school}</dd>
          <dt>
            <span id="major">
              <Translate contentKey="fluxApp.person.major">Major</Translate>
            </span>
          </dt>
          <dd>{personEntity.major}</dd>
          <dt>
            <span id="socialLinks">
              <Translate contentKey="fluxApp.person.socialLinks">Social Links</Translate>
            </span>
          </dt>
          <dd>{personEntity.socialLinks}</dd>
          <dt>
            <span id="nationalityId">
              <Translate contentKey="fluxApp.person.nationalityId">Nationality Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.nationalityId}</dd>
          <dt>
            <span id="genderId">
              <Translate contentKey="fluxApp.person.genderId">Gender Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.genderId}</dd>
          <dt>
            <span id="pronounId">
              <Translate contentKey="fluxApp.person.pronounId">Pronoun Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.pronounId}</dd>
          <dt>
            <span id="raceId">
              <Translate contentKey="fluxApp.person.raceId">Race Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.raceId}</dd>
          <dt>
            <span id="addressId">
              <Translate contentKey="fluxApp.person.addressId">Address Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.addressId}</dd>
          <dt>
            <span id="birthdate">
              <Translate contentKey="fluxApp.person.birthdate">Birthdate</Translate>
            </span>
          </dt>
          <dd>
            {personEntity.birthdate ? <TextFormat value={personEntity.birthdate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fluxApp.person.school">School</Translate>
          </dt>
          <dd>{personEntity.school ? personEntity.school.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.person.major">Major</Translate>
          </dt>
          <dd>{personEntity.major ? personEntity.major.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.person.nationalityId">Nationality Id</Translate>
          </dt>
          <dd>{personEntity.nationalityId ? personEntity.nationalityId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.person.genderId">Gender Id</Translate>
          </dt>
          <dd>{personEntity.genderId ? personEntity.genderId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.person.pronounId">Pronoun Id</Translate>
          </dt>
          <dd>{personEntity.pronounId ? personEntity.pronounId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.person.raceId">Race Id</Translate>
          </dt>
          <dd>{personEntity.raceId ? personEntity.raceId.id : ''}</dd>
          <dt>
            <Translate contentKey="fluxApp.person.addressId">Address Id</Translate>
          </dt>
          <dd>{personEntity.addressId ? personEntity.addressId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/person" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/person/${personEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonDetail;
