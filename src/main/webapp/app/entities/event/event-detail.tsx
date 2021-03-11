import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EventDetail = (props: IEventDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { eventEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.event.detail.title">Event</Translate> [<b>{eventEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="eventId">
              <Translate contentKey="dashboardApp.event.eventId">Event Id</Translate>
            </span>
          </dt>
          <dd>{eventEntity.eventId}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="dashboardApp.event.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{eventEntity.companyId}</dd>
          <dt>
            <span id="eventName">
              <Translate contentKey="dashboardApp.event.eventName">Event Name</Translate>
            </span>
          </dt>
          <dd>{eventEntity.eventName}</dd>
          <dt>
            <span id="eventStartDate">
              <Translate contentKey="dashboardApp.event.eventStartDate">Event Start Date</Translate>
            </span>
          </dt>
          <dd>
            {eventEntity.eventStartDate ? (
              <TextFormat value={eventEntity.eventStartDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eventEndDate">
              <Translate contentKey="dashboardApp.event.eventEndDate">Event End Date</Translate>
            </span>
          </dt>
          <dd>
            {eventEntity.eventEndDate ? <TextFormat value={eventEntity.eventEndDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="eventStartTime">
              <Translate contentKey="dashboardApp.event.eventStartTime">Event Start Time</Translate>
            </span>
          </dt>
          <dd>
            {eventEntity.eventStartTime ? (
              <TextFormat value={eventEntity.eventStartTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eventEndTime">
              <Translate contentKey="dashboardApp.event.eventEndTime">Event End Time</Translate>
            </span>
          </dt>
          <dd>
            {eventEntity.eventEndTime ? <TextFormat value={eventEntity.eventEndTime} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="dashboardApp.event.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{eventEntity.eventType}</dd>
          <dt>
            <Translate contentKey="dashboardApp.event.company">Company</Translate>
          </dt>
          <dd>{eventEntity.company ? eventEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/event" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/event/${eventEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ event }: IRootState) => ({
  eventEntity: event.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventDetail);
