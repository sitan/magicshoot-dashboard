import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EventUpdate = (props: IEventUpdateProps) => {
  const [companyId, setCompanyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { eventEntity, companies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/event');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...eventEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dashboardApp.event.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.event.home.createOrEditLabel">Create or edit a Event</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : eventEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="event-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="event-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="eventIdLabel" for="event-eventId">
                  <Translate contentKey="dashboardApp.event.eventId">Event Id</Translate>
                </Label>
                <AvField id="event-eventId" type="string" className="form-control" name="eventId" />
              </AvGroup>
              <AvGroup>
                <Label id="companyIdLabel" for="event-companyId">
                  <Translate contentKey="dashboardApp.event.companyId">Company Id</Translate>
                </Label>
                <AvField id="event-companyId" type="string" className="form-control" name="companyId" />
              </AvGroup>
              <AvGroup>
                <Label id="eventNameLabel" for="event-eventName">
                  <Translate contentKey="dashboardApp.event.eventName">Event Name</Translate>
                </Label>
                <AvField id="event-eventName" type="text" name="eventName" />
              </AvGroup>
              <AvGroup>
                <Label id="eventStartDateLabel" for="event-eventStartDate">
                  <Translate contentKey="dashboardApp.event.eventStartDate">Event Start Date</Translate>
                </Label>
                <AvField id="event-eventStartDate" type="date" className="form-control" name="eventStartDate" />
              </AvGroup>
              <AvGroup>
                <Label id="eventEndDateLabel" for="event-eventEndDate">
                  <Translate contentKey="dashboardApp.event.eventEndDate">Event End Date</Translate>
                </Label>
                <AvField id="event-eventEndDate" type="date" className="form-control" name="eventEndDate" />
              </AvGroup>
              <AvGroup>
                <Label id="eventStartTimeLabel" for="event-eventStartTime">
                  <Translate contentKey="dashboardApp.event.eventStartTime">Event Start Time</Translate>
                </Label>
                <AvField id="event-eventStartTime" type="date" className="form-control" name="eventStartTime" />
              </AvGroup>
              <AvGroup>
                <Label id="eventEndTimeLabel" for="event-eventEndTime">
                  <Translate contentKey="dashboardApp.event.eventEndTime">Event End Time</Translate>
                </Label>
                <AvField id="event-eventEndTime" type="date" className="form-control" name="eventEndTime" />
              </AvGroup>
              <AvGroup>
                <Label id="eventTypeLabel" for="event-eventType">
                  <Translate contentKey="dashboardApp.event.eventType">Event Type</Translate>
                </Label>
                <AvInput
                  id="event-eventType"
                  type="select"
                  className="form-control"
                  name="eventType"
                  value={(!isNew && eventEntity.eventType) || 'ONEOFF'}
                >
                  <option value="ONEOFF">{translate('dashboardApp.EventType.ONEOFF')}</option>
                  <option value="MULTIPLEDAYS">{translate('dashboardApp.EventType.MULTIPLEDAYS')}</option>
                  <option value="SEMIPERMANENT">{translate('dashboardApp.EventType.SEMIPERMANENT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="event-company">
                  <Translate contentKey="dashboardApp.event.company">Company</Translate>
                </Label>
                <AvInput id="event-company" type="select" className="form-control" name="company.id">
                  <option value="" key="0" />
                  {companies
                    ? companies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/event" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  companies: storeState.company.entities,
  eventEntity: storeState.event.entity,
  loading: storeState.event.loading,
  updating: storeState.event.updating,
  updateSuccess: storeState.event.updateSuccess,
});

const mapDispatchToProps = {
  getCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventUpdate);
