import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { ICollegeDegree } from 'app/shared/model/college-degree.model';
import { getEntities as getCollegeDegrees } from 'app/entities/college-degree/college-degree.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IGender } from 'app/shared/model/gender.model';
import { getEntities as getGenders } from 'app/entities/gender/gender.reducer';
import { IPronoun } from 'app/shared/model/pronoun.model';
import { getEntities as getPronouns } from 'app/entities/pronoun/pronoun.reducer';
import { IRace } from 'app/shared/model/race.model';
import { getEntities as getRaces } from 'app/entities/race/race.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './person.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const schools = useAppSelector(state => state.school.entities);
  const collegeDegrees = useAppSelector(state => state.collegeDegree.entities);
  const countries = useAppSelector(state => state.country.entities);
  const genders = useAppSelector(state => state.gender.entities);
  const pronouns = useAppSelector(state => state.pronoun.entities);
  const races = useAppSelector(state => state.race.entities);
  const addresses = useAppSelector(state => state.address.entities);
  const personEntity = useAppSelector(state => state.person.entity);
  const loading = useAppSelector(state => state.person.loading);
  const updating = useAppSelector(state => state.person.updating);
  const updateSuccess = useAppSelector(state => state.person.updateSuccess);
  const handleClose = () => {
    props.history.push('/person');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSchools({}));
    dispatch(getCollegeDegrees({}));
    dispatch(getCountries({}));
    dispatch(getGenders({}));
    dispatch(getPronouns({}));
    dispatch(getRaces({}));
    dispatch(getAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...personEntity,
      ...values,
      school: schools.find(it => it.id.toString() === values.school.toString()),
      major: collegeDegrees.find(it => it.id.toString() === values.major.toString()),
      nationalityId: countries.find(it => it.id.toString() === values.nationalityId.toString()),
      genderId: genders.find(it => it.id.toString() === values.genderId.toString()),
      pronounId: pronouns.find(it => it.id.toString() === values.pronounId.toString()),
      raceId: races.find(it => it.id.toString() === values.raceId.toString()),
      addressId: addresses.find(it => it.id.toString() === values.addressId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...personEntity,
          school: personEntity?.school?.id,
          major: personEntity?.major?.id,
          nationalityId: personEntity?.nationalityId?.id,
          genderId: personEntity?.genderId?.id,
          pronounId: personEntity?.pronounId?.id,
          raceId: personEntity?.raceId?.id,
          addressId: personEntity?.addressId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fluxApp.person.home.createOrEditLabel" data-cy="PersonCreateUpdateHeading">
            <Translate contentKey="fluxApp.person.home.createOrEditLabel">Create or edit a Person</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="person-id"
                  label={translate('fluxApp.person.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('fluxApp.person.meta')} id="person-meta" name="meta" data-cy="meta" type="textarea" />
              <ValidatedBlobField
                label={translate('fluxApp.person.profilePicture')}
                id="person-profilePicture"
                name="profilePicture"
                data-cy="profilePicture"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('fluxApp.person.firstname')}
                id="person-firstname"
                name="firstname"
                data-cy="firstname"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.person.lastname')}
                id="person-lastname"
                name="lastname"
                data-cy="lastname"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.person.middlename')}
                id="person-middlename"
                name="middlename"
                data-cy="middlename"
                type="text"
              />
              <ValidatedField label={translate('fluxApp.person.bio')} id="person-bio" name="bio" data-cy="bio" type="textarea" />
              <ValidatedField label={translate('fluxApp.person.school')} id="person-school" name="school" data-cy="school" type="text" />
              <ValidatedField label={translate('fluxApp.person.major')} id="person-major" name="major" data-cy="major" type="text" />
              <ValidatedField
                label={translate('fluxApp.person.socialLinks')}
                id="person-socialLinks"
                name="socialLinks"
                data-cy="socialLinks"
                type="textarea"
              />
              <ValidatedField
                label={translate('fluxApp.person.nationalityId')}
                id="person-nationalityId"
                name="nationalityId"
                data-cy="nationalityId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.person.genderId')}
                id="person-genderId"
                name="genderId"
                data-cy="genderId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.person.pronounId')}
                id="person-pronounId"
                name="pronounId"
                data-cy="pronounId"
                type="text"
              />
              <ValidatedField label={translate('fluxApp.person.raceId')} id="person-raceId" name="raceId" data-cy="raceId" type="text" />
              <ValidatedField
                label={translate('fluxApp.person.addressId')}
                id="person-addressId"
                name="addressId"
                data-cy="addressId"
                type="text"
              />
              <ValidatedField
                label={translate('fluxApp.person.birthdate')}
                id="person-birthdate"
                name="birthdate"
                data-cy="birthdate"
                type="date"
              />
              <ValidatedField id="person-school" name="school" data-cy="school" label={translate('fluxApp.person.school')} type="select">
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="person-major" name="major" data-cy="major" label={translate('fluxApp.person.major')} type="select">
                <option value="" key="0" />
                {collegeDegrees
                  ? collegeDegrees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="person-nationalityId"
                name="nationalityId"
                data-cy="nationalityId"
                label={translate('fluxApp.person.nationalityId')}
                type="select"
              >
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="person-genderId"
                name="genderId"
                data-cy="genderId"
                label={translate('fluxApp.person.genderId')}
                type="select"
              >
                <option value="" key="0" />
                {genders
                  ? genders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="person-pronounId"
                name="pronounId"
                data-cy="pronounId"
                label={translate('fluxApp.person.pronounId')}
                type="select"
              >
                <option value="" key="0" />
                {pronouns
                  ? pronouns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="person-raceId" name="raceId" data-cy="raceId" label={translate('fluxApp.person.raceId')} type="select">
                <option value="" key="0" />
                {races
                  ? races.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="person-addressId"
                name="addressId"
                data-cy="addressId"
                label={translate('fluxApp.person.addressId')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/person" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PersonUpdate;
